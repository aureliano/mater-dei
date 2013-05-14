package br.materdei.bdd.steps;

import static br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum.SELENIUM_TIMEOUT;

import org.jbehave.core.annotations.When;

import br.materdei.bdd.jbehave.SeleniumServerControllerSingleton;
import br.materdei.bdd.web.page.POFinder;

import com.thoughtworks.selenium.Selenium;

public class WebSteps {

	private Selenium driver;
	
	public WebSteps() {
		this.driver = SeleniumServerControllerSingleton.getInstancia().getSelenium();
	}
	
	@When("eu acesso a página pela url '$url'")
	public void dadoQueEuAcessoPaginaPelaUrl(String url) {
		this.driver.open(url);
		this.driver.waitForPageToLoad(SELENIUM_TIMEOUT.getValue());
	}
	
	@When("eu acesso a página '$nomePagina'")
	public void dadoQueEuAcessoPagina(String nomePagina) {
		POFinder.findByName(nomePagina).abrePagina();
		this.driver.waitForPageToLoad(SELENIUM_TIMEOUT.getValue());
	}
	
	@When("eu clico no botão '$botao'")
	public void quandoEuClicoBotao(String botao) {
		this.driver.click("//input[@value='" + botao + "']");
		this.driver.waitForPageToLoad(SELENIUM_TIMEOUT.getValue());
	}
	
	@When("eu clico no link '$link'")
	public void quandoEuClicoLink(String link) {
		this.driver.click("link=" + link);
		this.driver.waitForPageToLoad(SELENIUM_TIMEOUT.getValue());
	}
}