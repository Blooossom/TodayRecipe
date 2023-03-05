package com.example.todayrecipe.user.controller;

import com.example.todayrecipe.controller.UserController;
import com.example.todayrecipe.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;


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