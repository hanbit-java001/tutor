package com.hanbit.tutor.core.service;

import java.util.List;

import com.hanbit.tutor.core.dao.ScheduleDAO;
import com.hanbit.tutor.core.vo.ScheduleVO;

public class SchedulerService {

	public int addSchedule(ScheduleVO schedule) {
		ScheduleDAO scheduleDAO = new ScheduleDAO();

		return scheduleDAO.insertSchedule(schedule);
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
