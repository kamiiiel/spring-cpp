package com.googlecode.wmlynar.springcpp.domain;

/**
 * Definition of bean property assignment.
 * 
 * @author Wojciech Mlynarczyk (wmlynar@gmail.com)
 * 
 */
public class Property {

    private String name;
    private String value;
    private String ref;
    private boolean text;

    private boolean useSetter = true;

    // not needed
    // public String getGetterName() {
    // final String name1 = name.substring(0, 1).toUpperCase()
    // + name.substring(1);
    // boolean isBoolean = false;
    // if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
    // isBoolean = true;
    // }
    // if (ref != null || !isBoolean) {
    // return "get" + name1;
    // } else {
    // return "is" + name1;
    // }
    // }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setRef(final String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public void setText(final boolean text) {
        this.text = text;
    }

    public boolean isText() {
        return text;
    }

    public void setUseSetter(final boolean useSetter) {
        this.useSetter = useSetter;
    }

    public boolean isUseSetter() {
        return useSetter;
    }

}
