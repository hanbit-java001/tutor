package com.hanbit.tutor.core.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.hanbit.tutor.core.vo.ScheduleVO;

public class SchedulerService {

	public int addSchedule(ScheduleVO schedule) {
		String url = "jdbc:oracle:thin:@127.0.0.1/xe";
		String user = "hanbit";
		String password = "hanbit";

		/*String sql = "INSERT INTO SCHEDULE (SCHEDULE_ID, TITLE, MEMO, START_DT, END_DT) "
				+ " VALUES('" + schedule.getScheduleId() + "', "
				+ "'" + schedule.getTitle() + "', "
				+ "'" + schedule.getMemo() + "', "
				+ "'" + schedule.getStartDt() + "', "
				+ "'" + schedule.getEndDt() + "')";*/

		String sql = "INSERT INTO SCHEDULE (SCHEDULE_ID, TITLE, MEMO, START_DT, END_DT) "
				+ " VALUES(?, ?, ?, ?, ?)";

		int result = 0;

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection connection = DriverManager.getConnection(url, user, password);

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, schedule.getScheduleId());
			statement.setString(2, schedule.getTitle());
			statement.setString(3, schedule.getMemo());
			statement.setString(4, schedule.getStartDt());
			statement.setString(5, schedule.getEndDt());

			result = statement.executeUpdate();

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
