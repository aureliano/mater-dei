package br.materdei.bdd.jbehave.reporters.html;

import java.io.File;
import java.text.MessageFormat;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.failures.PendingStepFound;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.reporters.StoryReporterBuilder;

import com.thoughtworks.selenium.Selenium;

public class SeleniumScreenshotOnFailure {

	public static final String DEFAULT_SCREENSHOT_PATH_PATTERN = "{0}/view/screenshots/failed-scenario-{1}.png";
	
	private Selenium selenium;
	protected final StoryReporterBuilder reporterBuilder;
	protected final String screenshotPathPattern;

	public SeleniumScreenshotOnFailure(Selenium selenium) {
		this(selenium, new StoryReporterBuilder());
	}

	public SeleniumScreenshotOnFailure(Selenium selenium, StoryReporterBuilder reporterBuilder) {
		this(selenium, reporterBuilder, DEFAULT_SCREENSHOT_PATH_PATTERN);
	}

	public SeleniumScreenshotOnFailure(Selenium selenium, StoryReporterBuilder reporterBuilder, String screenshotPathPattern) {
		this.selenium = selenium;
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
			currentUrl = selenium.getLocation();
		} catch (Exception e) {
		}

		try {
			// Criando diretório se não existir
			File pastaTelasCapturadas = new File(screenshotPath);
			FileUtils.forceMkdir(new File(pastaTelasCapturadas.getParent()));
		} catch (Exception ex) {
			System.out.println("WARN: Falha ao criar diretório com imagens capturadas de telas com erro. " + ex.getMessage());
		}

		boolean savedIt = false;
		try {
			selenium.captureEntirePageScreenshot(screenshotPath, "");
			savedIt = true;
		} catch (Exception ex) {
			
			System.out.println("WARN: Screenshot da página '" + currentUrl + ". A tentar novamente. Causa: " + ex.getMessage());
			// Try it again. WebDriver (on SauceLabs at least?) has blank-page and zero length files issues.
			try {
				selenium.captureEntirePageScreenshot(screenshotPath, "");
				savedIt = true;
			} catch (Exception e) {
				System.out.println("WARN: Screenshot da página '" + currentUrl + "' NÃO foi salvo em '" + screenshotPath
						+ "' porque o erro '" + e.getMessage() + "' foi encontrado.");
				e.printStackTrace();
				return;
			}
		}

		if (savedIt) {
			System.out.println("Screenshot da página '" + currentUrl + "' foi salvo em '" + screenshotPath + "' com "
					+ new File(screenshotPath).length() + " bytes");
		} else {
			System.out.println("WARN: Screenshot da página '" + currentUrl +
							"' NÃO foi salvo. Se não houver erro, talvez o tipo de WebDriver usado não seja compatível com a captura de tela");
		}
	}

	protected String screenshotPath(UUID uuid) {
		return MessageFormat.format(screenshotPathPattern, reporterBuilder.outputDirectory(), uuid);
	}
}