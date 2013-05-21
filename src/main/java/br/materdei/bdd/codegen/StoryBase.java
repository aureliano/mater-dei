package br.materdei.bdd.codegen;

import static java.util.Arrays.asList;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.web.selenium.PerStoryWebDriverSteps;
import org.jbehave.web.selenium.WebDriverSteps;
import org.junit.After;
import org.junit.Before;

import br.materdei.bdd.TestModel;
import br.materdei.bdd.TestRunnerHelper;
import br.materdei.bdd.jbehave.config.JBehaveConfigurationUtil;
import br.materdei.bdd.jbehave.steps.WebSteps;
import br.materdei.bdd.web.driver.WebDriverSingleton;

import com.google.common.util.concurrent.MoreExecutors;

public class StoryBase extends JUnitStory {

	private TestModel model;
	private WebDriverSteps lifecycleSteps;
	
	public StoryBase() {
		this.lifecycleSteps = new PerStoryWebDriverSteps(WebDriverSingleton.get().getWebDriverProvider());
		if (lifecycleSteps instanceof PerStoryWebDriverSteps) {
			configuredEmbedder().useExecutorService(MoreExecutors.sameThreadExecutor());
		}
	}
	
	public void run(TestModel model) throws Throwable {
		this.model = model;
		Embedder embedder = configuredEmbedder();
		embedder.useMetaFilters(this.model.getMetaFilters());
		
		if (!TestRunnerHelper.shouldExecuteTests()) {
			return;
		}
		
        try {
            embedder.runStoriesAsPaths(asList(this.model.getStoryPath()));
        } finally {
            embedder.generateCrossReference();
        }
	}
	
	@Override
	public InjectableStepsFactory stepsFactory() {
		Configuration configuration = configuration();
		
		List<Object> scenarios = ScenarioCreator.instantiateScenarios();
		scenarios.add(new WebSteps());
		scenarios.add(this.lifecycleSteps);
		
		return new InstanceStepsFactory(configuration, scenarios);
	}
	
	@Override
	public Configuration configuration() {
		return JBehaveConfigurationUtil.createSeleniumConfiguration(this.getClass());
	}
	
	@Before
	public void beforeTest() {}

	@After
	public void afterTest() throws Exception {}
	
	// This Embedder is used by Maven or Ant and it will override anything set in the constructor.
	public static class SameThreadEmbedder extends Embedder {
		public SameThreadEmbedder() {
			useExecutorService(MoreExecutors.sameThreadExecutor());
		}
	}
}