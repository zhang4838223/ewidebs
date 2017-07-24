/*
 * Yan Xin 2016/05/26
 */

/**
 * 页面加载事件
 */
$(document).ready(function() {
	//初始化下拉列表
	initSelect();
	//初始化按钮
	initButton();
	//初始化验证
	initValidator();
});

/**
 * 初始化下拉列表
 */
function initSelect(){
	$("#type").bindDict({
		select2Option : {
			allowClear : false,
			placeholder : '请选择'
		},
		dictTypeName:"sys_office_type"
	});

	/*$("#grade").bindDict({
		select2Option : {
			allowClear : true,
			placeholder : '请选择'
		},
		dictTypeName:"sys_office_grade"
	});*/

	$("#useable").bindDict({
		select2Option : {
			allowClear : false,
			//placeholder : '请选择'
		},
		dictTypeName:"yes_no"
	});
}

/**
 * 初始化按钮
 */
function initButton(){
	var parentData = $.modal.getParentData();
	if(parentData.length==0){
		add();
	}else{
		//判断是否有子节点，如果有子节点，机构类型禁止修改
		var parentId = $('#parentId').val();
		if(parentId == '0'){
			$('#type').attr('disabled','disabled');
		}else{
			var treeObj = $.fn.zTree.getZTreeObj("ztree");
			var value = treeObj.getNodeByParam("id", $('#id').val(), null);
			if(value!=null && value.children!=null &&value.children.length>0){
				$('#type').attr('disabled','disabled');
			}
		}
	}

	$.modal.setBtnFun([ function() {//对应yes
		$('#officeForm').data('bootstrapValidator').validate();  
        if(!$('#officeForm').data('bootstrapValidator').isValid()){  
        	$.modal.setChildData(false);
            return ;  
        }
		save();
		$.modal.setChildData(true);
	}, function() {//对应no
		 
	}]);

}

/**
 * 初始化验证
 */
function initValidator(){
	$('#officeForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			'parent.name' : {
				validators : {
					callback : {
						message : '请选择上级机构！',
						callback : function(value, validator) {
							//是否有跟节点
							return noParentId(value);
						}
					}
				}
			},
			'area.name' : {
				validators : {
					notEmpty : {
						message : '请选择归属区域！'
					}
				}
			},
			name : {
				validators : {
					notEmpty : {
						message : '请填写机构名称！'
					},
					callback: {
	                	message: '机构名称已存在!',
	                	callback: function(value, validator) {
	                		return true;
	                	}
	                },
	                charLength:{
	                	message: '机构名称长度必须在1到20位之间！',
	                	maxLength:20
	                }
//					remote: {
//	                	message: '机构名称已存在!',
//	                	url:ctx+'/sys/office/checkOfficeName',
//	                	data:{
//	                		name:'name',
//	                		oldName:$("#oldName").val()
//	                	}
//	                }
				}
			},
			type: {
				validators : {
					notEmpty : {
						message : '请选择机构类型！'
					}
				}
			},
			/*grade:{
				validators : {
					notEmpty : {
						message : '请选择机构级别！'
					}
				}
			},*/
			sort : {
				validators : {
					notEmpty : {
						message : '请填写排序！'
					},
					regexp: {
	 	               message: '排序长度必须在1到9位之间！',
	 	               regexp: /^\d{1,9}$/
	 	           }
				}
			},
			email: {
	            validators: {
	                emailAddress: {
	                    message: '不是有效的电子邮件地址！'
	                },
	                charLength:{
	                	message: '邮箱长度必须在1到50位之间！',
	                	maxLength:50
	                }
	            }
	        },
	        phone:{
	        	validators: {
	        		charLength:{
	                	message: '电话号码长度必须在1到15位之间！',
	                	maxLength:15
	                }
//	           		regexp: {
//	                    message: '请填写正确格式的电话号码！',
//	                    regexp: /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/
//	                }
		         }
	       },
	       fax:{
	       	validators: {
	       		charLength:{
                	message: '传真长度必须在1到15位之间！',
                	maxLength:15
                }
//	          		regexp: {
//	                   message: '请填写正确格式的传真号码！',
//	                   regexp: /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/
//	               }
		         }
	      },
	       zipCode:{
	    	   validators: {
	    		   integer: {
	                   message: '邮政编码只能为数字！'
	               },
	         		stringLength: {
	                  message: '请填写6位有效的邮政编码！',
	                  max:6,
	                  min:6
	              }
	  	        }
	       },
	       address:{
	    	   validators: {
	    		   charLength:{
	                	message: '地址长度必须在1到200位之间！',
	                	maxLength:200
	                }
	  	        }
	       },
	      /* master:{
	    	   validators: {
	    		   charLength:{
	                	message: '输入的负责人太长！',
	                	maxLength:20
	                }
	  	        }
	       },*/
	        remarks:{
	        	validators: {
	        		charLength:{
	                	message: '备注长度必须在1到200位之间！',
	                	maxLength:200
	                }
	        	}
	        }

		}
	});
	
	//验证机构名称是否唯一
	$("#name").blur(function(){
		checkOfficeName();
	});

	// 提示定位
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
 * 判断是否存在根节点
 * @param value
 * @returns {Boolean}
 */
