package com.example.todayrecipe.service;

import org.springframework.http.ResponseEntity;

public interface TokenService {

    ResponseEntity logout(String header);

    boolean checkBlacklist(String token);
}
