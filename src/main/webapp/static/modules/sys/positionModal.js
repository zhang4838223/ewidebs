$(document).ready(function() {
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
	$('#positionForm').data('bootstrapValidator').validate();
	if(!$('#positionForm').data('bootstrapValidator').isValid())
	{
	$.modal.setChildData(false);
	return;// 直接return 不做后续任何操作
	}
	$.ajax({
		type : "POST",
		url : system.getContextPath() + "/sys/position/save",
		processData : true,
		data : $("#positionForm").serialize(),
		success : function() {
			$.modal.showPrompt({
				title : '提醒',
				content : "保存成功!",
				time : 1500
			});
			initData();
		}
	});
	$.modal.setChildData(true);
}
/**
 * 初始化表单验证
 */
function bootstrapValidator(){
	$('#positionForm').bootstrapValidator(
			{
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					positionName : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '职位名称不能为空！'// 这是提示信息
							},
							stringLength: {
					             min: 1,
					             max: 10,
					             message: '职位名称长度必须在1到10位之间'
								           },
								callback : {
									message : '职位名称已存在！',
									callback : function(value, validator) {
										return true;
									}
								}
						}
					},
					positionNo : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '职位编号不能为空！'// 这是提示信息
							},
							stringLength: {
					             min: 1,
					             max: 10,
					             message: '职位编号长度必须在1到10位之间'
								           },
								callback : {
									message : '职位编号已存在！',
									callback : function(value, validator) {
										return true;
									}
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
					},
					'office.name' : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '归属公司不能为空！'// 这是提示信息
							}
						}
					}
				}
		    });
	//查询职位编号是否已存在
	$("#updateNo").blur(function(){
		updateNo();
	  });
	//查询职位名称是否已存在
	$("#updateNames").blur(function(){
		updateNames();
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
/**
 * 验证code
 */
function updateNo(){
	//获取选择归属机构id
	var officeId=$("#officeId").val();
	var noofficeId=$("#noofficeId").val();
	var code=$("#updateNo").val();
	//判断code是否存在
	var noCode=$("#noCode").val();
	if(code!=noCode || officeId!=noofficeId){
	$.ajax({
		url: system.getContextPath() + "/sys/position/findNameOrCode",
		type: 'GET',
		async: false,
		data:{'positionNo':code,'noCode':noCode,'office.id':officeId},
		datatype:'json',
		success: function(data){
			if('true'==data){
				noofficeId='';
				$('#positionForm').data('bootstrapValidator').updateStatus('positionNo', 'VALID',null);
			}else if('false'==data){
				$('#positionForm').data('bootstrapValidator').updateStatus('positionNo', 'INVALID','callback');
			}
		}
	});
	}
}
/**
 * 验证name
 */
function updateNames(){
	var officeId=$("#officeId").val();
	var noofficeId=$("#noofficeId").val();
	var name=$("#updateNames").val();
	//判断name是否存在
	var noName=$("#noName").val();
	if(name!=noName || officeId!=noofficeId){
	$.ajax({
		url: system.getContextPath() + "/sys/position/findNameOrCode",
		type: 'GET',
		async: false,
		data:{'positionName':name,'noName':noName,'office.id':officeId},
		datatype:'json',
		success: function(data){
			if('true'==data){
				noofficeId='';
				$('#positionForm').data('bootstrapValidator').updateStatus('positionName', 'VALID',null);
			}else if('false'==data){
				$('#positionForm').data('bootstrapValidator').updateStatus('positionName', 'INVALID','callback');
			}
		}
	});
	}
}
/**
 * 归属公司回调
 * @param v
 * @param nodes
 */
function officeTreeselectCallBack(v,nodes){
	if (v=="ok"){
		if(nodes!=null && nodes.length == 0 ){
			$('#positionForm').data('bootstrapValidator').updateStatus('office.name', 'INVALID',null);
		}else{
			$('#positionForm').data('bootstrapValidator').updateStatus('office.name', 'VALID',null);
		}
		updateNames();
		updateNo();
	}else if (v=="clear"){
			$('#positionForm').data('bootstrapValidator').updateStatus('office.name', 'INVALID',null);
    }
}