package br.materdei.bdd.codegen;

import static java.util.Arrays.asList;

import org.apache.log4j.Logger;
import org.jbehave.core.embedder.Embedder;
import org.junit.After;
import org.junit.Before;

public class StoryBaseMock extends StoryBase {

	private static final Logger logger = Logger.getLogger(StoryBaseMock.class);
	
	private boolean beforeDone;
	private boolean afterDone;
	
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
		logger.info("\nExecutando beforeTest de => " + this.getClass().getName());
		this.beforeDone = true;
	}

	@After
	public void afterTest() throws Exception {
		logger.info("Executando afterTest de => " + this.getClass().getName() + "\n");
		this.afterDone = true;
	}
	
	public boolean isBeforeDone() {
		return this.beforeDone;
	}
	
	public boolean isAfterDone() {
		return this.afterDone;
	}
}