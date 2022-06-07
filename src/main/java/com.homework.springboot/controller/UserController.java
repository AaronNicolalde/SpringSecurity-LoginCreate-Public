package com.homework.springboot.controller;

import com.homework.springboot.persistence.model.User;
import com.homework.springboot.persistence.repository.UserRepository;
import com.homework.springboot.service.UserService;
import com.homework.springboot.validation.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    //

    @RequestMapping
    public ModelAndView list() {
        Iterable<User> users = this.userRepository.findAll();
        return new ModelAndView("tl/list", "users", users);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") User user) {
        return new ModelAndView("tl/view", "user", user);
    }

    @RequestMapping("search/{id}")
    public ModelAndView viewSearch(@PathVariable("id") User user) {
        return new ModelAndView("tl/viewSearch", "user", user);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid User user, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("tl/form", "formErrors", result.getAllErrors());
        }

        try {
            userService.registerNewUser(user);
        } catch (EmailExistsException e) {
            result.addError(new FieldError("user", "email", e.getMessage()));
            return new ModelAndView("tl/form", "formErrors", result.getAllErrors());
        }
        if(user.getId() == null) {
            redirect.addFlashAttribute("globalMessage", "Successfully created a new user");
        } else {
            redirect.addFlashAttribute("globalMessage", "Successfully updated existing user");
        }

        user = this.userRepository.save(user);
        return new ModelAndView("redirect:/user/{user.id}", "user.id", user.getId());
    }

    @RequestMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.userRepository.findById(id)
            .ifPresent(user -> this.userRepository.delete(user));
        return new ModelAndView("redirect:/user/");
    }

    @RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
    public ModelAndView modifyForm(@PathVariable("id") User user) {
        return new ModelAndView("tl/formUpdate", "user", user);
    }


    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute User user) {
        return "tl/form";
    }

    @RequestMapping(params = "formUpdate", method = RequestMethod.GET)
    public String updateForm(@ModelAttribute User user) {
        return "tl/formUpdate";
    }


    @RequestMapping(params = "formUpdate", method = RequestMethod.POST)
    public ModelAndView update(@Valid User user, BindingResult result, RedirectAttributes redirect) throws EmailExistsException {
        if (result.hasErrors()) {
            return new ModelAndView("tl/formUpdate", "formErrors", result.getAllErrors());
        }
        userService.updateExistingUser(user);

        if(user.getId() == null) {
            redirect.addFlashAttribute("globalMessage", "Successfully created a new user");
        } else {
            redirect.addFlashAttribute("globalMessage", "Successfully updated existing user");
        }
        return new ModelAndView("redirect:/user/{user.id}", "user.id", user.getId());
    }


    @RequestMapping("search/")
    public ModelAndView userSearch(String email, RedirectAttributes redirect) {
        if(email!=null && userRepository.findByEmail(email)!=null) {
            User user = userRepository.findByEmail(email);
            return new ModelAndView("redirect:/user/search/{user.id}", "user.id", user.getId());
        }else {
            redirect.addFlashAttribute("noEmail", "We could\n" +
                    "not find a user with the given email");
            return new ModelAndView("redirect:/user/");
        }
    }



}
