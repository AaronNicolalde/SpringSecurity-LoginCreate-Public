package com.homework.springboot.service;

import com.homework.springboot.persistence.model.User;
import com.homework.springboot.validation.EmailExistsException;

public interface UserService {

    User registerNewUser(User user) throws EmailExistsException;

    User updateExistingUser(User user) throws EmailExistsException;

    User searchUser(String email) throws EmailExistsException;

}
