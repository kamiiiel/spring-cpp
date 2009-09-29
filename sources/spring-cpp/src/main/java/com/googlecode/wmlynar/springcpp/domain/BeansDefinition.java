package com.googlecode.wmlynar.springcpp.domain;

import java.util.ArrayList;

/**
 * Stores definitions of all beans and some default attributes.
 * 
 * @author Wojciech Mlynarczyk (wmlynar@gmail.com)
 * 
 */
public class BeansDefinition {

    private ArrayList<Bean> beanList = new ArrayList<Bean>();
    private ArrayList<String> includeList = new ArrayList<String>();

    private boolean defaultUseSetter = true;
    private boolean defaultManaged = true;
    private String newOperator = "new";
    private String deleteOperator = "delete";
    private String defaultIncludeExtension = null;
    private boolean defaultLazyInit = true;

    public void setBeanList(final ArrayList<Bean> beanList) {
        this.beanList = beanList;
    }

    public ArrayList<Bean> getBeanList() {
        return beanList;
    }

    public void setIncludeList(final ArrayList<String> includeList) {
        this.includeList = includeList;
    }

    public ArrayList<String> getIncludeList() {
        return includeList;
    }

    public Bean findBeanById(final String ref) {
        for (final Bean b : beanList) {
            if (b.getId().equals(ref)) {
                return b;
            }
        }
        return null;
    }

    public void setDefaultUseSetter(final boolean defaultUseSetter) {
        this.defaultUseSetter = defaultUseSetter;
    }

    public boolean isDefaultUseSetter() {
        return defaultUseSetter;
    }

    public boolean isDefaultManaged() {
        return defaultManaged;
    }

    public void setDefaultManaged(final boolean defaultManaged) {
        this.defaultManaged = defaultManaged;
    }

    public String getNewOperator() {
        return newOperator;
    }

    public void setNewOperator(final String newOperator) {
        this.newOperator = newOperator;
    }

    public void setDeleteOperator(final String deleteOperator) {
        this.deleteOperator = deleteOperator;
    }

    public String getDeleteOperator() {
        return deleteOperator;
    }

    public void setDefaultIncludeExtension(final String defaultIncludeExtension) {
        this.defaultIncludeExtension = defaultIncludeExtension;
    }

    public String getDefaultIncludeExtension() {
        return defaultIncludeExtension;
    }

    public void setDefaultLazyInit(boolean defaultLazyInit) {
        this.defaultLazyInit = defaultLazyInit;
    }

    public boolean isDefaultLazyInit() {
        return defaultLazyInit;
    }
}
