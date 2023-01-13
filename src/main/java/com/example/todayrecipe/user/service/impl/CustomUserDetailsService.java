package com.example.todayrecipe.user.service.impl;

import com.example.todayrecipe.user.dto.UserSessionDTO;
import com.example.todayrecipe.user.entity.User;
import com.example.todayrecipe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;
    private final HttpSession session;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUserid(username).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + username));
        session.setAttribute("user", new UserSessionDTO(user));

        return new CustomUserDetails(user);
    }
}
