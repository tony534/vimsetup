package com.systempartners.ci.pkg;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="metadata")
public class Meta{
	@XmlElement
	public String version;
	@XmlElementWrapper(name="components")
	@XmlElement(name="component")
	public List<Component> components;
}
