package br.materdei.bdd.jbehave;

import java.io.IOException;
import java.net.Socket;

import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumStepMonitor;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumServerControllerSingleton {
	
	private static final int PORTA_SELENIUM = 2222;
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
		String host = BddProperties.getPropriedade("selenium.web.host");

		// Alterando para a porta PORTA_SELENIUM pra não dar conflito com processos do servidor web.
		int porta = PORTA_SELENIUM;
		String navegador = BddProperties.getPropriedade("browser.location");
		String url = BddProperties.getPropriedade("project.home.page");

		selenium = new DefaultSelenium(host, porta, navegador, url);
		seleniumContext = new SeleniumContext();
		stepMonitor = new SeleniumStepMonitor(selenium, seleniumContext, new SilentStepMonitor());
	}

	private RemoteControlConfiguration getRemoteControlConfiguration() {
		RemoteControlConfiguration remoteControlConfiguration = new RemoteControlConfiguration();
		// Alterando para a porta PORTA_SELENIUM pra não dar conflito com processos do servidor web.
		remoteControlConfiguration.setPort(PORTA_SELENIUM);

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
			Socket socket = new Socket("127.0.0.1", PORTA_SELENIUM);
			socket.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}
}