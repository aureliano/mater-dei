package br.materdei.bdd.web.page;

import br.materdei.bdd.web.component.IComponent;

public interface IPage {

	public abstract void abrePagina();
	
	public abstract void verificaPagina();
	
	public abstract IComponent<?> getComponent(String key);
	
	public abstract void addComponents(IComponent<?> ... components);
}