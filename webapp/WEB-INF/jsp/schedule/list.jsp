<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<meta name="msapplication-tap-highlight" content="no" />
<title>스케줄러</title>
<link rel="stylesheet" href="/static/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="/static/plugins/material/iconfont/material-icons.css">
<link rel="stylesheet" href="/static/plugins/fullcalendar/fullcalendar.min.css" />
<link rel="stylesheet" href="/static/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet" href="/static/css/scheduler.css" />
</head>
<body>

	<header class="hanbit-header">
		<div id="btnGroupMain">
			<div id="btnMenu" class="hanbit-top-button">
				<i class="material-icons hanbit-abs-center">menu</i>
			</div>
		</div>

		<div id="btnGroupSub">
			<div class="btnBack hanbit-top-button">
				<i class="material-icons hanbit-abs-center">navigate_before</i>
			</div>
		</div>

		<div id="btnGroupRight">
			<div id="btnGroupCalendar">
				<div id="btnPrev" class="hanbit-top-button right arrow">
					<i class="material-icons hanbit-abs-center">keyboard_arrow_left</i>
				</div>
				<div id="btnToday" class="hanbit-top-button right">
					<i class="material-icons hanbit-abs-center">today</i>
				</div>
				<div id="btnNext" class="hanbit-top-button right arrow">
					<i class="material-icons hanbit-abs-center">keyboard_arrow_right</i>
				</div>
				<div id="btnAddSchedule" class="hanbit-top-button right">
					<i class="material-icons hanbit-abs-center">add</i>
				</div>
			</div>

			<div id="btnGroupAddSchedule">
				<div class="btnApplyAddSchedule hanbit-top-button right">
					<i class="material-icons hanbit-abs-center">done</i>
				</div>
			</div>
		</div>
	</header>

	<div class="hanbit-container">
		<div id="calendar"></div>

		<div id="divAddSchedule">
			<div class="form-group">
    			<label for="txtTitle">제목</label>
    			<input type="text" class="form-control" id="txtTitle" placeholder="제목">
			</div>
			<div class="form-group">
    			<label for="txtStartDt">시작</label>
    			<input type="text" class="form-control" id="txtStartDt" placeholder="시작">
			</div>
			<div class="form-group">
    			<label for="txtEndDt">종료</label>
    			<input type="text" class="form-control" id="txtEndDt" placeholder="종료">
			</div>
			<div class="form-group">
    			<label for="txtMemo">메모</label>
    			<textarea class="form-control" id="txtMemo" placeholder="메모" rows="3"></textarea>
			</div>
			<div class="bottom-buttons">
				<button class="btnApplyAddSchedule btn btn-success">추가</button>
				<button class="btnBack btn btn-danger">취소</button>
			</div>
		</div>
	</div>

<script src="/static/plugins/jquery/jquery-3.1.0.min.js"></script>
<script src="/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/plugins/momentjs/moment.min.js"></script>
<script src="/static/plugins/fullcalendar/fullcalendar.min.js"></script>
<script src="/static/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/plugins/fullcalendar/locale/ko.js"></script>
<script src="/static/js/scheduler.js"></script>
</body>
</html>