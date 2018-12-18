package ru.sharovse.spring.utils.db.properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SpringBootApplication
@ConfigurationProperties
public class TestStarter  implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(TestStarter.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
	}	
	
}
