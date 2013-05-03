package br.materdei.bdd.codegen;

import static java.util.Arrays.asList;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.After;
import org.junit.Before;

import br.materdei.bdd.TestModel;
import br.materdei.bdd.jbehave.JBehaveConfigurationUtil;
import br.materdei.bdd.steps.WebSteps;

public class StoryBase extends JUnitStory {

	public StoryBase() {
		super();
	}
	
	public void run(TestModel model) throws Throwable {
		Embedder embedder = configuredEmbedder();
		embedder.useMetaFilters(model.getMetaFilters());
		
        try {
            embedder.runStoriesAsPaths(asList(model.getStoryPath()));
        } finally {
            embedder.generateCrossReference();
        }
	}
	
	@Override
	public List<CandidateSteps> candidateSteps() {
		List<Object> scenarios = ScenarioCreator.instantiateScenarios();
		scenarios.add(new WebSteps());
		return new InstanceStepsFactory(configuration(), scenarios).createCandidateSteps();
	}
	
	@Override
	public Configuration configuration() {
		return JBehaveConfigurationUtil.createSeleniumConfiguration(this.getClass());
	}
	
	@Before
	public void beforeTest() {}

	@After
	public void afterTest() throws Exception {}
}