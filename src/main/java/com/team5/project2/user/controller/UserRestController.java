package com.team5.project2.user.controller;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.service.UserService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @PutMapping({"/user/editEmail"})
    public ResponseEntity<?> updateEmail(@RequestBody Map<String, String> request, Principal principal) {
        String newEmail = request.get("email");
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        userService.updateUserEmail(user, newEmail);
        updateSecurityContext(newEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping({"/user/editName"})
    public ResponseEntity<?> updateName(@RequestBody Map<String, String> request, Principal principal) {
        String newName = request.get("name");
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        userService.updateUserName(user, newName);
        updateSecurityContext(userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping({"/user/editPhone"})
    public ResponseEntity<?> updatePhoneNumber(@RequestBody Map<String, String> request, Principal principal) {
        String newPhone  = request.get("phone");
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        userService.updateUserPhoneNumber(user, newPhone);
        updateSecurityContext(userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping({"/user/editPassword"})
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> request, Principal principal) {
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

    @PutMapping({"/user/editAddress"})
    public ResponseEntity<?> updateAddress(@RequestBody Map<String, String> request, Principal principal) {
        String newAddress  = request.get("address") + " " + request.get("detailAddress");
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        userService.updateUserAddress(user, newAddress);
        updateSecurityContext(userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping({"/admin/deleteUser/{userId}"})
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, Principal principal) {
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        userService.deleteUser(userId);
        updateSecurityContext(userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void updateSecurityContext(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
