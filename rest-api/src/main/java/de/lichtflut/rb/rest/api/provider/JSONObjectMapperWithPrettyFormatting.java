/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.provider;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.DeserializationConfig;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Please take in mind that formatting does have a negative effect on performance
 * </p>
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 17, 2013
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Component
public class JSONObjectMapperWithPrettyFormatting implements ContextResolver<ObjectMapper> {

	private ObjectMapper objectMapper;
	
	public JSONObjectMapperWithPrettyFormatting() throws Exception {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		
		objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
		
        objectMapper.setVisibility(JsonMethod.GETTER, Visibility.PUBLIC_ONLY);

        // Disabled, because we have to be sure, that only setters are used to set values!
        objectMapper.configure(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);

        // if no date format string is set, then use ISO-8601 format
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
	
	}

	@Override
	public ObjectMapper getContext(Class<?> objectType) {
		return objectMapper;
	}
}
