function acyncMovePage(url){
    console.log(url);
    // var page = $(this).attr('id').val();
    // ajax option
    var ajaxOption = {
        url : url,
        async : true,
        type : "POST",
        dataType : "html",
    };
    $.ajax(ajaxOption).done(function(data){
        // Contents 영역 삭제
        $('#body').children().remove();
        // Contents 영역 교체
        $('#body').html(data);
    });
}