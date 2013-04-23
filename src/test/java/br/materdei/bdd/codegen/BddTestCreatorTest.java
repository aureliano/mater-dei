package br.materdei.bdd.codegen;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BddTestCreatorTest {

	@Test
	public void testCreate() throws Exception {
		Object obj = BddTestCreator.create();
		assertNotNull(obj);
		assertEquals("br.materdei.bdd.ProjectBddTest", obj.getClass().getName());
	}
}