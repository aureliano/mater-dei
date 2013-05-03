package br.materdei.bdd;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.materdei.bdd.codegen.BddTestCreator;
import br.materdei.bdd.codegen.StoryBase;
import br.materdei.bdd.jbehave.SeleniumServerControllerSingleton;
import br.materdei.bdd.jbehave.StoryFinder;
import br.materdei.bdd.jbehave.StoryNameParser;
import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;

public class TestsRunner {

	public void run() throws Throwable {
		this.execute(new TestModel());
	}
	
	public void run(TestModel model) throws Throwable {
		this.execute(model);
	}
	
	private void execute(TestModel model) throws Throwable {
		List<String> stories = loadStories(model.getStoryPath());
		this.prepareSeleniumEnvironment();
		
		for (String story : stories) {
			String fileName = story.substring(story.lastIndexOf("/") + 1);
			String storyName = StoryNameParser.parse(story);
						
			StoryBase runnableStory = (StoryBase) BddTestCreator.create(model.getStoryBase(), storyName);
			model.useStoryPath(storyName.replaceAll("\\.", "/").substring(0, storyName.lastIndexOf(".") + 1) + fileName);
			
			runnableStory.beforeTest();			
			runnableStory.run(model);
			runnableStory.afterTest();
		}
			
		this.stopSelenium();
	}
	
	protected List<String> loadStories(String resourceName) {
		List<String> stories;
		
		if (resourceName == null) {
			stories = StoryFinder.find();
		} else {
			URL url = ClassLoader.getSystemResource(resourceName);
			if (url == null) {
				throw new RuntimeException("Não foi possível encontrar o recurso '" + resourceName + "' no Classpath do projeto.");
			}
			
			stories = new ArrayList<String>();
			stories.add(resourceName);
		}
		
		return stories;
	}
	
	private void prepareSeleniumEnvironment() {
		SeleniumServerControllerSingleton.createSeleniumResourcesFolder();
		this.startSelenium();
	}
	
	private void startSelenium() {
		String ignore = BddConfigPropertiesEnum.IGNORE_SELENIUM_START_UP.getValue();
		if ((ignore == null) || ("false".equalsIgnoreCase(ignore))) {
			SeleniumServerControllerSingleton controlador = SeleniumServerControllerSingleton.getInstancia();
			controlador.iniciaServidorSelenium();
			controlador.iniciaSelenium();
			controlador.getSelenium().windowMaximize();
		}
	}
	
	private void stopSelenium() {
		String ignore = BddConfigPropertiesEnum.IGNORE_SELENIUM_START_UP.getValue();
		if ((ignore == null) || ("false".equalsIgnoreCase(ignore))) {
			SeleniumServerControllerSingleton controlador = SeleniumServerControllerSingleton.getInstancia();
			controlador.paraSelenium();
			controlador.paraServidorSelenium();
		}
	}
}