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
</style>
</head>
<body>

	<header class="hanbit-header">
		<div id="btnMenu" class="hanbit-top-button">
			<i class="material-icons hanbit-abs-center">menu</i>
		</div>
		<div id="btnGroupRight">
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
	</header>

	<div class="hanbit-container">
		<div id="calendar"></div>
	</div>

<script src="/static/plugins/jquery/jquery-3.1.0.min.js"></script>
<script src="/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/plugins/momentjs/moment.min.js"></script>
<script src="/static/plugins/fullcalendar/fullcalendar.min.js"></script>
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
	    }).done(function(data) {
	    	  for (var i=0;i<data.length;i++) {
	    		  var event = {};

	    		  event.title = data[i].title;
	    		  event.start = moment(data[i].startDt, rangeFormat).format("YYYY-MM-DDTHH:mm");
	    		  event.end = moment(data[i].endDt, rangeFormat).format("YYYY-MM-DDTHH:mm");

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