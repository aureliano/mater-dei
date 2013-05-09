package br.materdei.bdd.jbehave;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContextOutput;

import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;
import br.materdei.bdd.jbehave.reporters.console.ColoredConsoleFormat;
import br.materdei.bdd.jbehave.reporters.html.ScreenShootingHtmlFormat;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;

public final class JBehaveConfigurationUtil {

	private JBehaveConfigurationUtil() {
		super();
	}
	
	public static Configuration createSeleniumConfiguration(Class<?> embeddableClass) {
					
		SeleniumServerControllerSingleton server = SeleniumServerControllerSingleton.getInstancia();
		
		Keywords keywords = new LocalizedKeywords(Locale.getDefault());
			
		return new SeleniumConfiguration()
			.useSelenium(server.getSelenium())
			.useSeleniumContext(server.getSeleniumContext())
			.useStepMonitor(server.getStepMonitor())
			.useStoryLoader(new LoadFromClasspath(embeddableClass))
			.useParanamer(new CachingParanamer(new BytecodeReadingParanamer()))
			.useStoryParser(new RegexStoryParser(keywords))
			.useKeywords(keywords)
			.useStoryReporterBuilder(
				new StoryReporterBuilder()		
					.withKeywords(keywords)
					.withDefaultFormats()					
					.withFormats(JBehaveConfigurationUtil.getFormats())
					.withFailureTrace(true)
					.withFailureTraceCompression(true));
	}
	
	private static Format[] getFormats() {
		SeleniumServerControllerSingleton controlador = SeleniumServerControllerSingleton.getInstancia();
		Format consoleFormat;
		String coloredConsole = BddConfigPropertiesEnum.JBEHAVE_REPORT_FORMAT_CONSOLE_COLORED.getValue();
		
		if((StringUtils.isEmpty(coloredConsole)) || ("false".equals(coloredConsole.toLowerCase()))) {
			consoleFormat = Format.CONSOLE;
		} else {
			consoleFormat = new ColoredConsoleFormat();
		}
		
		Format screenShootingFormat = new ScreenShootingHtmlFormat(controlador.getSelenium());
		
		return new Format[] { new SeleniumContextOutput(controlador.getSeleniumContext()), consoleFormat, screenShootingFormat };
	}
}