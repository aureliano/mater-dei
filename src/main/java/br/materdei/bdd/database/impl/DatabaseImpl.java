package br.materdei.bdd.database.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.lang.StringUtils;

import br.materdei.bdd.database.DatabaseInterface;
import br.materdei.bdd.database.DatabasesEnum;
import br.materdei.bdd.database.DbUrlCreator;
import br.materdei.bdd.database.config.DbConfigPropertiesEnum;
import br.materdei.bdd.database.init.InitData;

public abstract class DatabaseImpl implements DatabaseInterface {

	protected String urlConnection;
	protected String driverClass;
	protected String user;
	protected String password;
	protected String database;
	
	protected DatabaseImpl() {
		this.driverClass = DbConfigPropertiesEnum.DATABASE_CONNECTION_DRIVER.getValue();
		if (StringUtils.isEmpty(this.driverClass)) {
			throw new RuntimeException("Propriedade database.connection.driver não foi informada no arquivo bdd-config.properties");
		}
		
		this.user = DbConfigPropertiesEnum.DATABASE_CONNECTION_USER.getValue();
		if (StringUtils.isEmpty(this.user)) {
			throw new RuntimeException("Propriedade database.connection.user não foi informada no arquivo bdd-config.properties");
		}
		
		this.password = DbConfigPropertiesEnum.DATABASE_COONECTION_PASSWORD.getValue();
		if (StringUtils.isEmpty(this.password)) {
			throw new RuntimeException("Propriedade database.connection.password não foi informada no arquivo bdd-config.properties");
		}
		
		this.database = DbConfigPropertiesEnum.DATABASE_CONNECTION_DB.getValue();
		if (StringUtils.isEmpty(this.database)) {
			throw new RuntimeException("Propriedade database.connection.db não foi informada no arquivo bdd-config.properties");
		}
		
		DatabasesEnum db = DatabasesEnum.databaseFromDriverName(this.driverClass);
		if (db == null) {
			throw new RuntimeException("Driver JDBC desconhecido: " + this.driverClass);
		}
		this.urlConnection = DbUrlCreator.create(db);
	}
	
	@Override
	public Connection createDatabaseConnection() throws Exception {
		Class.forName(this.driverClass);		
		Connection c = DriverManager.getConnection(this.urlConnection + database, user, password);		
		return c;
	}

	@Override
	public void loadInitialData() throws Exception {
		System.out.println("Carregando dados iniciais no banco de dados " + this.database);
		InitData.init(this.createDatabaseConnection());
	}
}