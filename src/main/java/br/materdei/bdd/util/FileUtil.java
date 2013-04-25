package br.materdei.bdd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileUtil {

	private FileUtil() {
		super();
	}
	
	public static List<File> loadFiles() {
		return loadFiles(null);
	}
	
	public static List<File> loadFiles(String seed) {
		if (seed == null) {
			seed = "";
		}
		
		File file = new File(new File("").getAbsolutePath() + "/" + seed);
		if (!file.isDirectory()) {
			throw new RuntimeException(file.getAbsolutePath() + " não é um diretório.");
		}
		
		List<File> files = new ArrayList<File>();
		loadFiles(files, file);
		
		return files;
	}
	
	public static String textFromFile(File file) throws IOException {
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		StringBuilder builder = new StringBuilder();
		String line;
		
		while ((line = buffer.readLine()) != null) {
			builder.append(line).append("\n");
		}
		
		buffer.close();		
		builder.delete(builder.length() - 1, builder.length());
		
		return builder.toString();
	}
	
	private static void loadFiles(List<File> files, File seed) {
		if (seed.isDirectory()) {
			File[] _files = seed.listFiles();
			for (File f : _files) {
				loadFiles(files, f);
			}
		} else {
			files.add(seed);
		}
	}
}