package br.materdei.bdd.web.page;

import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumPage;

import br.materdei.bdd.jbehave.SeleniumServerControllerSingleton;
import br.materdei.bdd.web.annotation.PageObject;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public abstract class BasicPageObject extends SeleniumPage implements IPage {

	public BasicPageObject(Selenium selenium, ConditionRunner conditionRunner) {
		super(selenium, conditionRunner);
	}
	
	public BasicPageObject(ConditionRunner conditionRunner) {
		super(SeleniumServerControllerSingleton.getInstancia().getSelenium(), conditionRunner);
	}
	
	public BasicPageObject() {
		super(SeleniumServerControllerSingleton.getInstancia().getSelenium(),
				SeleniumConfiguration.defaultConditionRunner(
						SeleniumServerControllerSingleton.getInstancia().getSelenium()));
	}

	@Override
	public void abrePagina() {
		PageObject pageObject = this.getClass().getAnnotation(PageObject.class);
		if (pageObject == null) {
			throw new RuntimeException("Classes do tipo PageObject devem possuir anotação @PageObject!");
		}
		
		open(pageObject.url());
	}
}