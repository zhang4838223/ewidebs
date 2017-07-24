$(document).ready(function() {
	//初始化表单验证
	bootstrapValidator();
	
	$.modal.setBtnFun([ function() {
		//提交表单
		save();
	}, function() {

	} ]);
});
/**
 * 提交表单
 */
function save(){
	$('#subsystemForm').data('bootstrapValidator').validate();  
    if(!$('#subsystemForm').data('bootstrapValidator').isValid()){ 
    	$.modal.setChildData(false);
        return;  
    } 
	$.ajax({
		type : "POST",
		url : system.getContextPath() + "/sys/subsystem/save",
		processData : true,
		data : $("#subsystemForm").serialize(),
		success : function() {
			$.modal.showPrompt({
				title : '提示',
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
	$('#subsystemForm').bootstrapValidator(
			{
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					systemName : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '子系统名称不能为空'// 这是提示信息
							},
							stringLength: {
					             min: 1,
					             max: 20,
					             message: '子系统名称长度必须在1到20位之间'
								           },
								callback : {
									message : '子系统名称已存在！',
									callback : function(value, validator) {
										return true;
									}
								}
						}
					},
					systemCode : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '子系统编号不能为空！'// 这是提示信息
							},
							stringLength: {
					             min: 1,
					             max: 50,
					             message: '子系统编号长度必须在1到50位之间'
								           },
								callback : {
									message : '子系统编号已存在！',
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
					url : {// 这里为输入框的name
						validators : {
							stringLength : {
								min : 0,
								max : 1000,
								message : '链接地址必须在1000位之内'
							}
						}
					}
				}
		    });
	
	//查询子系统编号名是否已存在
	$("#systemCode").blur(function(){
		var code=$("#systemCode").val();
		//判断name是否存在
		var noCode=$("#noCode").val();
		if(code!=noCode){
		$.ajax({
			url: system.getContextPath() + "/sys/subsystem/findNameOrCode",
			type: 'GET',
			async: false,
			data:{'systemCode':code,'noCode':noCode},
			datatype:'json',
			success: function(data){
				if('true'==data){
					$('#subsystemForm').data('bootstrapValidator').updateStatus('systemCode', 'VALID','callback');
				}else if('false'==data){
					$('#subsystemForm').data('bootstrapValidator').updateStatus('systemCode', 'INVALID','callback');
				}
			}
		});
		}
	  });
	//查询子系统编号名是否已存在
	$("#systemName").blur(function(){
		var name=$("#systemName").val();
		//判断name是否存在
		var noName=$("#noName").val();
		if(name!=noName){
		$.ajax({
			url: system.getContextPath() + "/sys/subsystem/findNameOrCode",
			type: 'GET',
			async: false,
			data:{'systemName':name,'noName':noName},
			datatype:'json',
			datatype:'json',
			success: function(data){
				if('true'==data){
					$('#subsystemForm').data('bootstrapValidator').updateStatus('systemName', 'VALID','callback');
				}else if('false'==data){
					$('#subsystemForm').data('bootstrapValidator').updateStatus('systemName', 'INVALID','callback');
				}
			}
		});
		}
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