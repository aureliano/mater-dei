package br.materdei.bdd.web.component;

import org.apache.commons.lang.StringUtils;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

public class Button extends Component<Button> implements IButtonComponent<Button> {

	private String label;
	private WebDriverProvider driver;
	
	public Button(WebDriverProvider d) {
		this.driver = d;
	}
	
	@Override
	public void click() {
		this.driver.get().findElement(this.getByParam()).click();
	}
	
	@Override
	public boolean isElementPresent() {
		return this.driver.get().findElement(this.getByParam()) != null;
	}
	
	private By getByParam() {
		By by = null;
		if (!StringUtils.isEmpty(super.getId())) {
			by = By.id(super.getId());
		} else if (!StringUtils.isEmpty(this.label)) {
			by = By.xpath("//input[@value='" + this.label + "']");
		} else {
			by = By.xpath(super.getXPath());
		}
		
		return by;
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