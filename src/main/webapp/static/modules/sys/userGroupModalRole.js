
/**
 * 汪涛
 * 2016年5月25日15:23:20
 */
// 绑定按钮    选择角色弹出的模态框确定按钮
var $roleTable = $('#roleTable'), $selectRole = $('#selectRole');

$(document).ready(function() {
	//父页面传子页面参数
	initRoleData();
	//初始化roleTable
	initTable();
	//初始化table数据
	initTableData();
	//初始化按钮
	initButton();
	
	$.modal.setBtnFun([ function() {
		//roletable页确定事件
		saveRole();
		$.modal.setChildData(true);
	}, function() {

	} ]);
		
});
/**
 * roletable页确定事件
 */
function saveRole(){
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
}
/**
 * 父页面传子页面参数
 */
function initRoleData(){
	var data = $.modal.getParentData();
	if (data!=null && data.length > 0) {
		$("#noRole").val(data[0]);
		$("#officeId").val(data[1]);
		$("#parentids").val(data[2]);
	}
}
/**
 * 初始化table数据
 */
function initTableData(){
	$('#roleTable').bootstrapTable("removeAll");
	$("#roleTable").bootstrapTable("refresh",{url:system.getContextPath() + "/sys/role/officefindRole"});
}
/**
 * 初始化roleTable
 */
function initTable(){
	//用户组变量
	var sys_data_scope = jQuery.parseJSON($('#sys_data_scope').text());
	//roleTable参数
	var roleParams = function(params) {
			var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, // 页面大小
				offset : params.offset, // 页码
				id : $("#officeId").val(), // roleTable所属机构查询角色
				parentIds : $("#parentids").val(),// roleTable
				noRole : $("#noRole").val(),
				rolenames : $("#rolenames").val(),
			};
			return temp;
	}
	$('#roleTable').bootstrapTable({
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
		queryParams : roleParams,
		columns : [ {
			checkbox : true
		},{
			field: 'Number',
			title: '序号',
			width: 15,
			formatter: function (value, row, index) {
			return index+1;
			}
		},  {
			field : 'name',
			title : '角色名称'
		}, {
			field : 'enname',
			title : '英文名称'
		}, {
			field : 'office.name',
			title : '归属机构'
		}, {
			title : '数据范围',
			formatter : function(value, row, index) {
				return getDictLabel(sys_data_scope, row.dataScope, "无");
			}
		} ]
	});
	
}
/**
 *初始化按钮
 */
function initButton(){
	//重置按钮
	$("#resetRole").click(function(){
		$("#rolenames").val('');
	});
	//查询
	$("#from_findRole").click(function() {
		initTableData();
	});
	//绑定选择角色按钮
	$selectRole.click(function() {
		//选择角色点击事件
		selectRole();
	});
}
/**
 * 选择角色点击事件
 */
function selectRole(){
	var obj = $('#roleTable').bootstrapTable('getSelections');
	var uid = $("#roles button");
	var falg = true;
	//判断选择的角色
	for (var i = 0; i < obj.length; i++) {
		for (var j = 0; j < uid.length; j++) {
			if ($(uid[j]).data("uid") == obj[i].id) {
				falg = false;
				break;
			} else {
				falg = true;
			}
	}
	if (falg) {
		//添加选择的角色按钮
		$("#roles").append("<button class='btn input-sm' data-uid='"
								+ obj[i].id
								+ "' type='button' id='"
								+ obj[i].id
								+ "' style='background-color:#BC4DA0;color:#fff'>"
								+ obj[i].name
								+ "<i class='icon-remove'></i></button> ");
	}
	}
}