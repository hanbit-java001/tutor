package com.hanbit.tutor.core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public abstract class AbstarctDAO {

	protected Connection getConnection() {
		String url = "jdbc:oracle:thin:@127.0.0.1/xe";
		String user = "hanbit";
		String password = "hanbit";

		Connection connection = null;

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection(url, user, password);
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
