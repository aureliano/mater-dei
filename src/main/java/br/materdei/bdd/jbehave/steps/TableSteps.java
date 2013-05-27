package br.materdei.bdd.jbehave.steps;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import br.materdei.bdd.web.component.Table;
import br.materdei.bdd.web.page.POFinder;

public class TableSteps {
	
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
	
	@Then("eu devo ver tabela '$table' da página '$pageName' com o texto '$text'")
	public void entaoEuDevoVerTabelaNaPaginaComTexto(String table, String pageName, String text) {
		Table t = (Table) POFinder.findByName(pageName).getComponent(table);
		t.isElementPresent();
		if (!t.containsText(text)) {
			throw new RuntimeException(String.format("A tabela '%s' da página '%s' não contém o texto '%s'", table, pageName, text));
		}
	}
}