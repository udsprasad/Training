package com.example.Training.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@org.aspectj.lang.annotation.Aspect
@Slf4j
@Component
public class Aspect {

    @Pointcut("execution(* com.example.Training.controller.FirstController.get(..))")
    private void pointCut() {}

    @Pointcut("@annotation(ExecutionTimer)")
    private void pointCut2(){}

    @Before(value = "pointCut()")
    public void logging(JoinPoint joinPoint){
        log.info("before advice");
    }

    @Around(value ="pointCut2()")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object output=joinPoint.proceed();
        Long stopTime = System.currentTimeMillis();

        log.info("Exceution time for {} method is {}",(joinPoint.getSignature()).getName(),stopTime-startTime);
        return output;
    }


}
