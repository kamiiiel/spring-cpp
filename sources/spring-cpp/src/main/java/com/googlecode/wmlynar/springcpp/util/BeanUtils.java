/**
 * 
 */
package com.googlecode.wmlynar.springcpp.util;

/**
 * @author Wojciech Mlynarczyk (wmlynar@gmail.com)
 * 
 */
public class BeanUtils {

    public static String getBeanGetterName(final String id) {
        final String camel = id.substring(0, 1).toUpperCase() + id.substring(1);
        return "get" + camel;
    }

}
