package com.study.library.controller;

import com.study.library.service.AccountMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mail")
public class AccountMailController {

    @Autowired
    private AccountMailService mailService;

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<?> send() {
        return ResponseEntity.ok(mailService.sendAuthMail());
    }

    @GetMapping("/authenticate")
    public String ResultPage(@RequestParam String authToken) {
        return "result_page";
    }
}
