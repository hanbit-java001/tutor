package com.hanbit.tutor.core.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.hanbit.tutor.core.vo.ScheduleVO;

public class SchedulerService {

	public int addSchedule(ScheduleVO schedule) {
		String url = "jdbc:oracle:thin:@127.0.0.1/xe";
		String user = "hanbit";
		String password = "hanbit";

		String sql = "INSERT INTO SCHEDULE (SCHEDULE_ID, TITLE, MEMO, START_DT, END_DT) "
				+ " VALUES('1', '학원가기', '공부하기', '20160912', '20160913')";

		int result = 0;

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection connection = DriverManager.getConnection(url, user, password);

			Statement statement = connection.createStatement();
			result = statement.executeUpdate(sql);

			connection.close();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int modifySchedule(ScheduleVO schedule) {

		return 0;
	}

	public int removeSchedule(String scheduleId) {

		return 0;
	}

	public List<ScheduleVO> listSchedules(String startDt, String endDt) {

		return null;
	}

	public ScheduleVO getSchedule(String scheduleId) {

		return null;
	}

}