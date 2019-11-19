package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	private final Logger logger = LoggerFactory.getLogger(DemoApplication .class);

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}

	@Scheduled(fixedDelay = 10000)
	public void scheduleFixedDelayTask() {
		logger.info("---RAPOR--- Son 10 saniyede cagirilan servisler ve sayilari:");
		//key = servicename value = count
		for (Map.Entry<String, LongAdder> pair: MonitorFilter.requestCounts.entrySet()) {
			logger.info(pair.getKey() + " - " + pair.getValue().longValue());
		}
		logger.info("---RAPOR SONU---");
		// 10 saniye sonra sayacı sıfırlamak için
		MonitorFilter.requestCounts.clear();
	}
}
