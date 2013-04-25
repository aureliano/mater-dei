package br.materdei.bdd.codegen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.materdei.bdd.jbehave.ScenarioFinder;
import br.materdei.bdd.util.ClassUtil;

public final class ScenarioCreator {

	private ScenarioCreator() {
		super();
	}
	
	public static List<Object> instantiateScenarios() {
		List<Object> scenarios = new ArrayList<Object>();
		
		for (String path : ScenarioFinder.find()) {
			String className = ClassUtil.extractClassNameFromJavaFile(new File(path));
			Object scenario;
			
			try {
				scenario = Class.forName(className).newInstance();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
			
			scenarios.add(scenario);
		}
		
		return scenarios;
	}
}