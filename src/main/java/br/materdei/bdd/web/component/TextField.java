package br.materdei.bdd.web.component;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.selenium.Selenium;

public class TextField extends Component<TextField> implements ITextComponent<TextField> {

	private Selenium selenium;
	
	public TextField(Selenium s) {
		this.selenium = s;
	}

	@Override
	public void type(String text) {
		String locator = null;
		if (!StringUtils.isEmpty(this.getId())) {
			locator = super.getId();
		} else if (!StringUtils.isEmpty(super.getXPath())) {
			locator = super.getXPath();
		}
		
		this.selenium.type(locator, text);
	}
}