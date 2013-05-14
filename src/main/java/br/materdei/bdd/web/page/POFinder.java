package br.materdei.bdd.web.page;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.materdei.bdd.util.ClassUtil;
import br.materdei.bdd.util.FileUtil;
import br.materdei.bdd.web.annotation.PageObject;

public final class POFinder {

	private static final Map<Class<?>, IPage> PAGE_OBJECTS = new HashMap<Class<?>, IPage>();
	
	static {
		try {
			loadPageObjectClasses();
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private POFinder() {
		super();
	}
	
	public static IPage findByName(String name) {
		for (Class<?> page : PAGE_OBJECTS.keySet()) {
			if (page.getAnnotation(PageObject.class).name().equals(name)) {
				return findByClass(page);
			}
		}
		
		return null;
	}
	
	public static <T> T findByClass(Class<?> clazz) {
		if (!PAGE_OBJECTS.containsKey(clazz)) {
			return null;
		}
		
		T page = (T) PAGE_OBJECTS.get(clazz);
		if (page == null) {
			try {
				page = (T) clazz.newInstance();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		
		return page;
	}
	
	private static void loadPageObjectClasses() throws ClassNotFoundException {
		List<File> classes = getJavaClassFiles();
		
		for (File f : classes) {
			Class<?> clazz = Class.forName(ClassUtil.extractClassNameFromJavaFile(f));
			if (clazz.getAnnotation(PageObject.class) != null) {
				PAGE_OBJECTS.put(clazz, null);
			}
		}
	}
	
	private static List<File> getJavaClassFiles() throws ClassNotFoundException {
		List<File> files = FileUtil.loadMatchFiles("[\\w\\d]*.java");
		List<File> classes = new ArrayList<File>();
		
		for (File f : files) {
			classes.add(f);
		}
		
		return classes;
	}
}