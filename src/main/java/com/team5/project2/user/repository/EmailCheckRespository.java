package com.team5.project2.user.repository;

import com.team5.project2.user.domain.Email;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailCheckRespository extends JpaRepository<Email, Long> {
    Email findByEmail(String email);
    List<Email> findByEmailOrderByCreatedAtDesc(String email);
    Optional<Email> findFirstByEmailOrderByCreatedAtDesc(String email);
}
