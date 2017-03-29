package com.sysc4806;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@SpringBootApplication
@Configuration
public class AmaApplication {

	@Bean
	public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator(){

		Resource sourceData = new ClassPathResource(System.getProperty("user.dir") +
				"\\src\\com\\sysc4806\\testData.json");

		Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
		factory.setResources(new Resource[] {sourceData});
		return factory;
	}

	public static void main(String[] args) {
		SpringApplication.run(AmaApplication.class, args);
	}
}
