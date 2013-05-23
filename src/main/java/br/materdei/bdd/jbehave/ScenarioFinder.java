package br.materdei.bdd.jbehave;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.materdei.bdd.model.ThreadLocalModel;
import br.materdei.bdd.util.FileUtil;

public final class ScenarioFinder {

	private ScenarioFinder() {
		super();
	}
	
	public static List<String> find() {
		List<File> files = FileUtil.loadFiles(ThreadLocalModel.getJBehaveModel().getScenarioClassesPath());
		List<String> stories = new ArrayList<String>();
		
		for (File f : files) {
			if ((f.getAbsolutePath().endsWith("Cenario.java")) ||(f.getAbsolutePath().endsWith("Scenario.java"))) {
				stories.add(f.getAbsolutePath());
			}
		}
		
		return stories;
	}
}