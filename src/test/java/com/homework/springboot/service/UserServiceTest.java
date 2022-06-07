package com.homework.springboot.service;

import com.homework.springboot.persistence.model.User;
import com.homework.springboot.persistence.repository.UserRepository;
import com.homework.springboot.validation.EmailExistsException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("User Service Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup(){
        user = createUser();
        MockitoAnnotations.openMocks(this);
        userRepository.deleteAll();
        userRepository.delete(user);
    }

    @Test
    @DisplayName("Register new user returns user When Successful")
    void registerNewUser_ReturnsUser_WhenSuccessful() throws EmailExistsException {
        UserService mockService = mock(UserService.class);
        userRepository.deleteAll();
        when(mockService.registerNewUser(any(User.class))).thenReturn(user);
        User registerUser = mockService.registerNewUser(user);

        assertThat(registerUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Register new user ThrowsEmailExistsException user When email exists")
    void registerNewUser_ThrowsEmailExistsException_WhenEmailExists() {
//        userService.registerNewUser(user);
        assertThatExceptionOfType(EmailExistsException.class)
                .isThrownBy(()-> userService.registerNewUser(user));
        assertThrows(EmailExistsException.class, ()-> userService.registerNewUser(user));
    }

    @Test
    @DisplayName("Updating existing user returns updated user When Successful")
    void updateExistingUser_ReturnsUpdatedUser_WhenSuccessful() throws EmailExistsException {
        user = createUser();

        user.setEmail("testUpdate@gmail.com");
        User updatedUser = userService.updateExistingUser(user);

        assertThat(updatedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Search user returns user When Successful")
    void searchUser_ReturnsUser_WhenSuccessful() throws EmailExistsException {
        User foundUser = userService.searchUser(user.getEmail());

        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Search user returns null When UserNotFound")
    void searchUser_ReturnsNull_WhenUserNotFound() throws EmailExistsException {
        User foundUser = userService.searchUser("DoesntExist@gmail.com");

        assertThat(foundUser).isNull();
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