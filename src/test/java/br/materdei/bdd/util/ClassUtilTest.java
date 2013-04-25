package br.materdei.bdd.util;

import org.junit.Test;

import br.materdei.bdd.codegen.BddTestCreator;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ClassUtilTest {

	@Test
	public void testClassExists() throws Exception {
		assertFalse(ClassUtil.classExists(""));
		assertFalse(ClassUtil.classExists("br.materdei.bdd.util.classUtil"));
		
		assertTrue(ClassUtil.classExists("br.materdei.bdd.util.ClassUtil"));
		assertTrue(ClassUtil.classExists("br.materdei.bdd.util.ClassUtilTest"));
		
		assertFalse(ClassUtil.classExists("br.mg.bhe.Testar"));
		BddTestCreator.create(null, "br.mg.bhe.Testar");
		assertTrue(ClassUtil.classExists("br.mg.bhe.Testar"));
	}
}