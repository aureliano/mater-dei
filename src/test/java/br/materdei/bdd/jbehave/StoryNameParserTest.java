package br.materdei.bdd.jbehave;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StoryNameParserTest {

	@Test
	public void testParse() {
		String root = new File("").getAbsolutePath();
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/src/main/resources/br/materdei/bdd/feature/cadastra_usuario.story"));
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/src/test/resources/br/materdei/bdd/feature/cadastra_usuario.story"));
		assertEquals("br.materdei.bdd.feature.AtualizaUsuarioTest", StoryNameParser.parse(root + "/src/main/resources/br/materdei/bdd/feature/atualiza_usuario.estoria"));
		assertEquals("br.materdei.bdd.feature.AtualizaUsuarioTest", StoryNameParser.parse(root + "/src/test/resources/br/materdei/bdd/feature/atualiza_usuario.estoria"));
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse("/src/test/resources/br/materdei/bdd/feature/cadastra_usuario.estoria"));
		assertEquals("br.materdei.bdd.feature.AtualizaUsuarioTest", StoryNameParser.parse("br/materdei/bdd/feature/atualiza_usuario.estoria"));
	}
	
	@Test(expected=RuntimeException.class)
	public void testParseWithInvalidFileName() {
		String root = new File("").getAbsolutePath();
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/src/main/resources/br/materdei/bdd/feature/cadastraUsuario.story"));
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/src/main/resources/br/materdei/bdd/feature/CadastraUsuario.story"));
	}
}