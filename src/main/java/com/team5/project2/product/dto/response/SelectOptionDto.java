package com.team5.project2.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SelectOptionDto {
    private String name;
    private String classAttribute;
}
