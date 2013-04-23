package br.materdei.bdd;

import org.junit.Test;

public class TestsRunnerTest {

	@Test
	public void testRun() throws Throwable {
		TestsRunner runner = new TestsRunner();
		runner.run();
	}
}