package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.entity.Blacklist;
import com.example.todayrecipe.repository.TokenRepository;
import com.example.todayrecipe.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepo;

    @Override
    public ResponseEntity logout(String token) {
        if (checkBlacklist(token)) {
            String result = "failed";
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
        else {
            try {
                tokenRepo.save(Blacklist.builder().token(token).build());
                String result = "success";
                return new ResponseEntity(result, HttpStatus.OK);
            }
            catch (Exception err) {
                err.printStackTrace();
                String result = "failed";
                return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public boolean checkBlacklist(String token) {
        return tokenRepo.existsByToken(token);
    }
}
