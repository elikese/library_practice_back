package com.study.library.repository;

import com.study.library.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findUserByUserName(String username);
    int saveUser(User user);
    int saveRole(int userId);
}