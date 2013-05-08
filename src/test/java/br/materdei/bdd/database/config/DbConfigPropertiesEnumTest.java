package br.materdei.bdd.database.config;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static br.materdei.bdd.database.config.DbConfigPropertiesEnum.*;

public class DbConfigPropertiesEnumTest {

	@Test
	public void testDbConfigProperties() {
		assertEquals("com.mysql.driver", DATABASE_CONNECTION_DRIVER.getValue());
		assertEquals("usuario", DATABASE_CONNECTION_USER.getValue());
		assertEquals("senha", DATABASE_COONECTION_PASSWORD.getValue());
		assertEquals("bancodados", DATABASE_CONNECTION_DB.getValue());
		assertEquals("12345", DATABASE_CONNECTION_PORT.getValue());
		assertEquals("127.0.0.1", DATABASE_CONNECTION_HOST.getValue());
		assertEquals("sis_ca,pf_org_ca,papeis_possiveis, pf_op_ca,pais  ,und_federativa,municipio, dsc_enumerados,  area_atuacao_enti_privada,cnae ,dom_pergunta_certidao,dom_pergunta_dirigente,fonte_recurso,seq_sol_abertura_conta,banco,tp_doc_contabil,und_fornecimento,natureza_despesa,natureza_despesa_sub_it,decisao_fundamentada,agendador,programa_trabalho_resumido", DATABASE_TABLES_TO_NOT_CLEAR.getValue());
		
	}
}