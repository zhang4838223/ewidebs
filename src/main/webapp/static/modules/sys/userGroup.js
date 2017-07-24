/**
 * wangtao
 * 2016年5月19日14:12:15
 */
// 表头自适应
$(window).resize(function() {
	$('#table').bootstrapTable('resetView',{height:$.rlLayout.getBsHeight()});
});

//绑定按钮
var $table = $('#table'), $remove = $('#remove'), $modify = $('#modify'), $add = $('#add');
//table第几页
var _params;

//加载事件
$(document).ready(function() {
	//初始化table
	initTable();
	//初始化按钮
	initButton();
	//modal按钮事件
	initButtonModal();
	//初始化数据
	initData();
});
/**
 * 初始化数据
 * @returns
 */
function initData(){
	$modify.prop('disabled', true);
	$remove.prop('disabled', true);
	$('#table').bootstrapTable("removeAll");
	$('#table').bootstrapTable("refresh",{url : system.getContextPath() + "/sys/userGroup/data",query : _params});
	$('#table').bootstrapTable('selectPage',calculatePage(_params));
}
/**
 * 初始化table
 */
function initTable(){
	//table参数
	var tableParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			name : $("#name").val(),// table 条件查询
			'company.id' : $("#companysId").val(),// table 条件查询
			'company.name' : $("#companysName").val(),// table 条件查询
			code : $("#code").val()// table 条件查询
		};
		 _params = temp;
		return temp;
	}
	$('#table').bootstrapTable({
		dataType : "json",// 请求后台的URL（*）
		method : "get",// 请求方式（*）
		striped : true,// 是否显示行间隔色
		clickToSelect : true,// 是否启用点击选中行
		height : $.rlLayout.getBsHeight(), // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		pagination : true,// 是否分页
		pageSize : 20,// 一页显示多少列
		pageList : [  20, 25 ],
		pageNumber : 1,// 当前第几页
		sidePagination : "server",
		showColumns : true,// 表头右边小工具
		minimumCountColumns : 2,// 最小只剩2个
		toolbar : "#toolbar",// 把自定义工具放在表头容器
		showHeader : true,// 是否显示表标题
		queryParams : tableParams,
		columns : [ {
			checkbox : true
		},{
			field: 'Number',
			title: '序号',
			width: 15,
			formatter: function (value, row, index) {
			return index+1;
			}
		}, {
			field : 'name',
			title : '组名称'
		}, {
			field : 'code',
			title : '组编号'
		}, {
			field : 'company.name',
			title : '归属公司'
		}, {
			field : 'sort',
			title : '排序'
		}, {
			field : 'remarks',
			width : 300,
			title : '备注',
				formatter : function(value, row, index){
					if(row.remarks==null){
						return row.remarks;
					}else{
						return "<div title="+row.remarks+" style='width : 300px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: center;'>"+row.remarks+"</div>";
					}
				}
		}

		]
	});
	$table.on('check.bs.table uncheck.bs.table '
			+ 'check-all.bs.table uncheck-all.bs.table', function() {
		$remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
		if ($table.bootstrapTable('getSelections').length == 1) {
			$modify.prop('disabled',!$table.bootstrapTable('getSelections').length);
		} else {
			$modify.prop('disabled', true);
		}
		selections = getIdSelections();
	});
}

/**
 * 初始化按钮
 */
function initButton(){
	//重置事件
	$("#reset").click(function(){
		$("#name").val('');
		$("#code").val('');
		$("#companysId").val('');
		$("#companysName").val('');
	});
	//高级查询
	$("#from_finduserGroup").click(function() {
		initData();
	});
}
/**
 * modal按钮事件
 */
function initButtonModal(){
	//新增
	$add.click(function() {
		//新增模态框
		addOnclick();
	});
	//修改
	$modify.click(function() {
		//修改模态框
		modifyOnclick();
		});
	//删除
	$remove.click(function() {
		//删除模态框
		removeOnclick();
	});
}

/**
 * 新增
 */
function addOnclick(){
	$.modal.showModalDialog({
		data:[], 
		title : '新增用户组',
		showBtn : true,
		width : '800',
		btn : [ '确定', '取消' ],
		url : system.getContextPath() + "/sys/userGroup/modal",
		yes : function(index, falg) {
			if(falg){
				//关闭模态框
				$.modal.close(index);
			}
		},
		cancel : function(index, childData) {

		}
	});
}
/**
 * 修改
 */
function modifyOnclick(){
	var obj = $('#table').bootstrapTable('getSelections');
	var userid="";
	$.ajax({
		url: system.getContextPath()+'/sys/userGroup/form',
		type: 'POST',
		async: false,
		data:{'ids':obj[0].id},
		success: function(data){
			if(data.userList!=null &&  data.userList.length>0){
				for (var i = 0; i < data.userList.length; i++) {
					userid+=data.userList[i].id+",";
				}
				userid=userid;
			}
		}
		});
	var data=[userid];
	$.modal.showModalDialog({
		data:data,
		title : '修改用户组',
		width : '800',
		showBtn : true,
		btn : [ '确定', '取消' ],
		url : system.getContextPath() + "/sys/userGroup/modal?id="+obj[0].id,
		yes : function(index, falg) {
			if(falg){
				//关闭模态框
				$.modal.close(index);
			}
		},
		cancel : function(index, childData) {
		}
	});
}
/**
 * 删除
 */
function removeOnclick(){
	var id = getIdSelections() + ",";
	$.modal.showConfirm({
		title : '提示',
		content : "确定删除吗？",
		icon : 2,
		yes : function(index) {
			var id = getIdSelections() + ",";
			$.post(system.getContextPath() + "/sys/userGroup/delete", {"ids" : id}, function() {
				$.modal.close(index);
				$.modal.showPrompt({
					title : '提示',
					content : "删除成功!",
					time : 1500
				});
				initData();
			});
		},
		cancel : function(index) {
		}
	});
}

/**
 * 选择行返回id
 * @returns
 */
function getIdSelections() {
	return $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id
	});
}
