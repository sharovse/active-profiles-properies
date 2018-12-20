package ru.sharovse.spring.utils.db.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveProfilesPropertyAutoConfiguration {
	
	@Bean
	public ActiveProfilesPropertyPostProcessor createValueDbAnnotationBeanPostProcessor() {
		return new ActiveProfilesPropertyPostProcessor();
	}

}
