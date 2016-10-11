function callAjax(ajaxObj) {
	$.ajax({
		url: ajaxObj.url,
		method: ajaxObj.method,
		data: ajaxObj.data
	}).done(ajaxObj.success)
	.fail(function(jqXHR, textStatus, errorThrown) {
		var errorMsg = "잠시 후 사용해 주세요.";

		if (jqXHR.status == 1500) {
			var error = JSON.parse(jqXHR.responseText);
			errorMsg = error.errorMsg;
		}

		alert(errorMsg);
	});
}