package br.materdei.bdd.jbehave;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import br.materdei.bdd.model.JBehave;
import br.materdei.bdd.model.ThreadLocalModel;

public class StoryNameParserTest {
	
	private String seed;
	
	public StoryNameParserTest() {
		this.seed = "src/test/resources";
	}
	
	@Before
	public void beforeTest() {
		ThreadLocalModel.setJBehaveModel(new JBehave().useStoriesPath(this.seed));
	}
	
	@Before
	public void afterTest() {
		ThreadLocalModel.setJBehaveModel(null);
	}

	@Test
	public void testParse() {
		String root = new File("").getAbsolutePath();
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/cadastra_usuario.story"));
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/cadastra_usuario.story"));
		assertEquals("br.materdei.bdd.feature.AtualizaUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/atualiza_usuario.estoria"));
		assertEquals("br.materdei.bdd.feature.AtualizaUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/atualiza_usuario.estoria"));
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(seed + "/br/materdei/bdd/feature/cadastra_usuario.estoria"));
		assertEquals("br.materdei.bdd.feature.AtualizaUsuarioTest", StoryNameParser.parse("br/materdei/bdd/feature/atualiza_usuario.estoria"));
	}
	
	@Test(expected=RuntimeException.class)
	public void testParseWithInvalidFileName() {
		String root = new File("").getAbsolutePath();
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/cadastraUsuario.story"));
		assertEquals("br.materdei.bdd.feature.CadastraUsuarioTest", StoryNameParser.parse(root + "/" + seed + "/br/materdei/bdd/feature/CadastraUsuario.story"));
	}
}