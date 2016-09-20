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
</style>
</head>
<body>
	<div id="calendar">
		<div id="navMonth">
			<button id="btnPrevMonth" class="btn btn-primary"><i class="fa fa-chevron-left"></i> 지난달</button>
			9월
			<button id="btnNextMonth" class="btn btn-primary">다음달 <i class="fa fa-chevron-right"></i></button>
		</div>
		<div>
			<table id="monthlySchedule">
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
					<tr>
						<td class="sunday">1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td day="sat">7</td>
					</tr>
					<tr>
						<td class="sunday">1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td day="sat">7</td>
					</tr>
					<tr>
						<td class="sunday">1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td day="sat">7</td>
					</tr>
					<tr>
						<td class="sunday">1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td day="sat">7</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="controlButtons">
			<button>일정추가</button>
		</div>
	</div>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>