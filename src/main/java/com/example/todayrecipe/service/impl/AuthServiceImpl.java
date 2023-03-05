package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.dto.user.LoginResDTO;
import com.example.todayrecipe.dto.user.UpdateUserReqDTO;
import com.example.todayrecipe.dto.user.UserReqDTO;
import com.example.todayrecipe.entity.User;
import com.example.todayrecipe.jwt.JwtProvider;
import com.example.todayrecipe.repository.UserRepository;
import com.example.todayrecipe.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtProvider provider;

    private final PasswordEncoder encoder;

    private final UserRepository userRepo;

    private static final String pattern = "^[A-Za-z[0-9]]{8,16}$"; // 영문, 숫자 8~16자리




    @Override
    public ResponseEntity signUp(UserReqDTO signUpReq) {
        User checkUser = null;

        try {
            checkUser = userRepo.findByEmail(signUpReq.getEmail()).orElse(null);
        }
        catch (Exception err) {
            err.printStackTrace();
        }
        if (checkUser != null) {
            String message = signUpReq.getEmail() + "는 이미 존재하는 이메일 입니다. 다른 이메일을 사용해주세요";
            System.out.println(message);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        else if (!signUpReq.getPassword().matches(pattern)) {
            String message = "비밀번호는 문자,숫자만 사용가능합니다. \n8~16자리로 설정해주세요";
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
        else {
            String password = encodingPassword(signUpReq.getPassword());
            signUpReq.setPassword(password);

            User user = signUpReq.toEntity();
            userRepo.save(user);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @Override
    public LoginResDTO login(LoginReqDTO loginReqDTO) {
        if (loginReqDTO.getEmail() == null || loginReqDTO.getPassword() == null) {
            return new LoginResDTO("아이디 혹은 비밀번호를 입력해주세요");
        }
        else {
            User user = null;
            try {
                user = userRepo.findByEmail(loginReqDTO.getEmail()).orElse(null);
            }
            catch (Exception err) {
                err.printStackTrace();
            }
            if (user == null || !encoder.matches(loginReqDTO.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException();
            }
            String token = provider.makeToken(user);
            LoginResDTO loginResDTO = new LoginResDTO(user, token);
            return loginResDTO;
        }
    }

    @Override
    public Map<String, String> checkPassword(String email, String requestPassword) {
        User user = userRepo.findByEmail(email).orElse(null);
        Map<String, String> userData = new HashMap<>();
        if (!encoder.matches(requestPassword, user.getPassword())) {
            throw new IllegalArgumentException();
        }
        userData.put("name", user.getName());
        return userData;
    }

    @Override
    public String updateUserInfo(UpdateUserReqDTO updateUserReqDTO, String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        String password = updateUserReqDTO.getPassword();
        String phone = updateUserReqDTO.getPhone();
        String nickname = updateUserReqDTO.getNickname();
        String address = updateUserReqDTO.getAddress();

            if (password == null || password.isBlank()) {
                password = user.getPassword();
            }
            if (phone == null || phone.isBlank()) {
                phone = user.getPhone();
            }
            if (nickname == null || nickname.isBlank()) {
                nickname = user.getNickname();
            }
            if (address == null || address.isBlank()) {
                address = user.getAddress();
            }
            if (password.length() < 20) {
                password = encodingPassword(password);
            }
            Integer result = userRepo.updateInfo(password, phone, nickname, address, email);

            return result > 0? "success" : "false";
    }


    private String encodingPassword(String password) {
        return encoder.encode(password);
    }
}
