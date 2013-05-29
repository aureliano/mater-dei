package br.materdei.bdd;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import br.materdei.bdd.model.JBehave;
import br.materdei.bdd.model.ThreadLocalModel;

public class TestRunnerHelperTest {

	@Before
	public void beforeTest() {
		ThreadLocalModel.setJBehaveModel(new JBehave());
	}
	
	@Test
	public void testRemoveDisabledStories() {
		String root = new File("").getAbsolutePath();
		List<String> data = Arrays.asList(root + "/src/main/resources/br/test/usuario.estoria", root + "/src/main/resources/br/test/case/funcionalidade.estoria", root + "/src/main/resources/br/test/case/pkg/test.estoria");
		List<String> stories = new ArrayList<String>();
		stories.addAll(data);
		
		List<String> disabled = new ArrayList<String>();
		disabled.add("br/test/usuario.estoria");
		
		TestRunnerHelper.removeDisabledStories(stories, disabled);
		assertTrue(CollectionUtils.isEqualCollection(stories, Arrays.asList(root + "/src/main/resources/br/test/case/funcionalidade.estoria", root + "/src/main/resources/br/test/case/pkg/test.estoria")));
		
		stories.clear();
		stories.addAll(data);
		
		disabled.add("br/test/case/funcionalidade.estoria");
		TestRunnerHelper.removeDisabledStories(stories, disabled);
		assertTrue(CollectionUtils.isEqualCollection(stories, Arrays.asList(root + "/src/main/resources/br/test/case/pkg/test.estoria")));
		
		stories.clear();
		stories.addAll(data);
		
		disabled.add("br/test/case/pkg/test.estoria");
		TestRunnerHelper.removeDisabledStories(stories, disabled);
		assertTrue(stories.isEmpty());
	}
}