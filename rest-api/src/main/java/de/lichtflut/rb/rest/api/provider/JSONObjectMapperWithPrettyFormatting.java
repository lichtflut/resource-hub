/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        objectMapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
        objectMapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
	}

	@Override
	public ObjectMapper getContext(Class<?> objectType) {
		return objectMapper;
	}
}
