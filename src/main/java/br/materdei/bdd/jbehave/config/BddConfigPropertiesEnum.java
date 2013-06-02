package br.materdei.bdd.jbehave.config;

public enum BddConfigPropertiesEnum {

	WEB_DRIVER_BROWSER("webdriver.browser"),
	WEB_DRIVER_CHROME_DRIVER("webdriver.chrome.driver"),
	WEB_DRIVER_INTERNET_EXPLORER_DRIVER("webdriver.ie.driver"),
	WEB_DRIVER_TIMEOUT("webdriver.timeout"),
	WEB_DRIVER_WINDOW_MAXIMIZED("webdriver.window.maximized"),
	JBEHAVE_IGNORE_TEST_EXECUTION("jbehave.ignore.test.execution"),
	JBEHAVE_DISABLED_TESTS("jbehave.disabled.tests"),
	JBEHAVE_REPORT_OUTPUT_DIR("jbehave.report.output.dir"),
	JBEHAVE_REPORT_FORMAT_CONSOLE_COLORED("jbehave.report.format.console.colored"),
	JBEHAVE_STORIES_PATH("jbehave.stories.path"),
	JBEHAVE_SCENARIO_CLASSES_PATH("jbehave.scenario.classes.path"),
	DATABASE_CONNECTION_DRIVER("database.connection.driver"),
	DATABASE_CONNECTION_USER("database.connection.user"),
	DATABASE_COONECTION_PASSWORD("database.connection.password"),
	DATABASE_CONNECTION_DB("database.connection.db"),
	DATABASE_CONNECTION_PORT("database.connection.port"),
	DATABASE_CONNECTION_HOST("database.connection.host"),
	DATABASE_INIT_DATA_FILE("database.init.data.file"),
	DATABASE_TABLES_TO_NOT_CLEAR("database.tables.to.not.clear");
	
	private BddConfigPropertiesEnum(String k) {
		this.key = k;
	}
	
	private String key;
	
	public String getKey() {
		return this.key;
	}
}