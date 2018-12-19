package ru.sharovse.spring.utils.db.properties;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import ru.sharovse.spring.utils.db.properties.ActiveProfilesPropertySource.StringValue;

@RunWith(MockitoJUnitRunner.class)
public class ActiveProfilesPropertySourceTest {

	ActiveProfilesPropertySource service;
	
	private String profile1 = "profile1";
	private String profile2 = "profile2";
	 
	private String name = "name";
	
	private String varName = "varName";
	private String varValue = "varValue";
	
	private String[] profiles = new String[]{ profile1, profile2 };
	
	Map<String, Map<String, String>> mapSections;
	Map<String, String> mapVars;

	private String allProfiles = profile1+","+profile2;
	
	@Before
	public void setUp() throws Exception {
		mapVars = new HashMap<>();
		mapVars.put(varName, varValue);
		
		mapSections = new HashMap<>();
		mapSections.put(profile1, mapVars);
		
		service = spy(new ActiveProfilesPropertySource(name, profiles, mapSections));
	}

	@Test
	public void testActiveProfilesPropertySource() {
		assertEquals(mapSections, service.map);
		assertArrayEquals(profiles, service.profiles);
		assertEquals(allProfiles, service.allProfiles);
		
		ActiveProfilesPropertySource s1 = new ActiveProfilesPropertySource(name, null, mapSections);
		
	}

	@Test
	public void testGetValue() {
		doNothing().when(service).setIfExistStringValue(any(StringValue.class), eq(profile1), eq(varName));
		doNothing().when(service).setIfExistStringValue(any(StringValue.class), eq(profile2), eq(varName));
		doNothing().when(service).setIfExistStringValue(any(StringValue.class), eq(allProfiles), eq(varName));
		service.getValue(varName);
		verify(service).setIfExistStringValue(any(StringValue.class), eq(profile1), eq(varName));
		verify(service).setIfExistStringValue(any(StringValue.class), eq(profile2), eq(varName));
		verify(service).setIfExistStringValue(any(StringValue.class), eq(allProfiles), eq(varName));
	}

	@Test
	public void testContainsPropertyAndSetCachedContainVar() {
		service.cache = new HashMap<>();
		service.cache.put(varName, varValue);
		assertTrue( service.containsPropertyAndSetCached(varName) );
	}

	@Test
	public void testContainsPropertyAndSetCachedTrue() {
		service.cache = new HashMap<>();
		doReturn(varValue).when(service).getValue(eq(varName));
		assertTrue( service.containsPropertyAndSetCached(varName) );
	}

	@Test
	public void testContainsPropertyAndSetCachedFalse() {
		service.cache = new HashMap<>();
		doReturn(null).when(service).getValue(eq(varName));
		assertFalse( service.containsPropertyAndSetCached(varName) );
	}

	@Test
	public void testGetCachedProperty() {
		service.cache = new HashMap<>();
		assertNull(service.getCachedProperty(varName));
		service.cache.put(varName, varValue);
		assertEquals(varValue, service.getCachedProperty(varName));
	}

	@Test
	public void testContainsPropertyString() {
		doReturn(false).when(service).containsPropertyAndSetCached(varName);
		assertFalse( service.containsProperty(varName) );
		doReturn(true).when(service).containsPropertyAndSetCached(varName);
		assertTrue( service.containsProperty(varName) );
	}

	@Test
	public void testGetPropertyString() {
		doReturn(null).when(service).getValue(eq(varName));
		assertNull(service.getProperty(varName));
		doReturn(varValue).when(service).getValue(eq(varName));
		assertEquals(varValue, service.getProperty(varName));
	}

	@Test
	public void testSetIfExistStringValue() {
		StringValue stringValue = new StringValue();
		service.setIfExistStringValue(stringValue, profile1, varName);
		assertEquals(varValue, stringValue.value);
	}

}
