package com.study.library.aop;

import com.study.library.exception.ValidException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
public class ValidAOP {

    @Pointcut("@annotation(com.study.library.aop.annotation.ValidAspect)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        BindingResult bindingResult =
            (BindingResult) Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg.getClass() == BeanPropertyBindingResult.class)
                .collect(Collectors.toList()).get(0);

        if(bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            System.out.println(bindingResult.getFieldErrors());
            Map<String, String> errorMap = new HashMap<>();
            fieldErrorList.forEach( error -> errorMap.put(error.getField(),error.getDefaultMessage()));
            throw new ValidException(errorMap);
        }

    return joinPoint.proceed();
    }
}
