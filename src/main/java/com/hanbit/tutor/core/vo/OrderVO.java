package com.hanbit.tutor.core.vo;

import java.util.List;

public class OrderVO {

	private String orderId;
	private String memberId;
	private int orderPrice;

	private List<OrderDetailVO> orderProducts;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public List<OrderDetailVO> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderDetailVO> orderProducts) {
		this.orderProducts = orderProducts;
	}


}
