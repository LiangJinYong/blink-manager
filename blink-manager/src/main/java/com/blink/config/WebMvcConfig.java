package com.blink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blink.interceptor.HospitalIdCheckInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public HospitalIdCheckInterceptor hospitalIdCheckInterceptor() {
		return new HospitalIdCheckInterceptor();
	}

}
