package com.team5.project2.cart.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team5.project2.common.entity.BaseTime;
import com.team5.project2.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

@Entity
@Getter
public class Cart extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

    public Optional<CartItem> findItemByProductId(Long productId) {
        return items.stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst();
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void removeItem(CartItem item) {
        items.remove(item);
    }
}