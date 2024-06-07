package com.team5.project2.category.repository;

import com.team5.project2.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentId(Long parentId);
    List<Category> findByParentIdIsNull();
    List<Category> findByName(String name);
}
