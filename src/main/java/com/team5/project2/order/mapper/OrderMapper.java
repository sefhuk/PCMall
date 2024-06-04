package com.team5.project2.order.mapper;

import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    Order OrderDtoToOrder(OrderDto orderDto);
    OrderDto OrderToOrderDto(Order order);
}
