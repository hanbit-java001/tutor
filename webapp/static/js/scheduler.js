$(document).ready(function() {
	var customViews = ["add"];

	var currentView = "month";
	var viewHistory = ["month"];

	var loadedMonths = [];

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
        },

        eventAfterAllRender: function(view) {
        	if (view.type == "listDay") {
    			var deleteButton = "<button class='delete-schedule btn btn-danger'>삭제</button>";

    			$(".fc-list-item-title").append(deleteButton);
        	}
        },

        eventClick: function(calEvent, jsEvent, view) {
        	var jqTarget = $(jsEvent.target);

        	if (jqTarget.hasClass("delete-schedule")) {
        		$.ajax({
        			url: "/api/schedule/" + calEvent.id,
        			method: "DELETE"
        		}).done(function(result) {
        			if (result.countRemove > 0) {
        				$("#calendar").fullCalendar("removeEvents", calEvent.id);
        			}
        		});
        	}
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
       	start: moment().startOf("month").format(rangeFormat),
       	end: moment().endOf("month").format(rangeFormat)
    };

    $(".btnBack").on("click", function() {
    	backwardView();
    });

    $("#btnPrev").on("click", function() {
    	showPrev();
    });

    $("#btnNext").on("click", function() {
    	showNext();
    });

    $("#btnToday").on("click", function() {
    	showToday();
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
    			changeView("add", date);
    		}
    	});
    }

    function backwardView() {
    	viewHistory = viewHistory.slice(0, viewHistory.length-1);

    	var viewName = viewHistory[viewHistory.length-1];

    	changeView(viewName, null, true);
    }

    function changeView(viewName, date, isBack) {
    	if (!isBack) {
    		viewHistory.push(viewName);
    	}

    	currentView = viewName;

		if (date && date != null) {
			currentMoment = moment(date);
	    	$("#calendar").fullCalendar("gotoDate", date);
		}

    	if (viewName == "add") {
    		showAddSchedule(date);
    	}
    	else {
    		hideAddSchedule();
    	}

    	if (viewHistory.length == 1) {
			$("#btnGroupSub").hide();
			$("#btnGroupMain").show();
		}
		else {
			$("#btnGroupMain").hide();
			$("#btnGroupSub").show();
		}

		if (customViews.indexOf(viewName) > -1) {
			return;
		}

		if ($("#calendar").fullCalendar("getView").name == viewName) {
			return;
		}

    	$("#calendar").fullCalendar("changeView", viewName);
    }

    function showAddSchedule(date) {
    	var defaultDate = moment(date);

		$("#btnGroupCalendar").hide();
		$("#btnGroupAddSchedule").show();

		$("#calendar").hide();
		$("#divAddSchedule").show();

		defaultDate.set("hour", moment().get("hour"));
		defaultDate.set("minute", moment().get("minute"));

		$("#txtTitle").val("");
		$("#txtStartDt").data("DateTimePicker").date(defaultDate.subtract(0, "minutes"));
		$("#txtEndDt").data("DateTimePicker").date(defaultDate.add(1, "hours"));
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

		  event.id = originEvent.scheduleId;
		  event.title = originEvent.title;
		  event.start = moment(originEvent.startDt, rangeFormat).format("YYYY-MM-DDTHH:mm");
		  event.end = moment(originEvent.endDt, rangeFormat).format("YYYY-MM-DDTHH:mm");

		  $("#calendar").fullCalendar("renderEvent", event, true);
	}

    $("#btnAddSchedule").on("click", function() {
    	changeView("add", currentMoment);
    });

    $(".btnApplyAddSchedule").on("click", function() {
    	addSchedule();
    });

    $(".btnCancelAddSchedule").on("click", function() {
    	hideAddSchedule();
    });

    function showToday() {
    	if (currentView == "month"
    		&& currentMoment.format("YYYYMM") == moment().format("YYYYMM")) {
    		return;
    	}

    	$("#calendar").fullCalendar("today");

    	currentMoment = moment();

    	getMonthlySchedules();
    }

    function showPrev() {
    	$("#calendar").fullCalendar("prev");

    	if (currentView == "month") {
    		currentMoment.subtract(1, "months");
    	}
    	else if (currentView == "listDay") {
    		currentMoment.subtract(1, "days");
    	}

		getMonthlySchedules();
    }

    function showNext() {
    	$("#calendar").fullCalendar("next");

    	if (currentView == "month") {
    		currentMoment.add(1, "months");
    	}
    	else if (currentView == "listDay") {
    		currentMoment.add(1, "days");
    	}

		getMonthlySchedules();
    }

    function getMonthlySchedules() {
    	var currentMonth = currentMoment.format("YYYYMM");

    	if (loadedMonths.indexOf(currentMonth) > -1) {
    		return;
    	}

    	var rangeMoment = moment(currentMoment);

    	range.start = rangeMoment.startOf("month").format(rangeFormat);
    	range.end = rangeMoment.endOf("month").format(rangeFormat);

    	getSchedules(range.start, range.end);

    	loadedMonths.push(currentMonth);
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
