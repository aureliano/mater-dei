package br.materdei.bdd.util;

import java.io.File;
import java.io.IOException;
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
		assertEquals(8, files.size());
		
		List<String> fileNames = new ArrayList<String>();
		for (File f : files) {
			fileNames.add(f.getAbsolutePath());
		}
		
		assertTrue(fileNames.contains(new File("src/test/resources/bdd-config.properties").getAbsolutePath()));
		assertTrue(fileNames.contains(new File("src/test/resources/br/materdei/feature/atualiza_usuario.story").getAbsolutePath()));
		assertTrue(fileNames.contains(new File("src/test/resources/br/materdei/feature/cadastra_usuario.estoria").getAbsolutePath()));
	}
	
	@Test
	public void testLoadMatchFiles() {
		List<File> files = FileUtil.loadMatchFiles("[\\w\\s\\d-+]*.properties");
		String root = new File("").getAbsolutePath();
		
		assertFalse(files.isEmpty());
		assertTrue(files.contains(new File(root + "/src/main/resources/log4j.properties")));
		assertTrue(files.contains(new File(root + "/src/test/resources/bdd-config.properties")));
	}
	
	@Test
	public void testExtractFromZipFile() throws IOException {
		List<File> files = FileUtil.extractFromZipFile("src/test/resources/resources.zip", "target");
		for (File file : files) {
			assertTrue(file.exists());
		}
	}
	
	@Test(expected = RuntimeException.class)
	public void testLoadFilesWithError() {
		FileUtil.loadFiles("pom.xml");
		FileUtil.loadFiles("README.md");
		FileUtil.loadFiles("src/test/resources/bdd-config.properties");
	}
	
	@Test
	public void testTextFromFile() throws IOException {
		String text = FileUtil.textFromFile(new File("src/test/java/br/materdei/bdd/integration/CadastraUsuarioCenario.java"));
		assertTrue(text.contains("package br.materdei.bdd.integration;"));
		assertTrue(text.contains("public class CadastraUsuarioCenario {"));
		assertTrue(text.contains("}"));
	}

	@Test
	public void testConfiPathSeparator() {
        	assertEquals("src" + File.separator + "main" + File.separator + "resources", FileUtil.configPathSeparator("src/main/resources"));
		assertEquals("src" + File.separator + "main" + File.separator + "resources", FileUtil.configPathSeparator("src/main\\resources"));
		assertEquals("src" + File.separator + "test" + File.separator + "resources" + File.separator + "br" + File.separator + "materdei", FileUtil.configPathSeparator("src\\test\\resources\\br\\materdei"));
	}
}