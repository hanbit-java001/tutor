package com.hanbit.tutor.core.session;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SessionHelpler {

	private static SessionHelpler sessionHelper;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void setSessionHelper() {
		sessionHelper = this;
	}

	private <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	public static Session getSession() {
		return sessionHelper.getBean(Session.class);
	}

}
