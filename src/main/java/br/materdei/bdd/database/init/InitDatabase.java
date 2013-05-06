package br.materdei.bdd.database.init;

import br.materdei.bdd.database.DatabaseInterface;
import br.materdei.bdd.database.DatabaseInterfaceFactory;

public final class InitDatabase {

	private InitDatabase() {
		super();
	}
	
	public static void init() {
		DatabaseInterface databaseInterface = DatabaseInterfaceFactory.createDatabaseInterface();
		try {
			databaseInterface.createClearDatabaseFunctions();
			databaseInterface.disableForeignKeys();
			databaseInterface.clearDatabase();
			databaseInterface.loadInitialData();
			databaseInterface.enableForeignKeys();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}