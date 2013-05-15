package br.materdei.bdd.web.page;

import java.util.HashMap;
import java.util.Map;

import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumPage;

import br.materdei.bdd.jbehave.SeleniumServerControllerSingleton;
import br.materdei.bdd.web.annotation.PageObject;
import br.materdei.bdd.web.component.IComponent;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public abstract class BasicPageObject extends SeleniumPage implements IPage {

	private Map<String, IComponent<?>> components;
	
	public BasicPageObject(Selenium selenium, ConditionRunner conditionRunner) {
		super(selenium, conditionRunner);
		this.components = new HashMap<String, IComponent<?>>();
	}
	
	public BasicPageObject(ConditionRunner conditionRunner) {
		this(SeleniumServerControllerSingleton.getInstancia().getSelenium(), conditionRunner);
	}
	
	public BasicPageObject() {
		this(SeleniumServerControllerSingleton.getInstancia().getSelenium(),
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
	
	@Override
	public void verificaPagina() {
		for (IComponent<?> c : this.components.values()) {
			if (!c.isElementPresent()) {
				throw new RuntimeException("Componente '" + c.getId() + "' não está presente na página atual.");
			}
		}
	}
	
	@Override
	public IComponent<?> getComponent(String key) {
		return this.components.get(key);
	}
	
	@Override
	public void addComponents(IComponent<?> ... components) {
		for (IComponent<?> c : components) {
			this.addComponent(c);
		}
	}
	
	private void addComponent(IComponent<?> component) {
		if ((component == null) || (component.getId() == null)) {
			throw new IllegalArgumentException("Componente ou id do componente é nulo.");
		}
		
		this.components.put(component.getId(), component);
	}
}