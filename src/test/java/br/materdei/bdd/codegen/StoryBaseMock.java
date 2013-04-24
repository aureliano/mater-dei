package br.materdei.bdd.codegen;

import static java.util.Arrays.asList;

import org.jbehave.core.embedder.Embedder;
import org.junit.After;
import org.junit.Before;

public class StoryBaseMock extends StoryBase {

	public StoryBaseMock() {
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
		System.out.println("\nExecutando beforeTest de => " + this.getClass().getName());
	}

	@After
	public void afterTest() throws Exception {
		System.out.println("Executando afterTest de => " + this.getClass().getName() + "\n");
	}
}