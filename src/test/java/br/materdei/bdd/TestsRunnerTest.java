package br.materdei.bdd;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestsRunnerTest {

	@Test
	public void testRun() throws Throwable {
		TestsRunner runner = new TestsRunner();
		runner.run();
	}
	
	@Test
	public void testLoadStories() {
		TestsRunner runner = new TestsRunner();
		assertEquals(2, runner.loadStories(null).size());
		assertEquals(1, runner.loadStories("br/materdei/feature/atualiza_usuario.story").size());
		assertEquals(1, runner.loadStories("br/materdei/feature/cadastra_usuario.estoria").size());
	}
	
	@Test(expected = RuntimeException.class)
	public void testLoadStoriesWhereResourceDoesNotExist() {
		TestsRunner runner = new TestsRunner();
		runner.loadStories("pacote/estoria/nao/existe.story");
		runner.loadStories("pacote/estoria/nao/existe.estoria");
	}
}