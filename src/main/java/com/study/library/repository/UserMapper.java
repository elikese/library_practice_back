package com.study.library.repository;

import com.study.library.entity.OAuth2;
import com.study.library.entity.RoleRegister;
import com.study.library.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findUserByUserName(String username);
    int saveUser(User user);
    int saveOAuth2(OAuth2 oAuth2);
    RoleRegister findRoleRegisterByUserIdAndRoleId(@Param("userId") int userId, @Param("roleId") int roleId);
    int saveRole(@Param("userId") int userId, @Param("roleId") int roleId);
    User findUserByOAuth2name(String oAuth2name);
    int modifyPassword(User user);

}