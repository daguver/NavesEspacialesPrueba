package com.prueba.navesespaciales.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class NegativeId {

    @Before("execution(* com.prueba.navesespaciales.repository.SpaceRepository.save(..)) || " +
            "execution(* com.prueba.navesespaciales.repository.SpaceRepository.update(..)) || " +
            "execution(* com.prueba.navesespaciales.repository.SpaceRepository.findById(..)) || " +
            "execution(* com.prueba.navesespaciales.repository.SpaceRepository.deleteById(..)) " )
    public void beforeSaveOrUpdateAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Long && (Long) arg < 0) {
                log.warn("Negative ID received: " + arg);
            }
        }
    }
}
