package br.materdei.bdd.database.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.lang.StringUtils;

import br.materdei.bdd.database.DatabaseInterface;
import br.materdei.bdd.database.config.DbConfigPropertiesEnum;

public final class PostgresHelper {

	private PostgresHelper() {
		super();
	}
	
	public static boolean tableExists(Connection conn, String table) throws Exception {
		String query = "SELECT table_name FROM information_schema.tables WHERE table_name = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, table);
		return (ps.executeQuery().next());
	}
	
	public static void createForeignKeysTable(Connection connection) throws Exception {
		PostgresFkHelper.createForeignKeysTable(connection);		
	}
	
	public static void createPostgresFunctions(Connection connection) throws Exception {
		PostgresFkHelper.createFunctionToDropAndRecreateFks(connection);
		createFunctionToClearDatabase(connection);
	}
	
	public static void dropAllFks(Connection connection) throws Exception {
		PostgresFkHelper.dropAllForeignKeys(connection);
	}
	
	public static void recreateAllFks(Connection connection) throws Exception {
		PostgresFkHelper.recreateAllForeignKeys(connection);
	}
	
	public static void deleteRowsFromAllTables(Connection connection) throws Exception {
		String query = "select mater_dei_clear_tables_function('" + getTablesToIgnoreOnDelete(connection) + "')";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.execute();
	}
	
	private static String getTablesToIgnoreOnDelete(Connection connection) throws Exception {
		String tables = DbConfigPropertiesEnum.DATABASE_TABLES_TO_NOT_CLEAR.getValue();
		if (StringUtils.isEmpty(tables)) {
			return String.format(",%s,", DatabaseInterface.FOREIGN_KEYS_TABLE);
		}
		
		return String.format(",%s,%s,", tables.replaceAll("\\s", ""), DatabaseInterface.FOREIGN_KEYS_TABLE);
	}
	
	private static void createFunctionToClearDatabase(Connection connection) throws Exception {
		String function = "CREATE OR REPLACE FUNCTION mater_dei_clear_tables_function(ignore_tables TEXT) RETURNS INTEGER AS " +
				"$$ DECLARE tupla RECORD; BEGIN " +
				"FOR tupla IN SELECT table_name FROM information_schema.tables WHERE table_schema NOT IN ('pg_catalog', 'information_schema') AND table_type = 'BASE TABLE' AND ignore_tables NOT LIKE '%,'||table_name||',%' " +
				"LOOP EXECUTE 'DELETE FROM ' || tupla.table_name; END LOOP; RETURN 0; END $$ LANGUAGE 'plpgsql';";
		PreparedStatement ps = connection.prepareStatement(function);
		ps.execute();
	}
}