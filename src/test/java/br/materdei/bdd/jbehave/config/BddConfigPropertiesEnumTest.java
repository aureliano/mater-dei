package br.materdei.bdd.jbehave.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import static br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum.*;

public class BddConfigPropertiesEnumTest {

	@Test
	public void testBddConfigPropertiesEnum() {
		assertEquals("webdriver.browser", WEB_DRIVER_BROWSER.getKey());
		assertEquals("webdriver.chrome.driver", WEB_DRIVER_CHROME_DRIVER.getKey());
		assertEquals("jbehave.ignore.test.execution", JBEHAVE_IGNORE_TEST_EXECUTION.getKey());
		assertEquals("webdriver.timeout", WEB_DRIVER_TIMEOUT.getKey());
		assertEquals("jbehave.disabled.tests", JBEHAVE_DISABLED_TESTS.getKey());
		assertEquals("jbehave.report.output.dir", JBEHAVE_REPORT_OUTPUT_DIR.getKey());
		assertEquals("jbehave.report.format.console.colored", JBEHAVE_REPORT_FORMAT_CONSOLE_COLORED.getKey());
		assertEquals("jbehave.stories.path", JBEHAVE_STORIES_PATH.getKey());
		assertEquals("jbehave.scenario.classes.path", JBEHAVE_SCENARIO_CLASSES_PATH.getKey());
	}
}