package com.study.library.service;

import com.study.library.jwt.JwtProvider;
import com.study.library.security.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class AccountMailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JwtProvider jwtProvider;
    @Value("${spring.mail.address}")
    private String fromMailAddress;
    @Value("${server.deploy-address}")
    private String serverAddress;
    @Value("${server.port}")
    private String serverPort;

    public boolean sendAuthMail() {
        boolean result = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        String toMailAddress = principalUser.getEmail();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            helper.setSubject("이 메일은 행운의 편지입니다. 영국을 돌아 한국까지왔습니다");
            helper.setFrom(fromMailAddress);
            helper.setTo(toMailAddress);

            String authMailToken = jwtProvider.generateAuthMailToken(toMailAddress);

            StringBuilder mailContent = new StringBuilder();
            mailContent.append("<div>");
            mailContent.append("<h1>계정 확성화 절차</h1>");
            mailContent.append("<h3>해당 계정을 인증하기 위해 아래의 버튼을 클릭해 주세요</h3>");
            mailContent.append("<a  href=\"http://" + serverAddress + ":" + serverPort + "/mail/authenticate?authToken=" + authMailToken + "\"" +
                    "style=\"border: 1px solid #dbdbdb; padding: 10px 20px; " +
                    "text-decoration: none; background-color: white; color: #222222;" +
                    "\">인증하기</a>");
            mailContent.append("</div>");

            mimeMessage.setText(mailContent.toString(), "utf-8", "html");

            javaMailSender.send(mimeMessage);
            result = true;
        } catch (MessagingException e) {
            e.printStackTrace();;
        }

        return result;
    }
}
