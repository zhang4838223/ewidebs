// 表头自适应
$(window).resize(function() {
	$('#table').bootstrapTable('resetView',{height:$.rlLayout.getBsHeight()});
});
//绑定按钮
var $table = $('#table'), $remove = $('#remove'), $modify = $('#modify'), $add = $('#add');
//table第几页
var _params;
$(document).ready(function() {
	//初始化table
	initTable();
	//初始化button
	initButton();
	//初始化模态框Button
	initButtonModal();
	//初始化table数据
	initData();
});
//初始化table数据
function initData(){
	//刷新table修改删除不可点
	$modify.prop('disabled', true);
	$remove.prop('disabled', true);
	$('#table').bootstrapTable("removeAll");
	$('#table').bootstrapTable("refresh",{url:system.getContextPath() + "/sys/subsystem/data",query : _params});
	$('#table').bootstrapTable('selectPage',calculatePage(_params));
}
/**
 * 初始化按钮
 */
function initButton(){
	///重置
	$("#reset").click(function(){
		$("#systemCodes").val('');
		$("#systemNames").val('');
	});
	//查询
	$("#from_findsubsystem").click(function() {
		initData();
	});
}
/**
 * 初始化模态框按钮
 */
function initButtonModal(){
	//新增
	$add.click(function() {
		addOnclick();
	});
	//修改
	$modify.click(function() {
		modifyOnclick();
	});
	//删除
	$remove.click(function() {
		removeData();
	});
}
/**
 * 删除模态框
 */
function removeData(){
	var obj = $table.bootstrapTable('getSelections');
	$.modal.showConfirm({
		title : '提示',
		content : "确定删除吗？",
		icon : 2,
		yes : function(index) {
			var id = getIdSelections() + ",";
			$.post(system.getContextPath() + "/sys/subsystem/delete", {"id" : id}, function() {
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
 * 修改模态框
 */
function modifyOnclick(){
	var obj = $table.bootstrapTable('getSelections');
	$.modal.showModalDialog({
		title : '修改子系统',
		showBtn : true,
		btn : [ '确定', '取消'],
		url : system.getContextPath() + "/sys/subsystem/modal?id=" + obj[0].id,
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
 * 新增模态框
 */
function addOnclick(){
	$.modal.showModalDialog({
		title : '新增子系统',
		showBtn : true,
		btn : [ '确定', '取消'],
		url : system.getContextPath() + "/sys/subsystem/modal",
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
 * 初始化table
 */
function initTable(){
	var queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			systemName : $("#systemNames").val(),
			systemCode : $("#systemCodes").val(),
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
		pageList : [ 20, 50 ],
		pageNumber : 1,// 当前第几页
		sidePagination : "server",
		showColumns : true,// 表头右边小工具
		minimumCountColumns : 2,// 最小只剩2个
		toolbar : "#toolbar",// 把自定义工具放在表头容器
		showHeader : true,// 是否显示表标题
		detailView : false,// 细节视图
		detailFormatter : "detailFormatter",// 细节视图返回的字符串
		queryParams : queryParams,
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
			field : 'systemCode',
			width : 200,
			title : '子系统编号',
			formatter : function(value, row, index){
				if(value==null || value.length<=15){
					return value;
				}else{
					return "<div title="+value+" style='width : 200px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: left;'>"+value+"</div>";
				}
			}
		}, {
			field : 'systemName',
			title : '子系统名称'
		}, {
			field : 'url',
			width : 200,
			title : '链接地址',
			formatter : function(value, row, index){
				if(value==null || value.length<=15){
					return value;
				}else{
					return "<div title="+value+" style='width : 200px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: left;'>"+value+"</div>";
				}
			}
		}, {
			field : 'remarks',
			width : 200,
			title : '备注',
				formatter : function(value, row, index){
					if(value==null || value.length<=15){
						return value;
					}else{
						return "<div title="+value+" style='width : 200px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: left;'>"+value+"</div>";
					}
				}
		}, {
			field : 'createDate',
			title : '创建时间'
		}, {
			field : 'createByName',
			title : '创建人'
		}, {
			field : 'updateDate',
			title : '修改时间'
		}, {
			field : 'updateByName',
			title : '修改人'
		} ]
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
 * 选择行返回ids
 * @returns
 */
function getIdSelections() {
	return $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id
	});
}
