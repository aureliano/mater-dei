package br.materdei.bdd;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.materdei.bdd.codegen.BddTestCreator;
import br.materdei.bdd.codegen.StoryBase;
import br.materdei.bdd.jbehave.StoryFinder;
import br.materdei.bdd.jbehave.StoryNameParser;

public class TestsRunner {

	public void run() throws Throwable {
		this.execute(null, null);
	}
	
	public void run(String storyPath) throws Throwable {
		this.execute(null, storyPath);
	}
	
	public void run(Class<?> storyBase) throws Throwable {
		this.execute(storyBase, null);
	}
	
	public void run(Class<?> storyBase, String storyPath) throws Throwable {
		this.execute(storyBase, storyPath);
	}
	
	private void execute(Class<?> storyBase, String resourceName) throws Throwable {
		List<String> stories = loadStories(resourceName);
		for (String story : stories) {
			String fileName = story.substring(story.lastIndexOf("/") + 1);
			String storyName = StoryNameParser.parse(story);
						
			StoryBase runnableStory = (StoryBase) BddTestCreator.create(storyBase, storyName);
			runnableStory.beforeTest();
			runnableStory.run(storyName.replaceAll("\\.", "/").substring(0, storyName.lastIndexOf(".") + 1) + fileName);
			runnableStory.afterTest();
		}
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
}