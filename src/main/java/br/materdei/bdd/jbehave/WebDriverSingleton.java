package br.materdei.bdd.jbehave;

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
	
	private WebDriverProvider webDriverProvider;

	private WebDriverSingleton() {
		if (TestRunnerHelper.shouldExecuteTests()) {
			this.configureWebDriverProvider();
		}
	}
	
	private void configureWebDriverProvider() {
		String navegador = BddConfigPropertiesEnum.WEB_DRIVER_BROWSER.getValue();
				
		if ((!"firefox".equalsIgnoreCase(navegador)) && (!"chrome".equalsIgnoreCase(navegador)) &&
				(!"ie".equalsIgnoreCase(navegador)) && (!"android".equalsIgnoreCase(navegador)) &&
				(!"htmlunit".equalsIgnoreCase(navegador))) {
			throw new RuntimeException("Navegador '" + navegador + "' não suportado. Permitido [firefox, chrome, ie, android, htmlunit]");
		}
		
		System.setProperty("browser", navegador);
		System.setProperty(BddConfigPropertiesEnum.WEB_DRIVER_CHROME_LOCATION.getKey(), BddConfigPropertiesEnum.WEB_DRIVER_CHROME_LOCATION.getValue());
		
		this.webDriverProvider = new PropertyWebDriverProvider();
	}
	
	public WebDriverProvider getWebDriverProvider() {
		return this.webDriverProvider;
	}
}