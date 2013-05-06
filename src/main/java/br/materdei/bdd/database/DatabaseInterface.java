package br.materdei.bdd.database;

import java.sql.Connection;

public interface DatabaseInterface {

	public abstract Connection createDatabaseConnection() throws Exception;
	
	public abstract void disableForeignKeys();
	
	public abstract void enableForeignKeys();
	
	public abstract void createClearDatabaseFunctions();
	
	public abstract void clearDatabase();
	
	public abstract void loadInitialData() throws Exception;
}