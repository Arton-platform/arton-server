//form데이터 전송
function dataSubmit() {
    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    var data = new FormData();
    data.append('title', $("#title").val())
    data.append('description', $("#description").val())
    data.append('startDate', $("#startDate").val())
    data.append('endDate', $("#endDate").val())
    data.append('runningTime', $("#runningTime").val())
    data.append('interMission', $("#interMission").val())
    data.append('limitTime', $("#limitTime").val())
    data.append('link', $("#link").val())
    data.append('performanceType', $("#performanceType").val())
    data.append('place', $("#place").val())
    data.append('ticketOpenDate', $("#ticketOpenDate").val())
    data.append('ticketEndDate', $("#ticketEndDate").val())
    data.append('purchaseLimit', $("#purchaseLimit").val())
    data.append('showCategory', $("#showCategory").val())
    data.append('images', $("#img_upload"))
    // var data=new FormData($("#storeAddForm")[0]);

    $.ajax({
        url: '/web/performance/add',
        data: data,
        processData:false,
        contentType:false,
        enctype:'multipart/form-data',
        type:"POST"
    }).done(function() {
        alert('알림이 등록되었습니다.');
    }).fail(function (error) {
        alert(JSON.stringify(error) + '이에러인가');
    });
}
