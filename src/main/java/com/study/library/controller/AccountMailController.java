package com.study.library.controller;

import com.study.library.service.AccountMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public String ResultPage(Model model, @RequestParam String authToken) {
        Map<String, Object> resultMap = mailService.authenticate(authToken);
        model.addAllAttributes(resultMap);
        return "result_page";
    }
}
