package com.blink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.convert.Jsr310Converters;

@EnableFeignClients
@EntityScan(basePackageClasses = { Jsr310Converters.class }, basePackages = { "com.blink" })
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BlinkManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlinkManagerApplication.class, args);
	}

}
