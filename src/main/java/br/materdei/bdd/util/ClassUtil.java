package br.materdei.bdd.util;

public final class ClassUtil {

	private ClassUtil() {
		super();
	}
	
	public static boolean classExists(String clazz) {
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