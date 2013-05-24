package br.materdei.bdd.web.component;

public abstract class Component<T> implements IComponent<T> {

	private String id;
	private String xPath;
	private boolean rendered;
	
	public Component() {
		this.rendered = true;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public T useId(String id) {
		this.id = id;
		return (T) this;
	}

	@Override
	public String getXPath() {
		return this.xPath;
	}

	@Override
	public T useXPath(String xpath) {
		this.xPath = xpath;
		return (T) this;
	}

	@Override
	public T useRendered(boolean rendered) {
		this.rendered = rendered;
		return (T) this;
	}

	@Override
	public boolean isRendered() {
		return this.rendered;
	}
}