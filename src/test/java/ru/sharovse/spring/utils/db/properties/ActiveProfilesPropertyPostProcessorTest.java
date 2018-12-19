package ru.sharovse.spring.utils.db.properties;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

@RunWith(MockitoJUnitRunner.class)
public class ActiveProfilesPropertyPostProcessorTest {

	ActiveProfilesPropertyPostProcessor service;

	TestStarter bean = new TestStarter();   
	ActiveProfilesPropertyUtils beanEmpty = new ActiveProfilesPropertyUtils();
	
	String beanName = "beanName";

	@Mock
	ConfigurableEnvironment environment;
	
	@Mock
	MutablePropertySources mutablePropertySources;
	
	@Mock
	ActiveProfilesPropertyUtils utils;
	
	
	String resource1 = "resource1";
	String resource2 = "resource2";
	String [] resources = new String[] { resource1, resource2 };
	
	@Mock
	ActiveProfilesPropertyConfiguration annotation;
	
	@Before
	public void setUp() throws Exception {
		service = spy(new ActiveProfilesPropertyPostProcessor());
		doReturn(resources).when(annotation).values();
		service.environment = environment;
		doReturn(mutablePropertySources).when(environment).getPropertySources();
	}

	@Test
	public void testPostProcessBeforeInitialization() throws IOException {
		service.postProcessBeforeInitialization(beanEmpty, beanName);
		verify(service,times(0)).configureProperties(eq(bean), eq(beanName), any(ActiveProfilesPropertyConfiguration.class));
		
		doNothing().when(service).configureProperties(eq(bean), eq(beanName), any(ActiveProfilesPropertyConfiguration.class));
		service.postProcessBeforeInitialization(bean, beanName);
		verify(service,times(1)).configureProperties(eq(bean), eq(beanName), any(ActiveProfilesPropertyConfiguration.class));
	}

	@Test
	public void testConfigureProperties() throws IOException {
		service.utils = utils;
		doReturn(null).when(utils).readVars(any(ClassLoader.class), anyString(), anyString());
		
		service.configureProperties(bean, beanName, annotation);
		verify(mutablePropertySources, times(2)).addFirst(any(PropertySource.class));
	}

	@Test(expected=BeanCreationException.class)
	public void testConfigurePropertiesException() throws IOException {
		service.utils = utils;
		doThrow(new IOException()).when(utils).readVars(any(ClassLoader.class), anyString(), anyString());
		service.configureProperties(bean, beanName, annotation);
	}

	@Test
	public void testPostProcessAfterInitialization() {
		assertEquals(bean, service.postProcessAfterInitialization(bean, beanName));
	}

}
