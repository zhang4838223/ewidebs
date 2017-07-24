/**
 * wangtao
 * 2016年5月23日10:04:22
 */
//加载事件
$(document).ready(function() {
	//父页面传子页面的参数
	initUserGroupData();
	//初始化userTable
	initTable();
	//初始化按钮
	initButton();
	//初始化Table数据
	initUserTableData();
	//初始化表单验证
	bootstrapValidator();
	
	//模态框按钮绑定事件
	$.modal.setBtnFun([ function() {
		 //新增
		 userGroupSave();
	}, function() {

	}]);
	
	
	
	
});
/**
 * 初始化Table数据
 */
function initUserTableData(){
	//刷新userTable
	$('#userTable').bootstrapTable("removeAll");
	$('#userTable').bootstrapTable("refresh",{url:system.getContextPath() + "/sys/user/userByIds.do"});
}
/**
 * 更新用户组
 */
function userGroupSave(){
	//表单验证
	$('#userGroupForm').data('bootstrapValidator').validate();
	if(!$('#userGroupForm').data('bootstrapValidator').isValid())
	{
	$.modal.setChildData(false);
	return;// 直接return 不做后续任何操作
	}
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
		  url: system.getContextPath()+"/sys/userGroup/save",
		  processData:true,
		  data:$("#userGroupForm").serialize(),
		  success: function(){
				$.modal.showPrompt({
					title : '提示',
					content : "保存成功!",
					time : 1500
				});
				initData();
		  }
		 });
	    $.modal.setChildData(true);
	    //刷新父页面table
	 	
}

/**
 * 父页面传子页面的参数
 */
function initUserGroupData(){
	var data=$.modal.getParentData();
	if(data!=null && data.length>0){
		$("#userids").val(data[0]);
	}
}
/**
 * 初始化userTable
 */
function initTable(){
	//userTable参数
	var userParams = function(params) {
			var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, // 页面大小
				offset : params.offset, // 页码
				ids : $("#userids").val(),// 用户table
			};
			return temp;
		}
	$('#userTable').bootstrapTable({
		dataType : "json",// 请求后台的URL（*）
		method : "get",// 请求方式（*）
		striped : true,// 是否显示行间隔色
		clickToSelect : true,// 是否启用点击选中行
		height : 380, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		pagination : true,// 是否分页
		pageSize : 10,// 一页显示多少列
		pageNumber : 1,// 当前第几页
		showHeader : true,// 是否显示表标题
		detailView : false,// 细节视图
		detailFormatter : "detailFormatter",// 细节视图返回的字符串
		queryParams : userParams,
		columns : [ 
		 {
			field: 'Number',
			title: '序号',
			width: 15,
			formatter: function (value, row, index) {
			return index+1;
			}
	     }, 
	     {
			field : 'company.name',
			title : '归属公司'
		}, {
			field : 'office.name',
			title : '归属部门'
		}, {
			field : 'loginName',
			title : '登录名'
		}, {
			field : 'name',
			title : '姓名'
		}, {
			field : 'phone',
			title : '电话'
		}, {
			field : 'mobile',
			title : '手机'
		}

		]
	});
}

/**
 * 初始化按钮
 */
function initButton(){
	//选择人员
	$("#allotuser").click(function() {
		allotuser();
	});
	//选择角色
	$("#allotrole").click(function() {
		allotrole();
	});
	//已选角色按钮删除
	$(".row").on("click",".buttonx i",function(){
		$(this).parent().remove();
	});
}
/**
 * 选择人员
 */
function allotuser(){
	//获取选择归属机构id
	var officeId=$("#companyId").val();
	//判断是否选择归属机构
	if(officeId==null || officeId==""){
		$('#userGroupForm').data('bootstrapValidator').updateStatus('company.name', 'INVALID',null);
		return;
	}
	var userids=$("#userids").val();
	 top.$.jBox.open("iframe:"+system.getContextPath()+"/sys/userGroup/usergrouptouser?ids="+userids+"&officeId="+officeId, "选择成员",1100,$(top).height()*0.7,{
		buttons:{"确定分配":"ok", "清除已选":"clear", "关闭":true}, bottomText:"通过选择部门，然后为列出的人员分配用户组。",submit:function(v, h, f){
			var ids = h.find("iframe")[0].contentWindow.ids;
			var pre_ids = h.find("iframe")[0].contentWindow.pre_ids;
			//绑定按钮
			if (v=="ok"){
				$("#userids").val("");
				if(ids!=null && ids!=""){
					$("#userids").val(ids+",");
				}
				initUserTableData();
		    	return true;
			} else if (v=="clear"){
				h.find("iframe")[0].contentWindow.clearAssign();
				return false;
          }
		}, loaded:function(h){
			$(".jbox-content", top.document).css("overflow-y","hidden");
		}
	}); 
	/*$.modal.showModalDialog({
		data : [],//参数
		title : '选择角色',
		width : '800',
		height : '600',
		showBtn : true,
		btn : [ '确定', '取消' ],
		url : system.getContextPath() + "/sys/userGroup/modalUser",
		yes : function(index, childData) {
			//关闭模态框
			$.modal.close(index);
		},
		cancel : function(index, childData) {

		}
	});*/
}
/**
 * 选择角色
 */
