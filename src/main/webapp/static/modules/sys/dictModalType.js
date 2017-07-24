$(document).ready(function() {
	//初始化父页面传的参数
	initDictTypeData();
	//初始化表单验证
	bootstrapValidator();
	
	$.modal.setBtnFun([ function() {
		//提交表单
		save();
	}, function() {

	}]);
});
/**
 * 提交表单
 */
function save(){
	//表单验证
	$('#dictTypeForm').data('bootstrapValidator').validate();
	if(!$('#dictTypeForm').data('bootstrapValidator').isValid())
	{
	$.modal.setChildData(false);
	return;// 直接return 不做后续任何操作
	}
	$.ajax({
		type : "POST",
		url : system.getContextPath() + "/sys/dict/save",
		processData : true,
		data : $("#dictTypeForm").serialize(),
		success : function() {
			$.modal.showPrompt({
				title : '提示',
				content : "保存成功!",
				time : 1500
			});
			refreshTree();
		}
	});
	$.modal.setChildData(true);
}
/**
 * 初始化数据
 */
function initDictTypeData(){
	var data=$.modal.getParentData();
	if(data!=null && data.length>0){
		$("#parentIdModalType").val(data[0]);
	}
}
/**
 * 初始化表单验证
 */
function bootstrapValidator(){
	$('#dictTypeForm').bootstrapValidator(
			{
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					label : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '类型名称不能为空！'// 这是提示信息
							},
							stringLength: {
					             min: 1,
					             max: 20,
					             message: '类型名称长度必须在1到20位之间'
								           },
								callback : {
									message : '类型名称已存在！',
									callback : function(value, validator) {
										return true;
									}
								}
						}
					},
					value : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '类型编号不能为空！'// 这是提示信息
							},stringLength: {
					               min: 1,
					               max: 50,
					               message: '类型编号长度必须在1到50位之间！'
					             },callback : {
										message : '类型编号已存在！',
										callback : function(value, validator) {
											return true;
										}
									}
						}
					},
					sort : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '排序不能为空！'// 这是提示信息
							},
							regexp: {
					             regexp: /^[0-9]*$/,
					             message: '排序只能输入数字！'
					           },stringLength: {
					               min: 1,
					               max: 9,
					               message: '排序长度必须在1到9位之间！'
					             }
						}
					},
					remarks : {// 这里为输入框的name
						validators : {
							stringLength : {
								min : 0,
								max : 1000,
								message : '备注必须在1000位之内'
							}
						}
					}
				}
		    });
	//查询字典项名是否已存在
	$("#label").blur(function(){
		var label=$("#label").val();
		//判断label是否存在
		var noLabel=$("#noLabel").val();
		//获取类型
		var dictType=$("#dictType").val();
		//获取parentId
		var parentIdModal=$("#parentIdModal").val();
		if(label!=noLabel){
		$.ajax({
			url: system.getContextPath() + "/sys/dict/findValueOrLabel",
			type: 'GET',
			async: false,
			data:{'label':label,'dictType':dictType,'parentId':parentIdModal,'noLabel':noLabel},
			datatype:'json',
			success: function(data){
				if('true'==data){
					$('#dictTypeForm').data('bootstrapValidator').updateStatus('label', 'VALID','callback');
				}else if('false'==data){
					$('#dictTypeForm').data('bootstrapValidator').updateStatus('label', 'INVALID','callback');
				}
			}
		});
		}
	  });
	//查询字典项值是否已存在
	$("#value").blur(function(){
		var value=$("#value").val();
		//判断value是否存在
		var noValue=$("#noValue").val();
		//获取类型
		var dictType=$("#dictType").val();
		//获取parentId
		var parentIdModal=$("#parentIdModal").val();
		if(value!=noValue){
		$.ajax({
			url: system.getContextPath() + "/sys/dict/findValueOrLabel",
			type: 'GET',
			async: false,
			data:{'value':value,'dictType':dictType,'parentId':parentIdModal,'noValue':noValue},
			datatype:'json',
			success: function(data){
				if('true'==data){
					$('#dictTypeForm').data('bootstrapValidator').updateStatus('value', 'VALID','callback');
				}else if('false'==data){
					$('#dictTypeForm').data('bootstrapValidator').updateStatus('value', 'INVALID','callback');
				}
			}
		});
		}不安于现状
	  });
//表单验证提示位置
	var prompt = $(".form-group .help-block")
	$("<div>").addClass("tooltip-arrow").appendTo(prompt.parent());
	prompt.css({
		"right" : -15,
		"top" : -27
	});
	$(".tooltip-arrow").show().css({
		"right" : 13,
		"top" : 0
	});
}