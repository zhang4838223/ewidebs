/*
 * Yan Xin 2016/05/26
 */

/**
 * 页面加载事件
 */
$(document).ready(function() {
	//初始化按钮
	initButton();
	//初始化下拉列表
	initSelect();
	//初始化验证
	initvalidator();
	//初始化点击事件
	initButtonClick();
});

/**
 * 初始化下拉列表
 */
function initSelect(){
	$("#loginFlag").bindDict({
		select2Option : {
			allowClear : false,
			placeholder : '请选择'
		},
		dictTypeName:"yes_no"
	});

	$("#userType").bindDict({
		select2Option : {
			allowClear : true,
			placeholder : '请选择'
		},
		dictTypeName:"sys_user_type"
	});

	$("#sex").bindDict({
		select2Option : {
			allowClear : true,
			placeholder : '请选择'
		},
		dictTypeName:"sex"
	});

	$("#position").bindSelect({
		selectName:'position',
		select2Option : {
			allowClear : true,
			placeholder : '请选择'
		},
		url:system.getContextPath() + "/sys/user/positions?companyId="+$("#companyId").val()
	});
}

/**
 * 初始化按钮
 */
function initButton(){
	//获取父页面传参
	var parentData = $.modal.getParentData();
	if(parentData.length>0){
		modify(parentData);
	}else{
		add();
	}
	
	$.modal.setBtnFun([ function() {
		 //对应yes
		$('#userForm').data('bootstrapValidator').validate(); 
	    if(!$('#userForm').data('bootstrapValidator').isValid()){
	    	console.info("inValid");
	    	$.modal.setChildData(false);
	        return ;
	    }
	    console.info("isValid");
	    $.modal.setChildData(true);
		save();
	}, function() {
		 //对应no
	}]);
}

/**
 * 初始化验证
 */
