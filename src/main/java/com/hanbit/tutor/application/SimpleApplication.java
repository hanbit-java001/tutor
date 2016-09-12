package com.hanbit.tutor.application;

import com.hanbit.tutor.core.service.SchedulerService;
import com.hanbit.tutor.core.vo.ScheduleVO;

public class SimpleApplication {

	public static void main(String[] args) {
		SchedulerService schedulerService = new SchedulerService();

		ScheduleVO schedule = new ScheduleVO();
		schedule.setScheduleId(String.valueOf(System.currentTimeMillis()));
		schedule.setTitle("저녁");
		schedule.setMemo("반찬 뭘까");
		schedule.setStartDt("201609131830");
		schedule.setEndDt("201609131930");

		int result = schedulerService.addSchedule(schedule);

		System.out.println("결과: " + result);
	}

}
