package br.materdei.bdd.database.init;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import br.materdei.bdd.database.DatabasesEnum;
import br.materdei.bdd.database.DbUrlCreator;
import br.materdei.bdd.database.config.DbConfigPropertiesEnum;
import br.materdei.bdd.jbehave.config.BddProperties;

public class InitData {

	public InitData() {
		super();
	}
	
	public static void init() {
		List<File> files = loadStatmentFiles();
		if (CollectionUtils.isEmpty(files)) {
			return;
		}
	
		try {
			Connection conn = createConnection();
			Statement stmt = conn.createStatement();
			for (File file : files) {
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
		String value = BddProperties.getPropriedade(DbConfigPropertiesEnum.DATABASE_INIT_DATA_FILE.getValue());
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
	
	private static Connection createConnection() throws Exception {
		String driver = BddProperties.getPropriedade(DbConfigPropertiesEnum.DATABASE_CONNECTION_DRIVER.getValue());
		Class.forName(driver);
		
		String url = DbUrlCreator.create(DatabasesEnum.databaseFromDriverName(driver));
		String user = BddProperties.getPropriedade(DbConfigPropertiesEnum.DATABASE_CONNECTION_USER.getValue());
		String password = BddProperties.getPropriedade(DbConfigPropertiesEnum.DATABASE_COONECTION_PASSWORD.getValue());
		String database = BddProperties.getPropriedade(DbConfigPropertiesEnum.DATABASE_CONNECTION_DB.getValue());
		
		Connection c = DriverManager.getConnection(url + database, user, password);
		c.setAutoCommit(false);
		
		return c;
	}
	
	private static List<String> statments(File file) {
		try {
			return FileUtils.readLines(file);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}