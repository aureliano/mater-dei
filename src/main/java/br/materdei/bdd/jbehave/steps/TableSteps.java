package br.materdei.bdd.jbehave.steps;

import static br.materdei.bdd.jbehave.steps.helper.TableStepsHelper.clickTableButtonByRowNumber;
import static br.materdei.bdd.jbehave.steps.helper.TableStepsHelper.clickTableButtonByRowValues;
import static br.materdei.bdd.jbehave.steps.helper.TableStepsHelper.clickTableLinkByRowNumber;
import static br.materdei.bdd.jbehave.steps.helper.TableStepsHelper.clickTableLinkByRowValues;
import static br.materdei.bdd.jbehave.steps.helper.TableStepsHelper.verifyTextInTable;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class TableSteps {
	
	@When("eu clico no botão '$button', na linha de número '$row', da tabela '$table', da página '$pageName'")
	public void quandoEuClicoBotaoTabelaPaginaNaLinha(String button, Integer row, String table, String pageName) {
		clickTableButtonByRowNumber(pageName, table, row, button);
	}
	
	@When("eu clico no botão '$button', do registro com o(s) valor(es) '$filter', da tabela '$table', da página '$pageName'")
	public void quandoEuClicoBotaoTabelaNaLinhaComValores(String button, String filter, String table, String pageName) {
		clickTableButtonByRowValues(pageName, table, filter, button);
	}
	
	@When("eu clico no link '$link', do registro com o(s) valor(es) '$filter', da tabela '$table', da página '$pageName'")
	public void quandoEuClicoLinhaTabelaNaLinhaComValores(String link, String filter, String table, String pageName) {
		clickTableLinkByRowValues(pageName, table, filter, link);
	}
	
	@When("eu clico no link '$link' da tabela '$table' da página '$pageName' na linha de número '$row'")
	public void quandoEuClicoLinhaTabelaNaLinha(String link, String table, String pageName, Integer row) {
		clickTableLinkByRowNumber(pageName, table, row, link);
	}
	
	@Then("eu devo ver tabela '$table' da página '$pageName' com o texto '$text'")
	public void entaoEuDevoVerTabelaNaPaginaComTexto(String table, String pageName, String text) {
		verifyTextInTable(pageName, table, text);
	}
}