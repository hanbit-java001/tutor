$(document).ready(function() {
	var isMobile = navigator.userAgent.indexOf("Mobile") > -1;
	var customViews = ["add", "detail", "modify"];

    var dbFormat = "YYYYMMDDHHmm";
    var viewFormat = "YYYY-MM-DD a HH:mm";
    var currentMoment = moment();

    var range = {
       	start: moment().startOf("month").format(dbFormat),
       	end: moment().endOf("month").format(dbFormat)
    };

	var currentView = "month";
	var viewHistory = [{
		viewName: "month",
		viewData: null
	}];
	var eventQueue = [];

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
        		removeSchedule(calEvent.id);
        	}
        	else {
        		var scheduleId = calEvent.id;

            	getSchedule(scheduleId, function(schedule) {
            		changeView("detail", {schedule: schedule});
            	});
        	}
        }
    });

    function removeSchedule(scheduleId) {
		$.ajax({
			url: "/api/schedule/" + scheduleId,
			method: "DELETE"
		}).done(function(result) {
			if (result.countRemove > 0) {
				if (currentView == "detail") {
					backwardView();
				}

				$("#calendar").fullCalendar("removeEvents", scheduleId);
			}
		});
    }

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

	if (isMobile) {
		$("#txtStartDt").attr("readonly", "readonly");
		$("#txtEndDt").attr("readonly", "readonly");
	}

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

    $(".btnUpdate").on("click", function() {
    	var scheduleId = $(this).attr("scheduleId");

    	getSchedule(scheduleId, function(schedule) {
    		changeView("modify", {schedule: schedule});
    	});
    });

    function getSchedule(scheduleId, callback) {
		$.ajax({
			url: "/api/schedule/" + scheduleId,
			method: "GET"
		}).done(function(result) {
			callback(result);
		});
    }

    $(".btnDelete").on("click", function() {
    	var scheduleId = $(this).attr("scheduleId");

    	removeSchedule(scheduleId);
    });

    function handleDayClick(date) {
    	$.ajax({
    		url: "/api/schedule/countSchedule",
    		method: "GET",
    		data: {
    			startDt: date.startOf("day").format(dbFormat),
    			endDt: date.endOf("day").format(dbFormat)
    		}
    	}).done(function(result) {
    		if (result.eventCount > 0) {
    			changeView("listDay", {date: date});
    		}
    		else {
    			changeView("add", {date: date});
    		}
    	});
    }

    function backwardView(schedule) {
    	viewHistory = viewHistory.slice(0, viewHistory.length-1);

    	var viewName = viewHistory[viewHistory.length-1].viewName;
    	var viewData = viewHistory[viewHistory.length-1].viewData;

    	if (schedule) {
    		viewData = {
    			schedule: schedule
    		};
    	}

    	changeView(viewName, viewData, true);
    }

    function changeView(viewName, data, isBack) {
    	if (!isBack) {
    		if (data && data.date) {
    			data.date = moment(data.date);
    		}

    		viewHistory.push({
    			viewName: viewName,
    			viewData: data
    		});
    	}

    	currentView = viewName;

    	var date;

		if (data && data.date) {
			date = data.date;

			currentMoment = moment(date);
	    	$("#calendar").fullCalendar("gotoDate", date);
		}

		hideAddSchedule();
		hideDetailSchedule();
		hideModifySchedule();

		if (viewName == "add") {
    		showAddSchedule(date);
    	}
    	else if (viewName == "detail") {
    		var schedule = data.schedule;

    		showDetailSchedule(schedule);
    	}
    	else if (viewName == "modify") {
    		var schedule = data.schedule;

    		showModifySchedule(schedule);
    	}

    	// 좌측 상단 메뉴 or 뒤로가기
    	if (viewHistory.length == 1) {
			$("#btnGroupSub").hide();
			$("#btnGroupMain").show();
		}
		else {
			$("#btnGroupMain").hide();
			$("#btnGroupSub").show();
		}

    	// Custom View 체크
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
		$("#btnGroupUpdateSchedule").show();

		$("#calendar").hide();
		$("#divUpdateSchedule").show();

		defaultDate.set("hour", moment().get("hour"));
		defaultDate.set("minute", moment().get("minute"));

		$("#txtScheduleId").val("");
		$("#txtTitle").val("");
		$("#txtStartDt").data("DateTimePicker").date(defaultDate.subtract(0, "minutes"));
		$("#txtEndDt").data("DateTimePicker").date(defaultDate.add(1, "hours"));
		$("#txtMemo").val("");
    }

    function showDetailSchedule(schedule) {
    	$("#btnGroupCalendar").hide();

    	$("#calendar").hide();
    	$("#divDetailSchedule").show();

    	var event = {
    		id: schedule.scheduleId,
    		title: schedule.title,
    		start: moment(schedule.startDt, dbFormat).format("YYYY-MM-DD a HH:mm"),
    		end: moment(schedule.endDt, dbFormat).format("YYYY-MM-DD a HH:mm"),
    		memo: newLineToBr(schedule.memo)
    	};

    	$("#detailTitle").html(event.title);
    	$("#detailStartDt").html(event.start);
    	$("#detailEndDt").html(event.end);
    	$("#detailMemo").html(event.memo);
    	$(".btnUpdate").attr("scheduleId", event.id);
    	$(".btnDelete").attr("scheduleId", event.id);
    }

    function showModifySchedule(schedule) {
    	$("#btnGroupCalendar").hide();
    	$("#btnGroupUpdateSchedule").show();

		$("#calendar").hide();
		$("#divUpdateSchedule").show();

		var startDt = moment(schedule.startDt, dbFormat);
		var endDt = moment(schedule.endDt, dbFormat);

		$("#txtScheduleId").val(schedule.scheduleId);
		$("#txtTitle").val(schedule.title);
		$("#txtStartDt").data("DateTimePicker").date(startDt);
		$("#txtEndDt").data("DateTimePicker").date(endDt);
		$("#txtMemo").val(schedule.memo);
    }

    function newLineToBr(str) {
    	if (str === undefined || str == null) {
    		return "";
    	}

    	str = str.replace(/\n/g, "<br>");

    	return str;
    }

    function showFullCalendar() {
    	$("#calendar").show();

    	for (var i=0;i<eventQueue.length;i++) {
    		var event = eventQueue[i];

    		$("#calendar").fullCalendar("removeEvents", event.id);
    		$("#calendar").fullCalendar("renderEvent", event, true);
    	}

    	eventQueue = [];
    }

    function hideAddSchedule() {
		$("#btnGroupUpdateSchedule").hide();
		$("#btnGroupCalendar").show();

		$("#divUpdateSchedule").hide();
		showFullCalendar();
    }

    function hideDetailSchedule() {
		$("#btnGroupUpdateSchedule").hide();
		$("#btnGroupCalendar").show();

		$("#divDetailSchedule").hide();
		showFullCalendar();
    }

    function hideModifySchedule() {
		$("#btnGroupCalendar").show();

		$("#divUpdateSchedule").hide();
		showFullCalendar();
    }

    function applySchedule() {
		var scheduleId = $("#txtScheduleId").val();
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
			startDt: startDt.format(dbFormat),
			endDt: endDt.format(dbFormat),
			memo: memo
		};

    	var url;

    	if (currentView == "add") {
    		url = "/api/schedule/add";
    	}
    	else {
    		schedule.scheduleId = scheduleId;

    		url = "/api/schedule/modify";
    	}

		$.ajax({
			url: url,
			method: "POST",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			data: JSON.stringify(schedule)
		}).done(function(result) {
			var viewName = currentView;

			backwardView(result);

			applyScheduleToCalendar(result, viewName);
		}).fail(function() {
			alert("사용자가 폭주하여 잠시 후 사용해주세요.");
		});
    }

    function applyScheduleToCalendar(originEvent, applyType) {
		  var event = {};

		  event.id = originEvent.scheduleId;
		  event.title = originEvent.title;
		  event.start = moment(originEvent.startDt, dbFormat).format("YYYY-MM-DDTHH:mm");
		  event.end = moment(originEvent.endDt, dbFormat).format("YYYY-MM-DDTHH:mm");

		  if (applyType == "add") {
			  $("#calendar").fullCalendar("renderEvent", event, true);
		  }
		  else {
			  eventQueue.push(event);
		  }
	}

    $("#btnAddSchedule").on("click", function() {
    	changeView("add", {date: currentMoment});
    });

    $(".btnApplySchedule").on("click", function() {
    	applySchedule();
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

    	range.start = rangeMoment.startOf("month").format(dbFormat);
    	range.end = rangeMoment.endOf("month").format(dbFormat);

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
	    		  applyScheduleToCalendar(result[i], "add");
	    	  }
	    }).fail(function() {
			alert("사용자가 폭주하여 잠시 후 사용해주세요.");
	    });
    }

    getMonthlySchedules();
});
