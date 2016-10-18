package com.hanbit.tutor.core.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanbit.tutor.core.dao.ScheduleDAO;
import com.hanbit.tutor.core.session.SessionHelpler;
import com.hanbit.tutor.core.vo.ScheduleVO;

@Service
public class SchedulerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);

	@Autowired
	private ScheduleDAO scheduleDAO;

	@Transactional
	public int addSchedule(ScheduleVO schedule) {
		int memberId = SessionHelpler.getSession().getMemberId();
		schedule.setMemberId(memberId);

		int result = scheduleDAO.insertSchedule(schedule);
		scheduleDAO.insertShares(schedule.getScheduleId(), memberId,
				false, true);

		return result;
	}

	public int modifySchedule(ScheduleVO schedule) {
		int memberId = SessionHelpler.getSession().getMemberId();
		schedule.setMemberId(memberId);

		return scheduleDAO.updateSchedule(schedule);
	}

	@Transactional
	public int removeSchedule(String scheduleId) {
		int memberId = SessionHelpler.getSession().getMemberId();

		int result = scheduleDAO.deleteSchedule(scheduleId, memberId);

		if (result > 0) {
			scheduleDAO.deleteShares(scheduleId);
		}

		return result;
	}

	public List<ScheduleVO> listSchedules(String startDt, String endDt) {
		int memberId = SessionHelpler.getSession().getMemberId();

		return scheduleDAO.selectSchedules(startDt, endDt, memberId);
	}

	public ScheduleVO getSchedule(String scheduleId) {
		int memberId = SessionHelpler.getSession().getMemberId();

		return scheduleDAO.selectSchedule(scheduleId, memberId);
	}

	public String generateId() {
		String time = String.valueOf(System.currentTimeMillis());
		String threadId = String.valueOf(Thread.currentThread().getId());
		threadId = StringUtils.leftPad(threadId, 4, "0");

		String uniqueId = time + threadId;

		return uniqueId;
	}

	public int countSchedule(String startDt, String endDt) {
		int memberId = SessionHelpler.getSession().getMemberId();

		return scheduleDAO.countSchedule(startDt, endDt, memberId);
	}

}
