package br.materdei.bdd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestRunnerTest {

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