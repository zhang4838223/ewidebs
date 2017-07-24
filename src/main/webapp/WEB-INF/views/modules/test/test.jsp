<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>测试管理</title>
<meta name="decorator" content="defaultx" />

</head>
<body>
	<form id="testForm" class="login-form">
		<input>
		<div>
			<select id="province" class="form-control select2">
			</select>
			<select id="city" class="form-control select2">
			</select>
		</div>

		
	<!-- 	开始日：<div id="startDate"></div>
结束日：<div id="endDate" class="laydate-icon"></div>

<div id="date" class="laydate-icon"></div> -->
	<div class="container">
    <div class='col-md-5'>
        <div class="form-group">
            <div class='input-group date' id='datetimepicker6'>
                <input type='text' class="form-control" />
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
    </div>
    </div>
    
    <div class="container">
    <div class='col-md-5'>
        <div class="form-group">
            <div class='input-group date' id='datetimepicker7'>
                <input type='text' class="form-control" />
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
    </div>
    <div class='col-md-5'>
        <div class="form-group">
            <div class='input-group date' id='datetimepicker8'>
                <input type='text' class="form-control" />
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
    </div>
</div>
	</form>
	<%@include file="/WEB-INF/views/include/js-footer.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
	/* 	var start = {
			    elem: '#startDate',
			    format: 'YYYY/MM/DD hh:mm:ss',
			    max: '2099-06-16 23:59:59', //最大日期
			    istime: true,
			    istoday: false,
			    choose: function(datas){
			         end.min = datas; //开始日选好后，重置结束日的最小日期
			         end.start = datas //将结束日的初始值设定为开始日
			    }
			};
			var end = {
			    elem: '#endDate',
			    format: 'YYYY/MM/DD hh:mm:ss',
			    max: '2099-06-16 23:59:59',
			    istime: true,
			    istoday: false,
			    choose: function(datas){
			        start.max = datas; //结束日选好后，重置开始日的最大日期
			    }
			};
			laydate(start);
			laydate(end); */
			
			$.towDatetime({id:"datetimepicker7"},{id:"datetimepicker8"});
			$("#datetimepicker6").oneDateTime();
			
			

		$("#province").cascadeSelect({
			select2Option : {
				allowClear : true,
				placeholder : '请选择',
				language: "zh-CN"
			},
			thisUrl : "${ctx}/test/province",
			targetUrl : "${ctx}/test/city?provinceName=",
			targetName : "city"
		});

	//	$(".start_datetime").datetime();
	//	$(".end_datetime").datetime();

		$("#testForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				starttime : {
					validators : {
						notEmpty : {
							message : '不能为空！'
						},
						date : {
							format : "YYYY-MM-DD",
							message : 'The format is yyy-mm-dd'
						}

					}
				},
				endtime : {
					validators : {
						notEmpty : {
							message : '不能为空！'
						},
						date : {
							format : "YYYY-MM-DD",
							message : 'The format is yyy-mm-dd'
						}

					}
				}
			}
		});

	});

	/* BindSelect("province", "${ctx}/test/province");
	
	$("#province").jilian();
	function jilian(id,url){
	 $("#province").on("change", function (e) {
	       var provinceName = $("#province").val();
	       $("#city").empty();
	       BindSelect(id, url+ provinceName);
	   });
	}
	});
	function BindDictItem(ctrlName, dictTypeName) {
	var url = '/DictData/GetDictJson?dictTypeName=' + encodeURI(dictTypeName);
	BindSelect(ctrlName, url);
	}
	
	function BindSelect(ctrlName, url) {
	var control = $('#' + ctrlName);
	//设置Select2的处理
	control.select2({
	  allowClear: true,
	  placeholder:'请选择'
	});
	//绑定Ajax的内容
	$.getJSON(url, function (data) {
	  control.empty();//清空下拉框
	  control.append("<option value='" + 0 + "' >&nbsp;请选择</option>");
	  //control.selectpicker('val',0);
	  $.each(data, function (i, item) {
	     control.append("<option value='" + item.Value + "'>&nbsp;" + item.Text + "</option>");
	  });
	}); 
	}*/
	
	
</script>
</body>
</html>
