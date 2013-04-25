package br.materdei.bdd.jbehave.config;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.materdei.bdd.jbehave.config.BddProperties;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BddProperties.class})
public class BddPropertiesTest {

	@Test
	public void testGetProperties() {
		Properties p = BddProperties.getProperties();
		assertNotNull(p);
		assertFalse(p.isEmpty());
		assertEquals(4, p.size());
	}
	
	@Test
	public void testGetProperty() {
		Properties p = BddProperties.getProperties();
		assertEquals("localhost", p.getProperty("selenium.web.host"));
		assertEquals("http://www.google.com", p.getProperty("project.home.page"));
		assertEquals("*firefox /usr/lib/firefox-3.6.13/firefox-bin", p.getProperty("browser.location"));
		assertEquals("true", p.getProperty("mater.dei.ignorar.iniciacao.selenium.server"));
	}
}