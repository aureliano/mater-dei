package br.materdei.bdd.jbehave;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import br.materdei.bdd.model.ThreadLocalModel;
import br.materdei.bdd.util.FileUtil;

public final class StoryFinder {

	private StoryFinder() {
		super();
	}
	
	public static List<String> find() {
		List<File> files = FileUtil.loadFiles(ThreadLocalModel.getJBehaveModel().getStoriesPath());
		List<String> stories = new ArrayList<String>();
		
		for (File f : files) {
			if ((f.getAbsolutePath().endsWith(".story")) || (f.getAbsolutePath().endsWith(".estoria"))) {
				stories.add(f.getAbsolutePath());
			}
		}
		
		return stories;
	}
	
	public static List<String> findDisabledStories() {
		String disabledTestsFile = ThreadLocalModel.getJBehaveModel().getDisabledTestsFile();
		if (StringUtils.isEmpty(disabledTestsFile)) {
			return new ArrayList<String>();
		}
		
		URL url = ClassLoader.getSystemResource(disabledTestsFile);
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