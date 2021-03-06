package br.materdei.bdd.web.component;

public interface IComponent<T> {

	public abstract String getId();
	
	public abstract T useId(String id);
	
	public abstract String getXPath();
	
	public abstract T useXPath(String xpath);
	
	public abstract void click();
	
	public abstract boolean isElementPresent();
	
	public abstract T useRendered(boolean rendered);
	
	public abstract boolean isRendered();
}