function allotrole(){
	$("#roleids").val("");
	$("#rolenames").val("");
	var uid =$("#roles button");
	var id="";
	for(var i=0;i<uid.length;i++){
			 id+=""+$(uid[i]).data("uid")+",";
	}
	//获取选择归属机构id
	var officeId=$("#companyId").val();
	//判断是否选择归属机构
	if(officeId==null || officeId==""){
		$('#userGroupForm').data('bootstrapValidator').updateStatus('company.name', 'INVALID',null);
		return;
	}
	//根据归属机构查询归属机构信息
	$.ajax({
		url : system.getContextPath() + '/sys/office/getOffice',
		type : 'POST',
		async : false,
		data : {'id' : officeId},
		success : function(data) {
			$("#parentids").val(data.parentIds + data.id+",");
		}
	});
	var parentIds=$("#parentids").val();
	var data=[id,officeId,parentIds];
	$.modal.showModalDialog({
		data : data,//参数
		title : '选择角色',
		showBtn : true,
		btn : [ '确定', '取消' ],
		url : system.getContextPath() + "/sys/userGroup/modalRole",
		yes : function(index, childData) {
			//关闭模态框
			$.modal.close(index);
		},
		cancel : function(index, childData) {

		}
	});
}
/**
 * 初始化表单验证
 */
function bootstrapValidator(){
	$('#userGroupForm').bootstrapValidator(
			{
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					name : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '用户组名称不能为空！'// 这是提示信息
							},
							stringLength: {
					             min: 1,
					             max: 10,
					             message: '用户组名称长度必须在1到10位之间！'
								           },
								callback : {
									message : '用户组名已存在！',
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
					code : {// 这里为输入框的name
						validators : {
							notEmpty : {// 校验名称
								message : '用户组编号不能为空！'// 这是提示信息
							},stringLength: {
					               min: 1,
					               max: 10,
					               message: '用户组编号长度必须在1到10位之间！'
					             },callback : {
										message : '用户组编号已存在！',
										callback : function(value, validator) {
											return true;
										}
									}
						}
					},
					'company.name' : {// 这里为输入框的name
					 validators : {
						notEmpty : {// 校验名称
							message : '归属机构不能为空！'// 这是提示信息
						}
					}
				  } ,
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
					}
				}
		    });
	//查询用户组名是否已存在
	$("#codes").blur(function(){
		var code=$("#codes").val();
		//判断name是否存在
		var noCode=$("#noCode").val();
		if(code!=noCode){
		$.ajax({
			url: system.getContextPath() + "/sys/userGroup/findNameOrCode",
			type: 'GET',
			async: false,
			data:{'code':code,'noCode':noCode},
			datatype:'json',
			success: function(data){
				if('true'==data){
					$('#userGroupForm').data('bootstrapValidator').updateStatus('code', 'VALID',null);
				}else if('false'==data){
					$('#userGroupForm').data('bootstrapValidator').updateStatus('code', 'INVALID','callback');
				}
			}
		});
		}
	  });
	//查询用户组名是否已存在
	$("#names").blur(function(){
		var name=$("#names").val();
		//判断name是否存在
		var noName=$("#noName").val();
		if(name!=noName){
		$.ajax({
			url: system.getContextPath() + "/sys/userGroup/findNameOrCode",
			type: 'GET',
			async: false,
			data:{'name':name,'noName':noName},
			datatype:'json',
			success: function(data){
				if('true'==data){
					$('#userGroupForm').data('bootstrapValidator').updateStatus('name', 'VALID','callback');
				}else if('false'==data){
					$('#userGroupForm').data('bootstrapValidator').updateStatus('name', 'INVALID','callback');
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
/**
 * 回调函数
 * @param v
 * @param nodes
 */
function companyTreeselectCallBack(v,nodes){
	//判断提示归属机构验证
	if (v=="ok"){
		$("#roles").text("");
		if(nodes!=null && nodes.length == 0){
			$('#userGroupForm').data('bootstrapValidator').updateStatus('company.name', 'INVALID',null);
		}else{
			$('#userGroupForm').data('bootstrapValidator').updateStatus('company.name', 'VALID',null);
		}
	}else if (v=="clear"){
			$('#userGroupForm').data('bootstrapValidator').updateStatus('company.name', 'INVALID',null);
    }
	$('#userTable').bootstrapTable("removeAll");
	$("#userids").val("");
}
