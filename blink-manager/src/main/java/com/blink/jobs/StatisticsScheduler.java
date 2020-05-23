package com.blink.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatisticsScheduler {

	@Scheduled(cron = "0 0 1 * * *")
	public void test() {
		System.out.println("*******");
	}
}
