package br.materdei.bdd.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.junit.Test;

public class JBehaveTest {

	@Test
	public void testDefaults() {
		JBehave j = new JBehave();
		assertNull(j.getDisabledTestsFile());
		assertEquals("target/jbehave/view", j.getReportOutputDir());
		assertFalse(j.isColoredConsoleOutput());
		assertEquals("src/main/resources", j.getStoriesPath());
		assertEquals("src/test/java", j.getScenarioClassesPath());
		assertFalse(j.isIgnoreTestExecution());
	}
	
	@Test
	public void testLoadProperties() throws Exception {
		Properties p = new Properties();
		p.load(new FileInputStream(new File("src/test/resources/bdd-config.properties")));
		JBehave j = new JBehave(p);
		
		assertEquals("testes_desabilitados", j.getDisabledTestsFile());
		assertEquals("target/jbehave/view", j.getReportOutputDir());
		assertTrue(j.isColoredConsoleOutput());
		assertEquals("src/test/resources", j.getStoriesPath());
		assertEquals("src/test/java", j.getScenarioClassesPath());
		assertTrue(j.isIgnoreTestExecution());
	}
	
	@Test
	public void testUseDisabledTestsFile() {
		String expected = "path/to/file";
		assertEquals(expected, new JBehave().useDisabledTestsFile(expected).getDisabledTestsFile());
	}
	
	@Test
	public void testUseReportOutputDir() {
		String expected = "path/to/file";
		assertEquals(expected, new JBehave().useReportOutputDir(expected).getReportOutputDir());
	}
	
	@Test
	public void testUseColoredConsoleOutput() {
		assertTrue(new JBehave().useColoredConsoleOutput(true).isColoredConsoleOutput());
	}
	
	@Test
	public void testUseStoriesPath() {
		String expected = "path/to/file";
		assertEquals(expected, new JBehave().useStoriesPath(expected).getStoriesPath());
	}
	
	@Test
	public void testUseScenarioClassesPath() {
		String expected = "path/to/file";
		assertEquals(expected, new JBehave().useScenarioClassesPath(expected).getScenarioClassesPath());
	}
	
	@Test
	public void testUseIgnoreTestExecution() {
		assertTrue(new JBehave().useIgnoreTestExecution(true).isIgnoreTestExecution());
	}
	
	@Test
	public void testValidation() {
		JBehave jb = new JBehave();
		try {
			jb.validation();
		} catch (Exception ex) {
			assertEquals("Um valor deve ser informado para jbehave.report.output.dir", ex.getMessage());
		}
		
		jb.useReportOutputDir("path/to/file");
		try {
			jb.validation();
		} catch (Exception ex) {
			assertEquals("Um valor deve ser informado para jbehave.stories.path", ex.getMessage());
		}
		
		jb.useReportOutputDir("path/to/file").useStoriesPath("path/to/file");
		try {
			jb.validation();
		} catch (Exception ex) {
			assertEquals("Um valor deve ser informado para jbehave.scenario.classes.path", ex.getMessage());
		}
	}
}