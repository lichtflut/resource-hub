/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;

/**
 * <p>
 * A Builder for {@link Constraint}s.
 * <p>
 * Created: May 2, 2012
 * 
 * @author Nils Bleisch
 * @author Ravi Knox
 */
//TODO: Builder or factory?
public final class ConstraintBuilder {

	/**
	 * Builds a Pattern-Constraint.
	 * @param literal - if null, a blank "" will be chosen instead
	 * @return {@link Constraint}
	 */
	public static Constraint buildLiteralConstraint(String literal) {
		if (literal == null) {
			literal = "";
		}
		return buildConstraint(null, null, literal, null, false, null);
	}

	/**
	 * Builds a Pattern-Constraint that can be referenced by any {@link PropertyDeclaration}s.
	 * @param id - unique identifier for this Constraint.
	 * @param name - A human readable name to reference this Constraint by
	 * @param literal - if null, a blank "" will be chosen instead
	 * @param list - A {@link List} op {@link Datatype}s where this {@link Constraint} will be applicable.
	 * @return {@link Constraint}
	 */
	public static Constraint buildPublicLiteralConstraint(ResourceID id, String name, String literal, List<Datatype> list) {
		if (literal == null) {
			literal = "";
		}
		if(list == null || list.isEmpty()){
			list.add(Datatype.STRING);
		}
		return buildConstraint(id, name, literal, null, true, list);
	}
	
	/**
	 * Builds a Pattern-Constraint that can be referenced by any {@link PropertyDeclaration}s.
	 * @param id - unique identifier for this Constraint.
	 * @param name - A human readable name to reference this Constraint by
	 * @param literal - if null, a blank "" will be chosen instead
	 * @param list - A {@link List} op {@link Datatype}s where this {@link Constraint} will be applicable.
	 * @return {@link Constraint}
	 */
	public static Constraint buildPublicLiteralConstraint(final ResourceID id,final String name, String literal,final Datatype datatype) {
		if (literal == null) {
			literal = "";
		}
		return buildConstraint(id, name, literal,null, true, new ArrayList<Datatype>(){{add(datatype);}});
	}

	/**
	 * Builds a Resource-Constraint.
	 * @param resource - {@link ResourceID}
	 * @return {@link Constraint}
	 */
	public static Constraint buildResourceConstraint(final ResourceID resource) {
		return buildConstraint(null, null, null, resource, false, null);
	}

	/**
	 * Builds a Resource-Constraint that can be referenced by any {@link PropertyDeclaration}s.
	 * @param id - unique identifier for this Constraint.
	 * @param name - A human readable name to reference this Constraint by
	 * @param resource -
	 * @return {@link Constraint}
	 */
	public static Constraint buildPublicResourceConstraint(ResourceID id, String name, final ResourceID resource) {
		return buildConstraint(id, name, null, resource, true, new ArrayList<Datatype>(){{add(Datatype.RESOURCE);}});
	}

	/**
	 * @return an empty Constraint for initialization purpose
	 */
	public static Constraint emptyConstraint() {
		return buildConstraint(null, null, null, null, false, null);
	}

//	// ------------------------------------------------------
//
//	public static class Builder{
//		
//		private boolean isPublic;
//		private String name;
//		private ResourceID id;
//		private ResourceID resource;
//		private String pattern;
//		private List<Datatype> datatypes = new ArrayList<Datatype>();
//		
//		public Builder(boolean isPublic){
//			this.isPublic = isPublic;
//		}
//		
//		public Builder setName(String name){
//			this.name = name;
//			return this;
//		}
//		
//		public Builder setId(ResourceID id){
//			this.id = id;
//			return this;
//		}
//		
//		public Builder setPattern(String pattern){
//			this.pattern = pattern;
//			return this;
//		}
//		
//		public Builder setResourceReference(ResourceID resource){
//			this.resource = resource;
//			return this;
//		}
//		
//		public Builder setApplicableDatatype(Datatype datatype){
//			if(!datatypes.contains(datatype)){
//				datatypes.add(datatype);
//			}
//			return this;
//		}
//		
//		public Builder setApplicableDatatype(List<Datatype> list){
//			for (Datatype dt : list) {
//				if(!datatypes.contains(dt)){
//					datatypes.add(dt);
//				}
//			}
//			return this;
//		}
//		
//		public Constraint build(){
//			return buildConstraint(id, name, pattern, resource, isPublic, datatypes);
//		}
//	}
//	
	// ------------------------------------------------------
	
	private static Constraint buildConstraint(final ResourceID id, final String name, final String pattern,
			final ResourceID resource, final boolean isPublic, final List<Datatype> list) {
		return new Constraint() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public ResourceID getID() {
				return id;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String getName() {
				if (!isPublicConstraint()) {
					return getDisplayName();
				}
				return name;
			}

			private String getDisplayName() {
				if (isResourceReference()) {
					return resource.toURI();
				}
				return pattern;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean isResourceReference() {
				if (resource != null) {
					return true;
				}
				return false;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String getLiteralConstraint() {
				return pattern;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public ResourceID getResourceTypeConstraint() {
				return resource;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean isPublicConstraint() {
				return isPublic;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public List<Datatype> getApplicableDatatypes() {
				return list;
			}
			
			/**
			 * {@inheritDoc}
			 */
			public String toString(){
				if (!isResourceReference()) {
					return "literal-constraint(" + getLiteralConstraint() + ")"; 
				} else {
					return "resource-constraint(" + getResourceTypeConstraint().getQualifiedName() + ")";
				}
			}
		};
	}
}
