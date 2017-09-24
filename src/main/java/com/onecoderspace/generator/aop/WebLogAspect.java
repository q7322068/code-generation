package com.onecoderspace.generator.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

@Component
@Aspect
public class WebLogAspect {

	private static final Logger logger = LoggerFactory
			.getLogger(WebLogAspect.class);

	ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<Long>();

	@Pointcut("execution(public * com.hexun.bdc.crawle.controller..*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTimeThreadLocal.set(System.currentTimeMillis());
	}

	@AfterReturning(pointcut = "webLog()")
	public void doAfterReturning(JoinPoint joinPoint) throws Throwable {
		long usedTime = System.currentTimeMillis() - startTimeThreadLocal.get();
		String args = Joiner.on(",").join(joinPoint.getArgs());
		if(usedTime > 100){
			logger.info(String.format("method=[%s.%s],args=[%s] use time=%d ms",
					joinPoint.getSignature().getDeclaringType().getSimpleName(),
					joinPoint.getSignature().getName(), args, usedTime));
		} else if(logger.isDebugEnabled()){
				logger.debug(String.format("method=[%s.%s],args=[%s] use time=%d ms",
						joinPoint.getSignature().getDeclaringType().getSimpleName(),
						joinPoint.getSignature().getName(), args, usedTime));
		}
	}

}