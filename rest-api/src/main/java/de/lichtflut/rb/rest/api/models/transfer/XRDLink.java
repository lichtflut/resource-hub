/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.models.transfer;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 16, 2013
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XRDLink {

	public static final String BODY_TEMPLATE_PROPERTY = "http://rb.lichtflut.de/properties/body";
	private String linkRel;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
	private HashMap<String, Object> properties = new HashMap<String, Object>();
	
	private String templateRel;
	
	/**
	 * @return the linkRel
	 */
	public String getLinkRel() {
		return linkRel;
	}

	/**
	 * @return the resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @return the templateRel
	 */
	public String getTemplate() {
		return templateRel;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @return the method
	 */
	public HttpMethod getMethod() {
		return method;
	}

	/**
	 * @return the properties
	 */
	public HashMap<String, Object> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(HashMap<String, Object> properties) {
		this.properties = properties;
	}

	public void setBodyProperty(Object body){
		properties.put(BODY_TEMPLATE_PROPERTY, body);
	}
	
	@JsonIgnore
	public Object getBodyProperty(){
		return properties.get(BODY_TEMPLATE_PROPERTY);
	}
	
	
	/**
	 * @param templateRel the templateRel to set
	 */
	public void setTemplateRel(String templateRel) {
		this.templateRel = templateRel;
	}

	private String resourceType = "Unknown";
	
	private HttpMethod method = HttpMethod.GET;
	
	private String href;
	
	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @param linkRel the linkRel to set
	 */
	public void setLinkRel(String linkRel) {
		this.linkRel = linkRel;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(HttpMethod method) {
		this.method = method;
	}
	
}
