package com.team5.project2.order.mapper;

import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.entity.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {OrderDetailMapper.class})
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderDetails", target = "orderDetails")
    OrderDto OrderToOrderDto(Order order);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "orderDetails", target = "orderDetails")
    Order OrderDtoToOrder(OrderDto orderDto);

    @AfterMapping
    default void calculateTotalPrice(Order order, @MappingTarget OrderDto orderDto) {
        orderDto.setTotalPrice(order.getTotalPrice());
    }
}

