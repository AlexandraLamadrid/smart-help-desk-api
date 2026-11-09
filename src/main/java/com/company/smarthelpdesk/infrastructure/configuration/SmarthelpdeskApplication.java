package com.company.smarthelpdesk.infrastructure.configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(
		scanBasePackages = "com.company.smarthelpdesk"
)
public class SmarthelpdeskApplication {
	public static void main(String[] args) {
		SpringApplication.run(
				SmarthelpdeskApplication.class,
				args
		);
	}
}