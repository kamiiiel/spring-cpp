package com.googlecode.wmlynar.springcpp;

import java.io.IOException;

import junit.framework.TestCase;

public class SpringCppTest extends TestCase {
	
	public void testLaunch() throws IOException, InterruptedException {
		String cmdLine = "--className test --outputDir src/test/resources/output --contextFile src/test/resources/test-context.xml";
		String[] args = cmdLine.split(" ");
		SpringCpp.main(args);
	}

}
