package br.materdei.bdd.jbehave.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import static br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum.*;

import java.util.Properties;

import org.junit.Test;

public class BddPropertiesTest {

	@Test
	public void testGetProperties() {
		Properties p = BddProperties.getProperties();
		assertNotNull(p);
		assertFalse(p.isEmpty());
		assertEquals(7, p.size());
	}
	
	@Test
	public void testGetProperty() {
		Properties p = BddProperties.getProperties();
		assertEquals("localhost", p.getProperty(SELENIUM_WEB_HOST.getKey()));
		assertEquals("http://www.google.com", p.getProperty(PROJECT_HOME_PAGE.getKey()));
		assertEquals("*firefox /usr/lib/firefox-3.6.13/firefox-bin", p.getProperty(BROWSER_LOCATION.getKey()));
		assertEquals("true", p.getProperty(IGNORE_SELENIUM_START_UP.getKey()));
		assertEquals("10000", p.getProperty(SELENIUM_TIMEOUT.getKey()));
		assertEquals("testes_desabilitados", p.getProperty(DISABLED_TESTS_FILE.getKey()));
		assertEquals("true", p.getProperty(TRUST_ALL_SSL_CERTIFICATES.getKey()));
	}
}