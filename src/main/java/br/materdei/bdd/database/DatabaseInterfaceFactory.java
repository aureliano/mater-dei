package br.materdei.bdd.database;

import br.materdei.bdd.database.config.DbConfigPropertiesEnum;
import br.materdei.bdd.database.impl.PostgresImpl;

public final class DatabaseInterfaceFactory {

	private DatabaseInterfaceFactory() {
		super();
	}
	
	public static DatabaseInterface createDatabaseInterface(DatabasesEnum db) {
		switch (db) {
			case POSTGRESQL : return new PostgresImpl();
			default 		: throw new RuntimeException("Não existe implementação para disponível para o driver '" + DbConfigPropertiesEnum.DATABASE_CONNECTION_DRIVER.getValue() + "'");
		}
	}
	
	public static DatabaseInterface createDatabaseInterface() {
		String driver = DbConfigPropertiesEnum.DATABASE_CONNECTION_DRIVER.getValue();
		return createDatabaseInterface(DatabasesEnum.databaseFromDriverName(driver));
	}
}