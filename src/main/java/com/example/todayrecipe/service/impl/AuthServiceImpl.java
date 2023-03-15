package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.dto.Response;
import com.example.todayrecipe.dto.user.*;
import com.example.todayrecipe.entity.User;
import com.example.todayrecipe.enums.Authority;
import com.example.todayrecipe.jwt.JwtProvider;
import com.example.todayrecipe.jwt.JwtTokenProvider;
import com.example.todayrecipe.repository.UserRepository;
import com.example.todayrecipe.security.SecurityUtil;
import com.example.todayrecipe.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.sql.Time;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Response response;

    private final JwtTokenProvider provider;

    private final RedisTemplate redisTemplate;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
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
    public ResponseEntity<?> signUp(UserReqDTO.SignUp signUp) {
        if (userRepo.existsByEmail(signUp.getEmail())) {
            return response.fail("이미 회원가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
        User user = User.builder()
                .email(signUp.getEmail())
                .phone(encoder.encode(signUp.getPassword()))
                .phone(signUp.getPhone())
                .name(signUp.getName())
                .address(signUp.getAddress())
                .roles(Collections.singletonList(Authority.ROLE_USER.name()))
                .build();
        userRepo.save(user);
        return response.success("회원가입에 성공하셨습니다.");
    }

    @Override
    public ResponseEntity<?> login(UserReqDTO.Login login) {
        if (userRepo.findByEmail(login.getEmail()).orElse(null) == null) {
            return response.fail("해당하는 유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);


        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        UserResDTO.TokenInfo tokenInfo = provider.generateToken(authentication);

        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "로그인에 성공했습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> reissue(UserReqDTO.Reissue reissue) {
        //1. Refresh Token 검증
        if (!provider.validateToken(reissue.getAccessToken())) {
            return response.fail("Refresh Token의 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        //2. Access Token 에서 User email을 가져옴
        Authentication authentication = provider.getAuthentication(reissue.getAccessToken());

        //3 Redis에서 User email을 기반으로 저장된 Refresh Token 값을 가져옴
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());

        //로그아웃 되어 Redis에 RefreshToken이 존재하지 않는 경우 처리
        if (ObjectUtils.isEmpty(refreshToken)) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
        if (!refreshToken.equals(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        //4. 새로운 토큰 생성
        UserResDTO.TokenInfo tokenInfo = provider.generateToken(authentication);

        //5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "Token 정보가 갱신되었습니다.", HttpStatus.OK);
    }
    public ResponseEntity<?> logout(String accessToken) {
        //1. Access Token 검증
        if (!provider.validateToken(accessToken)) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        //2. Access Token 에서 User Email 가져오기
        Authentication authentication = provider.getAuthentication(accessToken);

        //3. Redis에서 해당 User Email로 저장된 RefreshToken이 있는 지 검증, 있을 경우 삭제
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            redisTemplate.delete("RT:" + authentication.getName());
        }

        //4. 해당 Access Token 유효시간 가지고 와서 BlackList로 저장
        Long expireTime = provider.getExpiration(accessToken);
        redisTemplate.opsForValue()
                .set(accessToken, "logout", expireTime, TimeUnit.MILLISECONDS);
        return response.success("로그아웃 되셨습니다.");
    }
    public ResponseEntity<?> authority(){
        //Security Context에 담겨 있는 authentiacation user Email 정보
        String userEmail = SecurityUtil.getCurrentUserEmail();

        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        // add ROLE_ADMIN
        user.getRoles().add(Authority.ROLE_ADMIN.name());
        userRepo.save(user);

        return response.success();
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

    @Transactional
    @Override
    public ResponseEntity<?> updateUserInfo(UpdateUserReqDTO updateUserReqDTO, String accessToken) {
        User user = userRepo.findByEmail(provider.getAuthentication(accessToken).getName()).orElse(null);
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
            Integer result = userRepo.updateInfo(password, phone, nickname, address, provider.getAuthentication(accessToken).getName());
            return new ResponseEntity<>(result > 0? "success" : "false", result>0? HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }
    @Transactional
    @Override
    public ResponseEntity<String> signOut(LoginReqDTO loginReqDTO) {
        User user = userRepo.findByEmail(loginReqDTO.getEmail()).orElse(null);
        try {
            userRepo.delete(user);
        }
        catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> viewMyInfo(String accessToken){
        String email = provider.getAuthentication(accessToken).getName();
        User user = userRepo.findByEmail(email).orElse(null);
        return new ResponseEntity<UserResDTO>(new UserResDTO(User.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .name(user.getName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build()), HttpStatus.OK);
    }

    private String encodingPassword(String password) {
        return encoder.encode(password);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }



    @Override
    @Transactional(readOnly = true)
    public void checkNickNameDuplication(UserReqDTO.SignUp signUp) {
        boolean nicknameDuplication = userRepo.existsByNickname(signUp.getNickname());
        if (nicknameDuplication) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void checkEmailDuplication(UserReqDTO.SignUp signUp) {
        boolean emailDuplication = userRepo.existsByEmail(signUp.getEmail());
        if (emailDuplication) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }
}
