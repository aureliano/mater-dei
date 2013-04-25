package br.materdei.bdd.util;

import java.io.File;
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