package br.materdei.bdd.web.component;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ComboBox extends Component<ComboBox> {
	
	private WebDriverProvider driver;

	public ComboBox(WebDriverProvider d) {
		this.driver = d;
	}
	
	public void selectByLabel(String label) {
		this.select(null, label);
	}
	
	public void selectByValue(String value) {
		this.select("value", value);
	}
	
	private void select(String attribute, String text) {
		WebElement select = this.driver.get().findElement(this.getByParam());
		List<WebElement> options = select.findElements(By.tagName("option"));
		
		for (WebElement option : options) {
			if (!StringUtils.isEmpty(attribute) && option.getAttribute(attribute).equals(text)) {
				option.click();
				break;
			} else if (StringUtils.isEmpty(attribute) && option.getText().equals(text)) {
				option.click();
				break;
			}
		}
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
		} else {
			by = By.xpath(super.getXPath());
		}
		
		return by;
	}
}