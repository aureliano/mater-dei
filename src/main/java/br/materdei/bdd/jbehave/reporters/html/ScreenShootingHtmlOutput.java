package br.materdei.bdd.jbehave.reporters.html;

import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.web.selenium.WebDriverHtmlOutput;
import org.jbehave.web.selenium.WebDriverProvider;

public class ScreenShootingHtmlOutput extends WebDriverHtmlOutput {
	
	private MaterDeiWebDriverScreenshotOnFailure screenShotMaker;
	private static final Logger logger = Logger.getLogger(ScreenShootingHtmlOutput.class);

	public ScreenShootingHtmlOutput(PrintStream output, StoryReporterBuilder reporterBuilder, WebDriverProvider webDriverProvider) {
		super(output, reporterBuilder.keywords());
		this.screenShotMaker = new MaterDeiWebDriverScreenshotOnFailure(webDriverProvider);
		super.overwritePattern("failed",
				"<div class=\"step failed\">{0} <span class=\"keyword failed\">({1})</span><br/><span class=\"message failed\">{2}</span>"
						+ "<br/><img src=\"screenshots/failed-scenario-{3}.png\" alt=\"failing screenshot\"/></div>\n");
	}

	@Override
	public void afterStory(boolean givenStory) {
		super.afterStory(givenStory);
		print("\n</body>\n</html>");
	}

	@Override
	public void beforeStory(Story story, boolean givenStory) {
		print("<html>\n<head>\n\t<style type=\"text/css\">@import \"view/style/jbehave-core.css\";</style>\n</head>\n\n<body>\n");
		super.beforeStory(story, givenStory);
	}

	@Override
	public void failed(String step, Throwable storyFailure) {
		super.failed(step, storyFailure);
		try {
			
			if(storyFailure instanceof UUIDExceptionWrapper) {			
				UUIDExceptionWrapper uuidWrappedFailure = (UUIDExceptionWrapper) storyFailure;
				this.screenShotMaker.afterScenarioFailure(uuidWrappedFailure);
			} else {
				logger.warn("Tela de erro não foi salva");
			}

		} catch (Exception ex) {
			logger.warn("Falha ao capturar tela de erro");
		}
	}
}