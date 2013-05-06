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
	}
}