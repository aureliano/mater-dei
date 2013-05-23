package br.materdei.bdd.web.driver;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import br.materdei.bdd.model.ThreadLocalModel;
import br.materdei.bdd.model.WebDriver;

public final class WebDriverPreferences {

	private static final Logger logger = Logger.getLogger(WebDriverPreferences.class);
	
	public WebDriverPreferences() {
		super();
	}
	
	public static void applyPreferences() {
		Boolean alreadyLoaded = ThreadLocalModel.getWebDriverPreferencesDone();
		alreadyLoaded = (alreadyLoaded == null) ? false : alreadyLoaded.booleanValue();
		
		if (alreadyLoaded) {
			return;
		}		
		
		logger.info("Carregando preferências do web driver");
		WebDriver model = ThreadLocalModel.getWebDriverModel();
		org.openqa.selenium.WebDriver driver = WebDriverSingleton.get().getWebDriverProvider().get();
		
		logger.info("Web driver timeout: " + model.getDriverTimeout() + " segundos");
		driver.manage().timeouts().implicitlyWait(model.getDriverTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(model.getDriverTimeout(), TimeUnit.SECONDS);
		
		logger.info("Window maximized: " + model.isWindowMaximized());
		if (model.isWindowMaximized()) {
			driver.manage().window().maximize();
		}
		
		ThreadLocalModel.setWebDriverPreferencesDone(true);
	}
}