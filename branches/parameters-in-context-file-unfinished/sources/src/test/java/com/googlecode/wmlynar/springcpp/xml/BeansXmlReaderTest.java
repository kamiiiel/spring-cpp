package com.googlecode.wmlynar.springcpp.xml;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.googlecode.wmlynar.springcpp.domain.BeansDefinition;

import junit.framework.TestCase;

public class BeansXmlReaderTest extends TestCase {
	
	public void testReadingXmlFile() throws IOException, SAXException {
		BeansXmlReader r = new BeansXmlReader();
		BeansDefinition d = r.read("src/test/resources/test-context.xml");
		int i =0;
	}

}
