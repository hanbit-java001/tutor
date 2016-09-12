package com.hanbit.tutor.application;

import com.hanbit.tutor.core.service.SchedulerService;
import com.hanbit.tutor.core.vo.ScheduleVO;

public class SimpleApplication {

	public static void main(String[] args) {
		SchedulerService schedulerService = new SchedulerService();

		ScheduleVO schedule = new ScheduleVO();
		schedule.setScheduleId(String.valueOf(System.currentTimeMillis()));
		schedule.setTitle("점심");
		schedule.setMemo("메모");
		schedule.setStartDt("201609131130");
		schedule.setEndDt("201609131230");

		int result = schedulerService.addSchedule(schedule);

		System.out.println("결과: " + result);
	}

}
