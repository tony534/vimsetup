package com.systempartners.ci.flow;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class FlowUtil extends Task{

	private File targetSrc;
	private File sourceSrc;
	
	private Map<String, Set<Integer>> sourceFlowMap = new HashMap<String, Set<Integer>>();
	
	@Override
	public void execute() throws BuildException {
		super.execute();
		try {
			setFlows(sourceSrc.toPath().resolve("flows").toFile()); // source flows setup
			Set<File> fileToBeDeleted = new HashSet<File>();
			Map<String, Integer> targetActiveMap = getActiveMap(targetSrc.toPath().resolve("flowDefinitions").toFile());
			Map<String, Integer> sourceActiveMap = getActiveMap(sourceSrc.toPath().resolve("flowDefinitions").toFile());

			for(String fd : targetActiveMap.keySet()){ // fd: flow definition name
				Integer targetActiveVersion = targetActiveMap.get(fd);
				Set<Integer> flowVersions = this.sourceFlowMap.get(fd);
				if(flowVersions != null) {
					for(Integer versionNum: flowVersions) {
						if(versionNum <= targetActiveVersion) {
							fileToBeDeleted.add(sourceSrc.toPath().resolve("flows/"+fd+"-"+versionNum+".flow").toFile());
						}
					}
					
					Integer sourceActiveVersion = sourceActiveMap.get(fd);
					if(sourceActiveVersion != null && sourceActiveVersion <= targetActiveVersion) {
						File file = sourceSrc.toPath().resolve("flowDefinitions/"+fd+".flowDefinition").toFile();
						file.delete();
					}
				}
				
			}
			
			for(File file : fileToBeDeleted) {
				file.delete();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/***
	 * get active flow map
	 * 
	 * @param flowDefinitionFolder
	 * @return <active flow name, active flow version number>
	 * @throws Exception
	 */
	
	public Map<String, Integer> getActiveMap(File flowDefinitionFolder) throws Exception{
		Map<String, Integer> activeMap = new HashMap<String, Integer>();
		
		for(File file : flowDefinitionFolder.listFiles()) {
			if(file.getName().endsWith(".flowDefinition")) {
				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
				NodeList nl = doc.getElementsByTagName("activeVersionNumber");
				
				if(nl != null && nl.getLength() != 0 ) { // active
					Integer activeVersion = Integer.valueOf(nl.item(0).getFirstChild().getNodeValue());
					String flowFileName = file.getName().replace(".flowDefinition", "");
					
					activeMap.put(flowFileName, activeVersion);
				}
			}
			
		}
		return activeMap;
	}

	/**
	 * utility method to populate the flow set and map.
	 * 
	 * @param flowFolder  the file of flow folder
	 * @param flowSet <flow file names>
	 * @param flowMap <flow name, set of flow version number>
	 */
	public void setFlows(File flowFolder) {
		for(File file : flowFolder.listFiles()) {
			if(file.getName().endsWith(".flow")) {
				String[] ss = file.getName().replace(".flow", "").split("-");
				if(this.sourceFlowMap.get(ss[0]) == null) {
					Set<Integer> set = new HashSet<Integer>();
					set.add(Integer.valueOf(ss[1]));
					this.sourceFlowMap.put(ss[0], set);  // <flow name, flow version number>
				}else {
					this.sourceFlowMap.get(ss[0]).add(Integer.valueOf(ss[1]));
				}
			}
		}
	}
	
	public void cleanFlowDefinitation() {
		File sourceFlowFolder = this.sourceSrc.toPath().resolve("flows").toFile();
		File sourceFDFolder = this.sourceSrc.toPath().resolve("flowDefinitions").toFile();
		Set<String> flowNames = new HashSet<String>();
		// get all the flow names
		for(File file : sourceFlowFolder.listFiles()) {
			if(file.getName().endsWith(".flow")) {
				flowNames.add(file.getName().split("-")[0]);
			}
		}
		
		// delete the flow definitions if the corresponding flows do not exit
		for(File file : sourceFDFolder.listFiles()) {
			if(file.getName().endsWith(".flowDefinition")) {
				String fDName = file.getName().replace(".flowDefinition", "");
				if(!flowNames.contains(fDName)) {
					this.log("Flow Definition "+file.getName() + " is deleted because the corresponding flows were deleted");
					file.delete();
				}
			}
		}
	}

	public File getTargetSrc() {
		return targetSrc;
	}

	public void setTargetSrc(File targetSrc) {
		this.targetSrc = targetSrc;
	}

	public File getSourceSrc() {
		return sourceSrc;
	}

	public void setSourceSrc(File sourceSrc) {
		this.sourceSrc = sourceSrc;
	}

	
}
