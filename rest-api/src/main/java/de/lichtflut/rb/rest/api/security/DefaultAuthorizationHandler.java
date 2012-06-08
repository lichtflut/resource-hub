/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.security;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.security.RBUser;

/**
 * <p>
 * TODO: To document
 * </p>
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created May 9, 2012
 */
public class DefaultAuthorizationHandler implements AuthorizationHandler, OperationTypes{

	private static final String ROOT_USER = "root";
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private HashMap<OperationTypes.TYPE, AuthConstraint> opRequirementsMapping = new HashMap<OperationTypes.TYPE, AuthConstraint>();

	{
		AuthConstraint c = new AuthConstraint(true, Collections.<String> emptyList());
		for(TYPE type : TYPE.values()){
			opRequirementsMapping.put(type, c);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.rest.api.security.AuthorizationHandler#isAuthorized(org.arastreju.sge.security.User, java.lang.String)
	 */
	@Override
	public boolean isAuthorized(RBUser user, String domainID){
		if(user==null || domainID == null){
			return false;
		}
		TYPE opType = getRequestedOperation();
		if(opType==null){
			return false;
		}
		return isAuthorized(user, domainID, opType);
	}
		
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.rest.api.security.AuthorizationHandler#isAuthorized(org.arastreju.sge.security.User, java.lang.String, de.lichtflut.rb.rest.api.security.OperationTypes.TYPE)
	 */
	@Override
	public boolean isAuthorized(RBUser user, String domainID, TYPE type)
			throws MissingOperationTypeException {
		if(user==null || domainID == null){
			return false;
		}
		//First check if the user has to be in the requested domain
		if(user.getUsername().toLowerCase().equals(ROOT_USER)){
			return true;
		}
		if(isDomainRequired(type)){
			if(user.getDomesticDomain()==null || (!user.getDomesticDomain().equals(domainID))){
				return false;
			}
		}
		
		Collection<String> permissions = getRequiredPermissions(type);
		for(@SuppressWarnings("unused") String permission : permissions){
			//TODO: to implement
			break;
		}
		return true;
	}
	
	/**
	 * @param type
	 */
	private Collection<String> getRequiredPermissions(TYPE type) {
		AuthConstraint c = opRequirementsMapping.get(type);
		if(c==null){
			return Collections.emptyList();
		}
		return c.getPermissions();
	}

	/**
	 * @param type
	 * @return
	 */
	private boolean isDomainRequired(TYPE type) {
		AuthConstraint c  = opRequirementsMapping.get(type);
		if(c==null){
			return false;
		}
		return c.isDomainRequired;
	}

	private TYPE getRequestedOperation(){
		CallerRep rep = getCurrentCallerRep();
		//If there is no CallerRep, no operation can be found
		if(rep==null){
			return null;
		}
		//Check the op annotation of the caller method
		for(Method method : rep.getClazz().getMethods()){
			if(method.getName().equals(rep.getMethodname())){
				RBOperation anno = method.getAnnotation(RBOperation.class);
				if(anno==null){
					continue;
				}else{
					//There has to be just one method which this name and operationType
					return anno.type();
				}
			}
		}
		throw new MissingOperationTypeException("An annotated operation type could not be found." +
				"Please make sure that the calling method is annotated with " + RBOperation.class.getSimpleName());
	}
	
	
	private CallerRep getCurrentCallerRep() {
		CallerRep rep;
		try {
			rep = new CallerRep();
			StackTraceElement elem = new Throwable().getStackTrace()[3];
			rep.setClazz(Class.forName(elem.getClassName()));
			rep.setMethodname(elem.getMethodName());
		} catch (Exception any) {
			log.debug(
					"A CallerReprsentation couldnt be created. See the following exception",
					any);
			return null;
		}
		return rep;
	}

	static class CallerRep {
		Class<?> clazz;
		String methodname;

		public Class<?> getClazz() {
			return clazz;
		}

		public String getMethodname() {
			return methodname;
		}

		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}

		public void setMethodname(String methodname) {
			this.methodname = methodname;
		}
	}

	
	class AuthConstraint{
		boolean isDomainRequired;
		Collection<String> permissions;
		
		public AuthConstraint(boolean isDomainRequired, Collection<String> permissions){
			this.isDomainRequired = isDomainRequired;
			this.permissions = permissions;
		}
		
		public boolean isDomainRequired() {
			return isDomainRequired;
		}
		public Collection<String> getPermissions() {
			return permissions;
		}
		public void setDomainRequired(boolean isDomainRequired) {
			this.isDomainRequired = isDomainRequired;
		}
		public void setRoles(Collection<String> permissions) {
			this.permissions = permissions;
		}
	}


}
