package com.hanbit.tutor.core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstarctDAO {

	@Autowired
	private DataSource dataSource;

	protected Connection getConnection() {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}

	protected void closeConnection(Connection connection) {
		try {
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected int executeSql(Connection connection, String sql, List params) {
		int result = 0;

		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			for (int i=0;i<params.size();i++) {
				statement.setObject(i + 1, params.get(i));
			}

			result = statement.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
