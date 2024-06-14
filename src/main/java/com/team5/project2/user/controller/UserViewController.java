package com.team5.project2.user.controller;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.dto.UserLoginDto;
import com.team5.project2.user.dto.UserPostDto;
import com.team5.project2.user.mapper.UserMapper;
import com.team5.project2.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
public class UserViewController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @GetMapping("/user/home")
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
        user.setRole("ROLE_USER");
        User newUser = userService.createUser(user);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "/user/login-form";
    }

    @GetMapping("/admin/adminPage")
    public void admin() {
        System.out.println("hello");
    }

    @GetMapping("/user/myPage")
    public String showMyPage() {
        return "/user/myPage2";
    }

    @GetMapping("/user/editPage")
    public String showEditPage(Principal principal, Model model) {
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        model.addAttribute("user", user);
        return "/user/editPage";
    }

    @GetMapping("/user/deletePage")
    public String showDeleteAccountPage() {
        return "/user/deletePage";
    }

    @GetMapping("/user/deleteUser")
    public String deleteUser(Principal principal) {
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        userService.deleteUser(user.getId());
        return "redirect:/";
    }

    @PutMapping({"/user/editEmail"})
    public ResponseEntity updateEmail(@RequestBody Map<String, String> request, Principal principal) {
        String newEmail = request.get("email");
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        User updateUser = userService.updateUserEmail(user, newEmail);
        updateSecurityContext(newEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping({"/user/editName"})
    public ResponseEntity updateName(@RequestBody Map<String, String> request, Principal principal) {
        String newName = request.get("name");
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        User updateUser = userService.updateUserName(user, newName);
        updateSecurityContext(userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping({"/user/editPhone"})
    public ResponseEntity updatePhoneNumber(@RequestBody Map<String, String> request, Principal principal) {
        String newPhone  = request.get("phone");
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        User updateUser = userService.updateUserPhoneNumber(user, newPhone);
        updateSecurityContext(userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping({"/user/editPassword"})
    public ResponseEntity updatePassword(@RequestBody Map<String, String> request, Principal principal) {
        String currentPassword = request.get("currentPassword");
        String newPassword = request.get("newPassword");
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);

        if (!userService.checkIfValidOldPassword(user, currentPassword)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.updatePassword(user, newPassword);
        updateSecurityContext(userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void updateSecurityContext(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
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
