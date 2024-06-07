package com.team5.project2.order.mapper;

import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.entity.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "user.id", target = "userId")
    OrderDto OrderToOrderDto(Order order);

    @Mapping(source = "userId", target = "user.id")
    Order OrderDtoToOrder(OrderDto orderDto);

    @AfterMapping
    default void calculateTotalPrice(Order order, @MappingTarget OrderDto orderDto) {
        orderDto.setTotalPrice(order.getTotalPrice());
    }
}
