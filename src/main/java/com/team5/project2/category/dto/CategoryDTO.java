package com.team5.project2.category.dto;
import com.team5.project2.category.entity.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private Long parentId;
    private CategoryType type;
}
