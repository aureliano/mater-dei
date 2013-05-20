package br.materdei.bdd.jbehave.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import static br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum.*;

public class BddConfigPropertiesEnumTest {

	@Test
	public void testBddConfigPropertiesEnum() {
		assertEquals("firefox", WEB_DRIVER_BROWSER.getValue());
		assertEquals("true", IGNORE_SELENIUM_START_UP.getValue());
		assertEquals("10000", SELENIUM_TIMEOUT.getValue());
		assertEquals("testes_desabilitados", DISABLED_TESTS_FILE.getValue());
		assertEquals("true", SELENIUM_TRUST_ALL_SSL_CERTIFICATES.getValue());
		assertEquals("target/jbehave/view", JBEHAVE_REPORT_OUTPUT_DIR.getValue());
		assertEquals("true", JBEHAVE_REPORT_FORMAT_CONSOLE_COLORED.getValue());
		assertEquals("src/test/resources", JBEHAVE_STORIES_PATH.getValue());
		assertEquals("src/test/java", JBEHAVE_SCENARIO_CLASSES_PATH.getValue());
	}
}