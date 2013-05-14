package br.materdei.bdd.web.component;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.selenium.Selenium;

public class Button extends Component<Button> implements IButtonComponent<Button> {

	private String label;
	private Selenium selenium;
	
	public Button(Selenium s) {
		this.selenium = s;
	}
	
	@Override
	public void click() {
		String locator = null;
		if (!StringUtils.isEmpty(super.getId())) {
			locator = super.getId();
		} else if (!StringUtils.isEmpty(this.label)) {
			locator = "//input[@value='" + this.label + "']";
		} else {
			locator = super.getXPath();
		}
		
		this.selenium.click(locator);
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