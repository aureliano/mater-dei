package br.materdei.bdd.web.driver;

import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.WebDriverProvider;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WebDriverSingletonTest {

	@Test
	public void testGet() {
		WebDriverSingleton obj1 = WebDriverSingleton.get();
		WebDriverSingleton obj2 = WebDriverSingleton.get();
		
		assertTrue(obj1 == obj2);
		assertTrue(obj1.hashCode() == obj2.hashCode());
		assertEquals(obj1, obj2);
	}
	
	@Test
	public void testGetWebDriverProvider() {
		WebDriverProvider d = WebDriverSingleton.get().getWebDriverProvider();
		
		assertNotNull(d);
		assertTrue(d instanceof PropertyWebDriverProvider);
	}
}