package com.team5.project2.category.dto;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private Long parentId;
    private String type;
}
