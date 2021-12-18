function checkValue() {
    if (!document.SignInfo.agreeCheck.checked) {
        alert("동의에 체크하세요.");
        return false;
    }
};


function maxLengthCheck(object) {
    if (object.value.length > object.maxLength) {
        object.value = object.value.slice(0, object.maxLength);
    }
};

$(document).on("keyup", "input[engAndNumOnly]", function() { $(this).val($(this).val().replace(/[^a-zA-Z0-9]/gi, "")); })

$('#userEmail').blur(function() {
    var email_rule = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    var mail =$("#userEmail").val();

    if (!email_rule.test(mail)) {
        alert(mail);
        alert("이메일을 형식에 맞게 입력해주세요.");
        $("#submit").attr("disabled", "disabled");
        return false;
    }else{
        $("#submit").removeAttr("disabled");
    }
});

