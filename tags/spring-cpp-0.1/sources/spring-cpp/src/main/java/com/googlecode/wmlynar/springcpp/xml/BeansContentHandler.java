package com.googlecode.wmlynar.springcpp.xml;

import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.googlecode.wmlynar.springcpp.domain.Bean;
import com.googlecode.wmlynar.springcpp.domain.BeansDefinition;
import com.googlecode.wmlynar.springcpp.domain.ConstructorArg;
import com.googlecode.wmlynar.springcpp.domain.Extension;
import com.googlecode.wmlynar.springcpp.domain.Property;

/**
 * Content handler for the SAX xml reader.
 * 
 * @author Wojciech Mlynarczyk (wmlynar@gmail.com)
 * 
 */
public class BeansContentHandler extends DefaultHandler {

    public enum ITEM_TYPE {
        ITEM_NONE, ITEM_BEANS, ITEM_BEAN, ITEM_PROPERTY, ITEM_CONSTRUCTOR_ARG, ITEM_EXTENSION, ITEM_ADDITIONAL_INCLUDE,
    }

    private ITEM_TYPE itemType = ITEM_TYPE.ITEM_NONE;
    private final Stack<ITEM_TYPE> stack = new Stack<ITEM_TYPE>();

    private BeansDefinition beansDefinition;
    private ArrayList<Bean> beanList;
    private ArrayList<String> includeList;
    private Bean bean;

