package br.materdei.bdd.jbehave.steps.helper;

import br.materdei.bdd.web.component.Table;
import br.materdei.bdd.web.page.POFinder;

public final class TableStepsHelper {

	private TableStepsHelper() {
		super();
	}
	
	public static void clickTableButtonByRowNumber(String pageName, String table, Integer row, String button) {
		Table t = (Table) POFinder.findByName(pageName).getComponent(table);
		t.clickButton(button, row);
	}
	
	public static void clickTableLinkByRowNumber(String pageName, String table, Integer row, String link) {
		Table t = (Table) POFinder.findByName(pageName).getComponent(table);
		t.clickLink(link, row);
	}
	
	public static void clickTableButtonByRowValues(String pageName, String table, String filter, String button) {
		Table t = (Table) POFinder.findByName(pageName).getComponent(table);
		String xpath = createTableClicableComponentXPath(filter);
		t.clickButton(button, xpath);
	}
	
	public static void clickTableLinkByRowValues(String pageName, String table, String filter, String link) {
		Table t = (Table) POFinder.findByName(pageName).getComponent(table);
		String xpath = createTableClicableComponentXPath(filter);
		t.clickLink(link, xpath);
	}
	
	public static void verifyTextInTable(String pageName, String table, String text) {
		Table t = (Table) POFinder.findByName(pageName).getComponent(table);
		t.isElementPresent();
		if (!t.containsText(text)) {
			throw new RuntimeException(String.format("A tabela '%s' da página '%s' não contém o texto '%s'", table, pageName, text));
		}
	}
	
	private static String createTableClicableComponentXPath(String filter) {
		String[] filters = filter.split(",");
		StringBuilder b = new StringBuilder();
		
		for (String f : filters) {
			String[] tokens = f.split("[=|-]");
			b.append("/td[" + tokens[0] + "][text()='" + tokens[1] + "']/..");
		}
		
		return b.toString();
	}
}