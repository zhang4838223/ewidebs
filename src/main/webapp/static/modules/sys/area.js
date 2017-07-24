/**
 * wangtao
 * 2016年5月19日14:00:49
 */
// 表头自适应
$(window).resize(function() {
	$('#table').bootstrapTable('resetView',{height:$.rlLayout.getBsHeight()});
});
//绑定按钮
var $table = $('#table'), $remove = $('#remove'), $modify = $('#modify'), $add = $('#add');
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
	//初始化下拉框
	initSel();
	//初始化数据
	initData();
});
/**
 * 初始化下拉框
 */
function initSel(){
$("#typeSel").bindDict({
	dictTypeName:"sys_area_type"
});
}
/**
 * 初始化数据
 */
function initData(){
	//加载树
	refreshTree();
	//加载table
	refreshTable();
}

/**
 * 加载table
 */
function refreshTable(treeNode){
	//刷新table修改删除不可点
	$modify.prop('disabled', true);
	$remove.prop('disabled', true);
	//清空table数据
	$('#table').bootstrapTable("removeAll");
	if(treeNode!=null){
		$('#table').bootstrapTable("refresh",{url : system.getContextPath() + "/sys/area/data.do?id="+ treeNode.id + "&parentIds="+ treeNode.pIds,query : _params});
		$('#table').bootstrapTable('selectPage',calculatePage(_params));
	}else{
		$('#table').bootstrapTable("refresh",{url : system.getContextPath() + "/sys/area/data.do",query : _params});
		$('#table').bootstrapTable('selectPage',calculatePage(_params));
	}
}
/**
 * 初始化模态框按钮
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
 * 删除模态框
 */
function removeOnclick(){
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var obj = $('#table').bootstrapTable('getSelections');
	var content='';
	var bool=false;
	if(obj.length>0){
		for(var i=0;i<obj.length;i++){
			var value = treeObj.getNodeByParam("id", obj[i].id, null);
			if(value!=null && value.children!=null &&value.children.length>0){
				if(content!=''){
					content+='、';
				}
				content+='“'+obj[i].name+'”';
				bool=true;
			}
		}
	}
	if(bool){
		content+='的下级区域会被级联删除，请确定！';
	}else{
		content='确定删除吗？';
	}
	//获取选择行的ids
	var id = getIdSelections() + ",";
	$.modal.showConfirm({
		title : '提示',
		content : content,
		icon : 2,
		yes : function(index) {
			$.post(system.getContextPath() + "/sys/area/delete", {
				"id" : id
			}, function() {
				$.modal.close(index);
				$.modal.showPrompt({
					title : '提示',
					content : "删除成功!",
					time : 1500
				});
				//刷新树
				refreshTree();
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
	//获取选择树节点对象
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = treeObj.getSelectedNodes();
	var data=[];
	//判断是否选择节点
	if(nodes[0]!=null){
		data=[nodes[0].id,nodes[0].pIds];
	}
	var obj = $('#table').bootstrapTable('getSelections');
	$.modal.showModalDialog({
		data:data,
		title : '修改区域',
		showBtn : true,
		btn : [ '确定', '取消'],
		url : system.getContextPath() + "/sys/area/modal?id="+obj[0].id,
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
	//获取选择树节点对象
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = treeObj.getSelectedNodes();
	var data=[];
	//判断是否选择节点
	if(nodes[0]!=null){
		data=[nodes[0].id,nodes[0].name,nodes[0].pIds];
	}
	$.modal.showModalDialog({
		data:data,
		title : '新增区域',
		showBtn : true,
		btn : [ '确定', '取消'],
		url : system.getContextPath() + "/sys/area/modal",
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
	//table参数
	var queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			name : $("#names").val(),
			code : $("#codes").val(),
			type : $("#typeSel").val(),
		};
		 _params = temp;
		return temp;
	}
	//初始化table数据
	var sys_area_types = jQuery.parseJSON($('#sys_area_types').text());
	$('#table').bootstrapTable(
			{
				dataType : "json",// 请求后台的URL（*）
				method : "get",// 请求方式（*）
				striped : true,// 是否显示行间隔色
				clickToSelect : true,// 是否启用点击选中行
				height : $.rlLayout.getBsHeight(), // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				pagination : true,// 是否分页
				pageSize : 20,// 一页显示多少列
				pageList : [ 20, 50 ],
				showRefresh: false, // 开启刷新功能
				pageNumber : 1,// 当前第几页
				sidePagination : "server",
				showColumns : true,// 表头右边小工具
				minimumCountColumns : 2,// 最小只剩2个
				toolbar : "#toolbar",// 把自定义工具放在表头容器
				showHeader : true,// 是否显示表标题
				detailView : false,// 细节视图
				smartDisplay: true, // 智能显示 pagination 和 cardview 等
				queryParams : queryParams,
				columns : [
						{
							checkbox : true
						}
						,{
							field: 'Number',
							title: '序号',
							width: 15,
							formatter: function (value, row, index) {
							return index+1;
							}
						},
						{
							field : 'name',
							title : '区域名称'
						},
						{
							field : 'code',
							title : '区域编码'
						},
						{
							title : '区域类型',
							formatter : function(value, row, index) {
								return getDictLabel(sys_area_types , row.type, '');
							}
						}, {
							field : 'sort',
							title : '排序'
						}, {
							field : 'remarks',
							width : 200,
							title : '备注',
								formatter : function(value, row, index){
									if(row.remarks==null){
										return row.remarks;
									}else{
										return "<div title="+row.remarks+" style='width : 200px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: center;'>"+row.remarks+"</div>";
									}
								}
						}

				]
			});
	$table.on('check.bs.table uncheck.bs.table '
			+ 'check-all.bs.table uncheck-all.bs.table', function() {
		$remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
		if ($table.bootstrapTable('getSelections').length == 1) {
			$modify
					.prop('disabled',
							!$table.bootstrapTable('getSelections').length);
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
	//重置按钮
	$("#reset").click(function(){
		$("#names").val('');
		$("#codes").val('');
		$("#typeSel").select2("data",null);
	});
	//查询按钮事件
	$("#from_findarea").click(function() {
		//获取选择节点的对象
		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		var nodes = treeObj.getSelectedNodes();
		//判断是否选择节点
		var treeNode=null;
		if(nodes[0]!=null){
			treeNode= nodes[0];
		}
		//刷新table
		refreshTable(treeNode);
	});
	//刷新树按钮
	$("#refreshTree").click(function(){
		//刷新树
		refreshTree();
	});
}
/**
 * 获取行id
 * @returns
 */
function getIdSelections() {
	return $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id
	});
}


/**
 * 初始化树
 */
function refreshTree() {
	//获取选择树节点对象
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = null;
	if(treeObj!=null){
		nodes=treeObj.getSelectedNodes();
	}
	//树参数
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : '0'
			}
		},
		callback : {//单击树事件
			onClick : function(event, treeId, treeNode) {
				refreshTable(treeNode);
			}
		},view: {
			selectedMulti: false 
		}
	};
	
	$.getJSON(system.getContextPath() + "/sys/area/treeData", function(data) {
		//摧毁树
		$.fn.zTree.destroy("ztree");
		$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
		//判断刷新树之前是否选择节点
		if(nodes!=null && nodes[0]!=null && nodes[0].id!=null){
			var node = treeObj.getNodeByParam("id", nodes[0].id);
			treeObj.selectNode(node);
			refreshTable(nodes[0]);
		}else{
			refreshTable();
		}
	});
}