    @Override
    public void startElement(final String uri, String localName,
            final String qName, final Attributes atts) throws SAXException {
        // quick hack
        localName = qName;
        stack.push(itemType);
        if ("beans".equalsIgnoreCase(localName)) {
            if (itemType != ITEM_TYPE.ITEM_NONE) {
                throw new SAXException("unexpected tag beans");
            }
            itemType = ITEM_TYPE.ITEM_BEANS;
            beansDefinition = new BeansDefinition();
            beanList = beansDefinition.getBeanList();
            includeList = beansDefinition.getIncludeList();
            for (int i = 0; i < atts.getLength(); i++) {
                if ("default-use-setter".equalsIgnoreCase(atts.getLocalName(i))) {
                    beansDefinition.setDefaultUseSetter("true"
                            .equalsIgnoreCase(atts.getValue(i)));
                } else if ("default-managed".equalsIgnoreCase(atts
                        .getLocalName(i))) {
                    beansDefinition.setDefaultManaged("true"
                            .equalsIgnoreCase(atts.getValue(i)));
                } else if ("new-operator"
                        .equalsIgnoreCase(atts.getLocalName(i))) {
                    beansDefinition.setNewOperator(atts.getValue(i));
                } else if ("delete-operator".equalsIgnoreCase(atts
                        .getLocalName(i))) {
                    beansDefinition.setDeleteOperator(atts.getValue(i));
                } else if ("default-include-extension".equalsIgnoreCase(atts
                        .getLocalName(i))) {
                    beansDefinition
                            .setDefaultIncludeExtension(atts.getValue(i));
                } else if ("default-lazy-init".equalsIgnoreCase(atts
                        .getLocalName(i))) {
                    beansDefinition.setDefaultLazyInit("true"
                            .equalsIgnoreCase(atts.getValue(i)));
                } else if ("fail-on-error".equalsIgnoreCase(atts
                        .getLocalName(i))) {
                    beansDefinition.setFailOnError("true".equalsIgnoreCase(atts
                            .getValue(i)));
                }
            }
        } else if ("bean".equalsIgnoreCase(localName)) {
            if (itemType != ITEM_TYPE.ITEM_BEANS) {
                throw new SAXException("unexpected tag bean");
            }
            itemType = ITEM_TYPE.ITEM_BEAN;
            bean = new Bean();
            bean.setUseSetter(beansDefinition.isDefaultUseSetter());
            bean.setManaged(beansDefinition.isDefaultManaged());
            bean.setLazyInit(beansDefinition.isDefaultLazyInit());
            beanList.add(bean);
            boolean skipInclude = false;
            for (int i = 0; i < atts.getLength(); i++) {
                if ("id".equalsIgnoreCase(atts.getLocalName(i))) {
                    bean.setId(atts.getValue(i));
                } else if ("class".equalsIgnoreCase(atts.getLocalName(i))) {
                    bean.setClazz(atts.getValue(i));
                } else if ("include-file"
                        .equalsIgnoreCase(atts.getLocalName(i))) {
                    if (!"".equals(atts.getValue(i).trim())) {
                        includeList.add(atts.getValue(i));
                    }
                    skipInclude = true;
                } else if ("use-setter".equalsIgnoreCase(atts.getLocalName(i))) {
                    final boolean useSetter = "true".equalsIgnoreCase(atts
                            .getValue(i));
                    bean.setUseSetter(useSetter);
                } else if ("managed".equalsIgnoreCase(atts.getLocalName(i))) {
                    bean.setManaged("true".equalsIgnoreCase(atts.getValue(i)));
                } else if ("no-include-file".equalsIgnoreCase(atts
                        .getLocalName(i))) {
                    skipInclude = "true".equalsIgnoreCase(atts.getValue(i));
                } else if ("lazy-init".equalsIgnoreCase(atts.getLocalName(i))) {
                    bean.setLazyInit("true".equalsIgnoreCase(atts.getValue(i)));
                } else if ("init-method".equalsIgnoreCase(atts.getLocalName(i))) {
                    bean.setInitMethod(atts.getValue(i));
                } else if ("destroy-method".equalsIgnoreCase(atts
                        .getLocalName(i))) {
                    bean.setDestoryMethod(atts.getValue(i));
                } else if ("factory-method".equalsIgnoreCase(atts
                        .getLocalName(i))) {
                    bean.setFactoryMethod(atts.getValue(i));
                } else if ("factory-bean"
                        .equalsIgnoreCase(atts.getLocalName(i))) {
                    bean.setFactoryBean(atts.getValue(i));
                } else if ("delete-method".equalsIgnoreCase(atts
                        .getLocalName(i))) {
                    bean.setDeleteMethod(atts.getValue(i));
                } else {
                    throw new SAXException("unknown atribuite "
                            + atts.getLocalName(i));
                }
            }
            if (bean.getId() == null) {
                throw new SAXException("expected id atribuite");
            }
            if (bean.getClazz() == null) {
                throw new SAXException("expected class atribuite");
            }
            // if default include extension is specified and no specific include
            // is set for particular bean than add include file obtained by
            // getting the class name of the bean and adding default include
            // extension. setting the include file to empty string or setting
            // the noIncludeFile attribute to true
            // will skip adding include for particular bean
            if (!skipInclude
                    && beansDefinition.getDefaultIncludeExtension() != null) {
                includeList.add(bean.getClazz()
                        + beansDefinition.getDefaultIncludeExtension());
            }
        } else if ("additional-include-file".equalsIgnoreCase(localName)) {
            if (itemType != ITEM_TYPE.ITEM_BEANS) {
                throw new SAXException("unexpected tag additionalIncludeFile");
            }
            itemType = ITEM_TYPE.ITEM_ADDITIONAL_INCLUDE;
            final String name = atts.getValue("name");
            includeList.add(name);
            if (name == null) {
                throw new SAXException("expected name atribuite");
            }
        } else if ("property".equalsIgnoreCase(localName)) {
            if (itemType != ITEM_TYPE.ITEM_BEAN) {
                throw new SAXException("unexpected tag property");
            }
            itemType = ITEM_TYPE.ITEM_PROPERTY;
            final Property property = new Property();
            property.setUseSetter(bean.isUseSetter());
            for (int i = 0; i < atts.getLength(); i++) {
                if ("name".equalsIgnoreCase(atts.getLocalName(i))) {
                    property.setName(atts.getValue(i));
                } else if ("value".equalsIgnoreCase(atts.getLocalName(i))) {
                    property.setValue(atts.getValue(i));
                } else if ("text".equalsIgnoreCase(atts.getLocalName(i))) {
                    property.setText("true".equalsIgnoreCase(atts.getValue(i)));
                } else if ("ref".equalsIgnoreCase(atts.getLocalName(i))) {
                    property.setRef(atts.getValue(i));
                } else if ("use-setter".equalsIgnoreCase(atts.getLocalName(i))) {
                    final boolean useSetter = "true".equalsIgnoreCase(atts
                            .getValue(i));
                    property.setUseSetter(useSetter);
                }
            }
            bean.getProperties().add(property);
            if (property.getName() == null) {
                throw new SAXException("expected name atribuite");
            }
            if (property.getValue() == null && property.getRef() == null) {
                throw new SAXException("expected value or ref atribuite");
            }
        } else if ("constructor-arg".equalsIgnoreCase(localName)) {
            if (itemType != ITEM_TYPE.ITEM_BEAN) {
                throw new SAXException("unexpected tag constructor-arg");
            }
            itemType = ITEM_TYPE.ITEM_CONSTRUCTOR_ARG;
            final ConstructorArg constructorArg = new ConstructorArg();
            for (int i = 0; i < atts.getLength(); i++) {
                if ("value".equalsIgnoreCase(atts.getLocalName(i))) {
                    constructorArg.setValue(atts.getValue(i));
                } else if ("text".equalsIgnoreCase(atts.getLocalName(i))) {
                    constructorArg.setText("true".equalsIgnoreCase(atts
                            .getValue(i)));
                } else if ("ref".equalsIgnoreCase(atts.getLocalName(i))) {
                    constructorArg.setRef(atts.getValue(i));
                }
            }
            bean.getConstructorArgs().add(constructorArg);
            if (constructorArg.getValue() == null
                    && constructorArg.getRef() == null) {
                throw new SAXException("expected value or ref atribuite");
            }
        } else if ("extension".equalsIgnoreCase(localName)) {
            if (itemType != ITEM_TYPE.ITEM_BEAN) {
                throw new SAXException("unexpected tag extension");
            }
            itemType = ITEM_TYPE.ITEM_EXTENSION;
            final Extension extension = new Extension();
            for (int i = 0; i < atts.getLength(); i++) {
                if ("name".equalsIgnoreCase(atts.getLocalName(i))) {
                    extension.setName(atts.getValue(i));
                } else if ("ref".equalsIgnoreCase(atts.getLocalName(i))) {
                    extension.setRef(atts.getValue(i));
                }
            }
            bean.getExtensions().add(extension);
        } else {
            throw new SAXException("unexpected tag " + localName);
        }
    }

    @Override
    public void endElement(final String arg0, final String arg1,
            final String arg2) throws SAXException {
        itemType = stack.pop();
    }

    @Override
    public void characters(final char[] arg0, final int arg1, final int arg2)
            throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void endPrefixMapping(final String arg0) throws SAXException {
    }

    @Override
    public void ignorableWhitespace(final char[] arg0, final int arg1,
            final int arg2) throws SAXException {
    }

    @Override
    public void processingInstruction(final String arg0, final String arg1)
            throws SAXException {
    }

    @Override
    public void setDocumentLocator(final Locator arg0) {
    }

    @Override
    public void skippedEntity(final String arg0) throws SAXException {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startPrefixMapping(final String arg0, final String arg1)
            throws SAXException {
    }

    public void setBeansDefinition(final BeansDefinition beansDefinition) {
        this.beansDefinition = beansDefinition;
    }

    public BeansDefinition getBeansDefinition() {
        return beansDefinition;
    }

}
