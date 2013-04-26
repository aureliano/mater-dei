package br.materdei.bdd;

import java.util.ArrayList;
import java.util.List;

public class TestModel {

	private String storyPath;
	private Class<?> storyBase;
	private List<String> metaFilters;
	
	public TestModel() {
		this.metaFilters = new ArrayList<String>();
	}

	public String getStoryPath() {
		return storyPath;
	}

	public TestModel useStoryPath(String storyPath) {
		this.storyPath = storyPath;
		return this;
	}

	public Class<?> getStoryBase() {
		return storyBase;
	}

	public TestModel useStoryBase(Class<?> storyBase) {
		this.storyBase = storyBase;
		return this;
	}

	public List<String> getMetaFilters() {
		return metaFilters;
	}

	public TestModel useMetaFilters(List<String> metaFilters) {
		this.metaFilters = metaFilters;
		return this;
	}
	
	public TestModel useMetaFilters(String ...metaFilters) {
		if (metaFilters != null) {
			for (String filter : metaFilters) {
				this.metaFilters.add(filter);
			}
		}

		return this;
	}
}