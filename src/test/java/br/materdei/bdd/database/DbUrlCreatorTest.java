package br.materdei.bdd.database;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DbUrlCreatorTest {

	@Test
	public void testCreate() {
		assertEquals("jdbc:mysql://localhost:3306/", DbUrlCreator.create(DatabasesEnum.MYSQL));
		assertEquals("jdbc:postgresql://localhost:5432/", DbUrlCreator.create(DatabasesEnum.POSTGRESQL));
	}
}