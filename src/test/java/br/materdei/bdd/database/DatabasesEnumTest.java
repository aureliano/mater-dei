package br.materdei.bdd.database;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DatabasesEnumTest {

	@Test
	public void testDefaultPort() {
		assertEquals(new Integer(3306), DatabasesEnum.MYSQL.getDefaultPort());
		assertEquals(new Integer(5432), DatabasesEnum.POSTGRESQL.getDefaultPort());
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
		assertNull(DatabasesEnum.databaseFromDriverName(null));
	}
}