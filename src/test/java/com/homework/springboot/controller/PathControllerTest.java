package com.homework.springboot.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Path Controller Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PathControllerTest {

    @Autowired
    private PathController pathController;

    @Test
    @DisplayName("Path controller home method return string redirect:/user/ When successful")
    void home_ReturnsModelAndView_WhenSuccessful(){
        String result = pathController.home();

        assertThat(result).isEqualTo("redirect:/user/");
    }


}