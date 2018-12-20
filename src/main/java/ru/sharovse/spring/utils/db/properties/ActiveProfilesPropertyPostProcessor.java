package ru.sharovse.spring.utils.db.properties;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ClassUtils;

public class ActiveProfilesPropertyPostProcessor implements BeanPostProcessor {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(ActiveProfilesPropertyPostProcessor.class);

	@Autowired
	ConfigurableEnvironment environment;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		Class<?> userClass = ClassUtils.getUserClass(bean.getClass());
		if (userClass.isAnnotationPresent(ActiveProfilesPropertyConfiguration.class)) {
			configureProperties(bean, beanName, userClass.getAnnotation(ActiveProfilesPropertyConfiguration.class));
		}
		return bean;
	}
	ActiveProfilesPropertyUtils utils = new ActiveProfilesPropertyUtils();
	public static final String PREFIX = "ACTIVE_PROFILES_PROPERTY_";
	void configureProperties(Object bean, String beanName, ActiveProfilesPropertyConfiguration annotation) {
		int i = 0;
		for (String resources : annotation.values()) {
			try {
				environment.getPropertySources().addFirst(
						new ActiveProfilesPropertySource(
								PREFIX+beanName+(++i),
								environment.getActiveProfiles(),
								utils.readVars(
										bean.getClass().getClassLoader(), 
										resources, 
										annotation.codepage()
										),
								annotation.activeProfilesInjectName()
						)
				);
			} catch (IOException e) {
				throw new BeanCreationException("Error add ActiveProfile Properties "+annotation);
			}
		}
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		return bean;
	}

}
