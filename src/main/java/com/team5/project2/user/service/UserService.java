package com.team5.project2.user.service;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository jpaUserRepository;

    public UserService(UserRepository jpaUserRepository) {
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
        findUser.update(user);

        return jpaUserRepository.save(findUser);
    }

    public void deleteUser(Long userId) {
        jpaUserRepository.deleteById(userId);
    }
}
