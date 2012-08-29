/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.common.EntityLabelBuilder;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 *  Common implementation of {@link ResourceSchema}
 *  Please note:
 *  This class is flagged as final.
 *  That means: If you're willing to remove it to get ability to inherit or sth. like that, please be absolutely sure,
 *  that you know everything about this class when you want to override some members e.g. (See the test cases)
 * </p>
 *
 * <p>
 * 	Created Mar 01, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings({ "serial" })
public final class ResourceSchemaImpl implements ResourceSchema {

	private static Logger LOGGER = LoggerFactory.getLogger(ResourceSchemaImpl.class);

	// ------------------------------------------------------

	private final Set<ResourceID> propertyDescriptors = new HashSet<ResourceID>();

	private final List<PropertyDeclaration> declarations = new LinkedList<PropertyDeclaration>();

	private final List<ResourceID> quickInfos = new LinkedList<ResourceID>();

	private EntityLabelBuilder labelBuilder = EntityLabelBuilder.DEFAULT;

	private ResourceID describedType;

	// -----------------------------------------------------

	/**
	 * <p>
	 * This is the default constructor.
	 * </p>
	 */
	public ResourceSchemaImpl() {
	}

	/**
	 * Constructor.
	 * @param @param describedType The Resource Type defined by this schema
	 */
	public ResourceSchemaImpl(final ResourceID describedType) {
		this.describedType = new SimpleResourceID(describedType.getQualifiedName());
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getDescribedType() {
		return this.describedType;
	}

	/**
	 * <p>
	 * Set the Resource Type defined by this schema.
	 * </p>
	 * @param describedType the type
	 * @return This.
	 */
	public ResourceSchemaImpl setDescribedType(final ResourceID describedType) {
		this.describedType = describedType;
		return this;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PropertyDeclaration> getQuickInfo() {
		List<PropertyDeclaration> list = new ArrayList<PropertyDeclaration>();
		for (ResourceID id : quickInfos) {
			if(propertyDescriptors.contains(id)){
				PropertyDeclaration declaration = getPropertyDeclarationFor(id);
				if(null != declaration){
					list.add(declaration);
				}else{
					LOGGER.debug("No PropertyDeclaration found for: {} while getting quickInfo", id.toURI());
				}
			}
		}
		return list;
	}

	/**
	 * Adds a new reference that will be displayed as a quickInfo.
	 * @param resourceID
	 */
	public void addQuickInfo(final ResourceID resourceID){
		this.quickInfos.add(resourceID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PropertyDeclaration> getPropertyDeclarations() {
		return this.declarations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPropertyDeclaration(final PropertyDeclaration decl) {
		if(decl.getPropertyDescriptor() == null){
			throw new IllegalArgumentException("PropertyDescriptor must not be null " + decl.toString());
		}
		if (propertyDescriptors.contains(decl.getPropertyDescriptor())) {
			throw new IllegalArgumentException("Schema has already declaration for " + decl.getPropertyDescriptor());
		}
		declarations.add(decl);
		propertyDescriptors.add(decl.getPropertyDescriptor());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityLabelBuilder getLabelBuilder() {
		return labelBuilder;
	}

	/**
	 * Set the {@link EntityLabelBuilder}.
	 * @param labelBuilder the labelBuilder to set
	 * @return This.
	 */
	public ResourceSchemaImpl setLabelBuilder(final EntityLabelBuilder labelBuilder) {
		this.labelBuilder = labelBuilder;
		return this;
	}

	// -----------------------------------------------------

	/**
	 * @return Returns the toString()-representation of ResourceID followed
	 * by a list of toString()-PropertyDeclaration separated in '\n'
	 */
	@Override
	public String toString(){
		final StringBuilder sb = new StringBuilder();
		sb.append("Resource Schema for Type " + getDescribedType().getQualifiedName() + "\n");
		for (PropertyDeclaration decl : getPropertyDeclarations()) {
			sb.append(" + " + decl.toString() + "\n");
		}
		return sb.toString();
	}

	// ------------------------------------------------------

	/**
	 * Searches for a {@link PropertyDeclaration} within the current schema.
	 * @param id - The {@link ResourceID} of an existing property-declaration
	 * @return a PropertyDeclaration if it exists for the resourceId, returns <code>null</code> if none is found
	 */
	private PropertyDeclaration getPropertyDeclarationFor(final ResourceID id) {
		for (PropertyDeclaration pdec : declarations) {
			if(pdec.getPropertyDescriptor().toURI().equals(id.toURI())){
				return pdec;
			}
		}
		return null;
	}
}
