package com.team5.project2.user.domain;

import com.team5.project2.common.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Email extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String code;

    private Boolean emailStatus = false;

    public Email(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public boolean verifyEmailCode(String verifyCode) {
        if(verifyCode.equals(code)) {
            emailStatus = true;
            return true;
        }
        return false;
    }
}
