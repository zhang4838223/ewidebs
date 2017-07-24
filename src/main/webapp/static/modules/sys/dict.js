/**
 * Created by wangtao on 2016/3/23.
 */
// 表头自适应
$(window).resize(function() {
	$('#table').bootstrapTable('resetView',{height:$.rlLayout.getBsHeight()});
});
//绑定按钮
var $table = $('#table'), $remove = $('#remove'), $modify = $('#modify'), $add = $('#add'), 
	$removeType = $('#removeType'), $modifyType = $('#modifyType'), $addType = $('#addType');
//table第几页
var _params;
//加载
$(document).ready(function() {
	//初始化table
	initTable();
	//初始化按钮
	initButton();
	//初始化模态框按钮
	initButtonModal();
	//初始化数据
	initData();
});
/**
 * 初始化模态框按钮
 */
function initButtonModal(){
	//新增类型
	$addType.click(function() {
		addTypeOnclick();
	});
	//删除类型
	$removeType.click(function() {
		removeTypeOnclick();
	});
	//修改类型
	$modifyType.click(function() {
		modifyTypeOnclick();
	});
	//新增字典项
	$add.click(function() {
		addOnclick();
	});
	//修改字典项
	$modify.click(function() {
		modifyOnclick();
	});
	//删除字典项
	$remove.click(function() {
		removeOnclick();
	});
}
/**
 * 初始化按钮
 */
function initButton(){
	//查询字典类型
	$("#from_findDict").click(function() {
		$('#table').bootstrapTable("removeAll");
		refreshTree();
	});
	//刷新树
	$("#refreshTree").click(function(){
		refreshTree();
	});
}
/**
 * 初始化table
 */
