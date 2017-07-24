/**
 * Created by LYH on 2016/3/23.
 */
$(function(){
var $table = $('#table'),
    $remove = $('#remove'),
    $modify = $('#modify'),
    selections = [];
//表头自适应
$(window).resize(function () {
    $table.bootstrapTable('resetView');
});
$table.bootstrapTable({
//    url: "/../../../static/basecss/data/data2/demo.response.json",
    dataType: "json",//请求后台的URL（*）
    method: "get",//请求方式（*）
    striped: true,//是否显示行间隔色
    clickToSelect: true,//是否启用点击选中行
    height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
    pagination: true,//是否分页
    pageList: [10, 20,80],
    pageSize: 10,//一页显示多少条
    pageNumber: 1,//当前第几页
    showColumns: false,//表头右边小工具
    minimumCountColumns: 2,//最小只剩2个
    toolbar: "#toolbar",//把自定义工具放在表头容器
    showHeader: true,//是否显示表标题
    detailView: true,//细节视图
    idField: "id", //标识哪个字段为id主键
    uniqueId: "id",//为每一行显示一个惟一的标识符。
    //singleSelect:true,//复选只能能选择一行
    detailFormatter: "detailFormatter",//细节  视图返回的字符串
    formatter: "runningFormatter",//细节视图返回的字符串,
    //sidePagination:"server",
    footerFormatter: function (a, b) {
        console.log(a)
        console.log(b)
    },
   /* onClickRow: function (row, a, b) {//点击行的事件
        var $td = $(a).find("td");
        var $widthArray = [];
        for (var i = 0; i < $td.length; i++) {
            $widthArray.push($($td[i]).css("width"));
        }
        $(a).find("td:eq(1)").nextAll().text("");
        $(a).addClass("a").siblings().removeClass("a");
        for (var w = 2; w < $widthArray.length; w++) {
            (function (q) {
                $($td[w]).css("width", $widthArray[q]);
                $("<input>").addClass("form-control input-sm").appendTo($td[w]);
            })(w)
        }
    },*/
    //search: true,
    //sidePagination:'server',//设置为服务器端分页
    //queryParams: queryParams,//参数
    columns: [
        {
            checkbox: true


        },
        {
            field: 'id',
            title: 'Item ID'
        }, {
            field: 'name',
            title: 'Item Name'
        }, {
            field: 'price',
            title: 'Item Price'
        },
        {

            title: 'Item Price',
            //field: 'name',
            formatter: function (value, row, index) {
                //var e = '<a href="#" mce_href="#" onclick="edit(\''+ row.id + '\')">编辑</a> ';
                //var d = '<a href="#" mce_href="#" onclick="del(\''+ row.id +'\')">删除</a> '
                //console.log(value);
                //console.log(row);
                //console.log(index);
                var a = row.price + "ada";
                return row.price.cc;
            }
        }
    ]
})
;


//$table.on('post-body.bs.table', function () {
//    $(':checkbox').iCheck({
//        checkboxClass: 'icheckbox_square-blue',
//        radioClass: 'iradio_minimal',
//        increaseArea: '20%'
//    });
//})

function runningFormatter(value, row, index) {
    return index;
}
function detailFormatter(index, row) {
    var html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}

$table.on('check.bs.table uncheck.bs.table ' +
    'check-all.bs.table uncheck-all.bs.table', function () {
    $remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
    if ($table.bootstrapTable('getSelections').length == 1) {
        $modify.prop('disabled', !$table.bootstrapTable('getSelections').length);
    } else {
        $modify.prop('disabled', true);
    }
    // save your data, here just save the current page
    selections = getIdSelections();
    // push or splice the selections if you want to save all data selections
});
$remove.click(function () {
    $(".prompt-box").modal("show");
    $(".modal").on("click", "#deleteBtn", function () {
        var ids = getIdSelections();
        $table.bootstrapTable('remove', {
            field: 'id',
            values: ids
        });
        $(".prompt-box").modal("hide");
        $remove.prop('disabled', true);
    });
    $(".prompt-box").on('hide.bs.modal', function (event) {
        $("#deleteBtn,.prompt-box").unbind();

    });

});
var update_td = [];


$modify.click(function () {
    $("#modalTable1").modal("show");
    //var selectContent = $table.bootstrapTable('getSelections');
    //$table.bootstrapTable("append", selectContent);
    var selects = $table.bootstrapTable('getSelections');
    console.log(selects);

    //var index;
    //$('input[name="checkboxName"]:checked').each(function () {
    //    index = $(this).parents("tr").find("td");
    //});
    //for (var i = 0; i < index.length; i++) {
    //    update_td.push($(index[i]));
    //}
    ////$('#modalTable').modal("show");
    ////$modify.prop('disabled', true);

});
$('.modalTable').on('shown.bs.modal', function (event) {
    $("body").addClass("open-modal");

}).on('hide.bs.modal', function (event) {
    $("body").removeClass("open-modal");

    $("#determine").unbind("click");
    update_td = [];
    if (event.target.id == "modalTable") {
        $('#loginForm').data('bootstrapValidator').resetForm(true);
    }
});
function getIdSelections() {
    return $.map($table.bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}
//删除
window.operateEvents = {
    'click .remove': function (e, value, row, index) {
        $table.bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    }
};
////选择省
//var select_index;
//$('#first-disabled').on('show.bs.select', function (e) {
//    var $first = $('#first-disabled');
//    $first.empty();
//    $first.selectpicker({
//        noneSelectedText: "==请选择省==",
//    });
//    $.ajax({
//        url: "test.json",
//        dataType: "json",
//        success: function (data) {
//            for (var i = 0; i < data.length; i++) {
//                $first.append("<option class='option' value=" + data[i].address + " select-index=" + i + ">" + data[i].address + "</option>");
//            }
//
//
//            var ul_box = $first.next().find("ul.dropdown-menu");
//            var select_option;
//            $(ul_box).on("click", "li", function () {
//                select_option = $(this).text();
//                $first.attr('value', select_option);
//                $first.find("option").each(function (index, value) {
//                    if ($(value).text() == select_option) {
//                        select_index = index;
//                        console.log($(value).text());
//                    }
//                });
//            });
//            $('#first-disabled').unbind("show");
//            $first.selectpicker('val', "上海");
//            $first.selectpicker('refresh');
//
//        }
//    });
//
//
//}).on('hide.bs.select', function (e) {
//    if (typeof  select_index == "undefined") {
//        select_index=0;
//    }
//    $("#last-disabled").next().find(".filter-option").text("选择市");
//});
////选择市
//$('#last-disabled').on('show.bs.select', function (e) {
//    var $last = $("#last-disabled");
//    $last.empty();
//    $last.selectpicker({
//        noneSelectedText: "==请选择市=="
//    });
//    $.ajax({
//        url: "test.json",
//        dataType: "json",
//        success: function (data) {
//                for (var i = 0; i < data[select_index].city.length; i++) {
//                    $last.append("<option value=" + data[select_index].city[i].city + ">" + data[select_index].city[i].city + "</option>");
//                }
//                $last.selectpicker('val', '');
//                $last.selectpicker('refresh');
//        }
//    });
//});

/*$("#cancelBtn").click(function(){
	alert(1);
	$('#loginForm').bootstrapValidator();
});*/
$('#loginForm').bootstrapValidator();


$(document).ready(function () {
    //模态框的表格
    var $table1 = $('#table1');
    $('#twoModal').on('shown.bs.modal', function () {
        $table1.bootstrapTable('resetView');
    });
//        日历控件
                    $(".start_datetime,.end_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:ss",//设置时间格式，默认值: 'mm/dd/yyyy'weekStart : 0, //一周从哪一天开始。0（星期日）到6（星期六）,默认值0
        startDate: "2013-02-14 10:00",//可以被选择的最早时间
// endDate: "2016-02-14 10:00",//可以被选择的最晚时间
// daysOfWeekDisabled: "0,6",//禁止选择一星期中的某些天，例子中这样是禁止选择周六和周日
        autoclose: true,//当选择一个日期之后是否立即关闭此日期时间选择器
        startView: 2,//点开插件后显示的界面。0、小时1、天2、月3、年4、十年，默认值2
//minView: 0,//插件可以精确到那个时间，比如1的话就只能选择到天，不能选择小时了
        maxView: 4,//同理
        todayBtn: true,//是否在底部显示“今天”按钮
        todayHighlight: true,//是否高亮当前时间
        keyboardNavigation: true,//是否允许键盘选择时间
        language: 'zh-CN',//选择语言，前提是该语言已导入
        forceParse: true,//当选择器关闭的时候，是否强制解析输入框中的值。也就是说，当用户在输入框中输入了不正确的日期，选择器将会尽量解析输入的值，并将解析后的正确值按照给定的格式format设置到输入框中
        minuteStep: 5,//分钟的间隔
        pickerPosition: "bottom-left",//显示的位置，还支持bottom-left
// viewSelect: 0,//默认和minView相同
        showMeridian: true,//是否加上网格
// initialDate: "2015-02-14 10:00",//初始化的时间
        minView: 'month'//日期时间选择器所能够提供的最精确的时间选择视图。
    });
    //开始时间是否大于结束时间的验证
    $(".start_datetime").on('changeDate show', function (ev) {
        $('#loginForm').data('bootstrapValidator').updateElementStatus($("input[name='starttime']"), 'VALID');
    }).on("click", function (ev) {
        $(".start_datetime").datetimepicker("setEndDate", $("input[name='endtime']").val());
    });
    $(".end_datetime").on('changeDate', function (ev) {
        $('#loginForm').data('bootstrapValidator').updateElementStatus($("input[name='endtime']"), 'VALID');
    }).on("click", function (ev) {
        $(".end_datetime").datetimepicker("setStartDate", $("input[name='starttime']").val());
    });
//校验表单
    
    /*$("#cancelBtn").click(function(){
    	alert(1);
    	$('#loginForm').bootstrapValidator();
    });*/
    
//    $('#loginForm').bootstrapValidator();
    /*$('#loginForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitHandler: function (validator, form, submitButton) {
            validator.defaultSubmit();
        },
        fields: {
            number: {
                validators: {
                    notEmpty: {
                        message: '不能为空！'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '不能为空！'
                    },
                    emailAddress: {
                        message: '输入不是有效的电子邮件地址！'
                    }
                }
            },
            telephone: {
                validators: {
                    notEmpty: {
                        message: '不能为空！'
                    },
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '11位'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '不能为空！'
                    }
                    , identical: {
                        field: 'confirmPassword',
                        message: '密码不一样'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: '不能为空！'
                    },
                    identical: {
                        field: 'password',
                        message: '密码不一样'
                    }
                }
            },
            starttime: {
                validators: {
                    notEmpty: {
                        message: '不能为空！'
                    },
                    date: {
                        format: "YYYY-MM-DD hh:ii:ss",
                        message: 'The format is yyy-mm-dd'
                    }

                }
            },
            endtime: {
                validators: {
                    notEmpty: {
                        message: '不能为空！'
                    },
                    date: {
                        format: "YYYY-MM-DD hh:ii:ss",
                        message: 'The format is yyy-mm-dd'
                    }

                }
            },

            name: {
                validators: {
                    notEmpty: {
                        message: '不能为空！'
                    }
                }
            },
            'languages[]': {
                validators: {
                    notEmpty: {
                        message: '请选择兴趣爱好！'
                    }
                }
            }, user: {
                validators: {
                    notEmpty: {
                        message: '请选择用户类型！'
                    }
                }
            }
        }
    });
    });*/
//提示定位
    var prompt = $(".form-group .help-block")
    $("<div>").addClass("tooltip-arrow").appendTo(prompt.parent());
    prompt.css({"right": -15, "top": -27});
    $(".tooltip-arrow").show().css({"right": 13, "top": 0});
});});

