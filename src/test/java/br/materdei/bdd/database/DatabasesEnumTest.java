package br.materdei.bdd.database;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DatabasesEnumTest {

	@Test
	public void testDefaultPort() {
		assertEquals("3306", DatabasesEnum.MYSQL.getDefaultPort());
		assertEquals("5432", DatabasesEnum.POSTGRESQL.getDefaultPort());
	}
	
	@Test
	public void testDefaultHost() {
		for (DatabasesEnum db : DatabasesEnum.values()) {
			assertEquals("localhost", db.getDefaultHost());
		}
	}
	
	@Test
	public void testDatabaseFromDriverName() {
		assertEquals(DatabasesEnum.MYSQL, DatabasesEnum.databaseFromDriverName("com.mysql.jdbc.Driver"));
		assertEquals(DatabasesEnum.POSTGRESQL, DatabasesEnum.databaseFromDriverName("com.postgresql.Driver"));
	}
}