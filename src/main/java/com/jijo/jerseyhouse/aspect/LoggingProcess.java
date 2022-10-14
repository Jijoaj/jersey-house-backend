package com.jijo.jerseyhouse.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingProcess {

    @Around("execution(* com.jijo.jerseyhouse.service.impl.*.*(..))")
    public Object logProcess(ProceedingJoinPoint point) throws Throwable {
        log.info("Process {} : {} started",point.getSignature().getDeclaringType(),point.getSignature().getName());
        Object proceed = point.proceed();
        log.info("Process {} : {} completed",point.getSignature().getDeclaringType(),point.getSignature().getName());
        return proceed;
    }
}
