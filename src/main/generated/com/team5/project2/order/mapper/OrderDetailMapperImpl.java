package com.team5.project2.order.mapper;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.entity.Order;
import com.team5.project2.order.entity.OrderDetail;
import com.team5.project2.product.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-21T13:25:36+0900",
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

        orderDetailDto.setOrderId( orderDetailOrderId( orderDetail ) );
        orderDetailDto.setProductId( orderDetailProductId( orderDetail ) );
        orderDetailDto.setProductName( orderDetailProductName( orderDetail ) );
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

        orderDetail.setOrder( orderDetailDtoToOrder( orderDetailDto ) );
        orderDetail.setProduct( orderDetailDtoToProduct( orderDetailDto ) );
        orderDetail.setId( orderDetailDto.getId() );
        orderDetail.setCount( orderDetailDto.getCount() );
        orderDetail.setPrice( orderDetailDto.getPrice() );

        return orderDetail;
    }

    private Long orderDetailOrderId(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }
        Order order = orderDetail.getOrder();
        if ( order == null ) {
            return null;
        }
        Long id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long orderDetailProductId(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }
        Product product = orderDetail.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String orderDetailProductName(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }
        Product product = orderDetail.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected Order orderDetailDtoToOrder(OrderDetailDto orderDetailDto) {
        if ( orderDetailDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setId( orderDetailDto.getOrderId() );

        return order;
    }

    protected Product orderDetailDtoToProduct(OrderDetailDto orderDetailDto) {
        if ( orderDetailDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( orderDetailDto.getProductId() );

        return product.build();
    }
}
