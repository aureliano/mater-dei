package br.materdei.bdd.jbehave;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StoryNameParserTest {

	@Test
	public void testParse() {
		String root = new File("").getAbsolutePath();
		assertEquals("br.materdei.bdd.feature.BddTest", StoryNameParser.parse(root + "/src/main/resources/br/materdei/bdd/feature/BddTest.story"));
		assertEquals("br.materdei.bdd.feature.BddTest", StoryNameParser.parse(root + "/src/test/resources/br/materdei/bdd/feature/BddTest.story"));
		assertEquals("br.materdei.bdd.feature.BddTest", StoryNameParser.parse(root + "/src/main/resources/br/materdei/bdd/feature/BddTest.estoria"));
		assertEquals("br.materdei.bdd.feature.BddTest", StoryNameParser.parse(root + "/src/test/resources/br/materdei/bdd/feature/BddTest.estoria"));
		assertEquals("br.materdei.bdd.feature.BddTest", StoryNameParser.parse("/src/test/resources/br/materdei/bdd/feature/BddTest.estoria"));
		assertEquals("br.materdei.bdd.feature.BddTest", StoryNameParser.parse("br/materdei/bdd/feature/BddTest.estoria"));
	}
}