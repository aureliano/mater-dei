package br.materdei.bdd.jbehave.steps;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

import br.materdei.bdd.web.component.Table;
import br.materdei.bdd.web.component.TextField;
import br.materdei.bdd.web.driver.WebDriverSingleton;
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
	
	@When("eu clico no botão '$button', na linha de número '$row', da tabela '$table', da página '$pageName'")
	public void quandoEuClicoBotaoTabelaPaginaNaLinha(String button, Integer row, String table, String pageName) {
		Table t = (Table) POFinder.findByName(pageName).getComponent(table);
		t.clickButton(button, row);
	}
	
	@When("eu clico no botão '$button', do registro com o(s) valor(es) '$filter', da tabela '$table', da página '$pageName'")
	public void quandoEuClicoBotaoTabelaNaLinhaComValores(String button, String filter, String table, String pageName) {
		Table t = (Table) POFinder.findByName(pageName).getComponent(table);
		String[] filters = filter.split(",");
		StringBuilder b = new StringBuilder();
		
		for (String f : filters) {
			String[] tokens = f.split("[=|-]");
			b.append("/td[" + tokens[0] + "][text()='" + tokens[1] + "']/..");
		}
		
		t.clickButton(button, b.toString());
	}
	
	@When("eu clico no link '$link', do registro com o(s) valor(es) '$filter', da tabela '$table', da página '$pageName'")
	public void quandoEuClicoLinhaTabelaNaLinhaComValores(String link, String filter, String table, String pageName) {
		Table t = (Table) POFinder.findByName(pageName).getComponent(table);
		String[] filters = filter.split(",");
		StringBuilder b = new StringBuilder();
		
		for (String f : filters) {
			String[] tokens = f.split("[=|-]");
			b.append("/td[" + tokens[0] + "][text()='" + tokens[1] + "']/..");
		}
		
		t.clickLink(link, b.toString());
	}
	
	@When("eu clico no link '$link' da tabela '$table' da página '$pageName' na linha de número '$row'")
	public void quandoEuClicoLinhaTabelaNaLinha(String link, String table, String pageName, Integer row) {
		Table t = (Table) POFinder.findByName(pageName).getComponent(table);
		t.clickLink(link, row);
	}
	
	@When("eu clico no botão '$buttonId' da página '$pageName'")
	public void quandoEuClicoBotaoDaPagina(String buttonId, String pageName) {
		POFinder.findByName(pageName).getComponent(buttonId).click();
	}
	
	@When("eu clico no botão '$button'")
	public void quandoEuClicoBotao(String button) {
		this.provider.get().findElement(By.xpath("//input[@value='" + button + "']")).click();
	}
	
	@When("eu clico no link '$link'")
	public void quandoEuClicoLink(String link) {
		this.provider.get().findElement(By.xpath("//a[text()='" + link + "']")).click();
	}
	
	@When("eu preencho o campo '$fieldId' da página '$pageName' com '$value'")
	public void quandoEuPreenchoCampoDaPaginaComValor(String fieldId, String pageName, String value) {
		TextField txtField = (TextField) POFinder.findByName(pageName).getComponent(fieldId);
		txtField.type(value);
	}
	
	@Then("eu devo ver a página '$pageName'")
	public void entaoEuDevoVerPagina(String pageName) {
		POFinder.findByName(pageName).verificaPagina();
	}
	
	@Then("eu devo ver tabela '$table' da página '$pageName' com o texto '$text'")
	public void entaoEuDevoVerTabelaNaPaginaComTexto(String table, String pageName, String text) {
		Table t = (Table) POFinder.findByName(pageName).getComponent(table);
		t.isElementPresent();
		if (!t.containsText(text)) {
			throw new RuntimeException(String.format("A tabela '%s' da página '%s' não contém o texto '%s'", table, pageName, text));
		}
	}
}