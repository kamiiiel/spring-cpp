package com.googlecode.wmlynar.springcpp.domain;

import java.util.ArrayList;

/**
 * Stores definition of bean.
 * 
 * @author Wojciech Mlynarczyk (wmlynar@gmail.com)
 * 
 */
public class Bean {

    private String id;
    private String clazz;
    private ArrayList<Property> properties = new ArrayList<Property>();
    private ArrayList<ConstructorArg> constructorArgs = new ArrayList<ConstructorArg>();
    private ArrayList<Extension> extensions = new ArrayList<Extension>();

    private boolean useSetter = true;
    private boolean managed = true;

    public String getGetterName() {
        final String clazz1 = id.substring(0, 1).toUpperCase()
                + id.substring(1);
        return "get" + clazz1;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setClazz(final String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }

    public void setProperties(final ArrayList<Property> properties) {
        this.properties = properties;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setConstructorArgs(
            final ArrayList<ConstructorArg> constructorArgs) {
        this.constructorArgs = constructorArgs;
    }

    public ArrayList<ConstructorArg> getConstructorArgs() {
        return constructorArgs;
    }

    public void setExtensions(final ArrayList<Extension> extensions) {
        this.extensions = extensions;
    }

    public ArrayList<Extension> getExtensions() {
        return extensions;
    }

    public boolean isUseSetter() {
        return useSetter;
    }

    public void setUseSetter(final boolean useSetter) {
        this.useSetter = useSetter;
    }

    public void setManaged(final boolean managed) {
        this.managed = managed;
    }

    public boolean isManaged() {
        return managed;
    }
}
