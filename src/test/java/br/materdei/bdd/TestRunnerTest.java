package br.materdei.bdd;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.materdei.bdd.model.JBehave;
import br.materdei.bdd.model.ThreadLocalModel;

public class TestRunnerTest {
	
	@Before
	public void beforeTest() {
		ThreadLocalModel.setJBehaveModel(new JBehave()
			.useStoriesPath("src/test/resources")
			.useDisabledTestsFile("testes_desabilitados")
			.useIgnoreTestExecution(true));
	}
	
	@After
	public void afterTest() {
		ThreadLocalModel.setJBehaveModel(null);
	}

	@Test
	public void testRun() throws Throwable {
		TestRunner runner = new TestRunner();
		runner.run();
	}
	
	@Test
	public void testLoadStories() {
		TestRunner runner = new TestRunner();
		assertEquals(2, runner.loadStories(null).size());
		assertEquals(1, runner.loadStories("br/materdei/feature/atualiza_usuario.story").size());
		assertEquals(1, runner.loadStories("br/materdei/feature/cadastra_usuario.estoria").size());
	}
	
	@Test(expected = RuntimeException.class)
	public void testLoadStoriesWhereResourceDoesNotExist() {
		TestRunner runner = new TestRunner();
		runner.loadStories("pacote/estoria/nao/existe.story");
		runner.loadStories("pacote/estoria/nao/existe.estoria");
	}
}