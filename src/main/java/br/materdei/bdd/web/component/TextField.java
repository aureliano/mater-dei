package br.materdei.bdd.web.component;

import org.apache.commons.lang.StringUtils;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TextField extends Component<TextField> implements ITextComponent<TextField> {

	private WebDriverProvider driver;
	
	public TextField(WebDriverProvider d) {
		this.driver = d;
	}

	@Override
	public void type(String text) {
		WebElement e = this.driver.get().findElement(this.getByParam());
		e.clear();
		e.sendKeys(text);
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
		if (!StringUtils.isEmpty(this.getId())) {
			by = By.id(super.getId());
		} else if (!StringUtils.isEmpty(super.getXPath())) {
			by = By.xpath(super.getXPath());
		}
		
		return by;
	}
}