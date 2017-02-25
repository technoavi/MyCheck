package com.syntaxtree.agproengg.configuration;

import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.syntaxtree.agproengg.controller.RestFileStoreMongoDBExample;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.syntaxtree.agproengg")
public class SpringConfiguration extends Application {
	
	 @Override
	    public Set<Class<?>> getClasses() {
	        Set<Class<?>> resources = new java.util.HashSet<>();
	        resources.add(RestFileStoreMongoDBExample.class);
	        resources.add(MultiPartFeature.class);
	        return resources;
	    }
}
/*@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(UploadFileService.class);
        resources.add(MultiPartFeature.class);
        return resources;
    }
}*/