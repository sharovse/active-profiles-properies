package ru.sharovse.spring.utils.db.properties;

public class MyActiveProfilesResolver implements org.springframework.test.context.ActiveProfilesResolver {

	@Override
	public String[] resolve(Class<?> testClass) {
		return new String[] { "local", "alpha" };
	}

}
