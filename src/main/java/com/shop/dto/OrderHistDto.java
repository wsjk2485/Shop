package com.shop.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.shop.constant.OrderStatus;
import com.shop.entity.Order;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderHistDto {
	
	public OrderHistDto(Order order) {
		this.orderId = order.getId();
		this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 주문날짜 형태 수정하기
		this.orderStatus = order.getOrderStatus();
	}
	
	private Long orderId; // 주문아이디
	private String orderDate; // 주문날짜
	private OrderStatus orderStatus;	// 주문상태
	
	private List<OrderItemDto> orderItemDtoList = new ArrayList<>();
	
	// 주문 상품리스트
	public void addOrderItemDto(OrderItemDto orderItemDto) {
		orderItemDtoList.add(orderItemDto);
	}

}
