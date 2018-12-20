package ru.sharovse.spring.utils.db.properties;

public class MyActiveProfilesResolver implements org.springframework.test.context.ActiveProfilesResolver {

	public static String ALPHA = "alpha";
	public static String LOCAL = "local";
	
	@Override
	public String[] resolve(Class<?> testClass) {
		return new String[] { LOCAL, ALPHA  };
	}

}
