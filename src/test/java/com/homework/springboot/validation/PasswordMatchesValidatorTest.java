package com.homework.springboot.validation;

import com.homework.springboot.persistence.model.User;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("Passwords Validator Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PasswordMatchesValidatorTest {
    @Autowired
    private PasswordMatchesValidator passwordMatchesValidator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
//        doCallRealMethod().when(passwordMatchesValidator).initialize(any());
//        when(passwordMatchesValidator.isValid(any(), any())).thenCallRealMethod();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Is valid method returns false When Passwords are not equals")
    void isValid_ReturnFalse_WhenFail() {
        User user = createUser();
        user.setPassword("123");
        user.setPasswordConfirmation("111");
        assertThat(user.getPasswordConfirmation()).isNotEqualTo(user.getPassword());
        assertThat(passwordMatchesValidator.isValid(user,context)).isFalse();
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