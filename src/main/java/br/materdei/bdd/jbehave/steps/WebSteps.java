package br.materdei.bdd.jbehave.steps;

import static br.materdei.bdd.jbehave.steps.helper.WebStepsHelper.clickButton;
import static br.materdei.bdd.jbehave.steps.helper.WebStepsHelper.clickLink;
import static br.materdei.bdd.jbehave.steps.helper.WebStepsHelper.confirmModalMessage;
import static br.materdei.bdd.jbehave.steps.helper.WebStepsHelper.dismissModalMessage;
import static br.materdei.bdd.jbehave.steps.helper.WebStepsHelper.openPageByName;
import static br.materdei.bdd.jbehave.steps.helper.WebStepsHelper.openPageByUrl;
import static br.materdei.bdd.jbehave.steps.helper.WebStepsHelper.type;
import static br.materdei.bdd.jbehave.steps.helper.WebStepsHelper.verifyPage;
import static br.materdei.bdd.jbehave.steps.helper.WebStepsHelper.selectComboItemByLabel;
import static br.materdei.bdd.jbehave.steps.helper.WebStepsHelper.selectComboItemByValue;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class WebSteps {
	
	@When("eu acesso a página pela url '$url'")
	public void dadoQueEuAcessoPaginaPelaUrl(String url) {
		openPageByUrl(url);
	}
	
	@When("eu acesso a página '$pageName'")
	public void dadoQueEuAcessoPagina(String pageName) {
		openPageByName(pageName);
	}
	
	@When("eu clico no botão '$buttonId' da página '$pageName'")
	public void quandoEuClicoBotaoDaPagina(String buttonId, String pageName) {
		clickButton(pageName, buttonId);
	}
	
	@When("eu clico no botão '$button'")
	public void quandoEuClicoBotao(String button) {
		clickButton(button);
	}
	
	@When("eu clico no link '$linkId' da página '$pageName'")
	public void quandoEuClicoNoLinkDaPagina(String linkId, String pageName) {
		clickLink(pageName, linkId);
	}
	
	@When("eu clico no link '$link'")
	public void quandoEuClicoLink(String link) {
		clickLink(link);
	}
	
	@When("eu preencho o campo '$fieldId' da página '$pageName' com '$value'")
	public void quandoEuPreenchoCampoDaPaginaComValor(String fieldId, String pageName, String value) {
		type(pageName, fieldId, value);
	}
	
	@Then("eu devo ver a página '$pageName'")
	public void entaoEuDevoVerPagina(String pageName) {
		verifyPage(pageName);
	}
	
	@When("eu aceito a mensagem de confirmação '$message'")
	public void quandoEuAceitoMensagemConfirmacao(String message) {
		confirmModalMessage(message);
	}
	
	@When("eu rejeito a mensagem de confirmação '$message'")
	public void quandoEuRejeitoMensagemConfirmacao(String message) {
		dismissModalMessage(message);
	}
	
	@When("eu seleciono no campo '$field' da página '$pageName' o valor '$value'")
	public void quandoEuSelecionoNoCampoDaPaginaValor(String field, String pageName, String value) {
		selectComboItemByValue(pageName, field, value);
	}
	
	@When("eu seleciono no campo '$field' da página '$pageName' o texto '$text'")
	public void quandoEuSelecionoNoCampoDaPaginaTexto(String field, String pageName, String text) {
		selectComboItemByLabel(pageName, field, text);
	}
}