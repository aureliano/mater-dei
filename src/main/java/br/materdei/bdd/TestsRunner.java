package br.materdei.bdd;

import java.util.List;

import junit.textui.TestRunner;

import org.apache.log4j.Logger;

import br.materdei.bdd.codegen.BddTestCreator;
import br.materdei.bdd.codegen.StoryBase;
import br.materdei.bdd.jbehave.SeleniumServerControllerSingleton;
import br.materdei.bdd.jbehave.StoryFinder;
import br.materdei.bdd.jbehave.StoryNameParser;

public class TestsRunner {
	
	private static final Logger logger = Logger.getLogger(TestRunner.class);

	public void run() throws Throwable {
		this.execute(new TestModel());
	}
	
	public void run(TestModel model) throws Throwable {
		this.execute(model);
	}
	
	private void execute(TestModel model) throws Throwable {
		List<String> stories = loadStories(model.getStoryPath());
		List<String> disabledStories = StoryFinder.findDisabledStories();		
		stories.removeAll(disabledStories);
		
		this.prepareTestEnvironment();
		
		for (String story : stories) {
			String fileName = story.substring(story.lastIndexOf("/") + 1);
			String storyName = StoryNameParser.parse(story);
			
			StoryBase runnableStory = (StoryBase) BddTestCreator.create(model.getStoryBase(), storyName);
			model.useStoryPath(storyName.replaceAll("\\.", "/").substring(0, storyName.lastIndexOf(".") + 1) + fileName);
			
			logger.info("Executando estória " + model.getStoryPath());
			
			runnableStory.beforeTest();			
			runnableStory.run(model);
			runnableStory.afterTest();
		}
			
		this.tearDownTestEnvironment(disabledStories);
	}
	
	protected List<String> loadStories(String resourceName) {
		return TestRunnerHelper.loadStories(resourceName);
	}
	
	private void prepareTestEnvironment() {
		SeleniumServerControllerSingleton.createSeleniumResourcesFolder();
		TestRunnerHelper.startSelenium();
	}
	
	private void tearDownTestEnvironment(List<String> disabledStories) {
		TestRunnerHelper.stopSelenium();
		TestRunnerHelper.printDisabledTests(disabledStories);
	}
}