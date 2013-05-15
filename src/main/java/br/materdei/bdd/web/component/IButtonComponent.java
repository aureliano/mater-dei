package br.materdei.bdd.web.component;

public interface IButtonComponent<T> {
	
	public abstract String getLabel();
	
	public abstract T useLabel(String label);
}