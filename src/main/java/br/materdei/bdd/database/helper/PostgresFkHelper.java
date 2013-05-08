package br.materdei.bdd.database.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.materdei.bdd.database.DatabaseInterface;

public final class PostgresFkHelper {

	private PostgresFkHelper() {
		super();
	}
	
	public static void dropAllForeignKeys(Connection connection) throws Exception {
		String query = "select mater_dei_drop_or_recreate_foreign_keys_function(" + DatabaseInterface.FK_TYPE_DROP + ")";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.execute();
	}
	
	public static void recreateAllForeignKeys(Connection connection) throws Exception {
		String query = "select mater_dei_drop_or_recreate_foreign_keys_function(" + DatabaseInterface.FK_TYPE_ADD + ")";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.execute();
	}
	
	public static void createFunctionToDropAndRecreateFks(Connection connection) throws Exception {
		String function = "CREATE OR REPLACE FUNCTION mater_dei_drop_or_recreate_foreign_keys_function(action_type INT) RETURNS INTEGER AS " +
					"$$ DECLARE tupla RECORD;" +
					"BEGIN " +
					"FOR tupla IN (SELECT command FROM mater_dei_foreign_keys_table WHERE type = action_type) LOOP " +		
					"EXECUTE tupla.command;" +
					"END LOOP;" +
					"RETURN 0;" +
					"END $$ LANGUAGE 'plpgsql';";
		PreparedStatement ps = connection.prepareStatement(function);
		ps.execute();
	}
	
	public static void createForeignKeysTable(Connection connection) throws Exception {
		if (PostgresHelper.tableExists(connection, DatabaseInterface.FOREIGN_KEYS_TABLE)) {
			return;
		}
		
		String query = "CREATE TABLE " + DatabaseInterface.FOREIGN_KEYS_TABLE + " ( table_name CHARACTER VARYING(100), command TEXT, type INT )";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.execute();
		
		ps = connection.prepareStatement("insert into " + DatabaseInterface.FOREIGN_KEYS_TABLE + "(table_name, command, type) values(?, ?, ?)");
		for (String[] row : getDropFkCommands(connection)) {
			ps.setString(1, row[0]);
			ps.setString(2, row[1]);
			ps.setInt(3, DatabaseInterface.FK_TYPE_DROP);
			ps.addBatch();
		}
		
		for (String[] row : getAddFkCommands(connection)) {
			ps.setString(1, row[0]);
			ps.setString(2, row[1]);
			ps.setInt(3, DatabaseInterface.FK_TYPE_ADD);
			ps.addBatch();
		}
		
		ps.executeBatch();
	}
	
	private static List<String[]> getDropFkCommands(Connection conn) throws Exception {
		String query = "SELECT relname, 'ALTER TABLE '||nspname||'.'||relname||' DROP CONSTRAINT '||conname||';' " +
				"FROM pg_constraint " +
				"INNER JOIN pg_class ON conrelid=pg_class.oid " +
				"INNER JOIN pg_namespace ON pg_namespace.oid=pg_class.relnamespace " +
				"WHERE contype = 'f'";
		return getFkCommands(conn, query);
	}
	
	private static List<String[]> getAddFkCommands(Connection conn) throws Exception {
		String query = "SELECT relname, 'ALTER TABLE '||nspname||'.'||relname||' ADD CONSTRAINT '||conname||' '|| pg_get_constraintdef(pg_constraint.oid)||';' " +
				"FROM pg_constraint " +
				"INNER JOIN pg_class ON conrelid=pg_class.oid " +
				"INNER JOIN pg_namespace ON pg_namespace.oid=pg_class.relnamespace " +
				"WHERE contype = 'f'";
		return getFkCommands(conn, query);
	}
	
	private static List<String[]> getFkCommands(Connection conn, String query) throws Exception {
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		List<String[]> cmds = new ArrayList<String[]>();
		
		while (rs.next()) {
			cmds.add(new String[] {rs.getString(1), rs.getString(2)});
		}
		
		return cmds;
	}
}