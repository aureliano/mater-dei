package br.materdei.bdd.steps;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

import br.materdei.bdd.jbehave.WebDriverSingleton;
import br.materdei.bdd.web.component.TextField;
import br.materdei.bdd.web.page.POFinder;

public class WebSteps {

	private WebDriverProvider provider;
	
	public WebSteps() {
		this.provider = WebDriverSingleton.get().getWebDriverProvider();
	}
	
	@When("eu acesso a página pela url '$url'")
	public void dadoQueEuAcessoPaginaPelaUrl(String url) {
		this.provider.get().get(url);
	}
	
	@When("eu acesso a página '$pageName'")
	public void dadoQueEuAcessoPagina(String pageName) {
		POFinder.findByName(pageName).abrePagina();
	}
	
	@When("eu clico no botão '$button'")
	public void quandoEuClicoBotao(String button) {
		this.provider.get().findElement(By.xpath("//input[@value='" + button + "']")).click();
	}
	
	@When("eu clico no link '$link'")
	public void quandoEuClicoLink(String link) {
		this.provider.get().findElement(By.xpath("link=" + link)).click();
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