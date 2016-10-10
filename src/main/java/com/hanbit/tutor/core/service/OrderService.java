package com.hanbit.tutor.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanbit.tutor.core.dao.OrderDAO;
import com.hanbit.tutor.core.vo.OrderDetailVO;
import com.hanbit.tutor.core.vo.OrderVO;

@Service
public class OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderDAO orderDAO;

	@Transactional
	public void makeOrder(OrderVO order) {
		orderDAO.insertOrder(order);

		List<OrderDetailVO> orderProducts = order.getOrderProducts();

		for (OrderDetailVO orderDetail : orderProducts) {
			orderDAO.insertOrderDetail(orderDetail);

			LOGGER.debug("insertOrderDetail");
		}
	}

}
