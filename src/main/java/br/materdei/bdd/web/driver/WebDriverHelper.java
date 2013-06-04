package br.materdei.bdd.web.driver;

import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;
import br.materdei.bdd.model.ThreadLocalModel;
import br.materdei.bdd.model.WebDriver;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverHelper {

	private WebDriverHelper() {
		super();
	}

	public static WebDriverProvider prepareWebDriverProvider() {
		WebDriver model = ThreadLocalModel.getWebDriverModel();
		System.setProperty("browser", model.getBrowser().getKey().toLowerCase());

		if ("firefox".equalsIgnoreCase(model.getBrowser().getKey())) {
			return configureFirefoxWebDriverProvider();
		} else if ("chrome".equalsIgnoreCase(model.getBrowser().getKey())) {
			return configureGoogleChromeWebDriverProvider(model);
		} else if ("ie".equalsIgnoreCase(model.getBrowser().getKey())) {
			return configureInternetExplorerWebDriverProvider(model);
		} else {
			return configureFirefoxWebDriverProvider();
		}
	}

	private static WebDriverProvider configureFirefoxWebDriverProvider() {
		return new PropertyWebDriverProvider();
	}

	private static WebDriverProvider configureGoogleChromeWebDriverProvider(WebDriver model) {
	    	if (model.getChromeDriver() == null) {
			throw new RuntimeException("É necessário informar a localização do chromedriver através da propriedade webdriver.chrome.driver");
		} else {
			System.setProperty(BddConfigPropertiesEnum.WEB_DRIVER_CHROME_DRIVER.getKey(), model.getChromeDriver().getAbsolutePath());
		}

		return new PropertyWebDriverProvider();
	}

	private static WebDriverProvider configureInternetExplorerWebDriverProvider(WebDriver model) {
		if ("ie".equalsIgnoreCase(model.getBrowser().getKey())) {
			if (model.getInternetExplorerDriver() == null) {
				throw new RuntimeException("É necessário informar a localização do IEDriverServer.exe através da propriedade webdriver.ie.driver");
			} else {
				System.setProperty(BddConfigPropertiesEnum.WEB_DRIVER_INTERNET_EXPLORER_DRIVER.getKey(), model.getInternetExplorerDriver().getAbsolutePath());
			}
		}

		return new PropertyWebDriverProvider() {
			@Override
			protected InternetExplorerDriver createInternetExplorerDriver() {
				DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
				dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				dc.setCapability("ignoreZoomSetting", true);

				return new InternetExplorerDriver(dc);
			}
		};
	}
}