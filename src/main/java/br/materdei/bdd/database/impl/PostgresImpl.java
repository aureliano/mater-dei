package br.materdei.bdd.database.impl;

import java.sql.Connection;

import br.materdei.bdd.database.helper.PostgresHelper;

public class PostgresImpl extends DatabaseImpl {

	public PostgresImpl() {
		super();
	}

	@Override
	public void disableForeignKeys() throws Exception {
		Connection c = super.createDatabaseConnection();
		// Cria tabela somente se não existir.
		PostgresHelper.createForeignKeysTable(c);
		PostgresHelper.dropAllFks(c);
	}

	@Override
	public void enableForeignKeys() throws Exception {
		// Cria tabela somente se não existir.
		PostgresHelper.createForeignKeysTable(super.createDatabaseConnection());
		PostgresHelper.recreateAllFks(super.createDatabaseConnection());
	}

	@Override
	public void createClearDatabaseFunctions() throws Exception {
		PostgresHelper.createPostgresFunctions(super.createDatabaseConnection());
	}

	@Override
	public void clearDatabase() throws Exception {
		PostgresHelper.deleteRowsFromAllTables(super.createDatabaseConnection());		
	}
}