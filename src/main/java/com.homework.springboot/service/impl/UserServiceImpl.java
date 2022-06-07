package com.homework.springboot.service.impl;


import com.homework.springboot.persistence.model.User;
import com.homework.springboot.persistence.repository.UserRepository;
import com.homework.springboot.service.UserService;
import com.homework.springboot.validation.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUser(final User user) throws EmailExistsException {
        if (emailExist(user.getEmail())) {
            throw new EmailExistsException("The user already\n" +
                    "exists with the given email account: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPasswordConfirmation(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    private boolean emailExist(String email) {
        final User user = repository.findByEmail(email);
        return user != null;
    }

    @Override
    public User updateExistingUser(User user) {
        return repository.save(user);
    }

    @Override
    public User searchUser(String email){
        if(emailExist(email)){
            return repository.findByEmail(email);
        }else
            return null;
    }


}
