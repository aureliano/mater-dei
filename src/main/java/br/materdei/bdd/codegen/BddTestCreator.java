package br.materdei.bdd.codegen;

import javassist.ClassPool;
import javassist.CtClass;

public class BddTestCreator {

	private BddTestCreator() {
		super();
	}
	
	public static Object create() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass evalClass = pool.makeClass("br.materdei.bdd.ProjectBddTest");
		
		return evalClass.toClass().newInstance();
	}
}