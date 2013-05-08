package br.materdei.bdd.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseInterface {
	
	public static final String FOREIGN_KEYS_TABLE = "mater_dei_foreign_keys_table";
	
	public static final byte FK_TYPE_ADD = 0;
	
	public static final byte FK_TYPE_DROP = 1;

	public abstract Connection createDatabaseConnection() throws Exception;
	
	public abstract void disableForeignKeys() throws Exception;
	
	public abstract void enableForeignKeys() throws Exception;
	
	public abstract void createClearDatabaseFunctions() throws Exception;
	
	public abstract void clearDatabase() throws Exception;
	
	public abstract void loadInitialData() throws Exception;
	
	public abstract void closeConnection() throws SQLException;
}