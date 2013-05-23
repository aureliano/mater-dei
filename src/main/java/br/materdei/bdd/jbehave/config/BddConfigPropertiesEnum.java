package br.materdei.bdd.jbehave.config;

public enum BddConfigPropertiesEnum {

	WEB_DRIVER_BROWSER("webdriver.browser"/*, null*/),
	WEB_DRIVER_CHROME_DRIVER("webdriver.chrome.driver"/*, null*/),
	WEB_DRIVER_TIMEOUT("webdriver.timeout"/*, "30000"*/),
	JBEHAVE_IGNORE_TEST_EXECUTION("jbehave.ignore.test.execution"/*, null*/),
	JBEHAVE_DISABLED_TESTS("jbehave.disabled.tests"/*, "testes_desabilitados"*/),
	JBEHAVE_REPORT_OUTPUT_DIR("jbehave.report.output.dir"/*, "target/jbehave/view"*/),
	JBEHAVE_REPORT_FORMAT_CONSOLE_COLORED("jbehave.report.format.console.colored"/*, "false"*/),
	JBEHAVE_STORIES_PATH("jbehave.stories.path"/*, "src/main/resources"*/),
	JBEHAVE_SCENARIO_CLASSES_PATH("jbehave.scenario.classes.path"/*, "src/test/java"*/),
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