package com.team5.project2.order.service;

import com.team5.project2.order.dto.RefundDto;
import java.util.List;

public interface RefundService {
    RefundDto createRefund(RefundDto refundDto);
    List<RefundDto> getAllRefunds();
}
