package br.materdei.bdd.codegen;

import static java.util.Arrays.asList;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.junit.JUnitStory;
import org.junit.After;
import org.junit.Before;

public class StoryBase extends JUnitStory {

	public StoryBase() {
		super();
	}
	
	public void run(String storyName) throws Throwable {
		Embedder embedder = configuredEmbedder();
        try {
            embedder.runStoriesAsPaths(asList(storyName));
        } finally {
            embedder.generateCrossReference();
        }
	}
	
	@Before
	public void beforeTest() {
		System.out.println("Iniciando teste => " + this.getClass().getSimpleName());
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Finalizando teste => " + this.getClass().getSimpleName());
	}
}