package com.hanbit.tutor.core.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.tutor.core.dao.ScheduleDAO;
import com.hanbit.tutor.core.session.SessionHelpler;
import com.hanbit.tutor.core.vo.ScheduleVO;

@Service
public class SchedulerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);

	@Autowired
	private ScheduleDAO scheduleDAO;

	public int addSchedule(ScheduleVO schedule) {
		LOGGER.debug("스케줄 추가");

		return scheduleDAO.insertSchedule(schedule);
	}

	public int modifySchedule(ScheduleVO schedule) {
		return scheduleDAO.updateSchedule(schedule);
	}

	public int removeSchedule(String scheduleId) {
		return scheduleDAO.deleteSchedule(scheduleId);
	}

	public List<ScheduleVO> listSchedules(String startDt, String endDt) {
		return scheduleDAO.selectSchedules(startDt, endDt);
	}

	public ScheduleVO getSchedule(String scheduleId) {
		return scheduleDAO.selectSchedule(scheduleId);
	}

	public String generateId() {
		String time = String.valueOf(System.currentTimeMillis());
		String threadId = String.valueOf(Thread.currentThread().getId());
		threadId = StringUtils.leftPad(threadId, 4, "0");

		String uniqueId = time + threadId;

		return uniqueId;
	}

	public int countSchedule(String startDt, String endDt) {
		return scheduleDAO.countSchedule(startDt, endDt);
	}

}
