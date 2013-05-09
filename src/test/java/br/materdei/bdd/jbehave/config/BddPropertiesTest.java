package br.materdei.bdd.jbehave.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import static br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum.*;
import static br.materdei.bdd.database.config.DbConfigPropertiesEnum.*;

import java.util.Properties;

import org.junit.Test;

public class BddPropertiesTest {

	@Test
	public void testGetProperties() {
		Properties p = BddProperties.getProperties();
		assertNotNull(p);
		assertFalse(p.isEmpty());
		assertEquals(16, p.size());
	}
	
	@Test
	public void testGetProperty() {
		Properties p = BddProperties.getProperties();
		assertEquals("localhost", p.getProperty(SELENIUM_WEB_HOST.getKey()));
		assertEquals("http://www.google.com", p.getProperty(PROJECT_HOME_PAGE.getKey()));
		assertEquals("*firefox /usr/lib/firefox-3.6.13/firefox-bin", p.getProperty(BROWSER_LOCATION.getKey()));
		assertEquals("true", p.getProperty(IGNORE_SELENIUM_START_UP.getKey()));
		assertEquals("10000", p.getProperty(SELENIUM_TIMEOUT.getKey()));
		assertEquals("testes_desabilitados", p.getProperty(DISABLED_TESTS_FILE.getKey()));
		assertEquals("true", p.getProperty(TRUST_ALL_SSL_CERTIFICATES.getKey()));
		assertEquals("target/jbehave/view", p.getProperty(JBEHAVE_OUTPUT_REPORT_DIR.getKey()));
		
		assertEquals("com.mysql.driver", p.getProperty(DATABASE_CONNECTION_DRIVER.getKey()));
		assertEquals("usuario", p.getProperty(DATABASE_CONNECTION_USER.getKey()));
		assertEquals("senha", p.getProperty(DATABASE_COONECTION_PASSWORD.getKey()));
		assertEquals("bancodados", p.getProperty(DATABASE_CONNECTION_DB.getKey()));
		assertEquals("br/materdei/data/dados_iniciais.sql,br/materdei/data/script.sql", p.getProperty(DATABASE_INIT_DATA_FILE.getKey()));
		assertEquals("12345", p.getProperty(DATABASE_CONNECTION_PORT.getKey()));
		assertEquals("127.0.0.1", p.getProperty(DATABASE_CONNECTION_HOST.getKey()));
		assertEquals("sis_ca,pf_org_ca,papeis_possiveis, pf_op_ca,pais  ,und_federativa,municipio, dsc_enumerados,  area_atuacao_enti_privada,cnae ,dom_pergunta_certidao,dom_pergunta_dirigente,fonte_recurso,seq_sol_abertura_conta,banco,tp_doc_contabil,und_fornecimento,natureza_despesa,natureza_despesa_sub_it,decisao_fundamentada,agendador,programa_trabalho_resumido", p.getProperty(DATABASE_TABLES_TO_NOT_CLEAR.getKey()));
	}
}