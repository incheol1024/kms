package com.devworker.kms.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExecutionTimeLogger {

    private static Logger logger = LoggerFactory.getLogger(ExecutionTimeLogger.class);

    @Around("@annotation(ExecutionTimeLogging)")
    public Object methodTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object object = proceedingJoinPoint.proceed();
        logger.debug("========execution time {}ms=========", begin-System.currentTimeMillis());
        return object;
    }

}
