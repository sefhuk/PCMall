package com.team5.project2.order.mapper;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.entity.OrderDetail;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-14T17:03:53+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class OrderDetailMapperImpl implements OrderDetailMapper {

    @Override
    public OrderDetailDto OrderDetailToOrderDetailDto(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }

        OrderDetailDto orderDetailDto = new OrderDetailDto();

        orderDetailDto.setId( orderDetail.getId() );
        orderDetailDto.setCount( orderDetail.getCount() );
        orderDetailDto.setPrice( orderDetail.getPrice() );

        return orderDetailDto;
    }

    @Override
    public OrderDetail OrderDetailDtoToOrderDetail(OrderDetailDto orderDetailDto) {
        if ( orderDetailDto == null ) {
            return null;
        }

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setId( orderDetailDto.getId() );
        orderDetail.setCount( orderDetailDto.getCount() );
        orderDetail.setPrice( orderDetailDto.getPrice() );

        return orderDetail;
    }
}
