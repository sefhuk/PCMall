package com.team5.project2.user.service;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.repository.JpaUserRepository;
import java.util.List;

public class UserService {
    private final JpaUserRepository jpaUserRepository;

    public UserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public List<User> findUserAll() {
        return this.jpaUserRepository.findAll();
    }

    public User findUserById(Long id) {
        return (User)this.jpaUserRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException();
        });
    }

    public User createUser(User user) {
        return (User)this.jpaUserRepository.save(user);
    }

    public User updateUser(User user) {
        User findUser = this.findUserById(user.getId());
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

        return (User)this.jpaUserRepository.save(findUser);
    }

    public void deleteUser(Long userId) {
        this.jpaUserRepository.deleteById(userId);
    }
}
