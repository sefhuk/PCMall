package com.team5.project2.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone_number;

    private String address;

    private String role;

    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void update(User user) {
        if (user.getName() != null) {
            this.name = user.getName();
        }

        if (user.getEmail() != null) {
            this.email = user.getEmail();
        }

        if (user.getPassword() != null) {
            this.password = user.getPassword();
        }

        if (user.getPhone_number() != null) {
            this.phone_number = user.getPhone_number();
        }

        if (user.getAddress() != null) {
            this.address = user.getAddress();
        }
    }
}