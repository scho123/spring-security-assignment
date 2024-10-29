package com.spacesinspace.springsecurityassignment.user.model.service;

import com.spacesinspace.springsecurityassignment.user.model.dao.UserMapper;
import com.spacesinspace.springsecurityassignment.user.model.dto.SignupDTO;
import com.spacesinspace.springsecurityassignment.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private PasswordEncoder encoder;
    private UserMapper userMapper;

    @Autowired
    public UserService(PasswordEncoder encoder, UserMapper userMapper) {
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    public Integer regist(SignupDTO newUserInfo) {
        newUserInfo.setPassword(encoder.encode(newUserInfo.getPassword()));
        Integer result = null;

        try {
            result = userMapper.regist(newUserInfo);
        } catch (DuplicateKeyException e) {
            result = 0;
            e.printStackTrace();
        } catch (BadSqlGrammarException e) {
            result = 0;
            e.printStackTrace();
        }

        return result;
    }

    public UserDTO findByUsername(String username) {
        UserDTO foundUser = userMapper.findByUsername(username);

        if (!Objects.isNull(foundUser)) {
            return foundUser;
        } else {
            return null;
        }
    }
}
