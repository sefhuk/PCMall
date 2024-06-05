package com.team5.project2.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CpuRequestDto {
    private Integer coreCount;
    private Integer threadCount;
    private Float baseClockSpeed;
    private Float boostClockSpeed;
    private Integer powerConsumption;
}
