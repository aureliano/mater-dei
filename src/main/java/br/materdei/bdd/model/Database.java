package br.materdei.bdd.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

import br.materdei.bdd.database.DatabasesEnum;
import br.materdei.bdd.database.config.DbConfigPropertiesEnum;

public class Database {
	
	public static final String DEFAULT_CONNECTION_HOST = "localhost";

	private String connectionDriver;
	private String connectionUser;
	private String connectionPassword;
	private String connectionDatabase;
	private Integer connectionPort;
	private String connectionHost;
	private List<File> initDataFiles;
	private List<String> tablesToNotClear;
	
	public Database() {
		super();
	}
	
	public Database(Properties p) {
		this.useConnectionDriver(p.getProperty(DbConfigPropertiesEnum.DATABASE_CONNECTION_DRIVER.getKey()));
		this.useConnectionUser(p.getProperty(DbConfigPropertiesEnum.DATABASE_CONNECTION_USER.getKey()));
		this.useConnectionPassword(p.getProperty(DbConfigPropertiesEnum.DATABASE_COONECTION_PASSWORD.getKey()));
		this.useConnectionDatabase(p.getProperty(DbConfigPropertiesEnum.DATABASE_CONNECTION_DB.getKey()));
		this.useConnectionHost(p.getProperty(DbConfigPropertiesEnum.DATABASE_CONNECTION_HOST.getKey()));
		
		String value = p.getProperty(DbConfigPropertiesEnum.DATABASE_CONNECTION_PORT.getKey());
		if (!StringUtils.isEmpty(value)) {
			this.useConnectionPort(Integer.parseInt(value));
		}
		
		this.configInitDataFiles(p.getProperty(DbConfigPropertiesEnum.DATABASE_INIT_DATA_FILE.getKey()));
		this.configTablesToNotClear(p.getProperty(DbConfigPropertiesEnum.DATABASE_TABLES_TO_NOT_CLEAR.getKey()));
	}

	public String getConnectionDriver() {
		return connectionDriver;
	}

	public Database useConnectionDriver(String connectionDriver) {
		this.connectionDriver = connectionDriver;
		return this;
	}

	public String getConnectionUser() {
		return connectionUser;
	}

	public Database useConnectionUser(String connectionUser) {
		this.connectionUser = connectionUser;
		return this;
	}

	public String getConnectionPassword() {
		return connectionPassword;
	}

	public Database useConnectionPassword(String connectionPassword) {
		this.connectionPassword = connectionPassword;
		return this;
	}

	public String getConnectionDatabase() {
		return connectionDatabase;
	}
	
	public Database useConnectionDatabase(String db) {
		this.connectionDatabase = db;
		return this;
	}

	/**
	 * Usa configura objeto com valores padronizados para um sistema de banco de dados.
	 * Caso <b>sgdb</b> seja POSTGRESQL serão definidos como valores:
	 * <p/>
	 * <ul>
	 * <li>connectionPort = 5432</li>
	 * <li>connectionDriver = org.postgresql.Driver</li>
	 * <li>connectionHost = localhost</li>
	 * </ul>
	 * @param sgdb
	 * @return Este objeto
	 */
	public Database useDefaultValues(DatabasesEnum sgdb) {
		if (sgdb == null) {
			throw new IllegalArgumentException("SGDB não pode ser nulo.");
		}
		
		if (sgdb != null) {
			this.useConnectionPort(sgdb.getDefaultPort());
			this.useConnectionDriver(sgdb.getDefaultDriver());
			this.useConnectionHost(DEFAULT_CONNECTION_HOST);
		}
		
		return this;
	}

	public Integer getConnectionPort() {
		return connectionPort;
	}

	public Database useConnectionPort(Integer connectionPort) {
		if (connectionPort != null) {
			if (connectionPort <= 0) {
				throw new IllegalArgumentException("Porta de conexão deve ser maior do que zero.");
			}
		}
		
		this.connectionPort = connectionPort;
		return this;
	}

	public String getConnectionHost() {
		return connectionHost;
	}

	public Database useConnectionHost(String connectionHost) {
		this.connectionHost = connectionHost;
		return this;
	}

	public List<File> getInitDataFiles() {
		return initDataFiles;
	}

	public Database useInitDataFiles(List<File> initDataFiles) {
		this.initDataFiles = initDataFiles;
		return this;
	}

	public List<String> getTablesToNotClear() {
		return tablesToNotClear;
	}

	public Database useTablesToNotClear(List<String> tablesToNotClear) {
		this.tablesToNotClear = tablesToNotClear;
		return this;
	}
	
	public String getUrlConnection() {
		DatabasesEnum sgdb = DatabasesEnum.databaseFromDriverName(this.connectionDriver);
		if (sgdb == null) {
			return null;
		}
		
		String seed = "jdbc:%s://%s:%s/";
		switch (sgdb) {
			case MYSQL		: return String.format(seed, sgdb.name().toLowerCase(), this.connectionHost, this.connectionPort);
			case POSTGRESQL	: return String.format(seed, sgdb.name().toLowerCase(), this.connectionHost, this.connectionPort);
			default : throw new NotImplementedException("Implementação não disponível para " + sgdb.name());
		}
	}
	
	public void validation() {
		if (StringUtils.isEmpty(this.connectionDriver)) {
			throw new RuntimeException("Um valor deve ser informado para " + DbConfigPropertiesEnum.DATABASE_CONNECTION_DRIVER.getKey());
		}
		
		if (this.connectionPort == null) {
			throw new RuntimeException("Um valor deve ser informado para " + DbConfigPropertiesEnum.DATABASE_CONNECTION_PORT.getKey());
		}

		if (StringUtils.isEmpty(this.connectionHost)) {
			throw new RuntimeException("Um valor deve ser informado para " + DbConfigPropertiesEnum.DATABASE_CONNECTION_HOST.getKey());
		}
		
		if (StringUtils.isEmpty(this.connectionDatabase)) {
			throw new RuntimeException("Um valor deve ser informado para " + DbConfigPropertiesEnum.DATABASE_CONNECTION_DB.getKey());
		}
	}
	
	private void configInitDataFiles(String value) {
		if (!StringUtils.isEmpty(value)) {
			String[] tokens = value.split(",");
			if (tokens.length > 0) {
				this.initDataFiles = new ArrayList<File>();
			}
			
			for (String t : tokens) {
				this.initDataFiles.add(new File(t.trim()));
			}
		}
	}
	
	private void configTablesToNotClear(String value) {
		if (!StringUtils.isEmpty(value)) {
			String[] tokens = value.split(",");
			if (tokens.length > 0) {
				this.tablesToNotClear = new ArrayList<String>();
			}
			
			for (String t : tokens) {
				this.tablesToNotClear.add(t.trim());
			}
		}
	}
}