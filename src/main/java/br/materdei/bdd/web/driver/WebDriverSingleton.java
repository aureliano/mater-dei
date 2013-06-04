package br.materdei.bdd.web.driver;

import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.WebDriverProvider;

import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;
import br.materdei.bdd.model.ThreadLocalModel;
import br.materdei.bdd.model.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

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
	
	private WebDriverProvider webDriverProvider;

	private WebDriverSingleton() {
		this.configureWebDriverProvider();
	}
	
	private void configureWebDriverProvider() {
		this.webDriverProvider = WebDriverHelper.prepareWebDriverProvider();
	}
	
	public WebDriverProvider getWebDriverProvider() {
		return this.webDriverProvider;
	}
}