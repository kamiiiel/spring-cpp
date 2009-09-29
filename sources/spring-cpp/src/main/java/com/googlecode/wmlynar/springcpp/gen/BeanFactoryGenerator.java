package com.googlecode.wmlynar.springcpp.gen;

import com.googlecode.wmlynar.springcpp.domain.Bean;
import com.googlecode.wmlynar.springcpp.domain.BeansDefinition;
import com.googlecode.wmlynar.springcpp.domain.ConstructorArg;
import com.googlecode.wmlynar.springcpp.domain.GeneratedClass;
import com.googlecode.wmlynar.springcpp.domain.Property;
import com.googlecode.wmlynar.springcpp.util.BeanUtils;

/**
 * Generates source code of the bean factory.
 * 
 * @author Wojciech Mlynarczyk (wmlynar@gmail.com)
 * 
 */
public class BeanFactoryGenerator {

    /**
     * C++ namespace of the generated class.
     */
    private String namespace;

    /**
     * Name of the generated class
     */
    private String className;

    /**
     * Definitions of all the beans
     */
    private BeansDefinition definition;

    /**
     * When true application should fail on error, for example bean id is not
     * found in ref="xxx". If false it will just generate wrong code - sometimes
     * it might be easier to track the problem in the generated code.
     */
    private boolean failOnError = true;

    /**
     * Generates c++ source code of the bean factory.
     * 
     * @param definition
     *            Definition of the beans, as read form the context file.
     * @param namespace
     *            C++ namespace of the bean factory class.
     * @param className
     *            Name of the bean factory class.
     * @return
     */
    public GeneratedClass generate(final BeansDefinition definition,
            final String namespace, final String className) {

        // prepare variables
        this.definition = definition;
        this.namespace = namespace;
        this.className = className;
        this.failOnError = definition.isFailOnError();

        // generate stuff
        final String header = generateHeader();
        final String source = generateSource();

        // save result
        final GeneratedClass gc = new GeneratedClass();
        gc.setHeaderContent(header);
        gc.setSourceContent(source);
        gc.setClassName(className);

        return gc;
    }

    /**
     * Generates the source code for the header file (*.h).
     * 
     * @return Generated source code of the header.
     */
    private String generateHeader() {

        final StringBuilder header = new StringBuilder();

        // Compute the guard name for the ifndef
        String guard;
        if (namespace != null) {
            guard = namespace + "_" + className;
        } else {
            guard = className;
        }
        guard = "_" + guard.toUpperCase() + "_H_";

        // add guard
        header.append("#ifndef " + guard + "\n");
        header.append("#define " + guard + "\n");

        // add all includes
        for (final String include : definition.getIncludeList()) {
            header.append("#include \"" + include + "\"\n");
        }

        // add namespace
        if (namespace != null) {
            header.append("namespace " + namespace + " {\n");
        }

        // declare class, constructor and standard methods
        header.append("class " + className + " {\n");
        header.append("public:\n");
        header.append("  " + className + "();\n");
        header.append("  virtual void* getBean(char* name);\n");
        header.append("  virtual void start();\n");
        header.append("  virtual void stop();\n");
        header.append("  virtual ~" + className + "();\n");

        // for all beans generate accessors
        for (final Bean bean : definition.getBeanList()) {
            header.append("  ");
            header.append(bean.getClazz());
            header.append("* ");
            header.append(BeanUtils.getBeanGetterName(bean.getId()));
            header.append("();\n");
        }

        header.append("protected:\n");

        // for all beans generate attributes

        for (final Bean bean : definition.getBeanList()) {
            header.append("  ");
            header.append(bean.getClazz());
            header.append("* ");
            header.append(bean.getId());
            header.append(";\n");
        }

        // close declaration of the class and namespace
        header.append("};\n");
        if (namespace != null) {
            header.append("};\n");
        }

        // close the guarding ifndef
        header.append("#endif // ");
        header.append(guard);
        header.append("\n\n");

        return header.toString();
    }

