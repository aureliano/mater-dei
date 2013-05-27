package br.materdei.bdd.jbehave.steps.helper;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.materdei.bdd.web.component.TextField;
import br.materdei.bdd.web.driver.WebDriverSingleton;
import br.materdei.bdd.web.page.POFinder;

public final class WebStepsHelper {
	
	private WebStepsHelper() {
		super();
	}

	public static void openPageByUrl(String url) {
		driver().get(url);
	}
	
	public static void openPageByName(String pageName) {
		POFinder.findByName(pageName).abrePagina();
	}
	
	public static void clickButton(String pageName, String buttonId) {
		POFinder.findByName(pageName).getComponent(buttonId).click();
	}
	
	public static void clickButton(String buttonLabel) {
		driver().findElement(By.xpath("//input[@value='" + buttonLabel + "']")).click();
	}
	
	public static void clickLink(String link) {
		driver().findElement(By.xpath("//a[text()='" + link + "']")).click();
	}
	
	public static void type(String pageName, String fieldId, String value) {
		TextField txtField = (TextField) POFinder.findByName(pageName).getComponent(fieldId);
		txtField.type(value);
	}
	
	public static void verifyPage(String pageName) {
		POFinder.findByName(pageName).verificaPagina();
	}
	
	public static void confirmModalMessage(String message) {
		Alert javascriptAlert = driver().switchTo().alert();
	    Assert.assertEquals(message, javascriptAlert.getText());
	    javascriptAlert.accept();
	}
	
	public static void dismissModalMessage(String message) {
		Alert javascriptAlert = driver().switchTo().alert();
	    Assert.assertEquals(message, javascriptAlert.getText());
	    javascriptAlert.dismiss();
	}
	
	public static WebDriver driver() {
		return WebDriverSingleton.get().getWebDriverProvider().get();
	}
}