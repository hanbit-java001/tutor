<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<title>스케줄러</title>
<link rel="stylesheet" href="/static/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="/static/plugins/material/iconfont/material-icons.css">
<link rel="stylesheet" href="/static/plugins/fullcalendar/fullcalendar.min.css" />
<link rel="stylesheet" href="/static/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" />
<style type="text/css">
.hanbit-header {
	position: fixed;
	width: 100%;
	height: 48px;
	background-color: #3F51B5;
	z-index: 1000;
}

.hanbit-top-button {
	position: relative;
	width: 48px;
	height: 48px;
	color: white;
	text-align: center;
	cursor: pointer;
}
.hanbit-top-button.right {
	display: inline-block;
	right: 0px;
}
.hanbit-top-button.arrow {
	width: 32px;
}

#btnGroupRight {
	position: absolute;
	top: 0px;
	right: 0px;
}

.hanbit-abs-center {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

.hanbit-container {
	padding: 58px 10px 10px 10px;
}

#btnGroupAddSchedule {
	display: none;
}

#divAddSchedule {
	display: none;
	position: relative;
}

.hanbit-schedule-detail {
	width: 100%;
}

.hanbit-schedule-detail .column-title {
	width: 20%;
	min-width: 80px;
	max-width: 150px;
}

.hanbit-schedule-detail .column-value {
	width: 80%;
}
</style>
</head>
<body>

	<header class="hanbit-header">
		<div id="btnMenu" class="hanbit-top-button">
			<i class="material-icons hanbit-abs-center">menu</i>
		</div>
		<div id="btnGroupRight">
			<div id="btnGroupCalendar">
				<div id="btnPrevMonth" class="hanbit-top-button right arrow">
					<i class="material-icons hanbit-abs-center">keyboard_arrow_left</i>
				</div>
				<div id="btnNextMonth" class="hanbit-top-button right arrow">
					<i class="material-icons hanbit-abs-center">keyboard_arrow_right</i>
				</div>
				<div id="btnAddSchedule" class="hanbit-top-button right">
					<i class="material-icons hanbit-abs-center">add</i>
				</div>
			</div>
			<div id="btnGroupAddSchedule">
				<div id="btnApplyAddSchedule" class="hanbit-top-button right">
					<i class="material-icons hanbit-abs-center">done</i>
				</div>
				<div id="btnCancelAddSchedule" class="hanbit-top-button right">
					<i class="material-icons hanbit-abs-center">clear</i>
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
    			<textarea class="form-control" id="txtMemo" placeholder="메모"></textarea>
			</div>
		</div>
	</div>

<script src="/static/plugins/jquery/jquery-3.1.0.min.js"></script>
<script src="/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/plugins/momentjs/moment.min.js"></script>
<script src="/static/plugins/fullcalendar/fullcalendar.min.js"></script>
<script src="/static/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/plugins/fullcalendar/locale/ko.js"></script>
<script type="text/javascript">
$(document).ready(function() {

    $('#calendar').fullCalendar({
    	locale: "ko",
    	height: "auto",

    	header: {
    	    left:   "",
    	    center: "title",
    	    right:  ""
    	},

    	dayClick: function(date) {
    		alert(date.format('MMMM Do YYYY, h:mm:ss a'));
        }
    });

    var currentMoment = moment();

    var range = {
       	start: currentMoment.startOf("month").format("YYYYMMDDHHmm"),
       	end: currentMoment.endOf("month").format("YYYYMMDDHHmm")
    };


    $("#btnPrevMonth").on("click", function() {
    	showPrevMonth();
    });

    $("#btnNextMonth").on("click", function() {
    	showNextMonth();
    });

    $("#btnAddSchedule").on("click", function() {
		$("#btnGroupCalendar").hide();
		$("#btnGroupAddSchedule").show();

		$("#calendar").hide();
		$("#divAddSchedule").show();
    });

    $("#btnCancelAddSchedule").on("click", function() {
		$("#btnGroupAddSchedule").hide();
		$("#btnGroupCalendar").show();

		$("#divAddSchedule").hide();
		$("#calendar").show();
    });

    $('#txtStartDt').datetimepicker();
    $('#txtEndDt').datetimepicker();

    var rangeFormat = "YYYYMMDDHHmm";

    function showPrevMonth() {
    	$('#calendar').fullCalendar("prev");

    	currentMoment = currentMoment.subtract(1, "month");

    	getMonthlySchedules();
    }

    function showNextMonth() {
    	$('#calendar').fullCalendar("next");

    	currentMoment = currentMoment.add(1, "month");

    	getMonthlySchedules();
    }

    function getMonthlySchedules() {
    	range.start = currentMoment.startOf("month").format(rangeFormat);
    	range.end = currentMoment.endOf("month").format(rangeFormat);

    	getSchedules(range.start, range.end);
    }

    function getSchedules(startDt, endDt) {
	    jQuery.ajax({
	    	url: "/api/schedule/list",
	    	method: "GET",
	    	data: {
	    		startDt: startDt,
	    		endDt: endDt
	    	}
	    }).done(function(result) {
	    	  for (var i=0;i<result.length;i++) {
	    		  var event = {};

	    		  event.title = result[i].title;
	    		  event.start = moment(result[i].startDt, rangeFormat).format("YYYY-MM-DDTHH:mm");
	    		  event.end = moment(result[i].endDt, rangeFormat).format("YYYY-MM-DDTHH:mm");

	    		  $('#calendar').fullCalendar("renderEvent", event);
	    	  }
	    }).fail(function() {
			alert("사용자가 폭주하여 잠시 후 사용해주세요.");
	    });
    }

    getSchedules(range.start, range.end);
});
</script>
</body>
</html>