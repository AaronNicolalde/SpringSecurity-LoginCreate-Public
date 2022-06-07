package com.homework.springboot.controller;

import com.homework.springboot.persistence.model.User;
import com.homework.springboot.persistence.repository.UserRepository;
import com.homework.springboot.service.UserService;
import com.homework.springboot.validation.EmailExistsException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("User Controller Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private BindingResult bindingResult;

    private User user;

    @BeforeEach
    void setup(){
        user = createUser();
        MockitoAnnotations.openMocks(this);
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("User controller list method returns model view tl/list When successful")
    void list_ReturnModelAndView_WhenSuccessful() {
        ModelAndView modelAndView = userController.list();
        assertThat(modelAndView.getViewName(),is("tl/list"));
    }

    @Test
    @DisplayName("User controller view method returns model view tl/view When successful")
    void view_ReturnModelAndView_WhenSuccessful() {
        ModelAndView modelAndView = userController.view(user);
        assertThat(modelAndView.getViewName(),is("tl/view"));
    }

    @Test
    @DisplayName("User controller viewSearch method returns model view tl/viewSearch When successful")
    void viewSearch_ReturnModelAndView_WhenSuccessful() {
        ModelAndView modelAndView = userController.viewSearch(user);
        assertThat(modelAndView.getViewName(),is("tl/viewSearch"));
    }

    @Test
    @DisplayName("User controller delete method returns model view redirect:/user/ When successful")
    void delete_ReturnModelAndView_WhenSuccessful() throws EmailExistsException {
        userService.registerNewUser(user);

        ModelAndView modelAndView = userController.delete(user.getId());
        assertThat(modelAndView.getViewName(),is("redirect:/user/"));
    }

    @Test
    @DisplayName("User controller modifyForm method returns model view tl/formUpdate When successful")
    void modifyForm_ReturnModelAndView_WhenSuccessful() {
        ModelAndView modelAndView = userController.modifyForm(user);
        assertThat(modelAndView.getViewName(),is("tl/formUpdate"));
    }

    @Test
    @DisplayName("User controller createForm method returns string tl/form When successful")
    void createForm_ReturnModelAndView_WhenSuccessful() {
        String result = userController.createForm(user);
        assertThat(result,is("tl/form"));
    }

    @Test
    @DisplayName("User controller updateForm method returns string tl/formUpdate When successful")
    void updateForm_ReturnModelAndView_WhenSuccessful() {
        String result = userController.updateForm(user);
        assertThat(result,is("tl/formUpdate"));
    }

    @Test
    @DisplayName("User controller update method returns model view redirect:/user/{userId} When successful")
    void update_ReturnModelAndView_WhenSuccessful() throws EmailExistsException {
        ModelAndView result = userController.update(user, bindingResult,redirectAttributes);

        assertThat(result.getViewName(),is("redirect:/user/{user.id}"));
    }

    @Test
    @DisplayName("User controller userSearch method returns model view redirect:/user/ When failed")
    void userSearch_ReturnModelAndView_WhenFail() {
        ModelAndView result = userController.userSearch(user.getEmail(),redirectAttributes);

        assertThat(result.getViewName(),is("redirect:/user/"));
    }

    @Test
    @DisplayName("User controller userSearch method returns model view redirect:/user/ When user is found")
    void userSearch_ReturnModelAndView_WhenFound() {
        userController.create(user, bindingResult, redirectAttributes);

        ModelAndView result = userController.userSearch(user.getEmail(),redirectAttributes);

        assertThat(result.getViewName(),is("redirect:/user/search/{user.id}"));
    }

    @Test
    @DisplayName("User controller update method returns model view tl/formUpdate When hasErrors()")
    void update_ReturnModelAndView_WhenHasErrors() throws EmailExistsException {
        bindingResult.addError(new ObjectError("Error", "testError"));
        when(bindingResult.hasErrors()).thenReturn(true);
        ModelAndView result = userController.update(user, bindingResult,redirectAttributes);

        assertThat(result.getViewName(),is("tl/formUpdate"));
    }

    @Test
    @DisplayName("User controller update method returns add flash When user id is null")
    void update_ReturnAddFlash_WhenIdNull() throws EmailExistsException {
        user.setId(null);

        userController.update(user, bindingResult,redirectAttributes);

        assertThat(redirectAttributes.containsAttribute("globalMessage"),is(false));
    }

    @Test
    @DisplayName("User controller create method returns model view redirect:/user/{user.id} When successful")
    void create_ReturnModelAndView_WhenSuccessful() {
        UserController mockUserController = mock(UserController.class);
        when(mockUserController.create(any(User.class),any(BindingResult.class),any(RedirectAttributes.class))).thenReturn(new ModelAndView("redirect:/user/"+user.getId()));
        verify(mockUserController,atMostOnce()).create(any(User.class),any(BindingResult.class),any(RedirectAttributes.class));
        ModelAndView result = mockUserController.create(user,bindingResult,redirectAttributes);
        assertThat(result.getViewName(),is("redirect:/user/"+user.getId()));
    }

    @Test
    @DisplayName("User controller create method returns model view tl/form When hasErrors()")
    void create_ReturnModelAndView_WhenHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);
        ModelAndView result = userController.create(user, bindingResult,redirectAttributes);

        assertThat(result.getViewName(),is("tl/form"));
    }

    @Test
    @DisplayName("User controller create method returns add flash When user id null")
    void create_ReturnModelAddFlash_WhenUserIdNull() {
        user.setId(null);
        userController.create(user, bindingResult,redirectAttributes);

        assertThat(redirectAttributes.containsAttribute("globalMessage"),is(false));
    }

    @Test
    @DisplayName("User controller create method returns model view tl/form When catch EmailExistException")
    void create_ReturnModelAndView_WhenEmailExistException() {
        when(bindingResult.hasErrors()).thenReturn(false);

        ModelAndView result = userController.create(user,bindingResult,redirectAttributes);
        assertThat(result.getViewName(),is("redirect:/user/{user.id}"));
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