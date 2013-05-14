package br.materdei.bdd.web.component;

public interface IComponent<T> {

	public abstract String getId();
	
	public abstract T useId(String id);
	
	public abstract String getXPath();
	
	public abstract T useXPath(String xpath);
}