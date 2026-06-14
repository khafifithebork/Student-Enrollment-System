package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* org.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[LOG] Starting ... " + joinPoint.getSignature().getName());
    }

    @After("execution(* org.example.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[LOG] Completed ! " + joinPoint.getSignature().getName());
    }
}
