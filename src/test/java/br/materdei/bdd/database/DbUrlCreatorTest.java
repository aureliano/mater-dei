package br.materdei.bdd.database;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DbUrlCreatorTest {

	@Test
	public void testCreate() {
		assertEquals("jdbc:mysql://127.0.0.1:12345/", DbUrlCreator.create(DatabasesEnum.MYSQL));
		assertEquals("jdbc:postgresql://127.0.0.1:12345/", DbUrlCreator.create(DatabasesEnum.POSTGRESQL));
	}
}