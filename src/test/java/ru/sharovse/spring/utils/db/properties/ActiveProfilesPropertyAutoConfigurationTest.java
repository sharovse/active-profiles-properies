package ru.sharovse.spring.utils.db.properties;

import static org.junit.Assert.*;

import org.junit.Test;

public class ActiveProfilesPropertyAutoConfigurationTest {

	ActiveProfilesPropertyAutoConfiguration service = new ActiveProfilesPropertyAutoConfiguration();

	@Test
	public void testCreateValueDbAnnotationBeanPostProcessor() {
		assertNotNull(service.createValueDbAnnotationBeanPostProcessor());
	}

}
