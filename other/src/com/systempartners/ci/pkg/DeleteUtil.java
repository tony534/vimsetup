package com.systempartners.ci.pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class DeleteUtil extends Task {
	/*
	private File statusFile;
	private File componentlist;
	private String version;
	private Set<Component> compSet = new HashSet<Component>();
	
	@Override
	public void execute() throws BuildException {
		super.execute();
		initConfig();
		
	}
	
	public void buildPackXml() throws Exception {
		Set<String> set = getDeletedFileNames();
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
	
	public Map<String, Set<String>> getDeletedFileNames() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(statusFile));
		String temp = null;
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		String[] namePair = null;
		while((temp = br.readLine()) != null) {
			if(temp.startsWith("D")) {
				namePair = getFileName(temp);
				if(namePair != null) {
					setNamePair(map, namePair[0], namePair[1]);
				}
			}else if(temp.startsWith("R")){
				String[] ss = temp.split("\\s");
				if(ss.length >= 2) {
					namePair = getFileName(ss[1]);
					if(namePair != null) {
						setNamePair(map, namePair[0], namePair[1]);
					}
				}
			}
		}
		br.close();
		return map;
	}
	
	public void setNamePair(Map<String, Set<String>> map, String filename, String fileExtension) {
		if(map.get(fileExtension) != null) {
			map.get(fileExtension).add(filename);
		}else {
			Set<String> set = new HashSet<String>();
			set.add(filename);
			map.put(fileExtension, set);
		}
	}

	public String[] getFileName(String filepath) {
		if(filepath.endsWith("-meta.xml")) {
			return null;
		}
		String name = null;
		if(filepath.indexOf("/") != -1) {
			name = filepath.substring(filepath.lastIndexOf("/")+1);
		}else if(filepath.indexOf('\\') != -1) {
			name = filepath.substring(filepath.lastIndexOf("\\")+1);
		}
		String[] ss = null;
		if(name != null) {
			ss = name.split("\\.");
		}
		return ss;
	}
	
	public File getStatusFile() {
		return statusFile;
	}

	public void setStatusFile(File statusFile) {
		this.statusFile = statusFile;
	}*/
}
