<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>스케줄러</title>
<link rel="stylesheet" href="/static/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="/static/plugins/fullcalendar/fullcalendar.min.css" />
<style type="text/css">
</style>
</head>
<body>

	<div id="calendar"></div>
	<button id="prevMonth">이전달</button>
	<button id="nextMonth">다음달</button>

<script src="/static/plugins/jquery/jquery-3.1.0.min.js"></script>
<script src="/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/plugins/momentjs/moment.min.js"></script>
<script src="/static/plugins/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {

    // page is now ready, initialize the calendar...

    $('#calendar').fullCalendar({
    	header: {
    	    left:   '',
    	    center: 'title',
    	    right:  'prevYear,prev,today,next,nextYear'
    	},

    	dayClick: function(date) {
    		alert(date.format('MMMM Do YYYY, h:mm:ss a'));
        }
    });

    $("#prevMonth").on("click", function() {
    	$('#calendar').fullCalendar('prev');
    });

    $("#nextMonth").on("click", function() {
    	$('#calendar').fullCalendar('next');
    });

    jQuery.ajax({
    	url: "/api/schedule/list?startDt=20160901&endDt=20160930"
    }).done(function(data) {
    	  for (var i=0;i<data.length;i++) {
    		  var event = {};

    		  event.title = data[i].title;
    		  event.start = moment(data[i].startDt, "YYYYMMDDHHmm").format("YYYY-MM-DDTHH:mm");
    		  event.end = moment(data[i].endDt, "YYYYMMDDHHmm").format("YYYY-MM-DDTHH:mm");

    		  $('#calendar').fullCalendar("renderEvent", event, true);
    	  }
    });

});
</script>
</body>
</html>