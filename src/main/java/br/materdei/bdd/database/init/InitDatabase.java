package br.materdei.bdd.database.init;

public final class InitDatabase {

	private InitDatabase() {
		super();
	}
	
	public static void init() {
		InitData.init();
	}
}