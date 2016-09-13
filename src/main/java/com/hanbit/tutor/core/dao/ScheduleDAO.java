package com.hanbit.tutor.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hanbit.tutor.core.vo.ScheduleVO;

@Repository
public class ScheduleDAO extends AbstarctDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleDAO.class);

	public int insertSchedule(ScheduleVO schedule) {
		LOGGER.debug("인서트 스케줄");

		Connection connection = getConnection();

		String sql = "INSERT INTO SCHEDULE (SCHEDULE_ID, TITLE, MEMO, START_DT, END_DT) "
				+ " VALUES(?, ?, ?, ?, ?)";

		List params = new ArrayList();
		params.add(schedule.getScheduleId());
		params.add(schedule.getTitle());
		params.add(schedule.getMemo());
		params.add(schedule.getStartDt());
		params.add(schedule.getEndDt());

		int result = executeSql(connection, sql, params);

		closeConnection(connection);

		return result;
	}

	public int updateSchedule(ScheduleVO schedule) {
		Connection connection = getConnection();

		String sql = "UPDATE SCHEDULE SET TITLE = ?, MEMO = ?, "
				+ "START_DT = ?, END_DT = ? "
				+ "WHERE SCHEDULE_ID = ?";

		List params = new ArrayList();
		params.add(schedule.getTitle());
		params.add(schedule.getMemo());
		params.add(schedule.getStartDt());
		params.add(schedule.getEndDt());
		params.add(schedule.getScheduleId());

		int result = executeSql(connection, sql, params);

		closeConnection(connection);

		return result;
	}

	public int deleteSchedule(String scheduleId) {
		Connection connection = getConnection();

		String sql = "DELETE FROM SCHEDULE WHERE SCHEDULE_ID = ?";

		List params = new ArrayList();
		params.add(scheduleId);

		int result = executeSql(connection, sql, params);

		closeConnection(connection);

		return result;
	}

	public List<ScheduleVO> selectSchedules(String startDt, String endDt) {
		Connection connection = getConnection();

		String sql = "SELECT SCHEDULE_ID, TITLE, MEMO, "
				+ "START_DT, END_DT FROM SCHEDULE "
				+ "WHERE START_DT <= ? AND END_DT >= ? "
				+ "ORDER BY START_DT";

		List params = new ArrayList();
		params.add(endDt);
		params.add(startDt);

		List<ScheduleVO> result = new ArrayList<ScheduleVO>();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			for (int i=0;i<params.size();i++) {
				statement.setObject(i + 1, params.get(i));
			}

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ScheduleVO schedule = new ScheduleVO();

				schedule.setScheduleId(resultSet.getString("SCHEDULE_ID"));
				schedule.setTitle(resultSet.getString("TITLE"));
				schedule.setMemo(resultSet.getString("MEMO"));
				schedule.setStartDt(resultSet.getString("START_DT"));
				schedule.setEndDt(resultSet.getString("END_DT"));

				result.add(schedule);
			}

			resultSet.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		closeConnection(connection);

		return result;
	}

	public ScheduleVO selectSchedule(String scheduleId) {
		Connection connection = getConnection();

		String sql = "SELECT SCHEDULE_ID, TITLE, MEMO, "
				+ "START_DT, END_DT FROM SCHEDULE "
				+ "WHERE SCHEDULE_ID = ?";

		List params = new ArrayList();
		params.add(scheduleId);

		ScheduleVO schedule = null;

		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			for (int i=0;i<params.size();i++) {
				statement.setObject(i + 1, params.get(i));
			}

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				schedule = new ScheduleVO();

				schedule.setScheduleId(resultSet.getString("SCHEDULE_ID"));
				schedule.setTitle(resultSet.getString("TITLE"));
				schedule.setMemo(resultSet.getString("MEMO"));
				schedule.setStartDt(resultSet.getString("START_DT"));
				schedule.setEndDt(resultSet.getString("END_DT"));
			}

			resultSet.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		closeConnection(connection);

		return schedule;
	}


}
