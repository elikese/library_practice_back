package com.study.library.aop;

import com.study.library.dto.OAuth2SignupReqDto;
import com.study.library.dto.SignupReqDto;
import com.study.library.exception.ValidException;
import com.study.library.repository.UserMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
public class ValidAOP {

    @Autowired
    private UserMapper userMapper;

    @Pointcut("@annotation(com.study.library.aop.annotation.ValidAspect)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();

        BindingResult bindingResult =
            (BindingResult) Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg.getClass() == BeanPropertyBindingResult.class)
                .collect(Collectors.toList()).get(0);

        if(methodName.equals("signup")) {
            SignupReqDto reqDto =
                    (SignupReqDto) Arrays.stream(joinPoint.getArgs())
                            .filter(arg -> arg.getClass() == SignupReqDto.class)
                            .collect(Collectors.toList()).get(0);

            if(userMapper.findUserByUserName(reqDto.getUsername()) != null) {
                ObjectError objectError = new FieldError("username", "username", "이미 존재하는 사용자이름입니다");
                bindingResult.addError(objectError);
            }
        }

        if(methodName.equals("oAuth2Signup")) {
            OAuth2SignupReqDto reqDto =
                    (OAuth2SignupReqDto) Arrays.stream(joinPoint.getArgs())
                            .filter(arg -> arg.getClass() == OAuth2SignupReqDto.class)
                            .collect(Collectors.toList()).get(0);

            if(userMapper.findUserByUserName(reqDto.getUsername()) != null) {
                ObjectError objectError = new FieldError("username", "username", "이미 존재하는 사용자이름입니다");
                bindingResult.addError(objectError);
            }
        }

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
