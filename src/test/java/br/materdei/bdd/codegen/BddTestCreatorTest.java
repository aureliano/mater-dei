package br.materdei.bdd.codegen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class BddTestCreatorTest {

	@Test
	public void testCreate() throws Exception {
		Object obj = BddTestCreator.create(null, "br.materdei.bdd.ProjectBddTest");
		assertNotNull(obj);
		assertEquals("br.materdei.bdd.ProjectBddTest", obj.getClass().getName());
		assertNotNull(obj.getClass().getSuperclass());
		assertEquals("br.materdei.bdd.codegen.StoryBase", obj.getClass().getSuperclass().getName());
		
		obj = BddTestCreator.create(null, "br.materdei.bdd.ProjectBddTest");
		assertNotNull(obj);
		
		obj = BddTestCreator.create(null, "br.materdei.bdd.codegen.BddTestCreator");
		assertNotNull(obj);
		assertEquals("br.materdei.bdd.codegen.BddTestCreator", obj.getClass().getName());
		
		obj = BddTestCreator.create(StoryBaseMock.class, "br.materdei.bdd.codegen.BddTestCreator");
		assertNotNull(obj);
		assertEquals("br.materdei.bdd.codegen.BddTestCreator", obj.getClass().getName());
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateWhereStoryDoesNotInheritFromStoryBase() throws Exception {
		BddTestCreator.create(Object.class, "br.materdei.bdd.OtherProjectBddTest");
		BddTestCreator.create(BddTestCreatorTest.class, "br.materdei.bdd.OtherProjectBddTest");
		BddTestCreator.create(ScenarioCreator.class, "br.materdei.bdd.OtherProjectBddTest");
	}
}