package br.materdei.bdd.jbehave;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class BddProperties {

	private BddProperties() {
		super();
	}
	
	private static Properties propriedades;
	private static final String CAMINHO_ARQUIVO_PROPRIEDADES;

	static {
		URL url = ClassLoader.getSystemResource("bdd-config.properties");
		CAMINHO_ARQUIVO_PROPRIEDADES = url.getFile();
	}
	
	private static void carregaPropriedades() {		
		propriedades = new Properties();

		InputStream stream = null;
		try {
			stream = new FileInputStream(CAMINHO_ARQUIVO_PROPRIEDADES);
			propriedades.load(stream);
		} catch (IOException ex) {
			throw new RuntimeException("Falha ao carregar propriedades de configuração do projeto. Ex.: ", ex);
		} finally {
			if(stream != null) {
				try {
					stream.close();
				} catch (IOException ex) { }
			}
		}
	}

	public static Properties getProperties() {
		if (propriedades == null) {
			carregaPropriedades();
		}

		return propriedades;
	}

	public static String getPropriedade(String chave) {
		if (propriedades == null) {
			carregaPropriedades();
		}

		return propriedades.getProperty(chave);
	}
}