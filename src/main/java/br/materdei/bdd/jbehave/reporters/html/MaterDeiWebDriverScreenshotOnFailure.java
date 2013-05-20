package br.materdei.bdd.jbehave.reporters.html;

import java.io.File;
import java.text.MessageFormat;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.failures.PendingStepFound;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.web.selenium.WebDriverProvider;

public class MaterDeiWebDriverScreenshotOnFailure {

	private static final String DEFAULT_SCREENSHOT_PATH_PATTERN = "{0}/view/screenshots/failed-scenario-{1}.png";
	private static final Logger logger = Logger.getLogger(MaterDeiWebDriverScreenshotOnFailure.class);
	
	private WebDriverProvider webDriverProvider;
	protected final StoryReporterBuilder reporterBuilder;
	protected final String screenshotPathPattern;

	public MaterDeiWebDriverScreenshotOnFailure(WebDriverProvider webDriverProvider) {
		this(webDriverProvider, new StoryReporterBuilder());
	}

	public MaterDeiWebDriverScreenshotOnFailure(WebDriverProvider webDriverProvider, StoryReporterBuilder reporterBuilder) {
		this(webDriverProvider, reporterBuilder, DEFAULT_SCREENSHOT_PATH_PATTERN);
	}

	public MaterDeiWebDriverScreenshotOnFailure(WebDriverProvider webDriverProvider, StoryReporterBuilder reporterBuilder, String screenshotPathPattern) {
		this.webDriverProvider = webDriverProvider;
		this.reporterBuilder = reporterBuilder;
		this.screenshotPathPattern = screenshotPathPattern;
	}

	@AfterScenario(uponOutcome = Outcome.FAILURE)
	public void afterScenarioFailure(UUIDExceptionWrapper uuidWrappedFailure) throws Exception {
		if (uuidWrappedFailure instanceof PendingStepFound) {
			return; // we don't take screen-shots for Pending Steps
		}
		String screenshotPath = screenshotPath(uuidWrappedFailure.getUUID());

		String currentUrl = "[unknown page title]";
		try {
			currentUrl = this.webDriverProvider.get().getCurrentUrl();
		} catch (Exception e) {
		}

		try {
			// Criando diretório se não existir
			File pastaTelasCapturadas = new File(screenshotPath);
			FileUtils.forceMkdir(new File(pastaTelasCapturadas.getParent()));
		} catch (Exception ex) {
			logger.warn("Falha ao criar diretório com imagens capturadas de telas com erro. ", ex);
		}

		boolean savedIt = false;
		try {
			this.webDriverProvider.saveScreenshotTo(screenshotPath);
			savedIt = true;
		} catch (Exception ex) {
			
			logger.warn("Screenshot da página '" + currentUrl + ". A tentar novamente. Causa: " + ex.getMessage());
			// Try it again. WebDriver (on SauceLabs at least?) has blank-page and zero length files issues.
			try {
				this.webDriverProvider.saveScreenshotTo(screenshotPath);
				savedIt = true;
			} catch (Exception e) {
				logger.warn("Screenshot da página '" + currentUrl + "' NÃO foi salvo em '" + screenshotPath
						+ "' porque o erro '" + e.getMessage() + "' foi encontrado.");
				e.printStackTrace();
				return;
			}
		}

		if (savedIt) {
			logger.info("Screenshot da página '" + currentUrl + "' foi salvo em '" + screenshotPath + "' com "
					+ new File(screenshotPath).length() + " bytes");
		} else {
			logger.warn("Screenshot da página '" + currentUrl +
							"' NÃO foi salvo. Se não houver erro, talvez o tipo de WebDriver usado não seja compatível com a captura de tela");
		}
	}

	protected String screenshotPath(UUID uuid) {
		return MessageFormat.format(screenshotPathPattern, reporterBuilder.outputDirectory(), uuid);
	}
}