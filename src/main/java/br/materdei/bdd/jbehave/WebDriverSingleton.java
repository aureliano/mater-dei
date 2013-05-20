package br.materdei.bdd.jbehave;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.WebDriverProvider;

import br.materdei.bdd.TestRunnerHelper;
import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;

public final class WebDriverSingleton {
	
	private static volatile WebDriverSingleton serverController = null;
	
	
	/**
	 * Obtém a instância do controlador do selenium server.
	 * 
	 * @return SeleniumServerControllerSingleton
	 */
	public static synchronized WebDriverSingleton get() {
		if (serverController == null) {
			serverController = new WebDriverSingleton();
		}

		return serverController;
	}
	
	// TODO: Remova-me!!!
	public static void createSeleniumResourcesFolder() {
		try {
			FileUtils.forceMkdir(new File(BddConfigPropertiesEnum.SELENIUM_RESOURCES_FOLDER.getValue()));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private WebDriverProvider webDriverProvider;

	private WebDriverSingleton() {
		if (TestRunnerHelper.shouldExecuteTests()) {
			this.configureWebDriverProvider();
		}
	}
	
	private void configureWebDriverProvider() {
		String navegador = BddConfigPropertiesEnum.SELENIUM_BROWSER_LOCATION.getValue();
				
		if ((!"firefox".equalsIgnoreCase(navegador)) && (!"chrome".equalsIgnoreCase(navegador)) &&
				(!"ie".equalsIgnoreCase(navegador)) && (!"android".equalsIgnoreCase(navegador)) &&
				(!"htmlunit".equalsIgnoreCase(navegador))) {
			throw new RuntimeException("Navegador '" + navegador + "' não suportado. Permitido [firefox, chrome, ie, android, htmlunit]");
		}
		
		System.setProperty("browser", navegador);
		this.webDriverProvider = new PropertyWebDriverProvider();
	}
	
	public WebDriverProvider getWebDriverProvider() {
		return this.webDriverProvider;
	}
}