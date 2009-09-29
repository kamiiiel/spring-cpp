package com.googlecode.wmlynar.springcpp.gen;

import java.io.IOException;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

import com.googlecode.wmlynar.springcpp.domain.BeansDefinition;
import com.googlecode.wmlynar.springcpp.domain.GeneratedClass;
import com.googlecode.wmlynar.springcpp.xml.BeansXmlReader;

public class BeanFactoryGeneratorTest extends TestCase {

    public void testBeanFactoryGenerator() throws IOException, SAXException {
        final BeansXmlReader r = new BeansXmlReader();
        final BeansDefinition d = r.read("src/test/resources/test-context.xml");

        final BeanFactoryGenerator gen = new BeanFactoryGenerator();
        final GeneratedClass res = gen.generate(d, "woj", "BeanFactory");

        // System.out.println(res.getHeaderContent());
        // System.out.println(res.getSourceContent());

    }

    public void testUseSetter() throws IOException, SAXException {
        final BeansXmlReader r = new BeansXmlReader();
        final BeansDefinition d = r
                .read("src/test/resources/test-use-setter.xml");

        final BeanFactoryGenerator gen = new BeanFactoryGenerator();
        final GeneratedClass res = gen.generate(d, "woj", "BeanFactory");

        // System.out.println(res.getHeaderContent());
        // System.out.println(res.getSourceContent());
    }

    public void testInitDestoryDelete() throws IOException, SAXException {
        final BeansXmlReader r = new BeansXmlReader();
        final BeansDefinition d = r
                .read("src/test/resources/init-destroy-delete.xml");

        final BeanFactoryGenerator gen = new BeanFactoryGenerator();
        final GeneratedClass res = gen.generate(d, "woj", "BeanFactory");

        System.out.println(res.getHeaderContent());
        System.out.println(res.getSourceContent());
    }
}
