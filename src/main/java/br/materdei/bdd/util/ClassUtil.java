package br.materdei.bdd.util;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static String extractClassNameFromJavaFile(File file) {
		String text;
		try {
			text = FileUtil.textFromFile(file);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		Matcher matcher = Pattern.compile("\\s*package\\s*[\\w\\d\\-_.]+").matcher(text);
		String pkg;
		if (matcher.find()) {
			pkg = matcher.group().replaceAll("\\s*package\\s*", "");
		} else {
			throw new RuntimeException("Não foi possível extrair o nome canônico de classe do arquivo '" + file.getAbsolutePath() + "'. É uma classe Java?");
		}
		
		matcher = Pattern.compile("\\s*public\\s*(final\\sclass|abstract\\sclass|class|enum|interface|@interface)\\s*[\\w_\\d]+").matcher(text);
		String clazz;
		if (matcher.find()) {
			clazz = matcher.group().replaceAll("\\s*public\\s*(final\\sclass|abstract\\sclass|class|enum|interface|@interface)\\s*", "");
		} else {
			throw new RuntimeException("Não foi possível extrair o nome canônico de classe do arquivo '" + file.getAbsolutePath() + "'. É uma classe Java?");
		}
		
		return pkg + "." + clazz;
	}
}