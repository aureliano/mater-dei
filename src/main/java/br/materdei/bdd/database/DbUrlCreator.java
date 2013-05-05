package br.materdei.bdd.database;

import org.apache.commons.lang.StringUtils;

import br.materdei.bdd.database.config.DbConfigPropertiesEnum;
import br.materdei.bdd.jbehave.config.BddProperties;

public final class DbUrlCreator {

	private DbUrlCreator() {
		super();
	}
	
	public static String create(DatabasesEnum db) {
		switch (db) {
			case MYSQL : return mysqlUrl();
			case POSTGRESQL : return postgresqlUrl();
			default : throw new RuntimeException("Não foi possível criar URL de conexão com o banco de dados.");
		}
	}
	
	private static String mysqlUrl() {
		String port = configPort(DatabasesEnum.MYSQL);
		String host = configHost(DatabasesEnum.MYSQL);
		return String.format("jdbc:mysql://%s:%s/", host, port);
	}
	
	private static String postgresqlUrl() {
		String port = configPort(DatabasesEnum.POSTGRESQL);
		String host = configHost(DatabasesEnum.POSTGRESQL);
		return String.format("jdbc:postgresql://%s:%s/", host, port);
	}
	
	private static String configPort(DatabasesEnum db) {
		String port = BddProperties.getPropriedade(DbConfigPropertiesEnum.DATABASE_CONNECTION_PORT.getValue());
		if (StringUtils.isEmpty(port)) {
			port = db.getDefaultPort();
		}
		
		return port;
	}
	
	private static String configHost(DatabasesEnum db) {
		String host = BddProperties.getPropriedade(DbConfigPropertiesEnum.DATABASE_CONNECTION_HOST.getValue());
		if (StringUtils.isEmpty(host)) {
			host = db.getDefaultHost();
		}
		
		return host;
	}
}