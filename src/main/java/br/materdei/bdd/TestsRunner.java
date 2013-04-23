package br.materdei.bdd;

import java.util.List;

import br.materdei.bdd.codegen.BddTestCreator;
import br.materdei.bdd.codegen.StoryBase;
import br.materdei.bdd.jbehave.StoryFinder;
import br.materdei.bdd.jbehave.StoryNameParser;

public class TestsRunner {

	public void run() throws Throwable {
		List<String> stories = StoryFinder.find();
		for (String story : stories) {
			String extension = story.substring(story.lastIndexOf("."));
			String storyName = StoryNameParser.parse(story);
						
			StoryBase runnableStory = (StoryBase) BddTestCreator.create(storyName);
			runnableStory.run(storyName.replaceAll("\\.", "/") + extension);
		}
	}
}