package br.materdei.bdd.jbehave.reporters.html;

import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.web.selenium.WebDriverProvider;

public class ScreenShootingHtmlFormat extends Format {

	private WebDriverProvider webDriverProvider;

	public ScreenShootingHtmlFormat(WebDriverProvider webDriverProvider) {
		super("HTML");
		this.webDriverProvider = webDriverProvider;
	}

	@Override
	public StoryReporter createStoryReporter(FilePrintStreamFactory factory, StoryReporterBuilder builder) {
		factory.useConfiguration(builder.fileConfiguration("html"));

		return new ScreenShootingHtmlOutput(factory.createPrintStream(), builder, this.webDriverProvider)
				.doReportFailureTrace(builder.reportFailureTrace())
				.doCompressFailureTrace(builder.compressFailureTrace());
	}
}