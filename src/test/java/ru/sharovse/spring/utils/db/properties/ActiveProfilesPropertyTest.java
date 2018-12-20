package ru.sharovse.spring.utils.db.properties;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(resolver=MyActiveProfilesResolver.class)
@ActiveProfilesPropertyConfiguration(codepage="UTF-8",values="classpath:active-profiles.properties")
public class ActiveProfilesPropertyTest {
	
	@Value("${URL}")
	String url;
	
	@Value("${SERVICE_ID}")
	String serviceId;
	
	@Value("${STEND}")
	String stend;
	
	@Value("${VALUE_DB_WRITE_PROPERTY:}")
	String write;
	
	@Value("${VALUE_DB_READ_PROPERTY:}")
	String read;
	
	@Value("${"+ActiveProfilesPropertyConstants.DEFAULT_ACTIVE_PROFILES_INJECT_NAME+"}")
	String profiles;
	
	@Test
	public void testGetVars(){
		assertEquals("local-alpha", url);
		assertEquals("SE000123l", serviceId);
		assertEquals("local", stend);
		assertEquals("file.txt", write);
		assertEquals("", read);
		
		assertNotNull(profiles);
		assertTrue(profiles.contains(MyActiveProfilesResolver.ALPHA));
		assertTrue(profiles.contains(MyActiveProfilesResolver.LOCAL));

		
	}
	
}
