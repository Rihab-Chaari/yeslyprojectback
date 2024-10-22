package com.java.demo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class YeslyBackend {

	public static void main(String[] args) {
		SpringApplication.run(YeslyBackend.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer customizer() {
    return builder -> builder.failOnUnknownProperties(false);
}

}
