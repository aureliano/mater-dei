package br.materdei.bdd.web.page;

import java.util.HashMap;
import java.util.Map;

import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;

import br.materdei.bdd.web.annotation.PageObject;
import br.materdei.bdd.web.component.IComponent;
import br.materdei.bdd.web.driver.WebDriverSingleton;

public abstract class BasicPageObject extends WebDriverPage implements IPage {

	private Map<String, IComponent<?>> components;
	protected WebDriverProvider driverProvider;
	
	public BasicPageObject(WebDriverProvider driverProvider) {
		super(driverProvider);
		this.components = new HashMap<String, IComponent<?>>();
		this.driverProvider = WebDriverSingleton.get().getWebDriverProvider();
	}
	
	public BasicPageObject() {
		this(WebDriverSingleton.get().getWebDriverProvider());
	}

	@Override
	public void abrePagina() {
		PageObject pageObject = this.getClass().getAnnotation(PageObject.class);
		if (pageObject == null) {
			throw new RuntimeException("Classes do tipo PageObject devem possuir anotação @PageObject!");
		}
		
		get(pageObject.url());
	}
	
	@Override
	public void verificaPagina() {
		for (IComponent<?> c : this.components.values()) {
			if ((c.isRendered()) && (!c.isElementPresent())) {
				throw new RuntimeException("Componente '" + c.getId() + "' não está presente na página atual.");
			} else if (!c.isRendered() && (c.isRendered())) {
				throw new RuntimeException("Componente '" + c.getId() + "' foi renderizado quando não deveria.");
			}
		}
	}
	
	@Override
	public IComponent<?> getComponent(String key) {
		IComponent<?> component = this.components.get(key);
		if (component == null) {
			PageObject pageObject = this.getClass().getAnnotation(PageObject.class);
			throw new RuntimeException(String.format("Objeto '%s' não existe na página '%s'", key, pageObject.name()));
		}
		
		return component;
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