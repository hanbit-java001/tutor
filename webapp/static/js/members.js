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
		}).done(function(pagingMembers) {
			$(".member-container").empty();

			for (var i=0;i<pagingMembers.members.length;i++) {
				var member = pagingMembers.members[i];

				var name = member.name;
				var email = member.email;

				var profileUrl = "";

				if (member.profileFileId !== undefined && member.profileFileId != null) {
					profileUrl = "/file/" + member.profileFileId;
				}

				addMember(name, email, profileUrl);
			}

			var totalCount = pagingMembers.totalCount;

			drawPaging(totalCount);
		});
	}

	function drawPaging(totalCount) {
		firstPage = parseInt((currentPage - 1) / pagingRange) * pagingRange + 1;
		lastPage = firstPage + pagingRange - 1;
		totalPages = parseInt(totalCount / itemsPerPage)
			+ (totalCount % itemsPerPage > 0 ? 1 : 0);

		$(".member-paging").empty();

		var pagingNumberHTML = "<div class='member-paging-number'>";
		pagingNumberHTML += "이전";
		pagingNumberHTML += "</div>";

		$(".member-paging").append(pagingNumberHTML);

		for (var i=firstPage;i<=lastPage;i++) {
			if (i > totalPages) {
				break;
			}

			pagingNumberHTML = "<div class='member-paging-number";

			if (i == currentPage) {
				pagingNumberHTML += " current-page";
			}

			pagingNumberHTML += "'>";
			pagingNumberHTML += i;
			pagingNumberHTML += "</div>";

			$(".member-paging").append(pagingNumberHTML);
		}

		pagingNumberHTML = "<div class='member-paging-number'>";
		pagingNumberHTML += "다음";
		pagingNumberHTML += "</div>";

		$(".member-paging").append(pagingNumberHTML);

		$(".member-paging-number").on("click", function() {
			var pageText = $(this).text();
			var pageNumber = 0;

			if (pageText == "이전") {
				pageNumber = firstPage - 1;

				if (pageNumber < 1) {
					return;
				}
			}
			else if (pageText == "다음") {
				pageNumber = lastPage + 1;

				if (pageNumber > totalPages) {
					return;
				}
			}
			else {
				pageNumber = Number(pageText);
			}

			currentPage = pageNumber;

			getMembers(pageNumber);
		});
	}

	var itemsPerPage = 3;
	var pagingRange = 5;
	var currentPage = 1;
	var firstPage;
	var lastPage;
	var totalPages;

	getMembers(currentPage);
});