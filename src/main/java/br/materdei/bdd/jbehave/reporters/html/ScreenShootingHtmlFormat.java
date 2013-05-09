package br.materdei.bdd.jbehave.reporters.html;

import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;

import com.thoughtworks.selenium.Selenium;

public class ScreenShootingHtmlFormat extends Format {

	private Selenium selenium;

	public ScreenShootingHtmlFormat(Selenium selenium) {
		super("HTML");
		this.selenium = selenium;
	}

	@Override
	public StoryReporter createStoryReporter(FilePrintStreamFactory factory, StoryReporterBuilder builder) {
		factory.useConfiguration(builder.fileConfiguration("html"));

		return new ScreenShootingHtmlOutput(factory.createPrintStream(), builder, selenium)
				.doReportFailureTrace(builder.reportFailureTrace())
				.doCompressFailureTrace(builder.compressFailureTrace());
	}
}