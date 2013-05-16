package br.materdei.bdd.web.component;

import org.apache.commons.lang.StringUtils;

import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;

import com.thoughtworks.selenium.Selenium;

public class Button extends Component<Button> implements IButtonComponent<Button> {

	private String label;
	private Selenium selenium;
	
	public Button(Selenium s) {
		this.selenium = s;
	}
	
	@Override
	public void click() {
		this.selenium.click(this.getLocator());
		this.selenium.waitForPageToLoad(BddConfigPropertiesEnum.SELENIUM_TIMEOUT.getValue());
	}
	
	@Override
	public boolean isElementPresent() {
		return this.selenium.isElementPresent(this.getLocator());
	}
	
	private String getLocator() {
		String locator = null;
		if (!StringUtils.isEmpty(super.getId())) {
			locator = super.getId();
		} else if (!StringUtils.isEmpty(this.label)) {
			locator = "//input[@value='" + this.label + "']";
		} else {
			locator = super.getXPath();
		}
		
		return locator;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public Button useLabel(String label) {
		this.label = label;
		return this;
	}
}