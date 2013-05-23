package br.materdei.bdd.database.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.materdei.bdd.TestRunnerHelper;
import br.materdei.bdd.database.DatabaseInterface;
import br.materdei.bdd.database.init.InitData;
import br.materdei.bdd.model.Database;
import br.materdei.bdd.model.ThreadLocalModel;

public abstract class DatabaseImpl implements DatabaseInterface {
	
	private Database model;
	private Connection connection;
	
	protected DatabaseImpl() {
		this.model = ThreadLocalModel.getDatabaseModel();
		
		if (!TestRunnerHelper.shouldExecuteTests()) {
			return;
		}
		
		if (this.model == null) {
			throw new RuntimeException("É necessário configurar um modelo de dados para manipular uma base de dados.");
		}
		
		this.model.validation();
	}
	
	@Override
	public Connection createDatabaseConnection() throws Exception {
		if ((this.connection == null) || (this.connection.isClosed())) {
			Class.forName(this.model.getConnectionDriver());		
			this.connection = DriverManager.getConnection(
					this.model.getUrlConnection() + this.model.getConnectionDatabase(),
					this.model.getConnectionUser(), this.model.getConnectionPassword());		
		}
		
		return this.connection;
	}

	@Override
	public void loadInitialData() throws Exception {
		System.out.println("Carregando dados iniciais no banco de dados " + this.model.getConnectionDatabase());
		InitData.init(this.createDatabaseConnection());
	}
	
	@Override
	public void closeConnection() throws SQLException {
		if (this.connection != null) {
			this.connection.close();
		}
	}
}