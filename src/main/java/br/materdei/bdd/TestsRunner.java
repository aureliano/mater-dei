package br.materdei.bdd;

import java.util.List;

import br.materdei.bdd.codegen.BddTestCreator;
import br.materdei.bdd.codegen.StoryBase;
import br.materdei.bdd.jbehave.StoryFinder;
import br.materdei.bdd.jbehave.StoryNameParser;

public class TestsRunner {

	public void run() throws Throwable {
		this.execute(null);
	}
	
	public void run(Class<?> storyBase) throws Throwable {
		this.execute(storyBase);
	}
	
	private void execute(Class<?> storyBase) throws Throwable {
		List<String> stories = StoryFinder.find();
		for (String story : stories) {
			String fileName = story.substring(story.lastIndexOf("/") + 1);
			String storyName = StoryNameParser.parse(story);
						
			StoryBase runnableStory = (StoryBase) BddTestCreator.create(storyBase, storyName);
			runnableStory.beforeTest();
			runnableStory.run(storyName.replaceAll("\\.", "/").substring(0, storyName.lastIndexOf(".") + 1) + fileName);
			runnableStory.afterTest();
		}
	}
}