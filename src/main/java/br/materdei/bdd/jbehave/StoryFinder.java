package br.materdei.bdd.jbehave;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
		File resources = new File(new File("").getAbsolutePath() + "/src/main/resources");
		List<File> mainResources = new ArrayList<File>();
		loadFiles(mainResources, resources);
		
		resources = new File(new File("").getAbsolutePath() + "/src/test/resources");
		List<File> testResources = new ArrayList<File>();
		loadFiles(testResources, resources);
		
		mainResources.addAll(testResources);
		return mainResources;
	}
	
	private static void loadFiles(List<File> files, File seed) {
		if (seed.isDirectory()) {
			File[] _files = seed.listFiles();
			for (File f : _files) {
				loadFiles(files, f);
			}
		} else {
			files.add(seed);
		}
	}
}