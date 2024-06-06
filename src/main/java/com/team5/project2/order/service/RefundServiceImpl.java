package com.team5.project2.order.service;

import com.team5.project2.order.dto.RefundDto;
import com.team5.project2.order.repository.RefundRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefundServiceImpl implements RefundService{

    private final RefundRepository refundRepository;

    @Override
    public RefundDto createRefund(RefundDto refundDto) {
        return null;
    }

    @Override
    public List<RefundDto> getAllRefunds() {
        return null;
    }
}
