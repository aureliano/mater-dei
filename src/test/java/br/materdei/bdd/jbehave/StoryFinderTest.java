package br.materdei.bdd.jbehave;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class StoryFinderTest {

	@Test
	public void testFind() {
		List<String> stories = StoryFinder.find();
		assertNotNull(stories);
		assertEquals(2, stories.size());
	}
}