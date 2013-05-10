package br.materdei.bdd.jbehave;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;
import br.materdei.bdd.util.FileUtil;

public final class StoryFinder {

	private StoryFinder() {
		super();
	}
	
	public static List<String> find() {
		List<File> files = FileUtil.loadFiles(BddConfigPropertiesEnum.JBEHAVE_STORIES_PATH.getValue());
		List<String> stories = new ArrayList<String>();
		
		for (File f : files) {
			if ((f.getAbsolutePath().endsWith(".story")) || (f.getAbsolutePath().endsWith(".estoria"))) {
				stories.add(f.getAbsolutePath());
			}
		}
		
		return stories;
	}
	
	public static List<String> findDisabledStories() {
		URL url = ClassLoader.getSystemResource(BddConfigPropertiesEnum.DISABLED_TESTS_FILE.getValue());
		if (url == null) {
			return new ArrayList<String>();
		}
		
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(new File(url.getFile()));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		return lines;
	}
}