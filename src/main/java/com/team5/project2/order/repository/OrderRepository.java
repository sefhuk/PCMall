package com.team5.project2.order.repository;

import com.team5.project2.order.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long id);

    @EntityGraph(attributePaths = {"orderDetails", "orderDetails.product"})
    List<Order> findAll();
}
