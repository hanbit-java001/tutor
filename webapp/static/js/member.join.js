$(function() {
	$(".btnApply").on("click", function() {
		var name = $("#txtName").val().trim();
		var email = $("#txtEmail").val().trim();
		var password = $("#txtPassword").val().trim();
		var passwordConfirm = $("#txtPasswordConfirm").val().trim();
		var imgProfile = $("#imgProfile").val();

		if (name == "") {
			alert("이름을 입력하세요.");
			$("#txtName").focus();
			return;
		}
		else if (email == "") {
			alert("이메일을 입력하세요.");
			$("#txtEmail").focus();
			return;
		}
		else if (password == "") {
			alert("비밀번호를 입력하세요.");
			$("#txtPassword").focus();
			return;
		}
		else if (password != passwordConfirm) {
			alert("비밀번호확인을 동일하게 입력하세요.");
			$("#txtPasswordConfirm").focus();
			return;
		}

		// send Data to server
	});
});