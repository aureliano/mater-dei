package br.materdei.bdd.web.driver;

import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.WebDriverProvider;

import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;
import br.materdei.bdd.model.ThreadLocalModel;
import br.materdei.bdd.model.WebDriver;

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
		WebDriver model = ThreadLocalModel.getWebDriverModel();
				
		System.setProperty("browser", model.getBrowser().getKey().toLowerCase());
		if ("chrome".equalsIgnoreCase(model.getBrowser().getKey())) {
			if (model.getChromeDriver() == null) {
				throw new RuntimeException("É necessário informar a localização do chromedriver através da propriedade webdriver.chrome.driver");
			} else {
				System.setProperty(BddConfigPropertiesEnum.WEB_DRIVER_CHROME_DRIVER.getKey(), model.getChromeDriver().getAbsolutePath());
			}
		}		
		
		this.webDriverProvider = new PropertyWebDriverProvider();
	}
	
	public WebDriverProvider getWebDriverProvider() {
		return this.webDriverProvider;
	}
}