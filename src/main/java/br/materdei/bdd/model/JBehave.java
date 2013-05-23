package br.materdei.bdd.model;

import java.util.Properties;

import br.materdei.bdd.jbehave.config.BddConfigPropertiesEnum;

public class JBehave {

	private String disabledTestsFile;
	private String reportOutputDir;
	private boolean coloredConsoleOutput;
	private String storiesPath;
	private String scenarioClassesPath;
	private boolean ignoreTestExecution;
	
	public JBehave() {
		this.useDefaultValues();
	}
	
	public JBehave(Properties p) {
		this.useDisabledTestsFile(p.getProperty(BddConfigPropertiesEnum.JBEHAVE_DISABLED_TESTS.getKey()));
		this.useReportOutputDir(p.getProperty(BddConfigPropertiesEnum.JBEHAVE_REPORT_OUTPUT_DIR.getKey()));
		this.useStoriesPath(p.getProperty(BddConfigPropertiesEnum.JBEHAVE_STORIES_PATH.getKey()));
		this.useScenarioClassesPath(p.getProperty(BddConfigPropertiesEnum.JBEHAVE_SCENARIO_CLASSES_PATH.getKey()));
		
		String value = p.getProperty(BddConfigPropertiesEnum.JBEHAVE_REPORT_FORMAT_CONSOLE_COLORED.getKey());
		this.useColoredConsoleOutput((value == null) ? false : Boolean.parseBoolean(value));
		
		value = p.getProperty(BddConfigPropertiesEnum.JBEHAVE_IGNORE_TEST_EXECUTION.getKey());
		this.useIgnoreTestExecution((value == null) ? false : Boolean.parseBoolean(value));
		
		this.useDefaultValues();
	}

	public String getDisabledTestsFile() {
		return disabledTestsFile;
	}

	/**
	 * Arquivo contendo a lista de arquivos de estória que terão a execução ignorada.
	 * 
	 * @param disabledTestsFile
	 * @return Este objeto
	 */
	public JBehave useDisabledTestsFile(String disabledTestsFile) {
		this.disabledTestsFile = disabledTestsFile;
		return this;
	}

	public String getReportOutputDir() {
		return reportOutputDir;
	}

	/**
	 * Caminho relativo para a pasta de relatório do JBehave.
	 * Valor padrão: target/jbehave/view
	 * 
	 * @param reportOutputDir
	 * @return Este objeto
	 */
	public JBehave useReportOutputDir(String reportOutputDir) {
		this.reportOutputDir = reportOutputDir;
		return this;
	}

	public boolean isColoredConsoleOutput() {
		return coloredConsoleOutput;
	}

	/**
	 * Define se o log das estórias sairá colorido no terminal.
	 * Valor padrão: false
	 * 
	 * @param coloredConsoleOutput
	 * @return Este objeto
	 */
	public JBehave useColoredConsoleOutput(boolean coloredConsoleOutput) {
		this.coloredConsoleOutput = coloredConsoleOutput;
		return this;
	}

	public String getStoriesPath() {
		return storiesPath;
	}

	/**
	 * Caminho relativo para a pasta raíz de estórias de usuário.
	 * Valor padrão: src/main/resources
	 * 
	 * @param storiesPath
	 * @return Este objeto
	 */
	public JBehave useStoriesPath(String storiesPath) {
		this.storiesPath = storiesPath;
		return this;
	}

	public String getScenarioClassesPath() {
		return scenarioClassesPath;
	}

	/**
	 * Caminho relativo para a pasta raíz dos arquivos de steps (cenários).
	 * Valor padrão: src/test/java
	 * 
	 * @param scenarioClassesPath
	 * @return Este objeto
	 */
	public JBehave useScenarioClassesPath(String scenarioClassesPath) {
		this.scenarioClassesPath = scenarioClassesPath;
		return this;
	}

	public boolean isIgnoreTestExecution() {
		return ignoreTestExecution;
	}

	/**
	 * Ignora execução de todas as estórias de usuário.
	 * Valor padrão: false
	 * 
	 * @param ignoreTestExecution
	 * @return Este objeto
	 */
	public JBehave useIgnoreTestExecution(boolean ignoreTestExecution) {
		this.ignoreTestExecution = ignoreTestExecution;
		return this;
	}
	
	public void validation() {
		if (this.reportOutputDir == null) {
			throw new RuntimeException("Um valor deve ser informado para " + BddConfigPropertiesEnum.JBEHAVE_REPORT_OUTPUT_DIR.getKey());
		}
		
		if (this.storiesPath == null) {
			throw new RuntimeException("Um valor deve ser informado para " + BddConfigPropertiesEnum.JBEHAVE_STORIES_PATH.getKey());
		}
		
		if (this.scenarioClassesPath == null) {
			throw new RuntimeException("Um valor deve ser informado para " + BddConfigPropertiesEnum.JBEHAVE_SCENARIO_CLASSES_PATH.getKey());
		}
	}
	
	private void useDefaultValues() {
		if (this.reportOutputDir == null) {
			this.useReportOutputDir("target/jbehave/view");
		}
		
		if (this.storiesPath == null) {
			this.useStoriesPath("src/main/resources");
		}
		
		if (this.scenarioClassesPath == null) {
			this.useScenarioClassesPath("src/test/java");
		}
	}
}