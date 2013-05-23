package br.materdei.bdd;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.junit.After;
import org.junit.Before;

import br.materdei.bdd.codegen.StoryBase;
import br.materdei.bdd.jbehave.StoryFinder;
import br.materdei.bdd.model.JBehave;
import br.materdei.bdd.model.ThreadLocalModel;
import br.materdei.bdd.model.WebDriver;
import br.materdei.bdd.util.ClassUtil;
import br.materdei.bdd.util.FileUtil;

public final class TestRunnerHelper {
	
	private static final Logger logger = Logger.getLogger(TestRunnerHelper.class);
	
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
	
	public static void runBeforeMethods(StoryBase story) throws Exception {
		ClassUtil.executeMethodsWithAnnotation(story, Before.class);
		ClassUtil.executeMethodsWithAnnotation(story, BeforeStory.class);
		ClassUtil.executeMethodsWithAnnotation(story, BeforeStories.class);
	}
	
	public static void runAfterMethods(StoryBase story) throws Exception {
		ClassUtil.executeMethodsWithAnnotation(story, After.class);
		ClassUtil.executeMethodsWithAnnotation(story, AfterStory.class);
		ClassUtil.executeMethodsWithAnnotation(story, AfterStories.class);
	}
	
	public static void copyJBehaveSiteResources() {
		if (!shouldExecuteTests()) {
			return;
		}
		
		WebDriver webDriverModel = ThreadLocalModel.getWebDriverModel();
		JBehave jbehaveModel = ThreadLocalModel.getJBehaveModel();
		
		String urlResources = "https://dl.dropboxusercontent.com/s/b0hoin4wghahik5/jbehave-site-resources.zip";
		File jbehaveSiteDir = new File(jbehaveModel.getReportOutputDir() + "/jbehave-site-resources.zip");
		logger.info("COPIANDO RECURSOS DE FORMATAÇÃO (css, imagens e js) DO RELATÓRIO DE TESTES PARA " + jbehaveSiteDir.getParent());
		
		if (jbehaveSiteDir.exists()) {
			return;
		}
		
		try {
			FileUtils.copyURLToFile(new URL(urlResources), jbehaveSiteDir, (webDriverModel.getDriverTimeout() * 1000), (webDriverModel.getDriverTimeout() * 1000));
			FileUtil.extractFromZipFile(jbehaveSiteDir.getAbsolutePath(), jbehaveModel.getReportOutputDir());
		} catch (IOException ex) {
			logger.warn("Não foi possível copiar o recurso jbehave-site-resources. " + ex.getMessage());
		}
	}
	
	public static void printDisabledTests(List<String> files) {
		if (!shouldExecuteTests()) {
			return;
		}
		
		JBehave jbehave = ThreadLocalModel.getJBehaveModel();
		if (files.isEmpty()) {
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
		System.out.println("PARA HABILITAR ALGUM TESTE REMOVA O CAMINHO RELATIVO DO TESTE DESEJADO DO ARQUIVO '" + jbehave.getDisabledTestsFile() + "'.");
		System.out.println(lineBreak);
	}
	
	public static boolean shouldExecuteTests() {
		return !(ThreadLocalModel.getJBehaveModel().isIgnoreTestExecution());
	}
}