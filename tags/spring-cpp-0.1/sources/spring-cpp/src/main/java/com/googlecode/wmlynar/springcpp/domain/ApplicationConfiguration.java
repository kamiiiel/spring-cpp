package com.googlecode.wmlynar.springcpp.domain;

public class ApplicationConfiguration {

    private String classNameWithNamespace;
    private String contextFileName;
    private String destinationDirectoryName;

    private String newOperator;
    private String includePrefix;

    public String getClassNameWithNamespace() {
        return classNameWithNamespace;
    }

    public void setClassNameWithNamespace(final String classNameWithNamespace) {
        this.classNameWithNamespace = classNameWithNamespace;
    }

    public String getContextFileName() {
        return contextFileName;
    }

    public void setContextFileName(final String contextFile) {
        this.contextFileName = contextFile;
    }

    public String getDestinationDirectoryName() {
        return destinationDirectoryName;
    }

    public void setDestinationDirectoryName(final String destinationDirectory) {
        this.destinationDirectoryName = destinationDirectory;
    }

}
