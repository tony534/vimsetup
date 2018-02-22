package com.systempartners.ci.pkg;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Component{
	@XmlElement
	public String name;
	@XmlElement
	public boolean wildCard;
	@XmlElement
	public String suffix;
	
	@XmlElement
	public boolean hasFolder;
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		Component that = (Component) obj;
		return this.name.equals(that.name);
	}
	
}
