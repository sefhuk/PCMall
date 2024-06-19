package com.team5.project2.user.service;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.dto.TokenInfoDto;
//import com.team5.project2.user.jwt.JwtTokenProvider;
import com.team5.project2.user.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final JwtTokenProvider jwtTokenProvider;

    public List<User> findUserAll() {
        return jpaUserRepository.findAll();
    }

    public User findUserById(Long id) {
        return jpaUserRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());
    }

    public User findUserByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    public boolean createUser(User user) {
        User existingUserByUsername = jpaUserRepository.findByPhoneNumber(user.getPhoneNumber());
        User existingUserByEmail = jpaUserRepository.findByEmail(user.getEmail());

        if (existingUserByUsername != null || existingUserByEmail != null) {
            return false;
        }

        jpaUserRepository.save(user);
        return true;
    }

    public void updateUserEmail(User user, String email) {
        user.updateEmail(email);
        jpaUserRepository.save(user);
    }

    public void updateUserName(User user, String name) {
        user.updateName(name);
        jpaUserRepository.save(user);
    }

    public void updateUserPhoneNumber(User user, String phone_number) {
        user.updatePhoneNumber(phone_number);
        jpaUserRepository.save(user);
    }

    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public void updatePassword(User user, String newPassword) {
        String password = passwordEncoder.encode(newPassword);
        user.updatePassword(password);
        jpaUserRepository.save(user);
    }

    public void updateUserAddress(User user, String address) {
        user.updateAddress(address);
        jpaUserRepository.save(user);
    }

    public void deleteUser(Long userId) {
        jpaUserRepository.deleteById(userId);
    }

//    @Transactional
//    public TokenInfoDto login(String memberId, String password) {
//        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
//        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
//
//        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
//        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        TokenInfoDto tokenInfoDto = jwtTokenProvider.generateToken(authentication);
//
//        return tokenInfoDto;
//    }
}
