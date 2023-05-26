package edu.uzz.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
class SpringBootStartApplication extends SpringBootServletInitializer{

	@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(SpringbootApplication.class);
	}
	}
