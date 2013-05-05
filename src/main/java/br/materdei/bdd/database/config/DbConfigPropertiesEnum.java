package br.materdei.bdd.database.config;

import br.materdei.bdd.jbehave.config.BddProperties;

public enum DbConfigPropertiesEnum {

	DATABASE_CONNECTION_DRIVER("database.connection.driver"),
	DATABASE_CONNECTION_USER("database.connection.user"),
	DATABASE_COONECTION_PASSWORD("database.connection.password"),
	DATABASE_CONNECTION_DB("database.connection.db"),
	DATABASE_CONNECTION_PORT("database.connection.port"),
	DATABASE_CONNECTION_HOST("database.connection.host"),
	DATABASE_INIT_DATA_FILE("database.init.data.file");
	
	private DbConfigPropertiesEnum(String key) {
		this.key = key;
		this.value = BddProperties.getPropriedade(this.key);
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