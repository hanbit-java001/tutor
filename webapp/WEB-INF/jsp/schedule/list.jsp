<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>스케줄러</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
<style type="text/css">
#txtYearMonth {
	font-size: 20px;
	font-weight: bold;
	margin-left: 10px;
	margin-right: 10px;
}

#monthlySchedule {
	width: 100%;
}

#monthlySchedule th {
	height: 25px;
	text-align: center;
}

#monthlySchedule td {
	height: 80px;
}

#navMonth {
	text-align: center;
	padding: 10px;
}

#controlButtons {
	text-align: right;
}

#calendar {
	padding: 10px;
}
</style>
</head>
<body>
	<div id="calendar">
		<div id="navMonth">
			<button id="btnPrevMonth" class="btn btn-primary"><i class="fa fa-chevron-left"></i> 지난달</button>
			<span id="txtYearMonth"></span>
			<button id="btnNextMonth" class="btn btn-primary">다음달 <i class="fa fa-chevron-right"></i></button>
		</div>
		<div>
			<table id="monthlySchedule" class="table table-bordered">
				<thead>
					<tr>
						<th class="sunday">일</th>
						<th>월</th>
						<th>화</th>
						<th>수</th>
						<th>목</th>
						<th>금</th>
						<th day="sat">토</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<div id="controlButtons">
			<button class="btn btn-success">일정추가</button>
		</div>
	</div>
<script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="/static/plugins/momentjs/moment.min.js"></script>
<script type="text/javascript">

var current = moment();

function makeCalendar(year, month) {
	var calendarTitle = $("#txtYearMonth");
	calendarTitle.html(year + "년 " + month + "월");

	var calendarBody = $("#monthlySchedule tbody");

	var date = moment(year + "-" + month + "-01");
	var firstDay = date.day();

	var day = 1;
	var days = "";
	var isFindFirstDay = false;
	var isEndOfMonth = false;

	while (!isEndOfMonth) {
		days += "<tr>";

		for (var d=0;d<7;d++) {
			var dateString = "";

			if (isEndOfMonth) {
				dateString = "";
			}
			else if (!isFindFirstDay) {
				if (firstDay == d) {
					dateString = day;
					isFindFirstDay = true;
				}
			}
			else {
				dateString = ++day;
			}

			days += "<td>" + dateString + "</td>";

			if (day == date.endOf("month").get("date")) {
				isEndOfMonth = true;
			}
		}

		days += "</tr>";
	}

	calendarBody.html(days);
}

makeCalendar(current.get("year"), current.get("month") + 1);

$("#btnPrevMonth").on("click", function() {
	var prev = current.subtract(1, "month");

	makeCalendar(prev.get("year"), prev.get("month") + 1);
});

$("#btnNextMonth").on("click", function() {
	var prev = current.add(1, "month");

	makeCalendar(prev.get("year"), prev.get("month") + 1);
});

</script>
</body>
</html>