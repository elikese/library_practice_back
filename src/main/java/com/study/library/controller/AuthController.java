package com.study.library.controller;

import com.study.library.aop.annotation.ParamPrintAspect;
import com.study.library.aop.annotation.ValidAspect;
import com.study.library.dto.OAuth2SignupReqDto;
import com.study.library.dto.SigninReqDto;
import com.study.library.dto.SignupReqDto;
import com.study.library.entity.OAuth2MergeReqDto;
import com.study.library.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ValidAspect
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto reqDto, BindingResult bindingResult) {
        authService.signup(reqDto);
        return ResponseEntity.created(null).body(true);
    }

    @ParamPrintAspect
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninReqDto reqDto) {
        return ResponseEntity.ok(authService.signin(reqDto));
    }

    @ValidAspect
    @PostMapping("/oauth2/signup")
    public ResponseEntity<?> oAuth2Signup(@Valid @RequestBody OAuth2SignupReqDto reqDto, BindingResult bindingResult) {
        authService.oAuth2signup(reqDto);
        return ResponseEntity.created(null).body(true);
    }

    @ValidAspect
    @PostMapping("/oauth2/merge")
    public ResponseEntity<?> oAuth2Merge(@Valid @RequestBody OAuth2MergeReqDto reqDto, BindingResult bindingResult) {
        authService.oAuth2Merge(reqDto);
        return ResponseEntity.created(null).body(true);
    }
}