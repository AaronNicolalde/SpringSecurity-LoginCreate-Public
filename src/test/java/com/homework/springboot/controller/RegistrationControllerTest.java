package com.homework.springboot.controller;

import com.homework.springboot.persistence.model.User;
import com.homework.springboot.validation.EmailExistsException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Registration Controller Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RegistrationControllerTest {

    @Autowired
    private RegistrationController registrationController;

    @Mock
    private BindingResult bindingResult;

    private User user;

    @BeforeEach
    void setup(){
        user = createUser();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("registrationForm method returns model view registrationPage When successful")
    void registrationForm_ReturnModelAndView_WhenSuccessful() throws EmailExistsException {
        ModelAndView result = registrationController.registrationForm();

        assertThat(result.getViewName()).isEqualTo("registrationPage");
    }

    @Test
    @DisplayName("registerUser method returns model view registrationPage When successful")
    void registerUser_ReturnModelAndView_WhenSuccessful() {
        when(bindingResult.hasErrors()).thenReturn(false);

        ModelAndView result = registrationController.registerUser(user,bindingResult);

        assertThat(result.getViewName()).isEqualTo("registrationPage");
    }

    @Test
    @DisplayName("registerUser method returns model view registrationPage When failed")
    void registerUser_ReturnRegistrationPage_WhenFailed() {
        when(bindingResult.hasErrors()).thenReturn(true);

        ModelAndView result = registrationController.registerUser(user,bindingResult);

        assertThat(result.getViewName()).isEqualTo("registrationPage");
    }

    @Test
    @DisplayName("registerUser method Throws EmailExistsException When email exist")
    void registerUser_ThrowEmailExistsException_WhenEmailExist() throws EmailExistsException{
        when(bindingResult.hasErrors()).thenReturn(false);

        ModelAndView result = registrationController.registerUser(user,bindingResult);

        assertThat(result.getViewName()).isEqualTo("redirect:/login");
    }



    public User createUser(){
        User user = new User();
        user.setId(1L);
        user.setEmail("admin@gmail.com");
        user.setPassword("123");
        user.setPasswordConfirmation("123");
        user.setFirstName("Admin");
        user.setLastName("user");
        user.setPhoneNumber("+503 5555 1111");
        return user;
    }
}