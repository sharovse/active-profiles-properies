package ru.sharovse.spring.utils.db.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBootApplication
@ConfigurationProperties
@ActiveProfilesPropertyConfiguration(values= {"classpath:active-profiles.properties"},codepage="UTF-8")
public class TestStarter implements CommandLineRunner {
	
	@Value("URL")
	String url;
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(TestStarter.class);
		application.setAdditionalProfiles(new String []{"local","alpha"});
		application.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("URL="+url);
	}	
	
}
