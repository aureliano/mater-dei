package br.materdei.bdd.web.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

public class Table extends Component<Table> {

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