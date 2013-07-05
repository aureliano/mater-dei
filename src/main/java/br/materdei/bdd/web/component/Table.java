package br.materdei.bdd.web.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Table extends Component<Table> {
	
	private static final Logger logger = Logger.getLogger(Table.class);

	private WebDriverProvider driver;
	private Map<Integer, String> columns;

	public Table(WebDriverProvider d) {
		this.driver = d;
		this.columns = new HashMap<Integer, String>();
	}

	@Override
	public void click() {
		this.driver.get().findElement(this.getByParam()).click();
	}

	@Override
	public boolean isElementPresent() {
		return this.driver.get().findElement(this.getByParam()) != null;
	}

	public boolean containsText(String text) {
		WebDriver d = this.driver.get();
		boolean repeat = true;
		boolean result = false;
		
		while (repeat) {
			try {
				result = this.verifyText(d, text);
				repeat = false;
			} catch (StaleElementReferenceException ex) {
				long milliseconds = 500;
				logger.info("StaleElementReferenceException >> Tentando recuperar a tabela '" + this.getId() + "' novamente dentro de " + milliseconds + " milisegundos.");
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException ex2) {
					logger.warn(ex2.getMessage());
				}
				
				repeat = true;
			}
		}
		
		return result;
	}

	private boolean verifyText(WebDriver d, String text) {
		String path = this.getTableRowsXpath();
		int rows = d.findElements(By.xpath(path)).size();

		for (int row = 1; row <= rows; row++) {
			for (Integer columnPosition : this.columns.keySet()) {
				List<WebElement> elements = d.findElements(By.xpath(path + "[" + row + "]/td[" + columnPosition + "]"));

				for (WebElement e : elements) {
					if (text.equals(e.getText())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public void clickButton(String buttonLabel, int row) {
		String path = this.getTableRowsXpath() + "[" + row + "]" + "/td/input[@value='" + buttonLabel + "']";
		this.driver.get().findElement(By.xpath(path)).click();
	}

	public void clickButton(String buttonLabel, String rowPath) {
		String path = this.getTableRowsXpath() + rowPath + "/td/input[@value='" + buttonLabel + "']";
		this.driver.get().findElement(By.xpath(path)).click();
	}

	public void clickLink(String linkLabel, int row) {
		String path = this.getTableRowsXpath() + "[" + row + "]" + "/td/a[text()='" + linkLabel + "']";
		this.driver.get().findElement(By.xpath(path)).click();
	}

	public void clickLink(String linkLabel, String rowPath) {
		String path = this.getTableRowsXpath() + rowPath + "/td/a[text()='" + linkLabel + "']";
		this.driver.get().findElement(By.xpath(path)).click();
	}

	public void addColumn(int position, String name) {
		this.columns.put(position, name);
	}

	public String[] getColumns() {
		List<Integer> keys = orderedKeys();
		String[] orderedColumns = new String[keys.size()];

		for (short i = 0; i < orderedColumns.length; i++) {
			orderedColumns[i] = this.columns.get(keys.get(i));
		}

		return orderedColumns;
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

	private String getTableRowsXpath() {
		return "//table[@id='" + super.getId() + "']//tr";
	}

	private List<Integer> orderedKeys() {
		Set<Integer> keys = this.columns.keySet();
		List<Integer> l = new ArrayList<Integer>();

		for (Integer k : keys) {
			l.add(k);
		}

		Collections.sort(l);
		return l;
	}
}