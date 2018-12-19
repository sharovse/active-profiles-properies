package ru.sharovse.spring.utils.db.properties;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ActiveProfilesPropertyUtilsTest {

	ActiveProfilesPropertyUtils service;
	
	@Before
	public void setUp() throws Exception {
		service = new ActiveProfilesPropertyUtils();
	}

	String resurceUrl = "active-profiles.properties";
	String resurceFile = "src/test/resources/active-profiles.properties";
	String codepage = "utf-8";
	
	@Test
	public void testReadVars() throws IOException {
		Map<String, Map<String, String>> da = service.readVars(this.getClass().getClassLoader(), ActiveProfilesPropertyUtils.CLASSPATH_PREFIX+resurceUrl, codepage);
		assertTrue(da.size()==8);
	}

	String value = "value";
	String name = "name";
	String line = name +ActiveProfilesPropertyUtils.VAR_DELIMETER+value;

	@Test
	public void testAddLine() {
		Map<String, String> vars = new HashMap<>();
		service.addLine(vars, line);
		assertEquals(value, vars.get(name));
	}

	@Test
	public void testAddVar() {
		Map<String, Map<String, String>> map = new HashMap<>();
		String currendSection = "currendSection";
		service.addVar(map, currendSection, line);
		assertEquals(value, map.get(currendSection).get(name));
	}

	String sectionName = "section";
	String sectionLine = String.format("#[%s]",sectionName);
	
	@Test
	public void testgetSection() {
		assertNull(service.getSection(line));
		assertEquals(sectionName, service.getSection(sectionLine));
	}

	@Test
	public void testReadFileAbsolute() throws IOException {
		assertNotNull(service.readFileAbsolute(resurceFile, codepage) );
	}

	@Test
	public void testReadFileClasspath() throws IOException {
		assertNotNull(service.readFileClasspath(this.getClass().getClassLoader(), resurceUrl, codepage) );
	}

	@Test
	public void testReadFile() throws IOException {
		assertNotNull(service.readFile(this.getClass().getClassLoader(), ActiveProfilesPropertyUtils.CLASSPATH_PREFIX + resurceUrl, codepage) );
	}

	
}
