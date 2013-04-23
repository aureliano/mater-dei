package br.materdei.bdd.jbehave;

import java.io.File;

public class StoryNameParser {

	private StoryNameParser() {
		super();
	}
	
	public static String parse(String storyPath) {
		if (!storyPath.equals(storyPath.toLowerCase())) {
			throw new RuntimeException("Formato de nome de arquivo inválido. O nome do arquivo deve ser todo minúsculo e separado com underline '_'.");
		}
		
		File root = new File("");
		storyPath = storyPath.replaceFirst(root.getAbsolutePath(), "");
		storyPath = storyPath.replaceFirst("/src/(main|test)/resources/", "");
		storyPath = storyPath.replaceAll("/", ".");
		storyPath = storyPath.replaceAll("(.story|.estoria)", "");		
		
		return convertToCamelCase(storyPath) + "Test";
	}
	
	private static String convertToCamelCase(String text) {
		String prefix = text.substring(0, text.lastIndexOf(".") + 1);
		text = text.substring(text.lastIndexOf(".") + 1);
		
		String[] tokens = text.split("_");
		StringBuilder builder = new StringBuilder();
		
		for (String token : tokens) {
			token = String.valueOf(token.charAt(0)).toUpperCase() + token.substring(1);
			builder.append(token);
		}
		
		return prefix + builder.toString();
	}
}