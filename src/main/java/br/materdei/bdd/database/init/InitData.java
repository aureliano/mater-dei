package br.materdei.bdd.database.init;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import br.materdei.bdd.database.config.DbConfigPropertiesEnum;

public class InitData {

	public InitData() {
		super();
	}
	
	public static void init(Connection conn) {
		List<File> files = loadStatmentFiles();
		if (files.isEmpty()) {
			return;
		}
	
		try {
			Statement stmt = conn.createStatement();
			for (File file : files) {
				System.out.println("Adicionando comandos do arquivo " + file.getAbsolutePath() + " ao batch.");
				List<String> statments = statments(file);
				
				for (String cmd : statments) {
					stmt.addBatch(cmd);
				}
			}
			
			stmt.executeBatch();
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private static List<File> loadStatmentFiles() {
		String value = DbConfigPropertiesEnum.DATABASE_INIT_DATA_FILE.getValue();
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		
		List<File> files = new ArrayList<File>();		
		String[] paths = value.split(",");
		
		for (String path : paths) {
			URL url = ClassLoader.getSystemResource(path);
			if (url == null) {
				throw new RuntimeException("Arquivo de comandos SQL '" + path + "' não encontrado.");
			}
			
			files.add(new File(url.getFile()));
		}
		
		return files;
	}
	
	private static List<String> statments(File file) {
		try {
			return FileUtils.readLines(file);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}