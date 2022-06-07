package com.homework.springboot.security;

import com.homework.springboot.persistence.model.User;
import com.homework.springboot.persistence.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("User details Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LssUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private LssUserDetailsService lssUserDetailsService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Load User By Username returns user when successful")
    void loadUserByUsername_ReturnsUser_WhenSuccessful() {
        User user = createUser();
        when(userRepository.save(user)).thenReturn(user);
        userRepository.save(user);
        UserDetails found = lssUserDetailsService.loadUserByUsername("admin@gmail.com");
        assertThat(found).isNotNull();

    }

    @Test
    @DisplayName("Load User By Username throws UsernameNotFoundException when user not found")
    void loadUserByUsername_ThrowsUsernameNotFoundException_WhenUserNotFound() {

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> lssUserDetailsService.loadUserByUsername("testInvalid@gmail.com"));

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