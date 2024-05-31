package com.team5.project2.user.controller;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.dto.UserPostDto;
import com.team5.project2.user.mapper.UserMapper;
import com.team5.project2.user.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/user"})
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUserAll() {
        List<User> users = userService.findUserAll();
        if(users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping({"/{userId}"})
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User findUser = this.userService.findUserById(userId);
        if(findUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(findUser, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserPostDto userPostDto) {
        User user = userMapper.userPostDtoToUser(userPostDto);
        User newUser = this.userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
