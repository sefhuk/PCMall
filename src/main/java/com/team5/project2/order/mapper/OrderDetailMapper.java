package com.team5.project2.order.mapper;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    OrderDetailDto OrderDetailToOrderDetailDto(OrderDetail orderDetail);

    OrderDetail OrderDetailDtoToOrderDetail(OrderDetailDto orderDetailDto);
}
