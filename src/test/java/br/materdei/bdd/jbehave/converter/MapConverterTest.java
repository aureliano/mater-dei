package br.materdei.bdd.jbehave.converter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class MapConverterTest {

	private MapConverter converter;

	public MapConverterTest() {
		converter = new MapConverter();
	}
	
	@Test
	public void testReplaceCommas() {
		assertEquals(",", converter.replaceCommas(","));
		assertEquals("\"&#44;\"", converter.replaceCommas("\",\""));
		assertEquals("key => \"banana&#44; maçã&#44; laranja&#44; limão\"", converter.replaceCommas("key => \"banana, maçã, laranja, limão\""));
		assertEquals("\"{casa}&#44;[aparatamento]\"", converter.replaceCommas("\"{casa},[aparatamento]\""));
	}
	
	@Test
	public void testReplaceSlashedQuotes() {
		assertEquals("\"", converter.replaceSlashedQuotes("\\\""));
		assertEquals("casa do \"mau\" hábito", converter.replaceSlashedQuotes("casa do \\\"mau\\\" hábito"));
		assertEquals("id => 548L, nome => \"Aureliano é um \"testador\"\", bonus => 235.68F", converter.replaceSlashedQuotes("id => 548L, nome => \"Aureliano é um \\\"testador\\\"\", bonus => 235.68F"));
		assertEquals("Aureliano é um \"testador\"", converter.replaceSlashedQuotes("Aureliano é um \"testador\""));
	}

	@Test
	public void testValueMatchesRule() {
		assertTrue(converter.valueMatchesRule("campo1=>valor2"));
		assertTrue(converter.valueMatchesRule("campo => valor"));
		assertTrue(converter.valueMatchesRule("c1=> v2v"));
		assertTrue(converter.valueMatchesRule(" chave   =>    valor"));
		assertTrue(converter.valueMatchesRule("   k=>  v, k1  =>v2 ,k3  =>v3"));
		assertTrue(converter.valueMatchesRule("k   =>   V   ,k2=>v2, k3 => v3   ,    k4   => v4  "));

		assertFalse(converter.valueMatchesRule(""));
		assertFalse(converter.valueMatchesRule("campo= >chave}"));
		assertFalse(converter.valueMatchesRule("campo"));
		assertFalse(converter.valueMatchesRule("campo=valor"));
		assertFalse(converter.valueMatchesRule("campo>valor"));
	}

	@Test
	public void testBuildDataMap() {
		Map<String, Object> map = converter.buildDataMap("c => \"v\"");
		assertEquals(map.get("c"), "v");

		map = converter.buildDataMap("id => 548L, nome => \"Aureliano\", bonus => 235.68F");
		assertEquals(map.get("id"), 548L);
		assertEquals(map.get("nome"), "Aureliano");
		assertEquals(map.get("bonus"), 235.68F);
	}

	@Test
	public void testConvertData() {
		assertTrue(converter.convertData("1245I") instanceof Integer);
		assertTrue(converter.convertData("1245L") instanceof Long);
		assertTrue(converter.convertData("1245.320D") instanceof Double);
		assertTrue(converter.convertData("1245.3321F") instanceof Float);
		assertTrue(converter.convertData("\"1245I.3321F\"") instanceof String);

		assertNull(converter.convertData(""));
		assertNull(converter.convertData(null));
	}

	@Test
	public void testLastChar() {
		assertEquals(converter.lastChar("casa"), 'a');
		assertEquals(converter.lastChar("ameixa seca"), 'a');
		assertEquals(converter.lastChar("robalo"), 'o');
		assertEquals(converter.lastChar("shopping"), 'g');
		assertEquals(converter.lastChar("O céu está lindo!"), '!');
		assertEquals(converter.lastChar("452.32D"), 'D');
		assertEquals(converter.lastChar("587454121354L"), 'L');
		assertEquals(converter.lastChar("46521I"), 'I');
		assertEquals(converter.lastChar("5784.9874F"), 'F');
	}
}