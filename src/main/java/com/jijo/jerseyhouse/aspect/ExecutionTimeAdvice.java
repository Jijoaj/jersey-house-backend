package com.jijo.jerseyhouse.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAdvice {

    @Around("@annotation(com.jijo.jerseyhouse.aspect.TrackExecutionTime)")
    public void executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        point.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Class name : {} , Method name : {} , Time taken for execution : {} ms",
                point.getSignature().getDeclaringType(), point.getSignature().getName(), startTime-endTime);
    }
}
