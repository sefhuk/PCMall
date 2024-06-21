package com.team5.project2.user.domain;

import com.team5.project2.cart.entity.Cart;
import com.team5.project2.common.entity.BaseTime;
import com.team5.project2.order.entity.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    private String address;

    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

    public void updateEmail(String email) {
        if(email != null) {
            this.email = email;
        }
    }

    public void updateName(String name) {
        if(name != null) {
            this.name = name;
        }
    }

    public void updatePhoneNumber(String phoneNumber) {
        if(phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
    }

    public void updatePassword(String password) {
        if(password != null) {
            this.password = password;
        }
    }

    public void updateAddress(String address) {
        if(address != null) {
            this.address = address;
        }
    }
}
