package com.homework.springboot.persistence.repository;

import com.homework.springboot.persistence.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


//@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
//@DataJpaTest
@DisplayName("User Repository Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup(){
        user = createUser();
    }

    @Test
    @DisplayName("Find by email returns user when successful")
    void findByEmail_ReturnUser_WhenSuccessful() {
        User savedUser = this.userRepository.save(user);

        String email = user.getEmail();

        User userSearched = this.userRepository.findByEmail(email);

        Assertions.assertThat(userSearched).isNotNull();

        Assertions.assertThat(userSearched.getId()).isEqualTo(savedUser.getId());
    }

    @Test
    @DisplayName("Find by email returns null user when user is not found")
    void findByEmail_ReturnNullUser_WhenUserNotFound() {
        String notFoundEmail = "notFound@gmail.com";

        User userSearched = this.userRepository.findByEmail(notFoundEmail);

        Assertions.assertThat(userSearched).isNull();
    }

    @Test
    @DisplayName("Delete removes user when successful")
    void delete_RemoveUser_WhenSuccessful() {
        this.userRepository.save(user);

        this.userRepository.delete(user);

        Optional<User> userOptional = this.userRepository.findById(user.getId());

        Assertions.assertThat(userOptional).isEmpty();
    }

    @Test
    @DisplayName("Save update user when successful")
    void save_UpdateUser_WhenSuccessful(){
        User savedUser = this.userRepository.save(user);

        savedUser.setFirstName("New name");

        User updatedUser = this.userRepository.save(savedUser);

        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getFirstName()).isNotNull();
        Assertions.assertThat(savedUser.getFirstName()).isEqualTo(updatedUser.getFirstName());
    }

    @Test
    @DisplayName("Save creates user when successful")
    void save_CreatesUser_WhenSuccessful() {
        User savedUser = this.userRepository.save(user);
        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getFirstName()).isNotNull();
        Assertions.assertThat(savedUser.getFirstName()).isEqualTo(user.getFirstName());
    }

    @Test
    @DisplayName("Save throw DataIntegrityViolationException when Firstname is null")
    void save_ThrowDataIntegrityViolationException_WhenFirstNameIsNull() {
        user.setFirstName(null);

        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> userRepository.save(user));
    }

    @Test
    @DisplayName("Save throw DataIntegrityViolationException when Lastname is null")
    void save_ThrowDataIntegrityViolationException_WhenLastNameIsNull() {
        user.setLastName(null);

        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> userRepository.save(user));
    }

    @Test
    @DisplayName("Save throw DataIntegrityViolationException when email is null")
    void save_ThrowDataIntegrityViolationException_WhenEmailIsNull() {
        user.setEmail(null);

        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> userRepository.save(user));
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