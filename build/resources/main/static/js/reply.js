    function showReplyDiv(objectNO) {
        var replyDiv = document.getElementById("replyDiv"+objectNO);
        if(replyDiv.style.display == "block"){
            replyDiv.style.display = "none";
        } else {
            replyDiv.style.display = "block";
        }
    }

    function showModifyReplyDesc(objectNO){
        var modifyObjectForm = document.getElementById("modifyReplyDesc"+objectNO);
        var objectP = document.getElementById("ReplyDesc"+objectNO);
        if(modifyObjectForm.style.display == "none" || modifyObjectForm.style.display == ""){
            modifyObjectForm.style.display = "block";
            objectP.style.display = "none";
        } else {
            modifyObjectForm.style.display = "none";
            objectP.style.display = "block";
        }
    }

    function addScheduleReply(scheduleReplyNO) {
        var addScheduleReplyForm = document.getElementById("addScheduleReply"+scheduleReplyNO);
        var userNO = document.getElementById("userNO").value;
        if(userNO == "" || userNO == null){
            alert("로그인 후 이용해주세요.");
            addScheduleReplyForm.scheduleReplyDesc.focus();
            return false;
        } else if (addScheduleReplyForm.scheduleReplyDesc.value == "" || addScheduleReplyForm.scheduleReplyDesc.value == null) {
            alert("내용을 입력하여 주세요.");
            addScheduleReplyForm.scheduleReplyDesc.focus();
            return false;
        }
        addScheduleReplyForm.submit();
    }

    function modifyScheduleReply(scheduleReplyNO){
        var modifyScheduleReplyForm = document.getElementById("modifyScheduleReply"+scheduleReplyNO);
        if (modifyScheduleReplyForm.scheduleReplyDesc.value == "" || modifyScheduleReplyForm.scheduleReplyDesc.value == null) {
            alert("내용을 입력하여 주세요.");
            modifyScheduleReplyForm.scheduleReplyDesc.focus();
            return false;
        }
        modifyScheduleReplyForm.submit();
    }


    function deleteScheduleReplyButton(scheduleReplyNO){
        var scheduleNO = document.getElementById("selectedScheduleNO").value;
        if (confirm("정말 삭제하시겠습니까??") == true){
            location.replace('/deleteScheduleReply?scheduleReplyNO='+scheduleReplyNO+'&scheduleNO='+scheduleNO);
        } else {
            return;
        }
    }

    function updateScheduleLike() {
        var scheduleNO = document.getElementById("selectedScheduleNO").value;
        var userNO = document.getElementById("userNO").value;
        if(userNO == "" || userNO == null){
            alert("로그인 후 이용해주세요.");
            return;
        }
        $.ajax({
            url: "updateScheduleLike",
            type: "get",
            data: {scheduleNO: scheduleNO, scheduleLikeUserNO: userNO},
            success:
                function (data) {
                    if(data.likeStatus == 1){
                        $("#ScheduleLike").attr("class", "icon_heart dis-none");
                    } else if(data.likeStatus == 0){
                        $("#ScheduleLike").attr("class", "icon_heart_alt");
                    }
                    $("#likeCount").html("&nbsp;"+data.likeCount);

                },
            error:
                function (request, status, error) {
                    alert("ajax실패")
                }
        });
    }

    function updateReviewLike() {
        var reviewNO = document.getElementById("selectedReviewNO").value;
        var userNO = document.getElementById("userNO").value;
        if(userNO == "" || userNO == null){
            alert("로그인 후 이용해주세요.");
            return;
        }
        $.ajax({
            url: "updateReviewLike",
            type: "get",
            data: {reviewNO: reviewNO, reviewLikeUserNO: userNO},
            success:
                function (data) {
                    if(data.likeStatus == 1){
                        $("#like").attr('class','icon_heart dis-none');
                    } else if(data.likeStatus == 0){
                        $("#like").attr('class','icon_heart_alt');
                    }
                    $("#likeCount").html("&nbsp;"+data.likeCount);

                },
            error:
                function (request, status, error) {
                    alert("ajax실패")
                }
        });
    }

    function addReviewReply(reviewReplyNO) {
        var addReviewReplyForm = document.getElementById("addReviewReply"+reviewReplyNO);
        var userNO = document.getElementById("userNO").value;
        if(userNO == "" || userNO == null){
            alert("로그인 후 이용해주세요.");
            addReviewReplyForm.reviewReplyDesc.focus();
            return false;
        } else if (addReviewReplyForm.reviewReplyDesc.value == "" || addReviewReplyForm.reviewReplyDesc.value == null) {
            alert("내용을 입력하여 주세요.");
            addReviewReplyForm.reviewReplyDesc.focus();
            return false;
        }
        addReviewReplyForm.submit();
    }

    function modifyReviewReply(reviewReplyNO){
        var modifyReviewReplyForm = document.getElementById("modifyReviewReply"+reviewReplyNO);
        if (modifyReviewReplyForm.reviewReplyDesc.value == "" || modifyReviewReplyForm.reviewReplyDesc.value == null) {
            alert("내용을 입력하여 주세요.");
            modifyReviewReplyForm.reviewReplyDesc.focus();
            return false;
        }
        modifyReviewReplyForm.submit();
    }


    function deleteReviewReplyButton(reviewReplyNO){
        var reviewNO = document.getElementById("selectedReviewNO").value;
        if (confirm("정말 삭제하시겠습니까??") == true){
            location.replace('/deleteReviewReply?reviewReplyNO='+reviewReplyNO+'&reviewNO='+reviewNO);
        } else {
            return;
        }
    }
