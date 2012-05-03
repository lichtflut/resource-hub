package de.lichtflut.rb.rest.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class RESTApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(ResourceService.class);
        return classes;
    }
	
	
}
