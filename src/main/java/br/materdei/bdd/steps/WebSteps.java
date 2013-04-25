package br.materdei.bdd.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;

import static br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum.SELENIUM_TIMEOUT;

import br.materdei.bdd.jbehave.SeleniumServerControllerSingleton;

import com.thoughtworks.selenium.Selenium;

public class WebSteps {

	private Selenium driver;
	
	public WebSteps() {
		this.driver = SeleniumServerControllerSingleton.getInstancia().getSelenium();
	}
	
	@Given("eu acesso a página '$pagina'")
	public void dadoQueEuAcessoPagina(String pagina) {
		this.driver.open(pagina);
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