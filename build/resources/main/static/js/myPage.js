// 회원 정보 수정 중복 확인.
$(".checkNickName").click(function () {
    var query = {userNickname: $("#userNickname").val()};
    $.ajax({
        url: "checkNickName",
        type: "post",
        data: query,
        success: function (data) {
            if (data == 1) {
                $(".nickNameResult .nickNameMsg").text("사용불가");
                $(".nickNameResult .nickNameMsg").attr("style", "color:#f00");
                $("#submit").attr("disabled", "disabled");
            } else {
                $(".nickNameResult .nickNameMsg").text("사용가능");
                $(".nickNameResult .nickNameMsg").attr("style", "color:#00f");

                $("#submit").removeAttr("disabled");
            }
        }
    });
});
$("#userNickname").keyup(function () {
    $(".nickNameResult .nickNameMsg").text("중복확인을 해주세요");
    $(".nickNameResult .nickNameMsg").attr("style", "color:#000");

    $("#submit").attr("disabled", "disabled");
});

//myPage myReviewPage에서 myReview 삭제 기능
function deleteMyReview(){
    var deleteMyReviewForm = document.getElementById("deleteMyReviewForm");
    var myReviewCheckbox = document.getElementsByName("myReviewCheckbox");
    var isChecked = false;

    for (var i=0; i<myReviewCheckbox.length; i++) {
        if (document.getElementsByName("myReviewCheckbox")[i].checked == true) {
            isChecked = true;
            break;
        }
    }

    if(isChecked){
        if(confirm("게시글을 삭제하시겠습니까?")){
            deleteMyReviewForm.submit();
        }
    }else{
        alert("삭제할 게시글을 선택해 주세요");
    }


}

function deleteMySchedule(){
    var deleteMyScheduleForm = document.getElementById("deleteMyScheduleForm");
    var myScheduleCheckbox = document.getElementsByName("myScheduleCheckbox");
    var isChecked = false;

    for (var i=0; i<myScheduleCheckbox.length; i++) {
        if (document.getElementsByName("myScheduleCheckbox")[i].checked == true) {
            isChecked = true;
            break;
        }
    }

    if(isChecked){
        if(confirm("게시글을 삭제하시겠습니까?")){
            deleteMyScheduleForm.submit();
        }
    }else{
        alert("삭제할 게시글을 선택해 주세요");
    }


}

function scheduleSelectAll(scheduleSelectAll)  {
    const checkboxes = document.getElementsByName('myScheduleCheckbox');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = scheduleSelectAll.checked;
    })
}

function reviewSelectAll(reviewSelectAll)  {
    const checkboxes = document.getElementsByName('myReviewCheckbox');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = reviewSelectAll.checked;
    })
}