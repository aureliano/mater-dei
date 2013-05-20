package br.materdei.bdd;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import br.materdei.bdd.jbehave.WebDriverSingleton;
import br.materdei.bdd.jbehave.StoryFinder;
import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;
import br.materdei.bdd.util.FileUtil;

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
	
	public static void configureBrowser() {
		String ignore = BddConfigPropertiesEnum.IGNORE_SELENIUM_START_UP.getValue();
		if ((ignore == null) || ("false".equalsIgnoreCase(ignore))) {
			//WebDriverSingleton.getInstancia().getWebDriver().manage().window().maximize();
		}
	}
	
	public static void quitBrowser() {
		String ignore = BddConfigPropertiesEnum.IGNORE_SELENIUM_START_UP.getValue();
		if ((ignore == null) || ("false".equalsIgnoreCase(ignore))) {
			//WebDriverSingleton.getInstancia().getWebDriver().quit();
		}
	}
	
	public static void copyJBehaveSiteResources() {
		String ignore = BddConfigPropertiesEnum.IGNORE_SELENIUM_START_UP.getValue();
		if ((ignore != null) && ("true".equalsIgnoreCase(ignore))) {
			return;
		}
		
		String urlResources = "https://dl.dropboxusercontent.com/s/b0hoin4wghahik5/jbehave-site-resources.zip";
		Integer connectionTimeout = Integer.parseInt(BddConfigPropertiesEnum.SELENIUM_TIMEOUT.getValue());
		Integer readTimeout = Integer.parseInt(BddConfigPropertiesEnum.SELENIUM_TIMEOUT.getValue());
		File jbehaveSiteDir = new File(BddConfigPropertiesEnum.JBEHAVE_REPORT_OUTPUT_DIR.getValue() + "/jbehave-site-resources.zip");
		System.out.println("COPIANDO RECURSOS DE FORMATAÇÃO (css, imagens e js) DO RELATÓRIO DE TESTES PARA " + jbehaveSiteDir.getParent());
		
		if (jbehaveSiteDir.exists()) {
			return;
		}
		
		try {
			FileUtils.copyURLToFile(new URL(urlResources), jbehaveSiteDir, connectionTimeout, readTimeout);
			FileUtil.extractFromZipFile(jbehaveSiteDir.getAbsolutePath(), BddConfigPropertiesEnum.JBEHAVE_REPORT_OUTPUT_DIR.getValue());
		} catch (IOException ex) {
			System.out.println("WARN: Não foi possível copiar o recurso jbehave-site-resources. " + ex.getMessage());
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