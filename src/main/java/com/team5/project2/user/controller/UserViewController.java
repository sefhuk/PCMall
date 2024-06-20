package com.team5.project2.user.controller;

import com.team5.project2.user.domain.Email;
import com.team5.project2.user.domain.User;
import com.team5.project2.user.dto.UserLoginDto;
import com.team5.project2.user.dto.UserPostDto;
import com.team5.project2.user.mapper.UserMapper;
import com.team5.project2.user.service.MailService;
import com.team5.project2.user.service.UserService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UserViewController {
    private final UserService userService;
    private final MailService mailService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("userPostDto", new UserPostDto());
        return "user/sign-up";
    }

    @PostMapping("/sign-up")
    public String processSignup(@Validated UserPostDto userPostDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "user/sign-up";
        }

        if (!userPostDto.getPassword().equals(userPostDto.getConfirmPassword())) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "user/sign-up";
        }

        User user = userMapper.userPostDtoToUser(userPostDto);
        Email email = mailService.findByEmail(user.getEmail());
        if(!email.getEmailStatus()) {
            model.addAttribute("error", "이메일 인증을 진행해주세요.");
            return "user/sign-up";
        }

        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");
        if (userService.createUser(user)) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "이메일이나 전화번호가 이미 존재합니다.");
            return "user/sign-up";
        }
    }

    @GetMapping("/")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        if (error != null) {
            model.addAttribute("error", "로그인 인증에 실패했습니다.");
        }
        return "user/login-form";
    }

    @GetMapping("/user/myPage")
    public String showMyPage(Principal principal, Model model) {
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);

        model.addAttribute("userId", user.getId());

        if(user.getRole().equals("ROLE_USER")) {
            return "user/myPage";
        }
        return "user/adminPage";
    }

    @GetMapping("/user/editPage")
    public String showEditPage(Principal principal, Model model) {
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        model.addAttribute("user", user);
        return "user/editPage";
    }

    @GetMapping("/user/deletePage")
    public String showDeleteAccountPage() {
        return "user/deletePage";
    }

    @GetMapping("/user/deleteUser")
    public String deleteUser(Principal principal) {
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        userService.deleteUser(user.getId());
        return "redirect:/";
    }

    @GetMapping("/admin/user")
    public String showAllUser(Model model) {
        List<User> users = userService.findUserAll();
        model.addAttribute("users", users);
        return "user/AllUsersDetail";
    }
}
