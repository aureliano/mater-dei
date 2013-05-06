package br.materdei.bdd.database;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.materdei.bdd.database.impl.PostgresImpl;

public class DatabaseInterfaceFactoryTest {

	@Test
	public void testCreateDatabaseInterface() {
		DatabaseInterface db = DatabaseInterfaceFactory.createDatabaseInterface(DatabasesEnum.POSTGRESQL);
		assertNotNull(db);
		assertTrue(db instanceof PostgresImpl);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateDatabaseInterfaceForMySql() {
		DatabaseInterfaceFactory.createDatabaseInterface(DatabasesEnum.MYSQL);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateDatabaseInterfaceForNull() {
		DatabaseInterfaceFactory.createDatabaseInterface(null);
	}
}