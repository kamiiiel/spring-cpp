package com.googlecode.wmlynar.springcpp;

import com.googlecode.wmlynar.springcpp.domain.ApplicationConfiguration;
import com.googlecode.wmlynar.springcpp.gen.BeanFactoryWriter;
import com.googlecode.wmlynar.springcpp.util.Version;

/**
 * Entry point of the application. Checks the input parameters and displays the
 * help message.
 * 
 * @author Wojciech Mlynarczyk (wmlynar@gmail.com)
 * 
 */
public class SpringCpp {

    /**
     * Application main entry point.
     * 
     * @param args
     *            Arguments.
     */
    public static void main(final String[] args) {
        System.out.println("spring-cpp, version " + Version.VERSION);
        System.out.println("http://code.google.com/p/spring-cpp/");

        if (args.length == 0) {
            help();
        }

        final ApplicationConfiguration conf = new ApplicationConfiguration();

        for (int i = 0; i < args.length; i++) {
            if ("--className".equalsIgnoreCase(args[i])) {
                checkParametersExist(args, i, 1);
                conf.setClassNameWithNamespace(args[i + 1]);
                i++;
            } else if ("--outputDir".equalsIgnoreCase(args[i])) {
                checkParametersExist(args, i, 1);
                conf.setDestinationDirectoryName(args[i + 1]);
                i++;
            } else if ("--contextFile".equalsIgnoreCase(args[i])) {
                checkParametersExist(args, i, 1);
                conf.setContextFileName(args[i + 1]);
                i++;
            } else {
                System.out.println("unknown parameter: " + args[i]);
                System.exit(1);
            }
        }

        if (conf.getClassNameWithNamespace() == null
                || conf.getContextFileName() == null
                || conf.getDestinationDirectoryName() == null) {
            System.out.println("some parameters are missing");
            System.exit(1);
        }

        final BeanFactoryWriter writer = new BeanFactoryWriter();

        try {
            writer.write(conf);
        } catch (final Exception e) {
            System.err.println("problem with generation of bean factory: "
                    + e.toString());
        }

        System.out.println("done");
    }

    /**
     * Checks that appropriate number of arguments exist for a parameter.
     * 
     * @param args
     *            Array of all arguments.
     * @param i
     *            Index of the parameter.
     * @param numParams
     *            Number of required parameters.
     */
    private static void checkParametersExist(final String[] args, final int i,
            final int numParams) {
        if (i >= args.length - numParams) {
            System.out.println("missing parameter for: " + args[i]);
            System.exit(1);
        }
    }

    /**
     * Displays the help message and quits the application.
     */
    private static void help() {
        System.out.println("\nusage:");
        System.out
                .println(" spring-cpp --className name --outputDir dir --contextFile file");
        System.out.println("\nwhere:");
        System.out
                .println(" name = name of the bean factory class, no namespace support yet");
        System.out
                .println(" dir = directory name to where to write generated files");
        System.out.println(" file = path to xml context file");
        System.out.println("\nexample:");
        System.out
                .println(" spring-cpp --className BeanFactory --outputDir generated --contextFile conf/app-context.xml");
        System.exit(1);
    }

}
