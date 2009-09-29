package com.googlecode.wmlynar.springcpp.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.googlecode.wmlynar.springcpp.domain.BeansDefinition;

/**
 * Reads bean definitions from the context file.
 * 
 * @author Wojciech Mlynarczyk (wmlynar@gmail.com)
 * 
 */
public class BeansXmlReader {

    /**
     * Reads bean definitions from the context file.
     * 
     * @param file
     *            Name of teh context file.
     * @return Bean definitions.
     */
    public BeansDefinition read(final String file) throws IOException,
            SAXException {

        final BeansContentHandler handler = new BeansContentHandler();

        final SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
        } catch (final ParserConfigurationException e) {
            throw new IOException("could not create xml parser: "
                    + e.toString());
        }
        saxParser.parse(new File(file), handler);

        return handler.getBeansDefinition();

    }
}
