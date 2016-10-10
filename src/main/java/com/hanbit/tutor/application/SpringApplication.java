package com.hanbit.tutor.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import com.hanbit.tutor.core.service.MemberService;
import com.hanbit.tutor.core.service.OrderService;
import com.hanbit.tutor.core.service.SecurityService;
import com.hanbit.tutor.core.vo.OrderDetailVO;
import com.hanbit.tutor.core.vo.OrderVO;
import com.hanbit.tutor.core.vo.ScheduleVO;

public class SpringApplication {

	public static void main(String[] args) {
		try {
			Log4jConfigurer.initLogging("classpath:config/log4j.xml");

			ApplicationContext applicationContext =
					new ClassPathXmlApplicationContext("spring/applicationContext-core.xml",
							"spring/applicationContext-dao.xml");

			OrderService service = applicationContext.getBean(OrderService.class);

			OrderVO order = new OrderVO();
			order.setOrderId("12345689");
			order.setMemberId("12345");
			order.setOrderPrice(5000);

			OrderDetailVO orderProduct1 = new OrderDetailVO();
			orderProduct1.setOrderId(order.getOrderId());
			orderProduct1.setProductId("123456");
			orderProduct1.setProductPrice(1000);
			orderProduct1.setProductQuantity(2);

			OrderDetailVO orderProduct2 = new OrderDetailVO();
			orderProduct2.setOrderId(order.getOrderId());
			orderProduct2.setProductId("1234567890123456789012345");
			orderProduct2.setProductPrice(1500);
			orderProduct2.setProductQuantity(2);

			List<OrderDetailVO> orderProducts = new ArrayList<>();
			orderProducts.add(orderProduct1);
			orderProducts.add(orderProduct2);

			order.setOrderProducts(orderProducts);

			service.makeOrder(order);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
