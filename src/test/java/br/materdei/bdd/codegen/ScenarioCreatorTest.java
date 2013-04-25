package br.materdei.bdd.codegen;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.materdei.bdd.integration.AtualizaUsuarioCenario;
import br.materdei.bdd.integration.CadastraUsuarioCenario;

public class ScenarioCreatorTest {

	@Test
	public void testInstantiateScenarios() {
		List<Object> scenarios = ScenarioCreator.instantiateScenarios();
		assertNotNull(scenarios);
		assertEquals(2, scenarios.size());
		
		Object[] expected = { AtualizaUsuarioCenario.class.getName(), CadastraUsuarioCenario.class.getName() };
		Object[] actual = new Object[scenarios.size()];
		
		for (short i = 0; i < actual.length; i++) {
			actual[i] = scenarios.get(i).getClass().getName();
		}
		
		Arrays.sort(expected);
		Arrays.sort(actual);
		
		assertArrayEquals(expected, actual);
	}
}