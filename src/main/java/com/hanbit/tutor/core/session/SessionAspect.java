package com.hanbit.tutor.core.session;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SessionAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionAspect.class);

	@Around("@annotation(com.hanbit.tutor.core.session.LoginRequired)")
	public Object checkLogin(ProceedingJoinPoint pjp) throws Throwable {
		Session session = SessionHelpler.getSession();

		if (session.isLoggedIn()) {
			return pjp.proceed();
		}

		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Class returnType = methodSignature.getReturnType();

		if (returnType == String.class) {
			return "login";
		}

		Map result = new HashMap();
		result.put("errorMsg", "로그인이 필요합니다.");

		return result;
	}

}