function initTable(){
	// table参数
	var queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			parentId : $("#parentId").val(),
		};
		 _params = temp;
		return temp;
	}
	$('#table').bootstrapTable({
		dataType : "json",// 请求后台的URL（*）
		method : "get",// 请求方式（*）
		striped : true,// 是否显示行间隔色
		clickToSelect : true,// 是否启用点击选中行
		height : $.rlLayout.getBsHeight(),
		pagination : true,// 是否分页
		pageSize : 20,// 一页显示多少列
		pageList : [ 20, 25 ],
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
			field : 'label',
			title : '字典名称'
		}, {
			field : 'value',
			title : '字典值',
			width :200,
			formatter : function(value, row, index){
				if(value==null){
					return value;
				}else{
					return "<div title="+value+" style='width : 200px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: center;'>"+value+"</div>";
				}
			}
		}, {
			field : 'orLabel',
			title : '上级字典',
		}, {
			field : 'sort',
			title : '排序',
		}, {
			field : 'remarks',
			title : '描述',
			width :200,
			formatter : function(value, row, index){
				if(value==null){
					return value;
				}else{
					return "<div title="+value+" style='width : 200px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: center;'>"+value+"</div>";
				}
			}
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
/**
 * 新增类型模态框
 */
function addTypeOnclick(){
	//获取根节点的id
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = treeObj.getNodes();
	var data=[nodes[0].id];
	$.modal.showModalDialog({
		data : data,
		title : '新增字典类型',
		showBtn : true,
		btn : [ '确定', '取消'],
		url : system.getContextPath() + "/sys/dict/modalType",
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
 * 删除类型模态框
 */
function removeTypeOnclick(){
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = treeObj.getSelectedNodes();
	//获取选择行的ids
	var content='确定删除吗？';
	$.ajax({
		type : "POST",
		url : system.getContextPath() + "/sys/dict/queryNode",
		processData : true,
		async: false,
		data : {'ids':nodes[0].id},
		success : function(data) {
			if(data){
				content=nodes[0].name+'下有字典项是否全部删除？';
			}
		}
	});
	$.modal.showConfirm({
		title : '提示',
		content : content,
		icon : 2,
		yes : function(index) {
			$.post(system.getContextPath() + "/sys/dict/deleteType", {"id" : nodes[0].id}, function() {
				$.modal.close(index);
				$.modal.showPrompt({
					title : '提示',
					content : "删除成功!",
					time : 1500
				});
				refreshTree();
			});
			
		},
		cancel : function(index) {
		}
	});
}
/**
 * 修改类型模态框
 */
function modifyTypeOnclick(){
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = treeObj.getSelectedNodes();
	$.modal.showModalDialog({
		data : [],
		title : '修改字典类型',
		showBtn : true,
		btn : [ '确定', '取消' ],
		url : system.getContextPath() + "/sys/dict/modalType?id="+nodes[0].id,
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
 * 新增字典项模态框
 */
function addOnclick(){
	var data=[$("#parentId").val()];
	$.modal.showModalDialog({
		data : data,
		title : '新增字典项',
		showBtn : true,
		btn : [ '确定', '取消'],
		url : system.getContextPath() + "/sys/dict/modal",
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
 * 修改字典项
 */
function modifyOnclick(){
	var obj = $table.bootstrapTable('getSelections');
	$.modal.showModalDialog({
		data : [],
		title : '修改字典项',
		showBtn : true,
		btn : [ '确定', '取消'],
		url : system.getContextPath() + "/sys/dict/modal?id=" + obj[0].id,
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
 * 删除字典项模态框
 */
function removeOnclick(){
	var obj = $table.bootstrapTable('getSelections');
	$.modal.showConfirm({
		title : '提示',
		content : "确定删除吗？",
		icon : 2,
		yes : function(index) {
			var id = getIdSelections() + ",";
			$.post(system.getContextPath() + "/sys/dict/delete", {"id" : id}, function() {
				$.modal.close(index);
				$.modal.showPrompt({
					title : '提示',
					content : "删除成功!",
					time : 1500
				});
				var treeObj = $.fn.zTree.getZTreeObj("ztree");
				var nodes = treeObj.getSelectedNodes();
				refreshTable(nodes[0]);
			});
			
		},
		cancel : function(index) {
		}
	});
}
/**
 * 初始化数据
 */
function initData(){
	//加载树
	refreshTree();
}
/**
 * 加载table
 */
function refreshTable(treeNode){
	if(treeNode==null){
		//获取选择节点的对象
		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		var nodes = treeObj.getSelectedNodes();
		if(nodes[0]!=null){
			treeNode={'name':nodes[0].name,'value':nodes[0].value,'remarks':nodes[0].remarks,'id':nodes[0].id};
		}
	}
	if(treeNode!=null){
		var id = treeNode.id == '1' ? '' : treeNode.id;
		$("#parentId").val(id);
		// 判断新增按钮是否显示
		if (id != '') {
			$add.prop('disabled', false);
			$("#modifyType").prop('disabled', false);
			$("#removeType").prop('disabled', false);
			
		} else {
			$add.prop('disabled', true);
			$("#modifyType").prop('disabled', true);
			$("#removeType").prop('disabled', true);
		}
		$("#labels").val(treeNode.name);
		$("#values").val(treeNode.value);
		$("#remarkss").val(treeNode.remarks);
		$("#modify").prop('disabled', true);
		$("#remove").prop('disabled', true);
		$('#table').bootstrapTable("removeAll");
		$('#table').bootstrapTable("refresh",{url:system.getContextPath() + "/sys/dict/data.do?dictType=VALUE",query : _params});
		$('#table').bootstrapTable('selectPage',calculatePage(_params));
	}else{
		$("#labels").val("");
		$("#values").val("");
		$("#remarkss").val("");
		$('#table').bootstrapTable("removeAll");
	}
}
/**
 * 加载树
 */
function refreshTree() {
	//获取选择树节点对象
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = null;
	if(treeObj!=null){
		nodes=treeObj.getSelectedNodes();
	}
	var setting = {
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : '1'
				}
			},
			callback : {//单击树事件
				onClick : function(event, treeId, treeNode) {
					refreshTable(treeNode);
				}
			} ,view: {
				selectedMulti: false 
			}
		};
	//获取条件
	var label =encodeURI($("#selectlabel").val(),"utf-8");
	$.getJSON(system.getContextPath() + "/sys/dict/treeData?dictType=TYPE&label=" + label,
			function(data) {
				$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
				var zTree = $.fn.zTree.init($("#ztree"), setting, data);
				// 获取根节点
				/*var nodes = zTree.getNodes();
				//默认选择根节点
				zTree.selectNode(nodes);
				//获取选择节点的对象
				var nodes = zTree.getSelectedNodes();
				//加载table
				refreshTable(nodes[0][0]);*/
				//判断刷新树之前是否选择节点
				if(nodes!=null && nodes[0]!=null && nodes[0].id!=null){
					var node = zTree.getNodeByParam("id", nodes[0].id);
					zTree.selectNode(node);
					refreshTable(node);
				}
				//展开所有节点
				zTree.expandAll(true); 
			});
}
