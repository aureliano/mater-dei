package br.materdei.bdd.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

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
		return FileUtils.readFileToString(file);
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