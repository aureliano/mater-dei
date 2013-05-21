package br.materdei.bdd.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BrowserTest {

	@Test
	public void testKeys() {
		assertEquals("firefox", Browser.FIREFOX.getKey());
		assertEquals("chrome", Browser.GOOGLE_CHROME.getKey());
		assertEquals("ie", Browser.INTERNET_EXPLORER.getKey());
		assertEquals("android", Browser.ANDROID.getKey());
		assertEquals("htmlunit", Browser.HTML_UNIT.getKey());
	}
}