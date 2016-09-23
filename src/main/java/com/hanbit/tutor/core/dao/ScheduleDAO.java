package com.hanbit.tutor.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanbit.tutor.core.vo.ScheduleVO;

@Repository
public class ScheduleDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleDAO.class);

	@Autowired
	private SqlSession sqlSession;

	public int insertSchedule(ScheduleVO schedule) {
		LOGGER.debug("인서트 스케줄");

		int result = sqlSession.insert("schedule.insertSchedule", schedule);

		return result;
	}

	public int updateSchedule(ScheduleVO schedule) {
		int result = sqlSession.update("schedule.updateSchedule", schedule);

		return result;
	}

	public int deleteSchedule(String scheduleId) {
		int result = sqlSession.delete("schedule.deleteSchedule", scheduleId);

		return result;
	}

	public List<ScheduleVO> selectSchedules(String startDt, String endDt) {
		Map params = new HashMap();
		params.put("startDt", startDt);
		params.put("endDt", endDt);

		List<ScheduleVO> result = sqlSession.selectList("schedule.selectSchedules", params);

		return result;
	}

	public ScheduleVO selectSchedule(String scheduleId) {
		ScheduleVO schedule = sqlSession.selectOne("schedule.selectSchedule", scheduleId);

		return schedule;
	}

	public int countSchedule(String startDt, String endDt) {
		Map params = new HashMap();
		params.put("startDt", startDt);
		params.put("endDt", endDt);

		int result = sqlSession.selectOne("schedule.countSchedule", params);

		return result;
	}

}
