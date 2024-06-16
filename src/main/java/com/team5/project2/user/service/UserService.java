package com.team5.project2.user.service;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository jpaUserRepository, PasswordEncoder passwordEncoder) {
        this.jpaUserRepository = jpaUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

    public User createUser(User user) {
        return jpaUserRepository.save(user);
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

//    public User login(String email, String password) {
//
//        // 지시사항을 참고하여 코드를 작성해 보세요.
//        User findUser = jpaUserRepository.findByEmail(email);
//
//        if(!findUser.getPassword().equals(password)) {
//            return null;
//        }
//
//        return findUser;
//    }
}
