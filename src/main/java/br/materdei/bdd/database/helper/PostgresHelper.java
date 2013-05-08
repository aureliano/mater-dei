package br.materdei.bdd.database.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
		String query = "select mater_dei_clear_tables_function(',sis_ca,pf_org_ca,papeis_possiveis,pf_op_ca,pais,und_federativa,municipio,dsc_enumerados,area_atuacao_enti_privada,cnae,dom_pergunta_certidao,dom_pergunta_dirigente,fonte_recurso,seq_sol_abertura_conta,banco,tp_doc_contabil,und_fornecimento,natureza_despesa,natureza_despesa_sub_it,decisao_fundamentada,agendador,programa_trabalho_resumido,')";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.execute();
	}
	
	private static void createFunctionToClearDatabase(Connection connection) throws Exception {
		String function = "CREATE OR REPLACE FUNCTION mater_dei_clear_tables_function(ignore_tables TEXT) RETURNS INTEGER AS " +
				"$$ DECLARE tupla RECORD; BEGIN " +
				"FOR tupla IN SELECT table_name FROM information_schema.tables WHERE table_schema NOT IN ('pg_catalog', 'information_schema') AND table_type = 'BASE TABLE' AND ignore_tables||'mater_dei_foreign_keys_table,' NOT LIKE '%,'||table_name||',%' " +
				"LOOP EXECUTE 'DELETE FROM ' || tupla.table_name; END LOOP; RETURN 0; END $$ LANGUAGE 'plpgsql';";
		PreparedStatement ps = connection.prepareStatement(function);
		ps.execute();
	}
}