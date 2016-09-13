package com.hanbit.tutor.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Log4jConfigurer;

public class SpringApplication {

	public static void main(String[] args) {
		try {
			Log4jConfigurer.initLogging("classpath:config/log4j.xml");

			Logger logger = LoggerFactory.getLogger(SpringApplication.class);

			logger.debug("hello");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
