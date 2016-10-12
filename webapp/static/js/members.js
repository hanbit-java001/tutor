$(function() {
	function addMember(name, email, profileUrl) {
		var memberHTML = "";
		memberHTML += "<div class='member-box'>";
		memberHTML += "<div class='member-name member-box-center' ";
		memberHTML += "style='background-image:";
		memberHTML += "url(" + profileUrl + ");'>";
		memberHTML += name;
		memberHTML += "</div>";
		memberHTML += "<div class='member-email member-box-center'>";
		memberHTML += email;
		memberHTML += "</div>";
		memberHTML += "</div>";

		$(".member-container").append(memberHTML);
	}

	function getMembers(pageNumber) {
		$.ajax({
			url: "/api/member/list",
			method: "POST",
			data: {
				page: pageNumber
			}
		}).done(function(members) {
			$(".member-container").empty();

			for (var i=0;i<members.length;i++) {
				var member = members[i];

				var name = member.name;
				var email = member.email;

				var profileUrl = "";

				if (member.profileFileId !== undefined && member.profileFileId != null) {
					profileUrl = "/file/" + member.profileFileId;
				}

				addMember(name, email, profileUrl);
			}
		});
	}

	$(".member-paging-number").on("click", function() {
		var pageNumber = $(this).text();

		currentPage = Number(pageNumber);

		getMembers(pageNumber);
	});

	var pagingRange = 5;
	var currentPage = 1;

	getMembers(currentPage);
});