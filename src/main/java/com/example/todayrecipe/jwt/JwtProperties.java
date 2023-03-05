package com.example.todayrecipe.jwt;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Getter
@ConstructorBinding
@Component
@ToString
public class JwtProperties {

    @Value("Test@test.com")
    private String issuer;

    @Value("Test")
    private String secretKey;

    @Value("Bearer")
    private String tokenPrefix;
}
