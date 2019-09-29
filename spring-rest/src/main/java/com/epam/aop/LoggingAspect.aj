package com.epam.aop;

public aspect LoggingAspect {

    private static void log(String s) {
        System.out.println(s);
    }

    pointcut logMethodInvocation():
            call(* *(..)) && @annotation(com.epam.annotation.LogMethodInvocation);

    before(): logMethodInvocation() {
        log("Method invoked: " + thisJoinPoint.getSignature());
    }
}