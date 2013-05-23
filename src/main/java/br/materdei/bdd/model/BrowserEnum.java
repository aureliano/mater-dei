package br.materdei.bdd.model;

public enum BrowserEnum {

	FIREFOX("firefox"),
	GOOGLE_CHROME("chrome"),
	INTERNET_EXPLORER("ie"),
	ANDROID("android"),
	HTML_UNIT("htmlunit");
	
	private BrowserEnum(String key) {
		this.key = key;
	}
	
	private String key;
	
	public String getKey() {
		return this.key;
	}
	
	public static BrowserEnum browserFromString(String browser) {
		if (FIREFOX.key.equalsIgnoreCase(browser)) {
			return FIREFOX;
		} else if (GOOGLE_CHROME.key.equalsIgnoreCase(browser)) {
			return GOOGLE_CHROME;
		} else if (INTERNET_EXPLORER.key.equalsIgnoreCase(browser)) {
			return INTERNET_EXPLORER;
		} else if (ANDROID.key.equalsIgnoreCase(browser)) {
			return ANDROID;
		} else if (HTML_UNIT.key.equalsIgnoreCase(browser)) {
			return HTML_UNIT;
		} else {
			return null;
		}
	}
}