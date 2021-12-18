function addReviewReply(userNO) {
    if (userNO == "" || userNO == null) {
        alert("로그인 후 이용해주세요.");
        return false;
    }
    location.replace("/reviewWrite")
}


function addReviewButton (){
    var addReviewForm = document.getElementById("reviewForm");
    if (addReviewForm.reviewSubject.value == "" || addReviewForm.reviewSubject.value == null) {
        alert("제목을 입력해주세요.");
        addReviewForm.reviewSubject.focus();
        return false;
    }
    if (addReviewForm.reviewDesc.value == "" || addReviewForm.reviewDesc.value == null) {
        alert("내용을 입력해주세요.");
        addReviewForm.reviewDesc.focus();
        return false;
    }
    addReviewForm.submit();
}

function changeReviewCategory(sVal) {
    var val = document.getElementById("reviewCategoryDiv");
    if (sVal == "REVIEW_BOARD") {
        val.style.display = "block";
    } else if (sVal == "FREE_BOARD") {
        $("#disabledOption").val("").prop("selected", true);
        val.style.display = "none";
    }
}

function deleteReviewButton(reviewNO) {
    if (confirm("정말 삭제하시겠습니까??") == true){
        location.replace('deleteReview?reviewNO='+reviewNO);
    } else {
        return;
    }
}

function moveModifyReviewPage(reviewNO) {
    if (confirm("수정하시겠습니까??") == true){
        location.replace('reviewWrite?reviewNO='+reviewNO);
    } else {
        return;
    }
}

function modifyReviewButton (reviewNO){
    var modifyReviewForm = document.getElementById("reviewForm");
    if (modifyReviewForm.reviewSubject.value == "" || modifyReviewForm.reviewSubject.value == null) {
        alert("제목을 입력해주세요.");
        modifyReviewForm.reviewSubject.focus();
        return false;
    }
    if (modifyReviewForm.reviewDesc.value == "" || modifyReviewForm.reviewDesc.value == null) {
        alert("내용을 입력해주세요.");
        modifyReviewForm.reviewDesc.focus();
        return false;
    }
    var input1 = document.createElement('input');
    input1.setAttribute("type", "hidden");
    input1.setAttribute("name", "reviewNO");
    input1.setAttribute("value", reviewNO);

    modifyReviewForm.appendChild(input1);
    modifyReviewForm.action = "modifyReview";
    modifyReviewForm.submit();
}