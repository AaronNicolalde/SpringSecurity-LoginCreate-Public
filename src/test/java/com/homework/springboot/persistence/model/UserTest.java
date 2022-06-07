package com.homework.springboot.persistence.model;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("User methods Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserTest {

    User user;

    @BeforeEach
    void setup(){
        user = createUser();
    }
    @Test
    @DisplayName("Get last name for user")
    void getLastName() {
        assertThat(user.getLastName()).isEqualTo("Nicolalde");
    }

    @Test
    @DisplayName("Get phone number for user")
    void getPhoneNumber() {
        assertThat(user.getPhoneNumber()).isEqualTo("+503 5555 1111");
    }

    @Test
    @DisplayName("Get password confirmation for user")
    void getPasswordConfirmation() {
        assertThat(user.getPasswordConfirmation()).isEqualTo("123");
    }

    @Test
    @DisplayName("Get created date for user")
    void getCreated() {
        assertThat(user.getCreated()).isNotNull();
    }

    @Test
    @DisplayName("Set created date for user")
    void setCreated() {
        user.setCreated(Calendar.getInstance());
        assertThat(user.getCreated()).isInstanceOf(Calendar.class);
    }

    public User createUser(){
        User user = new User();
        user.setId(1L);
        user.setEmail("admin@gmail.com");
        user.setPassword("123");
        user.setPasswordConfirmation("123");
        user.setFirstName("Aaron");
        user.setLastName("Nicolalde");
        user.setPhoneNumber("+503 5555 1111");
        user.setCreated(Calendar.getInstance());
        return user;
    }


}