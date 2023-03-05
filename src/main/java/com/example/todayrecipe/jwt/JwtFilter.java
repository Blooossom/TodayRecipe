package com.example.todayrecipe.jwt;


import com.example.todayrecipe.dto.user.UserRequest;
import com.example.todayrecipe.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Builder
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider provider;
    private final UserService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            if (!service.checkBlacklist(tokenHeader)) {
                UserRequest request1 = provider.tokenToUser(tokenHeader);
                if (request1 != null) {
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                            request1,
                            "",
                            request1.getAuthorities()
                    ));
                }
            }
        }catch (ExpiredJwtException err) {
            logger.error("ExpiredJwtException : expired token");
        }catch (Exception err) {
            logger.error("Exception : no token");
        }
        filterChain.doFilter(request, response);
    }
}
