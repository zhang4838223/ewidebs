/*
 * Yan Xin 2016/05/26
 */

/**
 * 页面加载事件
 */
$(document).ready(function() {
	//初始化参数
	initRoleData();
	//初始化按钮
	initRoleButton();
	//初始化角色列表
	initRoleTable();
	//初始化按钮点击事件
	initRoleClick();
});

/**
 * 初始化参数
 */
function initRoleData(){
	var data = $.modal.getParentData();
	if (data!=null && data.length > 0) {
		$("#noRole").val(data[0]);
		$("#companyId").val(data[1]);
		$("#parentids").val(data[2]);
	}
}

/**
 * 初始化按钮
 */
function initRoleButton(){
	$.modal.setBtnFun([ function() {//对应yes
		//显示选中的角色
		setRoles();
	}, function() {//对应no
		 
	}]);
}

/**
 * 初始化角色列表
 */
function initRoleTable(){
	//用户组变量
	var sys_data_scope = jQuery.parseJSON($('#sys_data_scope').text());
	var  queryParams = function(params){
		 var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		            limit: params.limit,   //页面大小
		            offset: params.offset,  //页码
		            id : $("#companyId").val(), // roleTable归属公司ID
					parentIds : $("#parentids").val(),// roleTable归属公司父级ID
					noRole : $("#noRole").val(),
					rolenames : $("#rolenames").val(),//已选择过的角色名
		        };
		 return temp;
		}

		$('#roleTable').bootstrapTable({
		url:system.getContextPath()+"/sys/role/officefindRole",  
		dataType : "json",//请求后台的URL（*）
		method : "get",//请求方式（*）
		striped : true,//是否显示行间隔色
		clickToSelect : true,//是否启用点击选中行
		height : 400, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		pagination : true,//是否分页
		 pageSize : 20,//一页显示多少列
		 pageList : [10,20,50],
		pageNumber : 1,//当前第几页
		showHeader : true,//是否显示表标题
		detailView : false,//细节视图
		detailFormatter : "detailFormatter",//细节视图返回的字符串
		queryParams:queryParams,
			columns: [
		     {	
		    	checkbox:true
		    },{
				field: 'Number',
				title: '序号',
				width: 15,
				formatter: function (value, row, index) {
				return index+1;
				}
			},{
		        field: 'name',
		        title: '角色名称'
			},{
		        field: 'roleCode',
		        title: '角色编号'
		    },{
		        field: 'office.name',
		        title: '归属公司'
		    } ,{
		    	title: '数据范围',
		    	formatter : function(value, row, index) {
		    		return getDictLabel(sys_data_scope,row.dataScope,"无");
				}
		    }
		]
		});
}

/**
 * 查询重置按钮点击事件
 */
function initRoleClick(){
	/**
	 * 查询按钮事件
	 */
	$("#from_findRole").click(function(){
		$('#roleTable').bootstrapTable("removeAll");
		  $('#roleTable').bootstrapTable("refresh",{url:system.getContextPath()+"/sys/role/officefindRole"});
	});

	/**
	 * 重置按钮事件
	 */
	$("#from_resetRole").click(function(){
		$("#rolenames").val("");
	});
}

/**
 * 将选中的角色显示在角色栏中
 */
function setRoles(){
	var obj = $('#roleTable').bootstrapTable('getSelections');
	var uid =$("#roles button");
	var insert = true;
	for (var i = 0; i < obj.length; i++) {
		 for(var j=0;j<uid.length;j++){
			 if($(uid[j]).data("uid")==obj[i].id){
				 insert = false;
				 break;
			 }else{
				 insert = true;
			 }
		}
		if(insert){
			 $("#roles").append("<button class='btn input-sm' data-uid='"+obj[i].id+"' type='button' id='"+obj[i].id+"' style='background-color:#BC4DA0;color:#fff'>"+obj[i].name+"<i class='icon-remove'></i></button> ");
		}
	 }
	if($("#roles button").length>0){
		$.modal.setChildData(true);
	}else{
		$("#roles").html("");
		$.modal.setChildData(false);
	}
}
