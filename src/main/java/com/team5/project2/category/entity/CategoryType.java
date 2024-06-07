package com.team5.project2.category.entity;

import lombok.Getter;

@Getter
public enum CategoryType {

    CATEGORY("카테고리"),
    MANUFACTURER("제조사");

    private final String displayName;
    CategoryType(String displayName) {
        this.displayName = displayName;
    }
}
