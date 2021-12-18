$(document).ready(function(){
    $('#password-check-field').on('click',function(){
        $('input').toggleClass('active');
        if($('input').hasClass('active')){
            $(this).attr('class',"fa fa-fw fa-eye-slash field-icon")
                .prev('input').attr('type',"text");
        }else{
            $(this).attr('class',"fa fa-fw fa-eye field-icon")
                .prev('input').attr('type','password');
        }
    });
    $('#password-field').on('click',function(){
        $('input').toggleClass('active');
        if($('input').hasClass('active')){
            $(this).attr('class',"fa fa-fw fa-eye-slash field-icon")
                .prev('input').attr('type',"text");
        }else{
            $(this).attr('class',"fa fa-fw fa-eye field-icon")
                .prev('input').attr('type','password');
        }
    })
});
function email_change() {
    var emailSelected = $("#emailSelected option:selected");
    if (emailSelected.val() == "SELF") {
        $('#email_domain').attr("readonly", false);
        $('#email_domain').val('');
        $('#email_domain').focus();
    } else {
        $('#email_domain').attr("readonly", true);
        $("#email_domain").val(emailSelected.text());
    }
}