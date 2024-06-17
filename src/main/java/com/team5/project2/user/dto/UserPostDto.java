package com.team5.project2.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPostDto {
    @NotEmpty(message = "이름을 입력해 주세요.")
    @Size(min = 2, message = "이름은 최소 2자 이상이어야 합니다.")
    private String name;

    @NotEmpty(message = "이메일을 입력해 주세요.")
    @Email(message = "유효한 이메일 주소를 입력해 주세요.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인을 입력해 주세요.")
    private String confirmPassword;

    @NotEmpty(message = "전화번호를 입력해 주세요.")
    @Pattern(regexp = "\\d{10,11}", message = "유효한 전화번호를 입력해 주세요.")
    private String phoneNumber;
}