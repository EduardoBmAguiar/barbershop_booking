package com.webedu.ben_barber.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
public class PerformanceMonitorAspect {

    @Around("@annotation(com.webedu.ben_barber.annotation.TrackExecutionTime)")
    public Object trackExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1_000_000;

        log.info("Method {} executed in {} ms", joinPoint.getSignature().getName(), duration);
        return result;
    }
}
