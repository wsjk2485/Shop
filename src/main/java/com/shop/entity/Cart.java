package com.shop.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart")
@Getter @Setter
@ToString
public class Cart {
	
	@Id
	@Column(name="cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)		// 일대일 연관관계 설정
	@JoinColumn(name="member_id")	// 외래키 설정
	private Member member;
	
	public static Cart createCart(Member meber) {
		Cart cart = new Cart();
		cart.setMember(meber);
		return cart;
	}
}
