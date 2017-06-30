package com.example.Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(public * *(..))")
    private void anyPublicMethod() {}

    @Before("anyPublicMethod() && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logAction(JoinPoint joinPoint) {
        //System.out.println("Execute method " + joinPoint.getSignature().getName());
//        LOGGER.info("Execute method " + joinPoint.getSignature().getName());
    }
}
