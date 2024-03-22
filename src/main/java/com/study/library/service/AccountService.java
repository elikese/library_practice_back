package com.study.library.service;

import com.study.library.dto.EditPasswordReqDto;
import com.study.library.entity.User;
import com.study.library.exception.ValidException;
import com.study.library.repository.UserMapper;
import com.study.library.security.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void editPassword(EditPasswordReqDto reqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userMapper.findUserByUserName(authentication.getName());
        if(!passwordEncoder.matches(reqDto.getOldPassword(), user.getPassword())) {
            throw new ValidException(Map.of("oldPassword","비밀번호 인증에 실패하였습니다 \n다시입력하세요"));
        }
        if(!reqDto.getOldPassword().equals(reqDto.getNewPassword())) {
            throw new ValidException(Map.of("newPasswordCheck","새로운 비밀번호가 서로 일치하지 않습니다 \n다시입력하세요"));
        }
    }
}

