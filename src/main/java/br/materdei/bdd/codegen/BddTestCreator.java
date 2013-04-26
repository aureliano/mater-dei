package br.materdei.bdd.codegen;

import javassist.ClassPool;
import javassist.CtClass;
import br.materdei.bdd.util.ClassUtil;

public final class BddTestCreator {

	private BddTestCreator() {
		super();
	}
	
	public static Object create(Class<?> storyBase, String storyName) throws Exception {
		Object obj;
		
		if (ClassUtil.classExists(storyName)) {
			obj = Class.forName(storyName).newInstance();
		} else {
			obj = createNewObject(storyBase, storyName);
		}
		
		return obj;
	}
	
	private static Object createNewObject(Class<?> storyBase, String storyName) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		if (storyBase == null) {
			storyBase = StoryBase.class;
		} else {
			if (!StoryBase.class.equals(storyBase.getSuperclass())) {
				throw new RuntimeException(storyBase.getName() + " deve herdar de " + StoryBase.class.getName());
			}
		}
		
		CtClass ctClassStoryBase = pool.getCtClass(storyBase.getName());
		CtClass evalClass = pool.makeClass(storyName, ctClassStoryBase);
		
		return evalClass.toClass().newInstance();
	}
}