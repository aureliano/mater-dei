package br.materdei.bdd.jbehave;

import java.util.Properties;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class BddPropertiesTest {

	@Test
	public void testGetProperties() {
		Properties p = BddProperties.getProperties();
		assertNotNull(p);
		assertFalse(p.isEmpty());
		assertEquals(2, p.size());
	}
	
	@Test
	public void testGetProperty() {
		Properties p = BddProperties.getProperties();
		assertEquals("localhost", p.getProperty("selenium.web.host"));
		assertEquals("/", p.getProperty("project.home.page"));
	}
}