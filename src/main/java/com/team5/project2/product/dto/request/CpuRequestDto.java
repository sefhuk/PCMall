package com.team5.project2.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CpuRequestDto extends ProductRequestDto{
    private Integer coreCount;
    private Integer threadCount;
    private Float baseClockSpeed;
    private Float boostClockSpeed;
}
