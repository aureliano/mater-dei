package br.materdei.bdd.jbehave;

import java.io.File;

public class StoryNameParser {

	private StoryNameParser() {
		super();
	}
	
	public static String parse(String storyPath) {
		File root = new File("");
		storyPath = storyPath.replaceFirst(root.getAbsolutePath(), "");
		storyPath = storyPath.replaceFirst("/src/(main|test)/resources/", "");
		storyPath = storyPath.replaceAll("/", ".");
		storyPath = storyPath.replaceAll("(.story|.estoria)", "");
		
		
		return storyPath;
	}
}