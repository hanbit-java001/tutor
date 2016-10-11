package com.hanbit.tutor.core.exception;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Order(1)
public class ExceptionAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAspect.class);

	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
		try {
			return pjp.proceed();
		}
		catch (Throwable t) {
			LOGGER.error(t.getMessage(), t);

			MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
			Class returnType = methodSignature.getReturnType();

			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

			if (returnType == String.class) {
				throw t;
			}

			response.setContentType("application/json;charset=utf-8");

			String json = "{\"errorMsg\":\"" + t.getMessage() + "\"}";

			response.getOutputStream().write(json.getBytes());
			response.setStatus(1500);
		}

		return null;
	}

}