function noParentId(value){
	var count=$("#count").val();
	var noParentId=$("#noParentId").val();
	var flag = true;
	if(count==0  && value==''){
		flag = true;
	}else if(count!=0  && value!=''){
		flag = true;
	}else if(count!=0 && noParentId=='0'){
		flag = true;
	}else if(count!=0 && typeof(noParentId) == "undefined"&&value!=''){
		flag = true;
	}else{
		flag = false;
	}
	return flag;
}

/**
 * 新增页面赋值
 */
function add(){
	$('#parentId').val($('#officeId1_').val());
	$('#parentName').val($('#officeName1_').val());
}

/**
 * 提交表单
 */
function save() {
	$.ajax({
		type : "POST",
		url : ctx + "/sys/office/save",
		processData : true,
		data : $("#officeForm").serialize(),
		success : function(data) {
			$.modal.showPrompt({
				title:'提醒',
				content:data,
				time:2000
			});
			$('#modify').prop('disabled', true);
			$('#remove').prop('disabled', true);
			$('#table').bootstrapTable("removeAll");
			$('#table').bootstrapTable("refresh", {url : ctx + "/sys/office/data"});
			initTree();
		}
	});
}

/**
 * 上级机构组件回调函数
 * @param v
 * @param nodes
 */
function parentTreeselectCallBack(v,nodes){
	var count=$("#count").val();
	if (v=="ok"){//确定
		if(nodes.length == 0 && count>0){
			$('#officeForm').data('bootstrapValidator').updateStatus('parent.name', 'INVALID',null);
		}else{
			$('#officeForm').data('bootstrapValidator').updateStatus('parent.name', 'VALID',null);
			checkOfficeName();
		}
	}if(v=="clear"){//清除
		if(count>0){
			$('#officeForm').data('bootstrapValidator').updateStatus('parent.name', 'INVALID',null);
		}else{
			$('#officeForm').data('bootstrapValidator').updateStatus('parent.name', 'VALID',null);
		}
	}
}

/**
 * 归属区域组件回调函数
 * @param v
 * @param nodes
 */
function areaTreeselectCallBack(v,nodes){
	if (v=="ok"){
		var area = $("#areaName").val();
			if(area != '' && area != null){
				  $('#officeForm').data('bootstrapValidator').updateStatus('area.name', 'VALID',null);
			}else{
				$('#officeForm').data('bootstrapValidator').updateStatus('area.name', 'INVALID',null);
			}
	}
}
/**
 * 验证机构名唯一性
 */
function checkOfficeName(){
	if($("#name").val()!=$("#oldName").val()){
		$.ajax({
			url: ctx+'/sys/office/checkOfficeName',
			type: 'GET',
			async: false,
			data:{'name':$("#name").val(),'oldName':$("#oldName").val(),'parentId':$("#parentId").val()},
			datatype:'json',
			success: function(data){
				if('true'==data){
					$('#officeForm').data('bootstrapValidator').updateStatus('name', 'VALID','callback');
				}else{
					$('#officeForm').data('bootstrapValidator').updateStatus('name', 'INVALID','callback');
				}
			}
		});
	}
}