package br.materdei.bdd.jbehave;

import java.io.File;

import org.junit.Test;

import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;
import static org.junit.Assert.assertEquals;

public class StoryNameParserTest {
	
	private String seed;
	
	public StoryNameParserTest() {
		this.seed = BddConfigPropertiesEnum.JBEHAVE_STORIES_PATH.getValue();
	}

	@Test
	public void testParse() {
		String root = new File("").getAbsolutePath();
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/cadastra_usuario.story"));
		//assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/cadastra_usuario.story"));
		//assertEquals("br.materdei.bdd.feature.AtualizaUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/atualiza_usuario.estoria"));
		//assertEquals("br.materdei.bdd.feature.AtualizaUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/atualiza_usuario.estoria"));
		//assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(seed + "/br/materdei/bdd/feature/cadastra_usuario.estoria"));
		//assertEquals("br.materdei.bdd.feature.AtualizaUsuarioTest", StoryNameParser.parse("br/materdei/bdd/feature/atualiza_usuario.estoria"));
	}
	
	@Test(expected=RuntimeException.class)
	public void testParseWithInvalidFileName() {
		String root = new File("").getAbsolutePath();
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/cadastraUsuario.story"));
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/CadastraUsuario.story"));
	}
}