package br.materdei.bdd.jbehave;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class ScenarioFinderTest {

	@Test
	public void testFind() {
		List<String> scenarios = ScenarioFinder.find();
		assertNotNull(scenarios);
		assertEquals(2, scenarios.size());
		
		assertTrue(scenarios.contains(new File("src/test/java/br/materdei/bdd/integration/AtualizaUsuarioCenario.java").getAbsolutePath()));
		assertTrue(scenarios.contains(new File("src/test/java/br/materdei/bdd/integration/CadastraUsuarioCenario.java").getAbsolutePath()));
	}
}