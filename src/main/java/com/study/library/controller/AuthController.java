package com.study.library.controller;

import com.study.library.aop.annotation.ValidAspect;
import com.study.library.dto.SignupReqDto;
import com.study.library.exception.ValidException;
import com.study.library.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ValidAspect
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto reqDto, BindingResult bindingResult) {
        if(authService.isDuplicatedUserName(reqDto.getUsername())) {
//            ObjectError error = new FieldError("reqDto", "username", "이미 존재하는 아이디입니다");
//            bindingResult.addError(error);
            throw new ValidException(Map.of("username","이미 존재하는 아이디입니다"));
        }
        authService.signup(reqDto);
        return ResponseEntity.created(null).body(true);
    }
}