package br.materdei.bdd.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class FileUtilTest {

	@Test
	public void testLoadFilesFromRoot() {
		List<File> files = FileUtil.loadFiles();
		assertNotNull(files);
		assertFalse(files.isEmpty());
		
		List<String> fileNames = new ArrayList<String>();
		for (File f : files) {
			fileNames.add(f.getAbsolutePath());
		}
		
		assertTrue(fileNames.contains(new File("pom.xml").getAbsolutePath()));
		assertTrue(fileNames.contains(new File("src/test/resources/bdd-config.properties").getAbsolutePath()));
		assertTrue(fileNames.contains(new File("src/test/java/br/materdei/bdd/util/FileUtilTest.java").getAbsolutePath()));
		assertTrue(fileNames.contains(new File("src/main/java/br/materdei/bdd/util/FileUtil.java").getAbsolutePath()));
	}
	
	@Test
	public void testLoadFiles() {
		List<File> files = FileUtil.loadFiles("src/test/resources");
		assertNotNull(files);
		assertFalse(files.isEmpty());
		assertEquals(3, files.size());
		
		List<String> fileNames = new ArrayList<String>();
		for (File f : files) {
			fileNames.add(f.getAbsolutePath());
		}
		
		assertTrue(fileNames.contains(new File("src/test/resources/bdd-config.properties").getAbsolutePath()));
		assertTrue(fileNames.contains(new File("src/test/resources/br/materdei/feature/atualiza_usuario.story").getAbsolutePath()));
		assertTrue(fileNames.contains(new File("src/test/resources/br/materdei/feature/cadastra_usuario.estoria").getAbsolutePath()));
	}
	
	@Test(expected = RuntimeException.class)
	public void testLoadFilesWithError() {
		FileUtil.loadFiles("pom.xml");
		FileUtil.loadFiles("README.md");
		FileUtil.loadFiles("src/test/resources/bdd-config.properties");
	}
}