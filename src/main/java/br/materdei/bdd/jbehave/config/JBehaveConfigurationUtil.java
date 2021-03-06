package br.materdei.bdd.jbehave.config;

import java.util.Locale;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumContextOutput;

import br.materdei.bdd.jbehave.converter.MapConverter;
import br.materdei.bdd.jbehave.reporters.console.ColoredConsoleFormat;
import br.materdei.bdd.jbehave.reporters.html.ScreenShootingHtmlFormat;
import br.materdei.bdd.model.ThreadLocalModel;
import br.materdei.bdd.web.driver.WebDriverSingleton;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;

public final class JBehaveConfigurationUtil {

	private JBehaveConfigurationUtil() {
		super();
	}
	
	public static Configuration createSeleniumConfiguration(Class<?> embeddableClass) {
		Keywords keywords = new LocalizedKeywords(Locale.getDefault());
			
		return new SeleniumConfiguration()
			.useWebDriverProvider(WebDriverSingleton.get().getWebDriverProvider())
			.useStoryLoader(new LoadFromClasspath(embeddableClass))
			.useParanamer(new CachingParanamer(new BytecodeReadingParanamer()))
			.useStoryParser(new RegexStoryParser(keywords))
			.useParameterConverters(new ParameterConverters().addConverters(new MapConverter()))
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
		WebDriverSingleton controlador = WebDriverSingleton.get();
		Format consoleFormat;
		boolean coloredConsole = ThreadLocalModel.getJBehaveModel().isColoredConsoleOutput();
		
		if(coloredConsole) {
			consoleFormat = new ColoredConsoleFormat();
		} else {
			consoleFormat = Format.CONSOLE;
		}
		
		Format screenShootingFormat = new ScreenShootingHtmlFormat(controlador.getWebDriverProvider());
		
		return new Format[] { new SeleniumContextOutput(new SeleniumContext()), consoleFormat, screenShootingFormat };
	}
}