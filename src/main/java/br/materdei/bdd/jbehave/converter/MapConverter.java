package br.materdei.bdd.jbehave.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

/**
 * Converte uma String numa coleção do tipo {@link Map}. O texto deverá seguir o
 * padrão {nomeCampo1 => valorCampo1, nomeCampo2 => valorCampo2}.
 */
public class MapConverter implements ParameterConverter {

	private static final String REGEX = "\\s*\\w+\\s*=>\\s*\"?[\\w\\W]+\"?\\s*";

	@Override
	public boolean accept(Type type) {
		if (type instanceof ParameterizedType) {
			Type rawType = this.rawType(type);
			Type[] argumentTypes = this.argumentTypes(type);
			return Map.class.isAssignableFrom((Class<?>) rawType) && String.class.isAssignableFrom((Class<?>) argumentTypes[0])
					&& Object.class.isAssignableFrom((Class<?>) argumentTypes[1]);
		}

		return false;
	}

	@Override
	public Object convertValue(String value, Type type) {
		if (!this.valueMatchesRule(value)) {
			throw new RuntimeException(String.format("O parâmetro '%s' da Tabela de Exemplos não bate com a expressão %s", value, REGEX));
		}

		return this.buildDataMap(value);
	}
	
	protected String replaceCommas(String value) {
		String allowedChars = "ÁáÀàÉéÍíÓóÚúÃãÕõÊêÔôÇç,.;:/?°º\\]}ª\\[{'\"!@#$%&*()-=+§\\\\";
		String text = "";
		Matcher matcher = Pattern.compile("\"[\\w\\s" + allowedChars + "]*,[\\w\\s" + allowedChars + "]*\"").matcher(value);
		
		while (matcher.find()) {       
            String found = matcher.group();
            String novo = found.replaceAll(",", "&#44;");
            text = value.replaceAll(Pattern.quote(found), novo);
        }
		
		return ("".equals(text)) ? value : text;
	}
	
	protected String replaceSlashedQuotes(String value) {
		return value.replaceAll("\\\\\"", "\"");
	}

	protected Map<String, Object> buildDataMap(String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		value = this.replaceCommas(value);
		value = this.replaceSlashedQuotes(value);
		
		String[] pares = value.split(",");

		for (String par : pares) {
			String[] chaveValor = par.split("=>");							
			map.put(chaveValor[0].trim(), this.convertData(chaveValor[1].trim()));
		}

		return map;
	}
	
	protected Object convertData(String data) {
		if (StringUtils.isEmpty(data)) {
			return null;
		}

		Class<?> tipo = null;
		char lastChar = this.lastChar(data);

		try {
			if ((data.charAt(0) == '"') && (lastChar == '"')) {
				return data.substring(1, data.length() - 1);
			} else if (lastChar == 'L') {
				tipo = Long.class;
				return Long.parseLong(data.substring(0, data.length() - 1));
			} else if (lastChar == 'D') {
				tipo = Double.class;
				return Double.parseDouble(data.substring(0, data.length() - 1));
			} else if (lastChar == 'I') {
				tipo = Integer.class;
				return Integer.parseInt(data.substring(0, data.length() - 1));
			} else if (lastChar == 'F') {
				tipo = Float.class;
				return Float.parseFloat(data.substring(0, data.length() - 1));
			} else {
				throw new IllegalArgumentException("Padrão do dado ('"+ data + "') não bate com os tipos String, Long, Double, Integer e Float. Ex.: String => \"valor\", Long => 452154L, Double => 5487D etc.");
			}
		} catch (NumberFormatException ex) {
			String nomeTipo =  tipo == null ? "nulo" : tipo.getName();
			throw new RuntimeException(String.format("Não foi possível converter o valor '%s' para '%s'", data, nomeTipo), ex);
		}
	}

	private Type[] argumentTypes(Type type) {
		return ((ParameterizedType) type).getActualTypeArguments();
	}

	private Type rawType(Type type) {
		return ((ParameterizedType) type).getRawType();
	}

	protected boolean valueMatchesRule(String value) {
		return (StringUtils.isEmpty(value) ? false : value.matches(REGEX));
	}
	
	protected char lastChar(String data) {
		return (data.charAt(data.length() - 1));
	}
}