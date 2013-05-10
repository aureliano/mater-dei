package br.materdei.bdd.jbehave;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumStepMonitor;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public final class SeleniumServerControllerSingleton {
	
	private static final int PORTA_SELENIUM = Integer.parseInt(BddConfigPropertiesEnum.SELENIUM_PORT.getValue());
	private static volatile SeleniumServerControllerSingleton serverController = null;
	
	/**
	 * Obtém a instância do controlador do selenium server.
	 * 
	 * @return SeleniumServerControllerSingleton
	 */
	public static synchronized SeleniumServerControllerSingleton getInstancia() {
		if (serverController == null) {
			serverController = new SeleniumServerControllerSingleton();
		}

		return serverController;
	}
	
	public static void createSeleniumResourcesFolder() {
		try {
			FileUtils.forceMkdir(new File(BddConfigPropertiesEnum.SELENIUM_RESOURCES_FOLDER.getValue()));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private boolean servidorSeleniumIniciado;
	private boolean seleniumIniciado;
	private Selenium selenium;
	private SeleniumContext seleniumContext;
	private SeleniumServer seleniumServer;

	private SeleniumStepMonitor stepMonitor;

	private SeleniumServerControllerSingleton() {
		this.servidorSeleniumIniciado = false;
		this.seleniumIniciado = false;
	}

	private void configuraSelenium() {
		String host = BddConfigPropertiesEnum.SELENIUM_WEB_HOST.getValue();

		// Alterando para a porta PORTA_SELENIUM pra não dar conflito com processos do servidor web.
		int porta = PORTA_SELENIUM;
		String navegador = BddConfigPropertiesEnum.SELENIUM_BROWSER_LOCATION.getValue();
		String url = BddConfigPropertiesEnum.SELENIUM_HOME_PAGE.getValue();

		if (StringUtils.isEmpty(navegador)) {
			throw new RuntimeException("Você deve informar a localização do navegador através da propriedade 'selenium.browser.location' do arquivo 'bdd-config.properties'.");
		}
		
		selenium = new DefaultSelenium(host, porta, navegador, url);
		seleniumContext = new SeleniumContext();
		stepMonitor = new SeleniumStepMonitor(selenium, seleniumContext, new SilentStepMonitor());
	}

	private RemoteControlConfiguration getRemoteControlConfiguration() {
		RemoteControlConfiguration remoteControlConfiguration = new RemoteControlConfiguration();
		// Alterando para a porta PORTA_SELENIUM pra não dar conflito com processos do servidor web.
		remoteControlConfiguration.setPort(PORTA_SELENIUM);
		remoteControlConfiguration.setTrustAllSSLCertificates(Boolean.parseBoolean(BddConfigPropertiesEnum.SELENIUM_TRUST_ALL_SSL_CERTIFICATES.getValue()));
		
		return remoteControlConfiguration;
	}

	public Selenium getSelenium() {
		return selenium;
	}

	public SeleniumContext getSeleniumContext() {
		return seleniumContext;
	}

	public SeleniumServer getSeleniumServer() {
		return seleniumServer;
	}

	public SeleniumStepMonitor getStepMonitor() {
		return stepMonitor;
	}

	public void iniciaSelenium() {
		if (this.isSeleniumIniciado()) {
			return;
		}

		this.configuraSelenium();
		this.selenium.start();
		selenium.setTimeout(BddConfigPropertiesEnum.SELENIUM_TIMEOUT.getValue());
		this.seleniumIniciado = true;
	}

	private void iniciaSeleniumServer() throws Exception {
		seleniumServer = new SeleniumServer(this.getRemoteControlConfiguration());
		seleniumServer.start();
	}

	/**
	 * Inicia execução do Selenium Server. Use este método apenas se for
	 * executar os testes fora do Maven, pois o Maven já tem o plugin
	 * apropriado pra se iniciar o servidor nas fases de teste.
	 */
	public void iniciaServidorSelenium() {
		if (this.isServidorSeleniumIniciado()) {
			return;
		}

		try {
			if (!this.seleniumEstaSendoExecutado()) {
				this.iniciaSeleniumServer();
				this.servidorSeleniumIniciado = true;
			}
		} catch (Exception ex) {
			throw new RuntimeException("Falha ao iniciar Selenium Server. " + ex.getMessage(), ex);
		}
	}

	public boolean isSeleniumIniciado() {
		return seleniumIniciado;
	}

	public boolean isServidorSeleniumIniciado() {
		return servidorSeleniumIniciado;
	}

	public void paraSelenium() {
		this.selenium.stop();
		this.seleniumIniciado = false;
	}

	/**
	 * Para a execução do Selenium Server. Use este método apenas se for
	 * executar os testes fora do Maven, pois o Maven já tem o plugin
	 * apropriado pra se iniciar o servidor nas fases de teste.
	 */
	public void paraServidorSelenium() {
		if (!this.isServidorSeleniumIniciado()) {
			return;
		}

		seleniumServer.stop();
		this.servidorSeleniumIniciado = false;
	}

	private boolean seleniumEstaSendoExecutado() {
		try {
			Socket socket = new Socket(BddConfigPropertiesEnum.SELENIUM_WEB_HOST.getValue(), PORTA_SELENIUM);
			socket.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}
}