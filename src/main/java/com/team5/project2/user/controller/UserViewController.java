package com.team5.project2.user.controller;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.dto.UserLoginDto;
import com.team5.project2.user.dto.UserPostDto;
import com.team5.project2.user.mapper.UserMapper;
import com.team5.project2.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class UserViewController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String showHome() {
        return "/user/home";
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("userPostDto", new UserPostDto());
        return "/user/sign-up";
    }

    @PostMapping("/sign-up")
    public String processSignup(@Validated UserPostDto userPostDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || !userPostDto.getPassword().equals(userPostDto.getConfirmPassword())) {
            return "/user/sign-up";
        }
        User user = userMapper.userPostDtoToUser(userPostDto);
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("USER");
        User newUser = userService.createUser(user);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "/user/login-form";
    }

//    @PostMapping("/login")
//    public String processLogin(@Validated @ModelAttribute UserLoginDto userLoginDto, BindingResult bindingResult, HttpServletRequest request) {
//        if (bindingResult.hasErrors()) {
//            return "login-form";
//        }
//        User findUser = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
//
//        if (findUser == null) {
//            bindingResult.reject("loginError", "아이디 또는 패스워드가 틀립니다.");
//            return "login-form";
//        }
//
//        HttpSession session = request.getSession();
//        session.setAttribute("loginUser", findUser);
//
//        return "redirect:/home";
//    }
}
