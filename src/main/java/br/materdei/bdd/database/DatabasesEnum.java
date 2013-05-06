package br.materdei.bdd.database;

public enum DatabasesEnum {

	MYSQL("3306"),
	POSTGRESQL("5432");
	
	private DatabasesEnum(String port) {
		this.defaultPort = port;
	}
	
	private String defaultPort;
	
	public String getDefaultPort() {
		return this.defaultPort;
	}
	
	public String getDefaultHost() {
		return "localhost";
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