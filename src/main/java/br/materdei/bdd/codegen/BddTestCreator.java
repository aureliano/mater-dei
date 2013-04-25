package br.materdei.bdd.codegen;

import br.materdei.bdd.util.ClassUtil;
import javassist.ClassPool;
import javassist.CtClass;

public final class BddTestCreator {

	private BddTestCreator() {
		super();
	}
	
	public static Object create(Class<?> storyBase, String storyName) throws Exception {
		Object obj;
		
		if (ClassUtil.classExists(storyName)) {
			obj = Class.forName(storyName).newInstance();
		} else {
			ClassPool pool = ClassPool.getDefault();
			if (storyBase == null) {
				storyBase = StoryBase.class;
			}
			
			CtClass ctClassStoryBase = pool.getCtClass(storyBase.getName());
			CtClass evalClass = pool.makeClass(storyName, ctClassStoryBase);
			
			obj = evalClass.toClass().newInstance();
		}
		
		return obj;
	}
}