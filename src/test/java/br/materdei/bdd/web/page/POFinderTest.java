package br.materdei.bdd.web.page;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class POFinderTest {

	@Test
	public void testFind() {
		assertNotNull(POFinder.findByName("MeuObjetoDePagina"));
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindWithNullNameParam() {
		POFinder.findByName(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindUnexistingPageName() {
		POFinder.findByName("NomeNaoExistente");
	}
	
	@Test
	public void testFindByClass() {
		IPage page = POFinder.findByClass(MyPageObject.class);
		assertNotNull(page);
		assertTrue(page instanceof MyPageObject);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindByClassWithNullClassParam() {
		POFinder.findByClass(IPage.class);
	}
}