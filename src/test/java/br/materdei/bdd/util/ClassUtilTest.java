package br.materdei.bdd.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import br.materdei.bdd.codegen.BddTestCreator;

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
	
	@Test
	public void testExtractClassNameFromJavaFile() {
		File f = new File("src/test/java/br/materdei/bdd/integration/CadastraUsuarioCenario.java");
		assertEquals("br.materdei.bdd.integration.CadastraUsuarioCenario", ClassUtil.extractClassNameFromJavaFile(f));
		
		f = new File("src/test/java/br/materdei/bdd/integration/AtualizaUsuarioCenario.java");
		assertEquals("br.materdei.bdd.integration.AtualizaUsuarioCenario", ClassUtil.extractClassNameFromJavaFile(f));
		
		f = new File("src/main/java/br/materdei/bdd/codegen/StoryBase.java");
		assertEquals("br.materdei.bdd.codegen.StoryBase", ClassUtil.extractClassNameFromJavaFile(f));
		
		f = new File("src/main/java/br/materdei/bdd/web/page/IPage.java");
		assertEquals("br.materdei.bdd.web.page.IPage", ClassUtil.extractClassNameFromJavaFile(f));
		
		f = new File("src/main/java/br/materdei/bdd/web/page/POFinder.java");
		assertEquals("br.materdei.bdd.web.page.POFinder", ClassUtil.extractClassNameFromJavaFile(f));
		
		f = new File("src/main/java/br/materdei/bdd/web/annotation/PageObject.java");
		assertEquals("br.materdei.bdd.web.annotation.PageObject", ClassUtil.extractClassNameFromJavaFile(f));
		
		f = new File("src/main/java/br/materdei/bdd/database/impl/DatabaseImpl.java");
		assertEquals("br.materdei.bdd.database.impl.DatabaseImpl", ClassUtil.extractClassNameFromJavaFile(f));
		
		f = new File("src/main/java/br/materdei/bdd/database/config/DbConfigPropertiesEnum.java");
		assertEquals("br.materdei.bdd.database.config.DbConfigPropertiesEnum", ClassUtil.extractClassNameFromJavaFile(f));
	}
}