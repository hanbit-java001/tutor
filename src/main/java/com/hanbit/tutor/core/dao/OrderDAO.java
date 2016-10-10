package com.hanbit.tutor.core.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanbit.tutor.core.vo.OrderDetailVO;
import com.hanbit.tutor.core.vo.OrderVO;

@Repository
public class OrderDAO {

	@Autowired
	private SqlSession sqlSession;

	public int insertOrder(OrderVO order) {

		Connection connection = null;

		try {
			connection.prepareStatement("SELECT SYSDATE FROM DUAL");
		}
		catch (SQLException e) {
			Throwable t = e.getCause();
			
			throw new RuntimeException(e);
		}

		return sqlSession.insert("order.insertOrder", order);
	}

	public int insertOrderDetail(OrderDetailVO orderDetail) {
		return sqlSession.insert("order.insertOrderDetail", orderDetail);
	}

}
