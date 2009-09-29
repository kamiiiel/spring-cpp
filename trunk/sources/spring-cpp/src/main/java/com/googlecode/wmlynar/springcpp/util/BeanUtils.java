/**
 * 
 */
package com.googlecode.wmlynar.springcpp.util;

/**
 * @author Wojciech Mlynarczyk (wmlynar@gmail.com)
 * 
 */
public class BeanUtils {

    /**
     * Returns the name of the bean getter in the BeanFactory class given its
     * name.
     * 
     * @param id
     *            Id of the bean as defined in the context file.
     * @return The name of the bean getter name in the BeanFactory class.
     */
    public static String getBeanGetterName(String id) {
        if (id == null) {
            throw new NullPointerException("Bean id cannot be null");
        }
        id = id.trim();
        if ("".equals(id)) {
            throw new IllegalArgumentException("Bean id cannot be empty");
        }
        final String id1 = id.substring(0, 1).toUpperCase() + id.substring(1);
        return "get" + id1;
    }

    /**
     * Returns the name of the property setter in the bean.
     * 
     * @param name
     *            Name of the property.
     * @return The name of the property getter.
     */
    public static String getPropertySetterName(String name) {
        if (name == null) {
            throw new NullPointerException("Property name cannot be null");
        }
        name = name.trim();
        if ("".equals(name)) {
            throw new IllegalArgumentException("Property name cannot be empty");
        }
        final String name1 = name.substring(0, 1).toUpperCase()
                + name.substring(1);
        return "set" + name1;
    }

}
