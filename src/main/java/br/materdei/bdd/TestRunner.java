package br.materdei.bdd;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;

import br.materdei.bdd.codegen.BddTestCreator;
import br.materdei.bdd.codegen.StoryBase;
import br.materdei.bdd.jbehave.StoryFinder;
import br.materdei.bdd.jbehave.StoryNameParser;
import br.materdei.bdd.model.Database;
import br.materdei.bdd.model.JBehave;
import br.materdei.bdd.model.ThreadLocalModel;
import br.materdei.bdd.model.WebDriver;

public class TestRunner {
	
	private WebDriver webDriverConfig;
	private Database databaseConfig;
	private JBehave jbehaveConfig;

	public void run() {
		this.execute(new TestModel());
	}
	
	public void run(TestModel model) {
		this.execute(model);
	}
	
	private void execute(TestModel model) {
		try {
			this.make(model);
		} catch (Throwable t) {
			Assert.fail(t.getMessage());
		}
	}
	
	private void make(TestModel model) throws Throwable {
		this.prepareTestEnvironment();
		Throwable exception = null;
		
		List<String> stories = loadStories(model.getStoryPath());
		List<String> disabledStories = StoryFinder.findDisabledStories();
		TestRunnerHelper.removeDisabledStories(stories, disabledStories);
				
		for (String story : stories) {
			String fileName = story.substring(story.lastIndexOf("/") + 1);
			String storyName = StoryNameParser.parse(story);
			
			try {
				StoryBase runnableStory = (StoryBase) BddTestCreator.create(model.getStoryBase(), storyName);
				model.useStoryPath(storyName.replaceAll("\\.", "/").substring(0, storyName.lastIndexOf(".") + 1) + fileName);
				
				System.out.println(" => Executando estória " + model.getStoryPath());
				
				TestRunnerHelper.runBeforeMethods(runnableStory);
				runnableStory.run(model);
				TestRunnerHelper.runAfterMethods(runnableStory);
			} catch (Throwable t) {
				exception = t;
			}
		}
		
		this.tearDownTestEnvironment(disabledStories);
		if (exception != null) {
			throw exception;
		}
	}
	
	protected List<String> loadStories(String resourceName) {
		return TestRunnerHelper.loadStories(resourceName);
	}
	
	private void prepareTestEnvironment() {
		this.saveObjectModels();
		TestRunnerHelper.copyJBehaveSiteResources();		
	}
	
	private void saveObjectModels() {
		WebDriver wd = this.getWebDriverConfig();
		wd.validation();		
		ThreadLocalModel.setWebDriverModel(wd);
		
		ThreadLocalModel.setDatabaseModel(this.databaseConfig);
		
		JBehave jb = this.getJBehaveConfig();
		jb.validation();
		ThreadLocalModel.setJBehaveModel(jb);
	}
	
	private void tearDownTestEnvironment(List<String> disabledStories) {
		TestRunnerHelper.printDisabledTests(disabledStories);
	}
	
	public TestRunner configWebDriver(WebDriver wd) {
		this.webDriverConfig = wd;
		return this;
	}
	
	public TestRunner configDatabase(Database db) {
		this.databaseConfig = db;
		return this;
	}
	
	public TestRunner configJBejave(JBehave jb) {
		this.jbehaveConfig = jb;
		return this;
	}
	
	public TestRunner configWithProperties(String propertiesFilePath) {
		URL url = ClassLoader.getSystemResource(propertiesFilePath);
		Properties properties = new Properties();
		
		try {
			properties.load(new FileInputStream(url.getFile()));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		this.webDriverConfig = new WebDriver(properties);
		this.databaseConfig = new Database(properties);
		this.jbehaveConfig = new JBehave(properties);
		
		return this;
	}
	
	public WebDriver getWebDriverConfig() {
		if (this.webDriverConfig == null) {
			this.webDriverConfig = new WebDriver();
		}
		
		return this.webDriverConfig;
	}
	
	public Database getDatabaseConfig() {
		if (this.databaseConfig == null) {
			this.databaseConfig = new Database();
		}
		
		return this.databaseConfig;
	}
	
	public JBehave getJBehaveConfig() {
		if (this.jbehaveConfig == null) {
			this.jbehaveConfig = new JBehave();
		}
		
		return this.jbehaveConfig;
	}
}