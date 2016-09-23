$(document).ready(function() {

    $("#calendar").fullCalendar({
    	timezone: "Korea/Seoul",
    	locale: "ko",
    	height: "auto",
    	eventLimit: 4,

    	header: {
    	    left:   "",
    	    center: "title",
    	    right:  ""
    	},

    	dayClick: function(date) {
    		handleDayClick(date);
        }
    });

    $("#txtStartDt").datetimepicker({
    	locale: "ko",
    	format: "YYYY-MM-DD a hh:mm",
    	stepping: 15,
    	ignoreReadonly: true,
    	showClose: true
    });

    $("#txtEndDt").datetimepicker({
    	locale: "ko",
    	format: "YYYY-MM-DD a hh:mm",
    	stepping: 15,
    	ignoreReadonly: true,
    	showClose: true
    });

	if (navigator.userAgent.indexOf("Mobile") > -1) {
		$("#txtStartDt").attr("readonly", "readonly");
		$("#txtEndDt").attr("readonly", "readonly");
	}

    var rangeFormat = "YYYYMMDDHHmm";
    var currentMoment = moment();

    var range = {
       	start: currentMoment.startOf("month").format(rangeFormat),
       	end: currentMoment.endOf("month").format(rangeFormat)
    };

    $("#btnBack").on("click", function() {
    	changeView("month");
    });

    $("#btnPrevMonth").on("click", function() {
    	showPrevMonth();
    });

    $("#btnNextMonth").on("click", function() {
    	showNextMonth();
    });

    $("#btnToday").on("click", function() {
    	showThisMonth();
    });

    function handleDayClick(date) {
    	$.ajax({
    		url: "/api/schedule/countSchedule",
    		method: "GET",
    		data: {
    			startDt: date.startOf("day").format(rangeFormat),
    			endDt: date.endOf("day").format(rangeFormat)
    		}
    	}).done(function(result) {
    		if (result.eventCount > 0) {
    			changeView("listDay", date);
    		}
    		else {
    			showAddSchedule(date);
    		}
    	});
    }

    function changeView(viewName, date) {
    	$("#calendar").fullCalendar("gotoDate", date);

		if (viewName == "month") {
			$("#btnGroupSub").hide();
			$("#btnGroupMain").show();
		}
		else {
			$("#btnGroupMain").hide();
			$("#btnGroupSub").show();
		}

    	$("#calendar").fullCalendar("changeView", viewName);

		if (viewName == "listDay") {
			var deleteButton = "<button class='delete-schedule btn btn-danger'>삭제</button>";

			$(".fc-list-item-title").append(deleteButton);
		}
    }

    function showAddSchedule(date) {
		$("#btnGroupCalendar").hide();
		$("#btnGroupAddSchedule").show();

		$("#calendar").hide();
		$("#divAddSchedule").show();

		var defaultDate = date.set("hour", moment().get("hour")).startOf("hour");

		$("#txtTitle").val("");
		$("#txtStartDt").data("DateTimePicker").date(defaultDate);
		$("#txtEndDt").data("DateTimePicker").date(defaultDate.add(1, "hour"));
		$("#txtMemo").val("");
    }

    function hideAddSchedule() {
		$("#btnGroupAddSchedule").hide();
		$("#btnGroupCalendar").show();

		$("#divAddSchedule").hide();
		$("#calendar").show();
    }

    function addSchedule() {
		var title = $("#txtTitle").val();
		var startDt = $("#txtStartDt").val();
		var endDt = $("#txtEndDt").val();
		var memo = $("#txtMemo").val();

		if (title.trim() == "") {
			alert("제목을 입력하세요.");
			$("#txtTitle").val("");
			$("#txtTitle").focus();
			return;
		}
		else if (startDt.trim() == "") {
			alert("시작일시를 입력하세요.");
			$("#txtStartDt").val("");
			$("#txtStartDt").focus();
			return;
		}
		else if (endDt.trim() == "") {
			alert("종료일시를 입력하세요.");
			$("#txtEndDt").val("");
			$("#txtEndDt").focus();
			return;
		}

		startDt = $("#txtStartDt").data("DateTimePicker").date();
		endDt = $("#txtEndDt").data("DateTimePicker").date();

		if (startDt.isAfter(endDt)) {
			alert("시작일시는 종료일시보다 이전 일시여야 합니다.");
			return;
		}

    	var schedule = {
			title: title,
			startDt: startDt.format(rangeFormat),
			endDt: endDt.format(rangeFormat),
			memo: memo
		};

		$.ajax({
			url: "/api/schedule/add",
			method: "POST",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			data: JSON.stringify(schedule)
		}).done(function(result) {
			$("#btnGroupAddSchedule").hide();
			$("#btnGroupCalendar").show();

			$("#divAddSchedule").hide();
			$("#calendar").show();

			addScheduleToCalendar(result);
		}).fail(function() {
			alert("사용자가 폭주하여 잠시 후 사용해주세요.");
		});
    }

    function addScheduleToCalendar(originEvent) {
		  var event = {};

		  event.title = originEvent.title;
		  event.start = moment(originEvent.startDt, rangeFormat).format("YYYY-MM-DDTHH:mm");
		  event.end = moment(originEvent.endDt, rangeFormat).format("YYYY-MM-DDTHH:mm");

		  $("#calendar").fullCalendar("renderEvent", event);
	}

    $("#btnAddSchedule").on("click", function() {
    	showAddSchedule(moment());
    });

    $(".btnApplyAddSchedule").on("click", function() {
    	addSchedule();
    });

    $(".btnCancelAddSchedule").on("click", function() {
    	hideAddSchedule();
    });

    function showThisMonth() {
    	if (currentMoment.format("YYYYMM") == moment().format("YYYYMM")) {
    		return;
    	}

    	$("#calendar").fullCalendar("today");

    	currentMoment = moment();

    	getMonthlySchedules();
    }

    function showPrevMonth() {
    	$("#calendar").fullCalendar("prev");

    	currentMoment = currentMoment.subtract(1, "month");

    	getMonthlySchedules();
    }

    function showNextMonth() {
    	$("#calendar").fullCalendar("next");

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
	    		  addScheduleToCalendar(result[i]);
	    	  }
	    }).fail(function() {
			alert("사용자가 폭주하여 잠시 후 사용해주세요.");
	    });
    }

    getMonthlySchedules();
});
