package br.materdei.bdd.database;

import br.materdei.bdd.model.Database;

public enum DatabasesEnum {

	MYSQL(3306, "com.mysql.jdbc.Driver"),
	POSTGRESQL(5432, "org.postgresql.Driver");
	
	private DatabasesEnum(Integer port, String driver) {
		this.defaultPort = port;
		this.defaultDriver = driver;
	}
	
	private Integer defaultPort;
	private String defaultDriver;
	
	public Integer getDefaultPort() {
		return this.defaultPort;
	}
	
	public String getDefaultDriver() {
		return this.defaultDriver;
	}
	
	public String getDefaultHost() {
		return Database.DEFAULT_CONNECTION_HOST;
	}
	
	public static DatabasesEnum databaseFromDriverName(String driverName) {
		if (driverName == null) {
			return null;
		}
		
		if (driverName.toLowerCase().contains("mysql")) {
			return MYSQL;
		} else if (driverName.toLowerCase().contains("postgresql")) {
			return POSTGRESQL;
		} else {
			return null;
		}
	}
}