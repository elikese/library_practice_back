package com.study.library.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ParamsPrintAOP {

    @Pointcut("@annotation(com.study.library.aop.annotation.ParamPrintAspect)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String className = codeSignature.getDeclaringTypeName();
        String methodName = codeSignature.getName();
        String[] argsName = codeSignature.getParameterNames();

        Object[] args = joinPoint.getArgs();

        for(int i =0; i < argsName.length; i++) {
            log.info("{}: {} ({}.{})", argsName[i], args[i], className, methodName);
        }

        return joinPoint.proceed();
    }
}
