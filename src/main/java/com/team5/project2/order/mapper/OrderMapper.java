package com.team5.project2.order.mapper;

import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "refund.id", target = "refundId")
    OrderDto OrderToOrderDto(Order order);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "refundId", target = "refund.id")
    Order OrderDtoToOrder(OrderDto orderDto);
}
