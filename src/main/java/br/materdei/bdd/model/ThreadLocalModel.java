package br.materdei.bdd.model;

public final class ThreadLocalModel {
	
	private static final ThreadLocal<WebDriver> THREAD_LOCAL_WEB_DRIVER = new ThreadLocal<WebDriver>();
	private static final ThreadLocal<Database> THREAD_LOCAL_DATABASE = new ThreadLocal<Database>();
	private static final ThreadLocal<JBehave> THREAD_LOCAL_JBEHAVE = new ThreadLocal<JBehave>();
	private static final ThreadLocal<Boolean> THREAD_LOCAL_WEB_DRIVER_PREFERENCES_DONE = new ThreadLocal<Boolean>();

	private ThreadLocalModel() {
		super();
	}
	
	public static WebDriver getWebDriverModel() {
		return THREAD_LOCAL_WEB_DRIVER.get();
	}
	
	public static Database getDatabaseModel() {
		return THREAD_LOCAL_DATABASE.get();
	}
	
	public static JBehave getJBehaveModel() {
		return THREAD_LOCAL_JBEHAVE.get();
	}
	
	public static void setWebDriverModel(WebDriver model) {
		THREAD_LOCAL_WEB_DRIVER.set(model);
	}
	
	public static void setDatabaseModel(Database model) {
		THREAD_LOCAL_DATABASE.set(model);
	}
	
	public static void setJBehaveModel(JBehave model) {
		THREAD_LOCAL_JBEHAVE.set(model);
	}
	
	public static void setWebDriverPreferencesDone(Boolean done) {
		THREAD_LOCAL_WEB_DRIVER_PREFERENCES_DONE.set(done);
	}
	
	public static Boolean getWebDriverPreferencesDone() {
		return THREAD_LOCAL_WEB_DRIVER_PREFERENCES_DONE.get();
	}
}