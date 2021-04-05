package com.mancala.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	
	@Pointcut("within(com.mancala.*.*.*)")
	protected void allMethod() {
	}

	
	@Before("allMethod()")
	public void logStartOfMethod(JoinPoint joinPoint) {
		logger.info("Inside method ["+joinPoint.getSignature().getName()+"] "+joinPoint.getTarget().getClass().getName()+ " @ "+LocalDateTime.now().toString());
	}
	
	@After("allMethod()")
	public void logEndOfMethod(JoinPoint joinPoint) {
		logger.info("Outside method ["+joinPoint.getSignature().getName()+"]  "+joinPoint.getTarget().getClass().getName()+ " @ "+LocalDateTime.now().toString());
	}
}