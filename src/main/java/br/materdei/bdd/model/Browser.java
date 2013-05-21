package br.materdei.bdd.model;

public enum Browser {

	FIREFOX("firefox"),
	GOOGLE_CHROME("chrome"),
	INTERNET_EXPLORER("ie"),
	ANDROID("android"),
	HTML_UNIT("htmlunit");
	
	private Browser(String key) {
		this.key = key;
	}
	
	private String key;
	
	public String getKey() {
		return this.key;
	}
}