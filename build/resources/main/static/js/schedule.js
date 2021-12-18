		function addScheduleContentButton() {
			var addScheduleForm = document.addScheduleContent;
			if (addScheduleForm.scheduleContentDate.value == ""
					|| addScheduleForm.scheduleContentDate.value == null) {
				alert("날짜를 선택해주세요.");
				addScheduleForm.scheduleContentDate.focus();
				return false;
			} else if (addScheduleForm.scheduleContentSubject.value == ""
					|| addScheduleForm.scheduleContentSubject.value == null) {
				alert("제목을 입력해주세요.");
				addScheduleForm.scheduleContentSubject.focus();
				return false;
			}else if (addScheduleForm.scheduleContentCost.value == ""
					|| addScheduleForm.scheduleContentCost.value == null) {
				alert("금액을 입력해주세요.");
				addScheduleForm.scheduleContentCost.focus();
				return false;
			}
			addScheduleForm.submit();
		}
		
		function deleteScheduleContentButton(){
			var deleteScheduleContentForm = document.deleteScheduleContent;
			deleteScheduleContentForm.submit();
		}
		
		function modifyScheduleContentButton(){
			var modifyScheduleContentForm = document.modifyScheduleContent;
			if (modifyScheduleContentForm.modifyScheduleContentSubject.value == ""
					|| modifyScheduleContentForm.modifyScheduleContentSubject.value == null) {
				alert("제목을 입력해주세요.");
				modifyScheduleContentForm.modifyScheduleContentSubject.focus();
				return false;
		}
			
			modifyScheduleContentForm.submit();
		}
		function showScheduleContentList(scheduleContentNO, scheduleContentSubject,scheduleContentDate,scheduleContentDesc,scheduleContentCost){
			var query = {scheduleContentDate : scheduleContentDate, scheduleContentSubject : scheduleContentSubject, scheduleContentNO : scheduleContentNO, scheduleContentDesc : scheduleContentDesc,scheduleContentCost : scheduleContentCost};

			$.ajax({
			  	url : "showScheduleContentList",
			  	type : "post",
			  	data : query,
			  	success : function(data) {

				  	$("#scheduleContentSubjectMessage").text(scheduleContentSubject);
					$("#scheduleContentDescMessage").text(scheduleContentDesc);
			    	$("#scheduleContentCostMessage").text(scheduleContentCost+"원");
			    	$('#modifyScheduleContentSubject').val(scheduleContentSubject);
			    	$('#modifyScheduleContentDesc').val(scheduleContentDesc);
			    	$('#modifyScheduleContentDate').val(scheduleContentDate);
			    	$('#modifyScheduleContentCost').val(scheduleContentCost);

			  }
			 });

		}

		function addScheduleButton() {
			var addScheduleForm = document.addSchedule;
			if (addScheduleForm.scheduleSubject.value == "" || addScheduleForm.scheduleSubject.value == null) {
				alert("제목을 입력해주세요.");
				addScheduleForm.scheduleSubject.focus();
				return false;
				}
			addScheduleForm.submit();
		}
		
		function modifyScheduleButton() {
			var modifyScheduleForm = document.modifySchedule;
			if (modifyScheduleForm.scheduleSubject.value == "" || modifyScheduleForm.scheduleSubject.value == null) {
				alert("제목을 입력해주세요.");
				modifyScheduleForm.scheduleSubject.focus();
				return false;
				}
			modifyScheduleForm.submit();
		}
		function swapSchedule(){
			document.swapSchedule.submit();
		}

		function deleteScheduleButton(){
			if (confirm("정말 삭제하시겠습니까??") == true){    
			   location.replace('deleteSchedule');
			} else {   
			    return;
			}
		}





				
