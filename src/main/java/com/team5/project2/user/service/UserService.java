package com.team5.project2.user.service;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;

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
        User existingUserByPhoneNumber = jpaUserRepository.findByPhoneNumber(user.getPhoneNumber());
        User existingUserByEmail = jpaUserRepository.findByEmail(user.getEmail());

        if (existingUserByPhoneNumber != null || existingUserByEmail != null) {
            return false;
        }

        jpaUserRepository.save(user);
        return true;
    }

    public boolean updateUserEmail(User user, String email) {
        User existingUserByEmail = jpaUserRepository.findByEmail(email);
        if (existingUserByEmail != null) {
            return false;
        }
        user.updateEmail(email);
        jpaUserRepository.save(user);
        return true;
    }

    public void updateUserName(User user, String name) {
        user.updateName(name);
        jpaUserRepository.save(user);
    }

    public boolean updateUserPhoneNumber(User user, String phoneNumber) {
        User existingUserByPhoneNumber = jpaUserRepository.findByPhoneNumber(phoneNumber);

        if (existingUserByPhoneNumber != null) {
            return false;
        }
        user.updatePhoneNumber(phoneNumber);
        jpaUserRepository.save(user);
        return true;
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
}
