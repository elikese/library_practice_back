package com.study.library.service;

import com.study.library.dto.OAuth2SignupReqDto;
import com.study.library.dto.SigninReqDto;
import com.study.library.dto.SignupReqDto;
import com.study.library.entity.User;
import com.study.library.exception.SaveException;
import com.study.library.jwt.JwtProvider;
import com.study.library.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;
    public boolean isDuplicatedUserName(String username) {
        return userMapper.findUserByUserName(username) != null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void signup(SignupReqDto reqDto) {
        int successCount = 0;
        User user = reqDto.toEntity();
        user.setPassword(passwordEncoder.encode(reqDto.getPassword()));

        successCount += userMapper.saveUser(user);
        successCount += userMapper.saveRole(user.getUserId(), 1);

        if(successCount < 2) {
            throw new SaveException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void oAuth2signup(OAuth2SignupReqDto reqDto) {
        int successCount = 0;
        User user = reqDto.toEntity();
        user.setPassword(passwordEncoder.encode(reqDto.getPassword()));

        successCount += userMapper.saveUser(user);
        successCount += userMapper.saveRole(user.getUserId(), 1);
        successCount += userMapper.saveOAuth2(reqDto.toOAuth2Entity(user.getUserId()));

        if(successCount < 3) {
            throw new SaveException();
        }
    }

    public String signin(SigninReqDto reqDto) {
        User user = userMapper.findUserByUserName(reqDto.getUsername());
        if(user == null) {
            throw new UsernameNotFoundException("사용자 정보를 조회할 수 없습니다");
        }
        if (!passwordEncoder.matches(reqDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("사용자 정보를 조회할 수 없습니다");
        }

        return jwtProvider.generateToken(user);
    }
}