package br.materdei.bdd.jbehave.config;

public enum BddConfigPropertiesEnum {

	SELENIUM_WEB_HOST("selenium.web.host", "localhost"),
	PROJECT_HOME_PAGE("project.home.page", "http://www.google.com"),
	BROWSER_LOCATION("browser.location", null),
	IGNORE_SELENIUM_START_UP("mater.dei.ignorar.iniciacao.selenium.server", null),
	SELENIUM_PORT("selenium.port", "2222"),
	SELENIUM_RESOURCES_FOLDER("selenium.resources.folder", "target/selenium"),
	SELENIUM_TIMEOUT("selenium.timeout", "30000"),
	DISABLED_TESTS_FILE("disabled.tests.file.location", "testes_desabilitados"),
	TRUST_ALL_SSL_CERTIFICATES("trust.all.ssl.certificates", "false");
	
	private BddConfigPropertiesEnum(String k, String defaultValue) {
		this.key = k;
		this.value = BddProperties.getPropriedade(k);
		
		if (this.value == null) {
			this.value = defaultValue;
		}
	}
	
	private String key;
	private String value;
	
	public String getKey() {
		return this.key;
	}
	
	public String getValue() {
		return this.value;
	}
}