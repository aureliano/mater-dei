package br.materdei.bdd.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import br.materdei.bdd.database.DatabasesEnum;

public class DatabaseTest {
	
	@Test
	public void testLoadProperties() throws Exception {
		Properties p = new Properties();
		p.load(new FileInputStream(new File("src/test/resources/bdd-config.properties")));
		Database d = new Database(p);
		
		assertEquals("com.mysql.driver", d.getConnectionDriver());
		assertEquals("usuario", d.getConnectionUser());
		assertEquals("senha", d.getConnectionPassword());
		assertEquals("bancodados", d.getConnectionDatabase());
		assertEquals(new Integer(12345), d.getConnectionPort());
		assertEquals("127.0.0.1", d.getConnectionHost());
	}
	
	@Test
	public void testUseConnectionDriver() {
		String name = "nome.do.driver.de.conexão";
		Database d = new Database().useConnectionDriver(name);
		assertEquals(name, d.getConnectionDriver());
	}
	
	@Test
	public void testUseConnectionUser() {
		String name = "usuario";
		Database d = new Database().useConnectionUser(name);
		assertEquals(name, d.getConnectionUser());
	}
	
	@Test
	public void testUseConnectionPassword() {
		String pass = "mudar123";
		Database d = new Database().useConnectionPassword(pass);
		assertEquals(pass, d.getConnectionPassword());
	}
	
	@Test
	public void testUseConnectionDatabase() {
		Database d = new Database().useDefaultValues(DatabasesEnum.POSTGRESQL);
		
		assertEquals(DatabasesEnum.POSTGRESQL.getDefaultPort(), d.getConnectionPort());
		assertEquals(DatabasesEnum.POSTGRESQL.getDefaultDriver(), d.getConnectionDriver());
		assertEquals(DatabasesEnum.POSTGRESQL.getDefaultHost(), d.getConnectionHost());
		
		d = new Database().useDefaultValues(DatabasesEnum.MYSQL);
		
		assertEquals(DatabasesEnum.MYSQL.getDefaultPort(), d.getConnectionPort());
		assertEquals(DatabasesEnum.MYSQL.getDefaultDriver(), d.getConnectionDriver());
		assertEquals(DatabasesEnum.MYSQL.getDefaultHost(), d.getConnectionHost());
		
		d = new Database();
		String expectedDriver = "any.driver";
		d.useConnectionDriver(expectedDriver);
		
		Integer expectedPort = 56687;
		d.useConnectionPort(expectedPort);
		
		String expectedHost = "127.0.0.1";
		d.useConnectionHost(expectedHost);
		d.useDefaultValues(DatabasesEnum.POSTGRESQL);
		
		assertEquals(DatabasesEnum.POSTGRESQL.getDefaultPort(), d.getConnectionPort());
		assertEquals(DatabasesEnum.POSTGRESQL.getDefaultDriver(), d.getConnectionDriver());
		assertEquals(DatabasesEnum.POSTGRESQL.getDefaultHost(), d.getConnectionHost());
	}
	
	@Test
	public void testUseConnectionDatabaseWithoutDefaults() {
		Database d = new Database();
		String expectedDriver = "any.driver";
		d.useConnectionDriver(expectedDriver);
		
		Integer expectedPort = 56687;
		d.useConnectionPort(expectedPort);
		
		String expectedHost = "127.0.0.1";
		d.useConnectionHost(expectedHost);
		
		assertEquals(expectedPort, d.getConnectionPort());
		assertEquals(expectedDriver, d.getConnectionDriver());
		assertEquals(expectedHost, d.getConnectionHost());
	}
	
	@Test
	public void testUseConnectionPort() {
		Integer port = 12345;
		Database d = new Database().useConnectionPort(port);
		assertEquals(port, d.getConnectionPort());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUseConnectionPortLessThanZero() {
		new Database().useConnectionPort(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUseConnectionPortLessEqualsZero() {
		new Database().useConnectionPort(0);
	}
	
	@Test
	public void testUseConnectionHost() {
		String name = "127.0.0.1";
		Database d = new Database().useConnectionHost(name);
		assertEquals(name, d.getConnectionHost());
	}
	
	@Test
	public void testUseInitDataFiles() {
		List<File> files = Arrays.asList(new File("src/test/resources/br/materdei/data/dados_iniciais.sql"), new File("src/test/resources/br/materdei/data/script.sql"));
		Database d = new Database().useInitDataFiles(files);
		assertTrue(CollectionUtils.isEqualCollection(files, d.getInitDataFiles()));
		
		List<File> wrongList = Arrays.asList(files.get(0));
		assertFalse(CollectionUtils.isEqualCollection(wrongList, d.getInitDataFiles()));
	}
	
	@Test
	public void testUseTablesToNotClear() {
		List<String> tables = Arrays.asList("table1", "teste2", "magistrae", "verbum_domine");
		Database d = new Database().useTablesToNotClear(tables);
		assertTrue(CollectionUtils.isEqualCollection(tables, d.getTablesToNotClear()));
		
		List<String> wrongList = Arrays.asList(tables.get(0), tables.get(2));
		assertFalse(CollectionUtils.isEqualCollection(wrongList, d.getTablesToNotClear()));
	}
	
	@Test
	public void testGetUrlConnection() {
		Database d = new Database().useDefaultValues(DatabasesEnum.POSTGRESQL);
		String expected = "jdbc:postgresql://localhost:5432/";
		assertEquals(expected, d.getUrlConnection());
		
		d = new Database().useDefaultValues(DatabasesEnum.MYSQL);
		expected = "jdbc:mysql://localhost:3306/";
		assertEquals(expected, d.getUrlConnection());
		
		d = new Database();
		assertNull(d.getUrlConnection());
	}
	
	@Test
	public void testValidation() {
		Database d = new Database();
		try {
			d.validation();
		} catch (Exception ex) {
			assertEquals("Um valor deve ser informado para database.connection.driver", ex.getMessage());
		}
		
		d = new Database().useConnectionDriver("any.driver");
		try {
			d.validation();
		} catch (Exception ex) {
			assertEquals("Um valor deve ser informado para database.connection.port", ex.getMessage());
		}
		
		d = new Database().useConnectionDriver("any.driver").useConnectionPort(123).useConnectionHost(null);
		try {
			d.validation();
		} catch (Exception ex) {
			assertEquals("Um valor deve ser informado para database.connection.host", ex.getMessage());
		}
		
		d = new Database().useConnectionDriver("any.driver").useConnectionPort(123).useConnectionHost("localhost");
		try {
			d.validation();
		} catch (Exception ex) {
			assertEquals("Um valor deve ser informado para database.connection.db", ex.getMessage());
		}
	}
}