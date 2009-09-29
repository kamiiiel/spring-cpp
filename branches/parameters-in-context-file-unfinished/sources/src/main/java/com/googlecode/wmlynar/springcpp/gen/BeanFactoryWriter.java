package com.googlecode.wmlynar.springcpp.gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.xml.sax.SAXException;

import com.googlecode.wmlynar.springcpp.domain.ApplicationConfiguration;
import com.googlecode.wmlynar.springcpp.domain.BeansDefinition;
import com.googlecode.wmlynar.springcpp.domain.GeneratedClass;
import com.googlecode.wmlynar.springcpp.xml.BeansXmlReader;

/**
 * Class that coordinates generation of the bean factory and writing it to disk.
 * 
 * @author Wojciech Mlynarczyk (wmlynar@gmail.com)
 * 
 */
public class BeanFactoryWriter {

    /**
     * Reads the context file, generates the bean factory and writes it on disk.
     * 
     * @param conf
     *            Configuration that tells where the context file is and where
     *            to write the generated source code.
     */
    public void write(final ApplicationConfiguration conf) throws IOException,
            SAXException {

        // generate output

        final BeansXmlReader reader = new BeansXmlReader();
        final BeansDefinition definition = reader.read(conf
                .getContextFileName());
        final BeanFactoryGenerator generator = new BeanFactoryGenerator();
        final GeneratedClass output = generator.generate(definition, null, conf
                .getClassNameWithNamespace());

        // create output directory

        String dir = conf.getDestinationDirectoryName().trim();
        if ("".equals(dir)) {
            dir = ".";
        }
        if (!dir.endsWith("/")) {
            dir = dir + "/";
        }
        new File(dir).mkdirs();

        // write to files

        String fileName = dir + output.getClassName() + ".h";
        String content = output.getHeaderContent();
        writeToFile(fileName, content);

        fileName = dir + output.getClassName() + ".cpp";
        content = output.getSourceContent();
        writeToFile(fileName, content);
    }

    /**
     * Writes the content of given string to a file.
     * 
     * @param fileName
     *            Name of the file.
     * @param content
     *            String to write to a file.
     */
    private static void writeToFile(final String fileName, final String content)
            throws FileNotFoundException, IOException {
        final FileOutputStream fos = new FileOutputStream(fileName);
        final PrintStream ps = new PrintStream(fos);
        ps.print(content);
        ps.close();
        fos.close();
    }

}
