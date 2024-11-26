package org.web_4th_lab.web_4th_lab.rest;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(AuthorizationController.class);
        return classes;
    }
}