    /**
     * Generates the source file *.cpp of the bean factory.
     * 
     * @return Generated source of the bean factory.
     */
    private String generateSource() {

        final StringBuilder source = new StringBuilder();

        // add includes
        source.append("#include <string.h>\n");
        source.append("#include \"" + className + ".h\"\n\n");

        // implementation of constructor
        source.append(generateConstructor());

        // implementation of getbean
        source.append(generateGetBean());

        // not yet implemented
        source.append(generateStartStop());

        // deletes beans depending on managed properties
        source.append(generateDestructor());

        // generate getters
        for (final Bean bean : definition.getBeanList()) {
            source.append(generateGetter(bean));
        }

        return source.toString();
    }

    /**
     * Generates body of constructor.
     * 
     * @return String containing body of constructor.
     */
    private String generateConstructor() {
        final StringBuilder source = new StringBuilder();

        source.append(className + "::" + className + "()\n{\n");

        // initialize pointers to all beans
        for (final Bean bean : definition.getBeanList()) {
            source.append("  " + bean.getId() + " = 0;\n");
        }
        source.append("}\n\n");

        return source.toString();
    }

    /**
     * Generates body of the getBean function.
     * 
     * @return String containing body of the getBean function.
     */
    private String generateGetBean() {
        final StringBuilder source = new StringBuilder();

        source.append("void *" + className + "::getBean(char *name)\n{\n");
        // for all beans generate accessors
        for (final Bean bean : definition.getBeanList()) {
            source.append("  if(strcmp(\"" + bean.getId() + "\",name)) return "
                    + BeanUtils.getBeanGetterName(bean.getId()) + "();\n");
        }
        source.append("  return 0;\n");
        source.append("}\n\n");

        return source.toString();
    }

    /**
     * Generates body of start function, stop function and destructor. Not
     * implemented yet.
     * 
     * @return String containing body of start,stop and destructor.
     */
    private String generateStartStop() {
        final StringBuilder source = new StringBuilder();

        // start method
        source.append("void " + className + "::start()\n{\n");
        for (final Bean bean : definition.getBeanList()) {
            if (bean.isLazyInit()) {
                source.append("  // ignored starting " + bean.getId() + "\n");
            } else {
                source.append("  " + BeanUtils.getBeanGetterName(bean.getId())
                        + "();\n");
            }
        }
        source.append("}\n\n");

        // stop method
        source.append("void " + className + "::stop()\n{\n");
        for (final Bean bean : definition.getBeanList()) {
            if (bean.getDestoryMethod() == null) {
                source.append("  // ignored stopping " + bean.getId() + "\n");
            } else {
                source.append("  if(" + bean.getId() + ") ");
                source.append(bean.getId());
                source.append("->" + bean.getDestoryMethod() + "();\n");
            }
        }
        source.append("}\n\n");

        return source.toString();
    }

    /**
     * Generates destructor method deleting beans depending on managed property.
     * 
     * @return
     */
    private String generateDestructor() {
        final StringBuilder source = new StringBuilder();

        source.append("" + className + "::~" + className + "()\n{\n");
        for (final Bean bean : definition.getBeanList()) {
            if (bean.isManaged()) {
                source.append("  if(" + bean.getId() + ") ");
                if (bean.getDeleteMethod() != null) {
                    source.append(" " + bean.getId());
                    source.append("->" + bean.getDeleteMethod() + "();\n");
                } else {
                    source.append(definition.getDeleteOperator());
                    source.append(" " + bean.getId() + ";\n");
                }
            } else {
                source.append("  // ignored deleting " + bean.getId() + "\n");
            }
        }
        source.append("}\n\n");

        return source.toString();
    }

