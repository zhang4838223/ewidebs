/**
 * wangtao
 * 2016年6月21日14:57:35
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
		$('#userInfoForm').data('bootstrapValidator').validate();
		if(!$('#userInfoForm').data('bootstrapValidator').isValid())
		{
		return;// 直接return 不做后续任何操作
		}
		$.ajax({
			type : "POST",
			url : system.getContextPath() + "/sys/user/infoUpdate",
			processData : true,
			async: true,
			data : $("#userInfoForm").serialize(),//获取表单所有值
			success : function() {
				//操作完后的提示
				$.modal.showPrompt({
					title : '提示',
					content : "保存成功!",
					time : 1500
				});
				//取消验证
				$('#userInfoForm').data('bootstrapValidator').updateStatus('phone', 'NOT_VALIDATED',null);
				$('#userInfoForm').data('bootstrapValidator').updateStatus('email', 'NOT_VALIDATED',null);
				$('#userInfoForm').data('bootstrapValidator').updateStatus('mobile', 'NOT_VALIDATED',null);
				$('#userInfoForm').data('bootstrapValidator').updateStatus('remarks', 'NOT_VALIDATED',null);
			}
		});
	});
}
/**
 * 初始化表单验证
 */
function bootstrapValidator(){
	$('#userInfoForm').bootstrapValidator({
	    feedbackIcons: {
	        valid: 'glyphicon glyphicon-ok',
	        invalid: 'glyphicon glyphicon-remove',
	        validating: 'glyphicon glyphicon-refresh'
	    },
	    fields: {
	        email: {
	            validators: {
	                emailAddress: {
	                    message: '不是有效的电子邮件地址！'
	                },
	                charLength:{
	                	message: '输入的邮件地址太长！',
	                	maxLength:50
	                }
	            }
	        },
	        phone:{
	       	 validators: {
	       		regexp: {
		             regexp: /^[0-9]*$/,
		             message: '电话只能输入数字！'
		           },
	       		charLength:{
                	message: '输入的电话号码太长！',
                	maxLength:15
                }
	         }
	        },
	        mobile:{
	      	 validators: {
	      		regexp: {
	               message: '请填写正确格式的手机号码！',
	               regexp: /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/
	           }
	      	 }
	        },
	        remarks:{
	        	validators: {
	        		charLength:{
	                	message: '备注内容过多！',
	                	maxLength:200
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