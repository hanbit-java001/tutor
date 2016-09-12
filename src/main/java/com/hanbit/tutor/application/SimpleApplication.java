package com.hanbit.tutor.application;

import com.hanbit.tutor.core.service.SchedulerService;
import com.hanbit.tutor.core.vo.ScheduleVO;

public class SimpleApplication {

	public static void main(String[] args) {
		SchedulerService schedulerService = new SchedulerService();

		ScheduleVO schedule = new ScheduleVO();

		int result = schedulerService.addSchedule(schedule);

		System.out.println("결과: " + result);
	}

}
