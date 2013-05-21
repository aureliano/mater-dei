package br.materdei.bdd.model;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class WebDriverTest {

	@Test
	public void testDefaults() {
		WebDriver d = new WebDriver();
		assertEquals(Browser.FIREFOX, d.getBrowser());
		assertEquals(new Integer(30), d.getDriverTimeout());
		assertNull(d.getChromeDriver());
	}
	
	@Test
	public void testUseBrowser() {
		WebDriver d = new WebDriver().useBrowser(null);
		assertEquals(Browser.FIREFOX, d.getBrowser());
	}
	
	@Test
	public void testUseDriverTimeout() {
		Integer expected = 5;
		WebDriver d = new WebDriver().useDriverTimeout(expected);
		assertEquals(expected, d.getDriverTimeout());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUseDriverTimeoutWithTimoutEqualsToZero() {
		Integer expected = 0;
		new WebDriver().useDriverTimeout(expected);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUseDriverTimeoutWithTimoutLesserThanZero() {
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