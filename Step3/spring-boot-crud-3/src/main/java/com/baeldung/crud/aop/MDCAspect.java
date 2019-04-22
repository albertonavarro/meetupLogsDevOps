package com.baeldung.crud.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.UUID;

@Configuration
@Aspect
public class MDCAspect {

    @Before("within(@org.springframework.stereotype.Controller *)")
    public void logIn() throws Throwable {
        MDC.put("requestId", UUID.randomUUID().toString());
    }

    @After("within(@org.springframework.stereotype.Controller *)")
    public void logOut() throws Throwable {
        MDC.remove("requestId");
    }
}
