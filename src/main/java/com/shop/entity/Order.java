package com.shop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.shop.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders") // 정렬할 때 사용하는 키워드 'order'가 있기 때문에 엔티티에 매핑되는 테이블로 'orders'로 지정
@Getter
@Setter
public class Order extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Long id;

	@ManyToOne // 한명의 회원은 여러 번 주문을 할 수 있으므로 주문 엔티티 기준에서 다대일 단방향 매핑을 한다.
	@JoinColumn(name = "member_id")
	private Member member;

	private LocalDateTime orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<OrderItem> orderItems = new ArrayList<>();

//	private LocalDateTime regTime;
//	private LocalDateTime updateTime;

	public void addOrderItem(OrderItem orderItem) { // 주문상품 정보들을 담아준다. orderItem 객체를 order객체의 orderItem에 추가한다.
		orderItems.add(orderItem);
		orderItem.setOrder(this); // Order 엔티티와 OrderItem 엔티티가 양방향 참조관계이므로, OrderItem객체에도 order객체를 세팅한다.
	}

	public static Order createOrder(Member member, List<OrderItem> orderItemList) {
		Order order = new Order();
		order.setMember(member); // 상품을 주문한 회원의 정보를 세팅한다.
		for (OrderItem orderItem : orderItemList) { // 상품 페이지에서는 1개의 상품을 주문하지만, 장바구니 페이지에선 한 번에 여러 개의 상품을
			// 주문할 수 있다. 따라서 여러 개의 주문 상품을 담을 수 있도록 리스트 형태로 파라미터 값을 받으며 주문 객체에 orderItem 객체를
			// 추가한다.
			order.addOrderItem(orderItem);
		}
		order.setOrderStatus(OrderStatus.ORDER); // 주문상태를 Order로 세팅
		order.setOrderDate(LocalDateTime.now()); // 현재 시간을 주문시간으로 세팅
		return order;
	}

	public int getTotalPrice() { // 총주문 금액을 구하는 메소드
		int totalPrice = 0;
		for (OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}
	
	public void cancelOrder() {
		this.orderStatus = OrderStatus.CANCEL;
		for(OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}
}
