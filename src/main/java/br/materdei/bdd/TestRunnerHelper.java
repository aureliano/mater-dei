package br.materdei.bdd;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.materdei.bdd.jbehave.SeleniumServerControllerSingleton;
import br.materdei.bdd.jbehave.StoryFinder;
import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;

public final class TestRunnerHelper {

	private TestRunnerHelper() {
		super();
	}
	
	public static List<String> loadStories(String resourceName) {
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
	
	public static void startSelenium() {
		String ignore = BddConfigPropertiesEnum.IGNORE_SELENIUM_START_UP.getValue();
		if ((ignore == null) || ("false".equalsIgnoreCase(ignore))) {
			SeleniumServerControllerSingleton controlador = SeleniumServerControllerSingleton.getInstancia();
			controlador.iniciaServidorSelenium();
			controlador.iniciaSelenium();
			controlador.getSelenium().windowMaximize();
		}
	}
	
	public static void stopSelenium() {
		String ignore = BddConfigPropertiesEnum.IGNORE_SELENIUM_START_UP.getValue();
		if ((ignore == null) || ("false".equalsIgnoreCase(ignore))) {
			SeleniumServerControllerSingleton controlador = SeleniumServerControllerSingleton.getInstancia();
			controlador.paraSelenium();
			controlador.paraServidorSelenium();
		}
	}
	
	public static void printDisabledTests(List<String> files) {
		String ignore = BddConfigPropertiesEnum.IGNORE_SELENIUM_START_UP.getValue();
		if (((ignore != null) && ("true".equalsIgnoreCase(ignore))) || (files.isEmpty())) {
			return;
		}
		
		String lineBreak = "*******************************************************************************************************************";
		System.out.println();
		System.out.println(lineBreak);
		System.out.println("\tTESTES DE INTEGRAÇÃO DESABILITADOS");
		System.out.println(lineBreak);
		System.out.println();
		
		for (String file : files) {
			System.out.println(file);
		}
		
		System.out.println();
		System.out.println("TOTAL DE TESTES DESABILITADOS: " + files.size());
		System.out.println();
		System.out.println("PARA HABILITAR ALGUM TESTE REMOVA O CAMINHO RELATIVO DO TESTE DESEJADO DO ARQUIVO '" +
				BddConfigPropertiesEnum.DISABLED_TESTS_FILE.getValue() + "'.");
		System.out.println(lineBreak);
	}
}