function initvalidator(){
	$('#userForm').bootstrapValidator({//
	    feedbackIcons: {
	        valid: 'glyphicon glyphicon-ok',
	        invalid: 'glyphicon glyphicon-remove',
	        validating: 'glyphicon glyphicon-refresh'
	    },
	    fields: {
	    	name: {
	            validators: {
	                notEmpty: {
	                    message: '请填写姓名！'
	                },
	                charLength:{
	                	message: '姓名长度必须在1到20位之间！',
	                	maxLength:20
	                }
	            }
	        },
	        'company.name': {
	            validators: {
	                notEmpty: {
	                    message: '请填写归属公司！'
	                }
	            }
	        },
	        'office.name': {
	            validators: {
	                notEmpty: {
	                    message: '请填写归属部门！'
	                }
	            }
	        }, 
	        loginName: {
	        	verbose: false,
	            validators: {
	                notEmpty: {
	                    message: '请填写登录名！'
	                },
	                callback: {
	                	message: '登录名已存在!',
	                	callback: function(value, validator) {
	                		return true;
	                	}
	                },
	                charLength:{
	                	message: '登录名长度必须在1到20位之间！',
	                	maxLength:20
	                }
//	                remote: {
//	                	message: '登录名已存在!',
//	                	url:system.getContextPath()+'/sys/user/checkLoginName',
//	                	data:{
//	                		loginName:'loginName',
//	                		oldLoginName:$("#oldLoginName").val()
//	                	}
//	                }
	            }
	        },
	        newPassword: {
	            validators: {
	                identical: {
	                    field: 'confirmNewPassword',
	                    message: '两次输入的密码不一样'
	                },
	                callback: {
	                	message: '请填写密码！',
	                    callback: function(value, validator) {
	                    	return pswIsNull(value);
	                    }
	                },
	                charLength:{
	                	message: '密码长度必须在1到50位之间！',
	                	maxLength:50
	                }
	            }
	        },
	        confirmNewPassword: {
	            validators: {
	                identical: {
	                    field: 'newPassword',
	                    message: '两次输入的密码不一样'
	                },
	                callback: {
	                	message: '请填写确认密码！',
	                    callback: function(value, validator) {
	                    	//判断密码是否需要做非空验证
	                    	return pswIsNull(value);
	                    }
	                },
	                charLength:{
	                	message: '密码长度必须在1到50位之间！',
	                	maxLength:50
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
//	        roles: {
//	        	 validators: {
//	            	callback: {
//	                	message: '请选择角色！',
//	                	callback: function(value, validator) {
//	                		var result = true;
//	                		var roleids = $("#roleids").val();
//	                		if(roleids==''){
//	                			result = false;
//	                		}
//	                		return result;
//	                	}
//	                }
//	            }
//	        },
	        phone:{
	       	 validators: {
	       		charLength:{
                	message: '电话号码长度必须在1到15位之间！',
                	maxLength:15
                }
//	       		regexp: {
//	                message: '请填写"区号-号码"格式的电话号码！',
//	                regexp: /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/
//	            }
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
	        no: {
	            validators: {
	                notEmpty: {
	                    message: '请填写工号！'
	                },
	                callback: {
	                	message: '工号已存在!',
	                	callback: function(value, validator) {
	                		//验证工号是否唯一
	                		return true;
	                	}
	                },
	                charLength:{
	                	message: '工号长度必须在1到15位之间！',
	                	maxLength:15
	                }
//	                remote: {
//	                	message: '工号已存在!',
//	                	url:system.getContextPath()+'/sys/user/checkEmployeeNo',
//	                	data:{
//	                		no:'no',
//	                		oldNo:$("#oldNo").val()
//	                	}
//	                }
	            }
	        },
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
	
	//验证登录名是否唯一
	$("#loginName").blur(function(){
		if($("#loginName").val()!=$("#oldLoginName").val()){
			$.ajax({
				url: system.getContextPath()+'/sys/user/checkLoginName',
				type: 'GET',
				async: false,
				data:{'loginName':$("#loginName").val(),'oldLoginName':$("#oldLoginName").val()},
				datatype:'json',
				success: function(data){
					if('true'==data){
//						$('#userForm').data('bootstrapValidator').validateField('loginName'); 
						$('#userForm').data('bootstrapValidator').updateStatus('loginName', 'VALID','callback');
					}else{
						$('#userForm').data('bootstrapValidator').updateStatus('loginName', 'INVALID','callback');
					}
				}
			});
		}
	  });
	
	//验证工号是否唯一
	$("#no").blur(function(){
		if($("#no").val()!=$("#oldNo").val()){
			$.ajax({
				url: system.getContextPath()+'/sys/user/checkEmployeeNo',
				type: 'GET',
				async: false,
				data:{'no':$("#no").val(),'oldNo':$("#oldNo").val()},
				datatype:'json',
				success: function(data){
					if('true'==data){
						$('#userForm').data('bootstrapValidator').updateStatus('no', 'VALID','callback');
					}else{
						$('#userForm').data('bootstrapValidator').updateStatus('no', 'INVALID','callback');
					}
				}
			});
		}
	  });
	
//	//提示定位
	var prompt = $(".form-group .help-block")
	$("<div>").addClass("tooltip-arrow").appendTo(prompt.parent());
	prompt.css({"right": -15, "top": -27});
	$(".tooltip-arrow").show().css({"right": 13, "top": 0});

}

/**
 * 判断密码是否需要做非空验证
 * @param value
 * @returns {Boolean}
 */
function pswIsNull(value){
	var result = true;
	if($('#id').val()==''){
		if(value==''){
			result = false;
		}
	}
	return result;
}

/**
 * 点击事件
 */
function initButtonClick(){
	/**
	 * 选择角色按钮事件
	 */
	$("#allotrole").click(function(){
		//找出已分配的角色
//		$("#roleids").val("");
		$("#rolenames").val("");
		var uid =$("#roles button");
		var id="";
		for(var i=0;i<uid.length;i++){
				 id+=""+$(uid[i]).data("uid")+",";
		}
		//获取选择归属机构id
		var companyId=$("#companyId").val();
		//判断是否选择归属机构
		if(companyId==null || companyId==""){
			$('#userForm').data('bootstrapValidator').updateStatus('company.name', 'INVALID',null);
			return;
		}
		//根据归属机构查询归属机构信息
		$.ajax({
			url : system.getContextPath() + '/sys/office/getOffice',
			type : 'POST',
			async : false,
			data : {'id' : companyId},
			success : function(data) {
				$("#parentids").val(data.parentIds + data.id+",");
			}
		});
		var parentIds=$("#parentids").val();
		var data=[id,companyId,parentIds];
			//打开选择角色modal
			$.modal.showModalDialog({
				data : data,//参数
				title : '选择角色',
				width : '800',
				showBtn : true,
				btn : [ '确定', '取消' ],
				url : system.getContextPath() + "/sys/user/userRoleModal",
				success : function() {
				},
				error : function() {
				},yes: function(index,childData){
//					if(childData){
//						$('#userForm').data('bootstrapValidator').updateStatus('roles', 'VALID',null);
//					}else{
//						$('#userForm').data('bootstrapValidator').updateStatus('roles', 'INVALID',null);
//					}
					$.modal.close(index);
				}
			});
			
	});

	/**
	 * 角色按钮点击删除事件
	 */
	$(".row").on("click","#roles i",function(){
		$(this).parent().remove();
		var uid =$("#roles button");
//		if(uid.length==0){
//			$('#userForm').data('bootstrapValidator').updateStatus('roles', 'INVALID',null);
//		}
	});
}


/**
 * 初始化新增页面
 */
function add(){
	//归属公司显示
	$('#companyId').val($("#pOfficeId").val());
	$('#companyName').val($("#pOfficeName").val());
	//归属部门显示
	$('#officeId').val($("#officeId_").val());
	$('#officeName').val($("#officeName_").val());
	//归属机构按照归属公司显示
	$("#officeUrl").val("/sys/office/treeData?type=2&parentId="+$("#companyId").val());
}

/**
 * 初始化修改页面
 */
function modify(parentData){
	//查询用户关联角色列表，并选中
	$.ajax({
		url: system.getContextPath()+'/sys/user/userRole',
		type: 'POST',
		async: true,
		data:{'id':parentData[0].id},
		datatype:'json',
		success: function(data){
			$("#roles").text("");
			if(data!=null &&  data.length>0){
				for (var i = 0; i < data.length; i++) {
					$("#roles").append("<button class='btn input-sm' data-uid='"+data[i].id+"' type='button' id='"+data[i].id+"' style='background-color:#BC4DA0;color:#fff'>"+data[i].name+"<i class='icon-remove'></i></button> ");
				}
			}
			var uid =$("#roles button");
			 var id="";
			 for(var i=0;i<uid.length;i++){
				id+=$(uid[i]).data("uid")+",";
			 }
			 $("#roleids").val(id);
		}
	});
	
	/*
	 * 查询用户关联机构列表，并选中
	 */
	$.ajax({
		url: system.getContextPath()+'/sys/user/userOffice',
		type: 'POST',
		async: true,
		data:{'id':parentData[0].id},
		datatype:'json',
		success: function(data){
			if(data!=null && data!=''){
				var ids='',names='';
				$.each(jQuery.parseJSON(data), function(i, field){
					ids+=field.office.id+',';
					names+=field.office.name+',';
				   });
				$('#userOfficeId').val(ids.substring(0,ids.length-1));
				$('#userOfficeName').val(names.substring(0,names.length-1));
			}
		}
	});
}

/**
 * 提交表单
 */
function save(){
	// 清空角色所有ids
	$("#roleids").val("");
	var uid =$("#roles button");
	var id="";
	if(uid!=null){
	for(var i=0;i<uid.length;i++){
		 if(uid.length-1==i){
			 id+=""+$(uid[i]).data("uid")+"";
		 }else{
			 id+=""+$(uid[i]).data("uid")+",";
		 }
	}
	}
	// 角色所有ids
	 $("#roleids").val(id);
	 $.ajax({
		  type: "POST",
		  url: system.getContextPath()+"/sys/user/save",
		  processData:false,
		  data:$("#userForm").serialize(),
		  success: function(data){
			  $.modal.showPrompt({
					title:'提醒',
					content:data,
					time:2000
				});
			  $('#modify').prop('disabled', true);
			  $('#remove').prop('disabled', true);
			  $('#table').bootstrapTable("removeAll");
			  $('#table').bootstrapTable("refresh",{url:system.getContextPath()+"/sys/user/listData?officeId="+$("#officeId_").val()+"&officeName="+$("#officeName_").val()});
		  }
		 });
}

/**
 * 归属部门组件回调函数
 * @param v
 * @param nodes
 */
function officeTreeselectCallBack(v,nodes){
	if (v=="ok"){
		if(nodes.length>0){
			forChange(nodes[0].id);
		}
		var office = $("#officeName").val();
		var company = $("#companyName").val();
			if(office != '' && office != null){
				  $('#userForm').data('bootstrapValidator').updateStatus('office.name', 'VALID',null);
			}else{
				$('#userForm').data('bootstrapValidator').updateStatus('office.name', 'INVALID',null);
				return;
			}
			if(company != '' && company != null){
				  $('#userForm').data('bootstrapValidator').updateStatus('company.name', 'VALID',null);
			}else{
				$('#userForm').data('bootstrapValidator').updateStatus('company.name', 'INVALID',null);
				return;
			}
	}else if (v=="clear"){
		$('#userForm').data('bootstrapValidator').updateStatus('office.name', 'INVALID',null);
    }
}

/**
 * 归属部门带出上级公司
 * @param id
 */
function forChange(id){
	$.ajax({
		url : system.getContextPath() + '/sys/office/getParentOffice?id='+id,
		type : 'GET',
		async : false,
		success : function(data) {
			$("#companyId").val(data.parentId);
			$("#companyName").val(data.name);
			$("#officeUrl").val("/sys/office/treeData?type=2&parentId="+data.parentId);
		}
	});
}

/**
 * 归属公司组件回调函数
 * @param v
 * @param nodes
 */
function companyTreeselectCallBack(v,nodes){
	if (v=="ok"){
		var companyName = $("#companyName").val();
		var companyId = $("#companyId").val();
		$("#officeId").val("");
		$("#officeName").val("");
		var office = $("#officeName").val();
			if(companyId != '' && companyId != null){
				  $('#userForm').data('bootstrapValidator').updateStatus('company.name', 'VALID',null);
				  $("#officeUrl").val("/sys/office/treeData?type=2&parentId="+companyId);
			}else{
				$('#userForm').data('bootstrapValidator').updateStatus('company.name', 'INVALID',null);
				$("#officeUrl").val("/sys/office/treeData?type=2");
			}
			if(office != '' && office != null){
				  $('#userForm').data('bootstrapValidator').updateStatus('office.name', 'VALID',null);
			}else{
				$('#userForm').data('bootstrapValidator').updateStatus('office.name', 'INVALID',null);
			}
		$("#position").bindSelect({
			selectName:'position',
			select2Option : {
				allowClear : true,
				placeholder : '请选择'
			},
			url:system.getContextPath() + "/sys/user/positions?companyId="+$("#companyId").val()
		});
		// 清空角色所有ids
		$("#roleids").val("");
		$("#roles").empty();
//		$('#userForm').data('bootstrapValidator').updateStatus('roles', 'INVALID',null);
	}else if (v=="clear"){
		$('#userForm').data('bootstrapValidator').updateStatus('company.name', 'INVALID',null);
		$("#officeUrl").val("/sys/office/treeData?type=2");
    }
}

/**
 * 兼职部门回显
 * @param h
 */
function userOfficeTreeselectLoaded(tree){
	var userOfficeId = $("#userOfficeId").val();
	if(typeof(userOfficeId) != "undefined"){
		var ids = userOfficeId.split(",");
		for(var i=0; i<ids.length; i++) {
			var node = tree.getNodeByParam("id", ids[i]);
			try{tree.checkNode(node, true, false);}catch(e){}
		}
	}
}

