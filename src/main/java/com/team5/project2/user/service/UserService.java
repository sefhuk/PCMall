package com.team5.project2.user.service;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.repository.JpaUserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JpaUserRepository jpaUserRepository;

    public UserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public List<User> findUserAll() {
        return jpaUserRepository.findAll();
    }

    public User findUserById(Long id) {
        return jpaUserRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());
    }

    public User createUser(User user) {
        return jpaUserRepository.save(user);
    }

    public User updateUser(User user) {
        User findUser = findUserById(user.getId());
        if (user.getName() != null) {
            findUser.setName(user.getName());
        }

        if (user.getEmail() != null) {
            findUser.setEmail(user.getEmail());
        }

        if (user.getPassword() != null) {
            findUser.setPassword(user.getPassword());
        }

        if (user.getPhone_number() != null) {
            findUser.setPhone_number(user.getPhone_number());
        }

        if (user.getAddress() != null) {
            findUser.setAddress(user.getAddress());
        }

        return jpaUserRepository.save(findUser);
    }

    public void deleteUser(Long userId) {
        jpaUserRepository.deleteById(userId);
    }
}