    /**
     * Generates a getXXX function for given bean. The name of the function
     * depends on id of the bean.
     * 
     * @param bean
     *            Bean for which to generate the getter code.
     * 
     * @return Source code of the getter function.
     */
    private String generateGetter(final Bean bean) {
        final StringBuilder source = new StringBuilder();

        source.append(bean.getClazz() + "* " + className + "::"
                + BeanUtils.getBeanGetterName(bean.getId()) + "()\n{\n");
        source.append("  if(!" + bean.getId() + ") {\n");
        source.append("    " + bean.getId() + " = ");
        source.append(generateConstructor(bean));
        source.append(";\n");
        for (final Property p : bean.getProperties()) {
            generateSetProperty(source, bean, p);
        }
        if (bean.getInitMethod() != null) {
            source.append("    " + bean.getId() + "->" + bean.getInitMethod()
                    + "();\n");
        }
        source.append("  }\n");
        source.append("  return " + bean.getId() + ";\n");
        source.append("}\n\n");

        return source.toString();
    }

    /**
     * Generates constructor for given bean. The parameters of the constructor
     * are generated using bean definition. Used from getXXX function.
     * 
     * @param bean
     *            Bean for which to generate the constructor.
     * 
     * @return Source code of the constructor.
     */
    private String generateConstructor(final Bean bean) {
        final StringBuilder source = new StringBuilder();

        if (bean.getFactoryMethod() == null) {
            source.append(definition.getNewOperator());
            source.append(" " + bean.getClazz() + "(");
        } else {
            if (bean.getFactoryBean() != null) {
                if (failOnError
                        && definition.findBeanById(bean.getFactoryBean()) == null) {
                    throw new RuntimeException("Cannot find factory bean "
                            + bean.getFactoryBean());
                }
                source.append(BeanUtils
                        .getBeanGetterName(bean.getFactoryBean()));
                source.append("()->");
            } else {
                source.append(bean.getClazz() + "::");
            }
            source.append(bean.getFactoryMethod() + "(");
        }

        final int len = bean.getConstructorArgs().size();
        for (int i = 0; i < len; i++) {
            final ConstructorArg arg = bean.getConstructorArgs().get(i);
            if (arg.getRef() != null) {
                if (failOnError
                        && definition.findBeanById(arg.getRef()) == null) {
                    throw new RuntimeException(
                            "Cannot find bean in constructor-arg ref="
                                    + arg.getRef());
                }
                source.append(BeanUtils.getBeanGetterName(arg.getRef()));
                source.append("()");
            } else if (arg.isText()) {
                source.append("\"");
                source.append(arg.getValue());
                source.append("\"");
            } else {
                source.append(arg.getValue());
            }
            if (i != len - 1) {
                source.append(",");
            }
        }
        source.append(")");
        return source.toString();
    }

    /**
     * Generates the set property code of the property of the given bean based
     * on beans definition. Used from getXXX function.
     * 
     * @param source
     *            String builder to which to append generated code.
     * @param bean
     *            Bean for which to generate the setter.
     * @param prop
     *            Property for which to generate the setter.
     */
    private void generateSetProperty(final StringBuilder source,
            final Bean bean, final Property prop) {
        final StringBuilder value = new StringBuilder();
        if (prop.isText()) {
            value.append("\"");
            value.append(prop.getValue());
            value.append("\"");
        } else if (prop.getRef() != null) {
            if (failOnError && definition.findBeanById(prop.getRef()) == null) {
                throw new RuntimeException("Cannot find bean property name ="
                        + prop.getName() + " ref=" + prop.getRef());
            }
            value.append(BeanUtils.getBeanGetterName(prop.getRef()));
            value.append("()");
        } else {
            value.append(prop.getValue());
        }
        if (prop.isUseSetter()) {
            source.append("    " + bean.getId() + "->"
                    + BeanUtils.getPropertySetterName(prop.getName()) + "("
                    + value + ");\n");
        } else {
            source.append("    " + bean.getId() + "->" + prop.getName() + " = "
                    + value + ";\n");
        }
    }

}
