package br.materdei.bdd.jbehave.config;

public enum BddConfigPropertiesEnum {

	WEB_DRIVER_BROWSER("webdriver.browser", null),
	WEB_DRIVER_CHROME_DRIVER("webdriver.chrome.driver", null),
	IGNORE_TEST_EXECUTION("mater.dei.ignore.test.execution", null),
	WEB_DRIVER_TIMEOUT("webdriver.timeout", "30000"),
	DISABLED_TESTS_FILE("disabled.tests.file.location", "testes_desabilitados"),
	JBEHAVE_REPORT_OUTPUT_DIR("jbehave.report.output.dir", "target/jbehave/view"),
	JBEHAVE_REPORT_FORMAT_CONSOLE_COLORED("jbehave.report.format.console.colored", "false"),
	JBEHAVE_STORIES_PATH("jbehave.stories.path", "src/main/resources"),
	JBEHAVE_SCENARIO_CLASSES_PATH("jbehave.scenario.classes.path", "src/test/java");
	
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