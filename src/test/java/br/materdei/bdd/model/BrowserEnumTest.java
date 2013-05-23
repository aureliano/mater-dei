package br.materdei.bdd.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BrowserEnumTest {

	@Test
	public void testKeys() {
		assertEquals("firefox", BrowserEnum.FIREFOX.getKey());
		assertEquals("chrome", BrowserEnum.GOOGLE_CHROME.getKey());
		assertEquals("ie", BrowserEnum.INTERNET_EXPLORER.getKey());
		assertEquals("android", BrowserEnum.ANDROID.getKey());
		assertEquals("htmlunit", BrowserEnum.HTML_UNIT.getKey());
	}
	
	@Test
	public void testBrowserFromString() {
		assertEquals(BrowserEnum.FIREFOX, BrowserEnum.browserFromString("firefox"));
		assertEquals(BrowserEnum.GOOGLE_CHROME, BrowserEnum.browserFromString("chrome"));
		assertEquals(BrowserEnum.INTERNET_EXPLORER, BrowserEnum.browserFromString("ie"));
		assertEquals(BrowserEnum.ANDROID, BrowserEnum.browserFromString("android"));
		assertEquals(BrowserEnum.HTML_UNIT, BrowserEnum.browserFromString("htmlunit"));
		
		assertNull(BrowserEnum.browserFromString(null));
		assertNull(BrowserEnum.browserFromString("none"));
	}
}