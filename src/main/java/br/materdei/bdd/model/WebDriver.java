package br.materdei.bdd.model;

import java.io.File;

public class WebDriver {

	/**
	 * Tempo em segundos.
	 */
	public static final Integer DEFAULT_DRIVER_TIMEOUT = 30;
	
	private Integer driverTimeout;
	private Browser browser;
	private File chromeDriver;
	
	public WebDriver() {
		this.driverTimeout = DEFAULT_DRIVER_TIMEOUT;
		this.browser = Browser.FIREFOX;
	}

	public Integer getDriverTimeout() {
		return driverTimeout;
	}

	public WebDriver useDriverTimeout(Integer driverTimeout) {
		if (driverTimeout != null) {
			if (driverTimeout <= 0) {
				throw new IllegalArgumentException("Driver timeout deve ser maior que zero.");
			}
			this.driverTimeout = driverTimeout;
		}
		
		return this;
	}

	public Browser getBrowser() {
		return browser;
	}

	public WebDriver useBrowser(Browser browser) {
		if (browser != null) {
			this.browser = browser;
		}
		
		return this;
	}

	public File getChromeDriver() {
		return chromeDriver;
	}

	public WebDriver useChromeDriver(File chromeDriver) {
		this.chromeDriver = chromeDriver;
		if (!chromeDriver.exists()) {
			throw new IllegalArgumentException(chromeDriver.getAbsolutePath() + " não existe!");
		} else if (chromeDriver.isDirectory()) {
			throw new IllegalArgumentException(chromeDriver.getAbsolutePath() + " é um diretório! Deveria ser um arquivo binário");
		}
		
		return this;
	}
}