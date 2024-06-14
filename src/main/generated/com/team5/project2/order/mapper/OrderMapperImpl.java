package com.team5.project2.order.mapper;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.entity.Order;
import com.team5.project2.order.entity.OrderDetail;
import com.team5.project2.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-14T17:03:53+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto OrderToOrderDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        orderDto.setUserId( orderUserId( order ) );
        orderDto.setId( order.getId() );
        orderDto.setStatus( order.getStatus() );
        orderDto.setTotalPrice( order.getTotalPrice() );
        orderDto.setOrderDetails( orderDetailListToOrderDetailDtoList( order.getOrderDetails() ) );

        calculateTotalPrice( order, orderDto );

        return orderDto;
    }

    @Override
    public Order OrderDtoToOrder(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setUser( orderDtoToUser( orderDto ) );
        order.setId( orderDto.getId() );
        order.setStatus( orderDto.getStatus() );
        order.setOrderDetails( orderDetailDtoListToOrderDetailList( orderDto.getOrderDetails() ) );

        return order;
    }

    private Long orderUserId(Order order) {
        if ( order == null ) {
            return null;
        }
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected OrderDetailDto orderDetailToOrderDetailDto(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }

        OrderDetailDto orderDetailDto = new OrderDetailDto();

        orderDetailDto.setId( orderDetail.getId() );
        orderDetailDto.setCount( orderDetail.getCount() );
        orderDetailDto.setPrice( orderDetail.getPrice() );

        return orderDetailDto;
    }

    protected List<OrderDetailDto> orderDetailListToOrderDetailDtoList(List<OrderDetail> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderDetailDto> list1 = new ArrayList<OrderDetailDto>( list.size() );
        for ( OrderDetail orderDetail : list ) {
            list1.add( orderDetailToOrderDetailDto( orderDetail ) );
        }

        return list1;
    }

    protected User orderDtoToUser(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( orderDto.getUserId() );

        return user;
    }

    protected OrderDetail orderDetailDtoToOrderDetail(OrderDetailDto orderDetailDto) {
        if ( orderDetailDto == null ) {
            return null;
        }

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setId( orderDetailDto.getId() );
        orderDetail.setCount( orderDetailDto.getCount() );
        orderDetail.setPrice( orderDetailDto.getPrice() );

        return orderDetail;
    }

    protected List<OrderDetail> orderDetailDtoListToOrderDetailList(List<OrderDetailDto> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderDetail> list1 = new ArrayList<OrderDetail>( list.size() );
        for ( OrderDetailDto orderDetailDto : list ) {
            list1.add( orderDetailDtoToOrderDetail( orderDetailDto ) );
        }

        return list1;
    }
}
