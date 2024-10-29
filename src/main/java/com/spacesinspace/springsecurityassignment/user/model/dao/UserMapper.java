package com.spacesinspace.springsecurityassignment.user.model.dao;

import com.spacesinspace.springsecurityassignment.user.model.dto.SignupDTO;
import com.spacesinspace.springsecurityassignment.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    Integer regist(SignupDTO newUserInfo);

    UserDTO findByUsername(String username);
}
