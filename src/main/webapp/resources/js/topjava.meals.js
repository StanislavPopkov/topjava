const mealAjaxUrl = "ajax/profile/meals/";
function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function updateFilteredTableDateTimePicker() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter2").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

jQuery(function(){
    jQuery('#datepicker_start').datetimepicker({
        format:'Y-m-d',
        //03.05.2015 20:00 21.06.2015 20:00
        onShow:function( ct ){
            this.setOptions({
                maxDate:jQuery('#date_timepicker_end').val()?jQuery('#date_timepicker_end').val():false
            })
        },
        timepicker:false,

    });
    jQuery('#datepicker_end').datetimepicker({
        format:'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                minDate:jQuery('#date_timepicker_start').val()?jQuery('#date_timepicker_start').val():false
            })
        },
        timepicker:false,
    });
});

jQuery(function(){
    jQuery('#timepicker_start').datetimepicker({
        format:'H:i',
        //03.05.2015 20:00 21.06.2015 20:00
        datepicker:false,

    });
    jQuery('#timepicker_end').datetimepicker({
        format:'H:i',
        datepicker:false,
    });
});

$(function () {
    makeEditable({
        ajaxUrl: "ajax/profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": "ajax/profile/meals/",
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date) {
                        return date.replace("T", " ");
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if(data.excess) {
                    $(row).attr("data-mealExcess", true);
                }
                else {
                    $(row).attr("data-mealExcess", false);
                }
            }
        }),
        updateTable: function () {
            $.get(mealAjaxUrl, updateTableByData);
        }
    });
});