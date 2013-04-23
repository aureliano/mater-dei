package br.materdei.bdd.jbehave;

import org.jbehave.core.configuration.Configuration;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class JBehaveConfigurationUtilTest {

	@Test
	public void testCreateSeleniumConfiguration() {
		Configuration config = JBehaveConfigurationUtil.createSeleniumConfiguration(this.getClass());
		assertNotNull(config);
	}
}