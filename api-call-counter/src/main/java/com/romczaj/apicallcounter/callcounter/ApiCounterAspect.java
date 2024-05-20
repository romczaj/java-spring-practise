package com.romczaj.apicallcounter.callcounter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ApiCounterAspect {

    private final CallCounterFacade counterNotification;

    @Before("@annotation(com.romczaj.apicallcounter.callcounter.ApiCounter)")
    public void logExecutionTime(JoinPoint joinPoint) {
        findUsername(joinPoint).ifPresent(counterNotification::increaseCounter);
    }

    private Optional<String> findUsername(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiCounter apiCounter = method.getAnnotation(ApiCounter.class);
        String[] parameterNames = signature.getParameterNames();
        String fieldName = apiCounter.field();
        int index = Arrays.asList(parameterNames).indexOf(fieldName);
        if (index == -1) {
            log.warn("Not found field {} in {} method, counter won't be increased", fieldName, signature.getMethod().getName());
            return Optional.empty();
        }
        return Optional.of((String) joinPoint.getArgs()[index]);
    }

}