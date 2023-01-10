package com.example.todayrecipe.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public class LogAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around(" execution(* com.example.todayrecipe..*Controller.*(..))"
            + "|| execution(* com.example.todayrecipe..*Service.*(..))"
            + "|| execution(* com.example.todayrecipe..*Repository.*(..))")
    public Object logAdvice(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        String parameters = Arrays.toString(proceedingJoinPoint.getArgs());

        logger.info("====================[START]====================");
        logger.info(""+className+"."+methodName+"() CALLED");
        logger.info("Parameter = "+parameters);
        logger.info("====================[END]====================");

        return proceedingJoinPoint.proceed();
    }
}
