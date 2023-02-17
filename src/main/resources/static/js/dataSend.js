//form데이터 전송
function dataSubmit() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var data=new FormData($("#storeAddForm")[0]);

    $.ajax({
        beforeSend: function(xhr){
            xhr.setRequestHeader(header,token);
        },
        url: "url",
        data: data,
        processData:false,
        contentType:false,
        enctype:'multipart/form-data',
        type:"POST",
    }).done(function (fragment) {
        $("#resultDiv").replaceWith(fragment);
    });
}
