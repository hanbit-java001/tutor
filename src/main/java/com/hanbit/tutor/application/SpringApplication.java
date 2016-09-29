package com.hanbit.tutor.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import com.hanbit.tutor.core.service.MemberService;
import com.hanbit.tutor.core.service.SecurityService;
import com.hanbit.tutor.core.vo.ScheduleVO;

public class SpringApplication {

	public static void main(String[] args) {
		try {
			Log4jConfigurer.initLogging("classpath:config/log4j.xml");

			ApplicationContext applicationContext =
					new ClassPathXmlApplicationContext("spring/applicationContext-core.xml",
							"spring/applicationContext-dao.xml");

			SecurityService service = applicationContext.getBean(SecurityService.class);

			System.out.println(service.getValidMember("aaa", "ccc"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
