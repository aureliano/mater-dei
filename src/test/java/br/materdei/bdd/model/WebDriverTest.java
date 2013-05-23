package br.materdei.bdd.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.junit.Test;

public class WebDriverTest {

	@Test
	public void testDefaults() {
		WebDriver d = new WebDriver();
		assertEquals(BrowserEnum.FIREFOX, d.getBrowser());
		assertEquals(new Integer(30), d.getDriverTimeout());
		assertNull(d.getChromeDriver());
	}
	
	@Test
	public void testLoadProperties() throws Exception {
		Properties p = new Properties();
		p.load(new FileInputStream(new File("src/test/resources/bdd-config.properties")));
		WebDriver d = new WebDriver(p);
		
		assertEquals(BrowserEnum.FIREFOX, d.getBrowser());
		assertEquals(new File("src/test/resources/chromedriver"), d.getChromeDriver());
		assertEquals(new Integer(10000), d.getDriverTimeout());
	}
	
	@Test
	public void testUseBrowser() {
		WebDriver d = new WebDriver().useBrowser(null);
		assertEquals(BrowserEnum.FIREFOX, d.getBrowser());
	}
	
	@Test
	public void testUseDriverTimeout() {
		Integer expected = 5;
		WebDriver d = new WebDriver().useDriverTimeout(expected);
		assertEquals(expected, d.getDriverTimeout());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUseDriverTimeoutWithTimoutEqualsZero() {
		Integer expected = 0;
		new WebDriver().useDriverTimeout(expected);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUseDriverTimeoutWithTimoutLessThanZero() {
		Integer expected = -1;
		new WebDriver().useDriverTimeout(expected);
	}
	
	@Test
	public void testUseChromeDriver() {
		File f = new File("src/test/resources/resources.zip");
		WebDriver d = new WebDriver().useChromeDriver(f);
		assertEquals(f, d.getChromeDriver());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUseChromeDriverWithNonExistingFile() {
		new WebDriver().useChromeDriver(new File("arquivo/nao/existente"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUseChromeDriverWhereFileIsDirectory() {
		new WebDriver().useChromeDriver(new File("src/test/resources"));
	}
}