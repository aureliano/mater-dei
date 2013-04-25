package br.materdei.bdd.jbehave;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.materdei.bdd.util.FileUtil;

public final class StoryFinder {

	private StoryFinder() {
		super();
	}
	
	public static List<String> find() {
		List<File> files = loadResources();
		List<String> stories = new ArrayList<String>();
		
		for (File f : files) {
			if ((f.getAbsolutePath().endsWith(".story")) || (f.getAbsolutePath().endsWith(".estoria"))) {
				stories.add(f.getAbsolutePath());
			}
		}
		
		return stories;
	}
	
	private static List<File> loadResources() {
		List<File> mainResources = FileUtil.loadFiles("src/main/resources");
		List<File> testResources = FileUtil.loadFiles("src/test/resources");
		
		mainResources.addAll(testResources);
		return mainResources;
	}
}