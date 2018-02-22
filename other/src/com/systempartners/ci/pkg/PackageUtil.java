package com.systempartners.ci.pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;


public class PackageUtil extends Task{
	private File componentlist;
	private File inputFileList;
	private File outputFileList;
	private File packageXML;
	private String version;
	private boolean hasChange = false;
	private Set<Component> compSet = new HashSet<Component>();
	
	private Map<String, List<String>> fileMap = new HashMap<String, List<String>>();
	
	@Override
	public void execute() throws BuildException {
		super.execute();
		
		try {
			generateFileList();
			if(hasChange) {
				getComponents();
				generatePackageXML();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Map<String, Component> initConfig() {
		Map<String, Component> map = new HashMap<String, Component>();
		try {
			JAXBContext ctx = JAXBContext.newInstance(Meta.class);
			Unmarshaller um = ctx.createUnmarshaller();
			Meta meta = (Meta) um.unmarshal(componentlist);
			for(Component component : meta.components) {
				map.put(component.suffix, component);
			}
			this.version = meta.version;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public void generatePackageXML() throws Exception{
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element pack = doc.createElementNS("http://soap.sforce.com/2006/04/metadata", "Package");
		
		for(Component c : compSet) {
			Element types = doc.createElement("types");
			if(c.wildCard) { // includes aura if exist
				Element members = doc.createElement("members");
				Text t = doc.createTextNode("*");
				members.appendChild(t);
				types.appendChild(members);
			}else { 
				for(String fileName : fileMap.get(c.name)) {
					Element members = doc.createElement("members");
					Text t = doc.createTextNode(fileName);
					members.appendChild(t);
					types.appendChild(members);
				}
			}
			
			Element name = doc.createElement("name");
			Text nameText = doc.createTextNode(c.name);
			name.appendChild(nameText);
			types.appendChild(name);
			
			pack.appendChild(types);
		}
		Element version = doc.createElement("version");
		Text versionText = doc.createTextNode(this.version);
		version.appendChild(versionText);
		pack.appendChild(version);
		doc.appendChild(pack);
		output(doc, packageXML);
	}
	
	private void output(Document doc, File packageFile) throws Exception {
		doc.setXmlStandalone(true);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new FileWriter(packageFile));
		transformer.transform(source, result);
		
	}
/**
 * gerenate a map component type name ==> list of component name(without file extension)
 * @throws Exception
 */
	public void getComponents() throws Exception{
		compSet.clear();
		Map<String, Component> map = initConfig();
		BufferedReader br = new BufferedReader(new FileReader(outputFileList));
		String temp = null;
		while((temp = br.readLine()) != null) {
			if(temp.indexOf(".") != -1) { // non-aura components
				// modified file extension 
				temp = temp.replaceAll("\\*", "");
				String suffix = temp.substring(temp.lastIndexOf(".")+1);
				temp = temp.replace('\\', '/');
				
				Component c = map.get(suffix);
				
				if(c != null) {
					this.compSet.add(c);
					int start = -1;
					if(temp.lastIndexOf("/") == -1) {
						continue;
					}
					String fileName = null;
					if(c.hasFolder) {
						String[] temps = temp.split("/");
						fileName = temps[temps.length-2] + "/" + temps[temps.length -1];
					}else {
						start = temp.lastIndexOf("/")+1;
						int end = temp.lastIndexOf(".");
						fileName = temp.substring(start, end);
					}
					
					// c.name is file type name. eg. ApexClass
					if(this.fileMap.get(c.name) == null) {
						List<String> list = new ArrayList<String>();
						list.add(fileName);
						this.fileMap.put(c.name, list);
					}else {
						this.fileMap.get(c.name).add(fileName);
					}
				}
			}else { // aura components, as all the aura componenent are replace by foldername/*
				
			}
		}
		br.close();
	}
	
	/***
	 * Create a file list (outputFileList) file to include all the file has been modified.
	 * @throws Exception
	 */
	public void generateFileList() throws Exception{
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFileList));
			Set<String> set = new TreeSet<String>();
			String temp = null;
			while((temp = br.readLine()) != null){
				if(temp.startsWith("A") || temp.startsWith("M")) {
					String[] ss = temp.split("\\s");
					if(ss.length > 1) {
						temp = ss[1];
					}else {
						continue;
					}
				}else if(temp.startsWith("R")) {
					String[] ss = temp.split("\\s");
					if(ss.length > 2) {
						temp = ss[2];
					}else {
						continue;
					}
				}else {
					continue;
				}
				
				File tempFile = new File(temp);
				if(!tempFile.exists()) {
					continue;
				}
				
				if(temp.indexOf("/aura/") != -1){ // lightning components
					int index = temp.lastIndexOf('/');
					temp = temp.substring(0, index);
					set.add(temp+"/*");
				}else{
					if(temp.trim().endsWith("-meta.xml")){
						int index = temp.lastIndexOf("-");
						temp = temp.substring(0, index);
					}
					set.add(temp+"*");
				}
					
				
			}
			br.close();
			
			if(set.size() == 0){
				log("no file changed");
				this.hasChange = false;
				return;
			}else {
				this.hasChange = true;
			}
			PrintWriter pw = new PrintWriter(new FileWriter(outputFileList));
			for(String s : set){
				pw.println(s);
			}
			pw.close();
			log(this.outputFileList.getName()+" has been generated successfully. "+ set.size() + " components in total");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public File getComponentlist() {
		return componentlist;
	}

	public void setComponentlist(File componentlist) {
		this.componentlist = componentlist;
	}

	public File getInputFileList() {
		return inputFileList;
	}

	public void setInputFileList(File inputFileList) {
		this.inputFileList = inputFileList;
	}

	public File getOutputFileList() {
		return outputFileList;
	}

	public void setOutputFileList(File outputFileList) {
		this.outputFileList = outputFileList;
	}

	public File getPackageXML() {
		return packageXML;
	}

	public void setPackageXML(File packageXML) {
		this.packageXML = packageXML;
	}

}
