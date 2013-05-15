package br.materdei.bdd.steps;

import static br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum.SELENIUM_TIMEOUT;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import br.materdei.bdd.jbehave.SeleniumServerControllerSingleton;
import br.materdei.bdd.web.component.TextField;
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
	
	@When("eu acesso a página '$pageName'")
	public void dadoQueEuAcessoPagina(String pageName) {
		POFinder.findByName(pageName).abrePagina();
		this.driver.waitForPageToLoad(SELENIUM_TIMEOUT.getValue());
	}
	
	@When("eu clico no botão '$button'")
	public void quandoEuClicoBotao(String button) {
		this.driver.click("//input[@value='" + button + "']");
		this.driver.waitForPageToLoad(SELENIUM_TIMEOUT.getValue());
	}
	
	@When("eu clico no link '$link'")
	public void quandoEuClicoLink(String link) {
		this.driver.click("link=" + link);
		this.driver.waitForPageToLoad(SELENIUM_TIMEOUT.getValue());
	}
	
	@When("eu preencho o campo '$fieldId' da página '$pageName' com '$value'")
	public void quandoEuPreenchoCampoDaPaginaComValor(String fieldId, String pageName, String value) {
		TextField txtField = (TextField) POFinder.findByName(pageName).getComponent(fieldId);
		txtField.type(value);
	}
	
	@When("eu clico no botão '$buttonId' da página '$pageName'")
	public void quandoEuClicoBotaoDaPagina(String buttonId, String pageName) {
		POFinder.findByName(pageName).getComponent(buttonId).click();
	}
	
	@Then("eu devo ver a página '$pageName'")
	public void entaoEuDevoVerPagina(String pageName) {
		POFinder.findByName(pageName).verificaPagina();
	}
}