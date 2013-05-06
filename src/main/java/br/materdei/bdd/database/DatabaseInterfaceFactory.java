package br.materdei.bdd.database;

import br.materdei.bdd.database.config.DbConfigPropertiesEnum;
import br.materdei.bdd.database.impl.PostgresImpl;

public final class DatabaseInterfaceFactory {

	private final static String MENSAGEM_PROPRIEDADE_NAO_CONFIGURADA = "Não existe implementação disponível para o driver '" +
			DbConfigPropertiesEnum.DATABASE_CONNECTION_DRIVER.getValue() +
			"'. Certifique-se de ter adicionado a propriedade 'database.connection.driver' no arquivo bdd-config.properties";
	
	private final static String MENSAGEM_DRIVER_NAO_IMPLEMENTADO = "Não existe implementação disponível para o driver '" +
			DbConfigPropertiesEnum.DATABASE_CONNECTION_DRIVER.getValue() + "'";
	
	private DatabaseInterfaceFactory() {
		super();
	}
	
	public static DatabaseInterface createDatabaseInterface(DatabasesEnum db) {
		if (db == null) {
			throw new RuntimeException(MENSAGEM_PROPRIEDADE_NAO_CONFIGURADA);
		}
		
		switch (db) {
			case POSTGRESQL : return new PostgresImpl();
			default 		: throw new RuntimeException(MENSAGEM_DRIVER_NAO_IMPLEMENTADO);
		}
	}
	
	public static DatabaseInterface createDatabaseInterface() {
		String driver = DbConfigPropertiesEnum.DATABASE_CONNECTION_DRIVER.getValue();
		return createDatabaseInterface(DatabasesEnum.databaseFromDriverName(driver));
	}
}