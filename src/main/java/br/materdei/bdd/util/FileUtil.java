package br.materdei.bdd.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
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
	
	public static List<File> loadMatchFiles(String regex) {
		List<File> allFiles = FileUtil.loadFiles();
		List<File> files = new ArrayList<File>();
		
		for (File f : allFiles) {
			if (f.getName().matches(regex)) {
				files.add(f);
			}
		}
		
		return files;
	}
	
	public static List<File> extractFromZipFile(String inputPath, String outputPath) throws IOException {
		List<File> unzipedFiles = new ArrayList<File>();

		ZipFile zipFile = new ZipFile(new File(inputPath));
		Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();

		while (entries.hasMoreElements()) {
			ZipArchiveEntry entry = entries.nextElement();
			File f = createFile(zipFile.getInputStream(entry), entry, outputPath);
			if (f != null) {
				unzipedFiles.add(f);
			}
		}

		return unzipedFiles;
	}
	
	public static String textFromFile(File file) throws IOException {
		return FileUtils.readFileToString(file);
	}
	
	private static File createFile(InputStream stream, ZipArchiveEntry zipArchiveEntry, String outputPath) throws IOException {
		long fileSize = zipArchiveEntry.getCompressedSize();
		byte[] buffer = new byte[(int) fileSize];
		if (!outputPath.endsWith("/")) {
			outputPath += "/";
		}
		
		File destinationFile = new File(outputPath + zipArchiveEntry.getName());
		if (zipArchiveEntry.isDirectory()) {
			destinationFile.mkdir();
			return null;
		}
		
		OutputStream outputStream = new FileOutputStream(destinationFile);
		int bytes = 0;

		while ((bytes = stream.read(buffer)) > -1) {
			if (bytes > 0) {
				outputStream.write(buffer, 0, bytes);
			}
		}

		stream.close();
		outputStream.flush();
		outputStream.close();

		return destinationFile;
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