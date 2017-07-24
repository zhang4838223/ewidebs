/**
 * wangtao
 * 2016年6月22日16:37:27
 */

//加载事件
$(document).ready(function() {
	//初始化表单验证
	bootstrapValidator();
	//初始化表单按钮
	initFormButton();
});
/**
 * 初始化表单按钮
 */
function initFormButton(){
	$("#submitForm").click(function(){
		//表单验证
		$('#userModifyPwdForm').data('bootstrapValidator').validate();
		if(!$('#userModifyPwdForm').data('bootstrapValidator').isValid())
		{
		return;// 直接return 不做后续任何操作
		}
		$.ajax({
			type : "POST",
			url : system.getContextPath() + "/sys/user/modifyPwdUpdate",
			processData : true,
			async: true,
			data : $("#userModifyPwdForm").serialize(),//获取表单所有值
			success : function(data) {
				var content="修改密码成功!";
				if(data){
					content="修改密码失败，旧密码错误!";
				}
				var time=1500;
				//操作完后的提示
				$.modal.showPrompt({
					title : '提示',
					content : content,
					time : time
				});
				//清空表单信息
				$('#userModifyPwdForm')[0].reset();
				//取消验证
				$('#userModifyPwdForm').data('bootstrapValidator').updateStatus('oldPassword', 'NOT_VALIDATED',null);
				$('#userModifyPwdForm').data('bootstrapValidator').updateStatus('newPassword', 'NOT_VALIDATED',null);
				$('#userModifyPwdForm').data('bootstrapValidator').updateStatus('confirmNewPassword', 'NOT_VALIDATED',null);
				setTimeout(function(){parent.location.href=system.getContextPath()+'/logout';},time); 
			}
		});
	});
}
/**
 * 初始化表单验证
 */
function bootstrapValidator(){
	$('#userModifyPwdForm').bootstrapValidator({
	    feedbackIcons: {
	        valid: 'glyphicon glyphicon-ok',
	        invalid: 'glyphicon glyphicon-remove',
	        validating: 'glyphicon glyphicon-refresh'
	    },
	    fields: {
	    	 newPassword: {
		            validators: {
		            	notEmpty: {
			                    message: '请填写新密码！'
			            },
		                identical: {
		                    field: 'confirmNewPassword',
		                    message: '两次输入的密码不一样'
		                },
		                charLength:{
		                	message: '输入的密码太长！',
		                	maxLength:50
		                }
		            }
		        },
		        confirmNewPassword: {
		            validators: {
		            	notEmpty: {
		                    message: '请填写确定新密码！'
		            	},
		                identical: {
		                    field: 'newPassword',
		                    message: '两次输入的密码不一样'
		                },
		                charLength:{
		                	message: '输入的密码太长！',
		                	maxLength:50
		                }
		            }
		        },oldPassword: {
		            validators: {
		            	notEmpty: {
		                    message: '请填写旧密码！'
		            	},
		                charLength:{
		                	message: '输入的密码太长！',
		                	maxLength:50
		                }
		            }
		        }
	    }
	});
// 表单验证提示位置
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