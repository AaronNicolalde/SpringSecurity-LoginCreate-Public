package com.homework.springboot;

import com.homework.springboot.config.SecurityConfig;
import com.homework.springboot.config.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan("com.homework.springboot")
@EnableJpaRepositories("com.homework.springboot")
@EntityScan("com.homework.springboot.persistence.model")
public class HomeworkWeek5Application {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	public static void main(String[] args) {
		SpringApplication.run(new Class[] {HomeworkWeek5Application.class, SecurityConfig.class, WebMvcConfig.class } , args);
	}



}
