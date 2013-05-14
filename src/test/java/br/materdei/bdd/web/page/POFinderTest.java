package br.materdei.bdd.web.page;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class POFinderTest {

	@Test
	public void testFind() {
		assertNull(POFinder.findByName(null));
		assertNull(POFinder.findByName("NomeNaoExistente"));
		assertNotNull(POFinder.findByName("MeuObjetoDePagina"));
	}
	
	@Test
	public void testFindByClass() {
		IPage page = POFinder.findByClass(MyPageObject.class);
		assertNotNull(page);
		assertTrue(page instanceof MyPageObject);
		
		assertNull(POFinder.findByClass(IPage.class));
	}
}