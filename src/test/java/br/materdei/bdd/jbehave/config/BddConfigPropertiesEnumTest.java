package br.materdei.bdd.jbehave.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import static br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum.*;

public class BddConfigPropertiesEnumTest {

	@Test
	public void testBddConfigPropertiesEnum() {
		assertEquals("*firefox /usr/lib/firefox-3.6.13/firefox-bin", BROWSER_LOCATION.getValue());
		assertEquals("true", IGNORE_SELENIUM_START_UP.getValue());
		assertEquals("http://www.google.com", PROJECT_HOME_PAGE.getValue());
		assertEquals("2222", SELENIUM_PORT.getValue());
		assertEquals("10000", SELENIUM_TIMEOUT.getValue());
		assertEquals("localhost", SELENIUM_WEB_HOST.getValue());
		assertEquals("testes_desabilitados", DISABLED_TESTS_FILE.getValue());
		assertEquals("true", TRUST_ALL_SSL_CERTIFICATES.getValue());
		assertEquals("target/jbehave/view", JBEHAVE_OUTPUT_REPORT_DIR.getValue());
	}
}