package br.materdei.bdd.jbehave;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class StoryFinderTest {

	@Test
	public void testFind() {
		List<String> stories = StoryFinder.find();
		assertNotNull(stories);
		assertEquals(2, stories.size());
		
		assertTrue(stories.contains(new File("src/test/resources/br/materdei/feature/atualiza_usuario.story").getAbsolutePath()));
		assertTrue(stories.contains(new File("src/test/resources/br/materdei/feature/cadastra_usuario.estoria").getAbsolutePath()));
	}
	
	@Test
	public void testFindDisabledStories() {
		List<String> stories = StoryFinder.findDisabledStories();
		assertNotNull(stories);
		assertEquals(1, stories.size());
		
		assertTrue(stories.contains("br/materdei/feature/atualiza_usuario.story"));
		assertFalse(stories.contains("br/materdei/feature/cadastra_usuario.estoria"));
	}
}