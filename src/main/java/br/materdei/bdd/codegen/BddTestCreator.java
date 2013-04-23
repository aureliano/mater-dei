package br.materdei.bdd.codegen;

import javassist.ClassPool;
import javassist.CtClass;

public class BddTestCreator {

	private BddTestCreator() {
		super();
	}
	
	public static Object create(String storyName) throws Exception {
		Object obj;
		
		if (isClassExist(storyName)) {
			obj = Class.forName(storyName).newInstance();
		} else {
			ClassPool pool = ClassPool.getDefault();
			CtClass storyBase = pool.getCtClass(StoryBase.class.getName());
			CtClass evalClass = pool.makeClass(storyName, storyBase);
			
			obj = evalClass.toClass().newInstance();
		}
		
		return obj;
	}
	
	private static boolean isClassExist(String clazz) {
		boolean exists = false;
		
		try {
			Class.forName(clazz);
			exists = true;
		} catch (ClassNotFoundException ex) {
			exists = false;
		}
		
		return exists;
	}
}