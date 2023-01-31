package com.example.todayrecipe.user.controller;

import com.example.todayrecipe.user.dto.UserRequest;
import com.example.todayrecipe.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@SpringBootTest
class UserControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(@Autowired UserController userController){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    @Mock
    HttpSession session;
    @Mock
    private UserService service;
    @InjectMocks
    private UserController userController;





}