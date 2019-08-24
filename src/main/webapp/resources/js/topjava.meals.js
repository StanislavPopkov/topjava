$(function () {
    makeEditable({
            ajaxUrl: "ajax/meal/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
        }
    );
});
let context2;
function filterMeals() {
    $.ajax({
        url: 'ajax/meal/filter?startDate='+$('#startDate').attr("value")+'&startTime='+$('#startTime').attr("value")+'&endDate='+$('#endDate').attr("value")+ '&endTime='+$('#endTime').attr("value"),
    }).done(function (data) {
        context.datatableApi.clear().rows.add(data).draw();
        successNoty("Filtered");
    });
}

function clearForm(){
    $('#startDate').val("");
    $('#startTime').val("");
    $('#endDate').val("");
    $('#endTime').val("");
}