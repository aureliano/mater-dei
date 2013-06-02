package br.materdei.bdd.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		assertFalse(d.isWindowMaximized());
	}
	
	@Test
	public void testLoadProperties() throws Exception {
		Properties p = new Properties();
		p.load(new FileInputStream(new File("src/test/resources/bdd-config.properties")));
		WebDriver d = new WebDriver(p);
		
		assertEquals(BrowserEnum.FIREFOX, d.getBrowser());
		assertEquals(new File("src/test/resources/chromedriver"), d.getChromeDriver());
		assertEquals(new Integer(10000), d.getDriverTimeout());
		assertTrue(d.isWindowMaximized());
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
		File f = new File("src/test/resources/chromedriver");
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

	@Test
	public void testUseInternetExplorerDriver() {
		File f = new File("src/test/resources/IEDriverServer.exe");
		WebDriver d = new WebDriver().useInternetExplorerDriver(f);
		assertEquals(f, d.getInternetExplorerDriver());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUseInternetExplorerDriverWithNonExistingFile() {
		new WebDriver().useInternetExplorerDriver(new File("arquivo/nao/existente"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUseInternetExplorerDriverWhereFileIsDirectory() {
		new WebDriver().useInternetExplorerDriver(new File("src/test/resources"));
	}
	
	@Test
	public void testUseWindowMaximized() {
		WebDriver d = new WebDriver().useWindowMaximized(true);
		assertTrue(d.isWindowMaximized());
		
		d.useWindowMaximized(false);
		assertFalse(d.isWindowMaximized());
	}
}