/**
 * wangtao
 * 2016年5月25日09:12:06
 */
// 表头自适应
$(window).resize(function() {
	$('#table').bootstrapTable('resetView',{height:$.rlLayout.getBsHeight()});
});

//table绑定按钮
var $table = $('#table'), $remove = $('#remove'), $modify = $('#modify'), $add = $('#add');
//table第几页
var _params;
//加载事件
$(document).ready(function() {
	//初始化下拉框
	initSelect();
	//初始化table
	initTable();
	//初始化模态框按钮
	initButtonModal();
	//初始化按钮
	initButton();	
	//初始化数据
	initData();
});

/**
 * 选择行返回id
 * @returns
 */
function getIdSelections() {
	return $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.id;
	});
}

/**
 * 初始化下拉框
 */
function initSelect(){
	//初始化子系统下拉框
	$("#menuSel").bindSelect({
		url : system.getContextPath() + "/sys/menu/subsystemList"
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
			name : $("#names").val(),//高级查询参数
		};
		 _params = temp;
		return temp;
	}
	//初始化table
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
		columns : [
				{
					checkbox : true
				},{
					field: 'Number',
					title: '序号',
					width: 15,
					formatter: function (value, row, index) {
					return index+1;
					}
				},{
					field : 'name',
					title : '菜单名称',
					/*formatter : function(value, row, index) {
						var i;
						if (row.icon != null) {
							i = row.icon;
						} else {
							i = 'hide';
						}
						return "<i class='" + i + "'></i>"
								+ row.name;
					}*/
				}, {
					field : 'href',
					title : '链接',
					width : 150,
					formatter : function(value, row, index){
						if(value==null || value.length<=15){
							return value;
						}else{
							return "<div title="+value+" style='width : 150px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: center;'>"+value+"</div>";
						}
					}
				}, {
					field : 'sort',
					title : '排序'
				}, {
					title : '可见',
					formatter : function(value, row, index) {
						if (row.isShow == '1') {
							return '显示';
						} else {
							return '隐藏';
						}
					}
				}, {
					field : 'permission',
					title : '权限标识',
					width : 200,
					formatter : function(value, row, index){
						if(value==null || value.length<=15){
							return value;
						}else{
							return "<div title="+value+" style='width : 200px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: center;'>"+value+"</div>";
						}
					}
				}, {
					field : 'remarks',
					width : 200,
					title : '备注',
						formatter : function(value, row, index){
							if(row.remarks==null || value.length<=15){
								return row.remarks;
							}else{
								return "<div title="+value+" style='width : 200px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: center;'>"+value+"</div>";
							}
						}
				}

		]
	});
	
	$table.on('check.bs.table uncheck.bs.table '+ 'check-all.bs.table uncheck-all.bs.table', function() {
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
 * 初始化模态框按钮
 */
function initButtonModal(){
	//添加模态框
	$add.click(function() {
		addOnclick();
	});
	//修改模态框
	$modify.click(function() {
		modifyOnclick();
	});
	//删除模态框
	$remove.click(function() {
		removeOnclick();
	});
}

/**
 * 添加模态框
 */
function addOnclick(){
	//获取选择树节点对象
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = treeObj.getSelectedNodes();
	//获取选择子系统code
	var menuSel=$("#menuSel").val();
	//传数组类型的参数
	var data=[menuSel];
	//判断是否选择节点
	if(nodes[0]!=null){
		data=[nodes[0].id,nodes[0].name,nodes[0].subId,nodes[0].pIds];
	}
	$.modal.showModalDialog({
		data:data,
		title : '新增菜单',
		showBtn : true,
		btn : [ '确定', '取消'],
		url : system.getContextPath() + "/sys/menu/modal?subsystemCode=" + menuSel,
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
 * 修改模态框
 */
function modifyOnclick(){
	//获取选择树节点对象
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = treeObj.getSelectedNodes();
	//获取选择子系统code
	var menuSel=$("#menuSel").val();
	//传数组类型的参数
	var data=[menuSel];
	//判断是否选择节点
	if(nodes[0]!=null){
		data=[nodes[0].id,nodes[0].subId,nodes[0].pIds];
	}
	//获取选择table的行
	var obj = $('#table').bootstrapTable('getSelections');
	$.modal.showModalDialog({
		data:data,
		title : '修改菜单',
		showBtn : true,
		btn : [ '确定', '取消'],
		url : system.getContextPath() + "/sys/menu/modal?id=" + obj[0].id,
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
 * 删除模态框
 */
function removeOnclick(){
	//获取选择table的行
	var obj = $table.bootstrapTable('getSelections');
	var syscode=$("#menuSel").val();
	//获取选择节点的对象
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = treeObj.getSelectedNodes();
	var treeNode=null;
	if(nodes[0]!=null){
		treeNode={'id':nodes[0].id,'pIds':nodes[0].pIds};
	}
	//获取选择行的ids
	var id = getIdSelections() + ",";
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
		content+='的下级菜单会被级联删除，请确定！';
	}else{
		content='确定删除吗？';
	}
	$.modal.showConfirm({
		title : '提示',
		content : content,
		icon : 2,
		yes : function(index) {
			$.post(system.getContextPath() + "/sys/menu/delete", {
				"id" : id
			}, function() {
				$.modal.close(index);
				$.modal.showPrompt({
					title : '提示',
					content : "删除成功!",
					time : 1500
				});
				//刷新树
				refreshTree(syscode);
				//刷新table
				refreshTable(syscode,treeNode);
			});
		},
		cancel : function(index) {
		}
	});
}
/**
 * 初始化按钮
 */
function initButton(){
	//查询
	$("#from_findmenu").click(function() {
		//获取选择节点的对象
		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		var nodes = treeObj.getSelectedNodes();
		//判断是否选择节点
		var treeNode=null;
		if(nodes[0]!=null){
			treeNode= {"id":nodes[0].id,"pIds":nodes[0].pIds}
		}
		//获取选择子系统code
		var menuSel=$("#menuSel").val();
		//刷新table
		refreshTable(menuSel,treeNode);
	});
	//重置
	$("#reset").click(function(){
		$("#names").val('');
	});
	//刷新树按钮
//	$("#refreshTree").click(function(){
//		//获取选择子系统code
//		var menuSel=$("#menuSel").val();
//		//刷新树
//		refreshTree(menuSel);
//	});
	//下拉框绑定事件
	$("#menuSel").change(function(){
		//获取选择子系统code
		var menusubsystemCode = $("#menuSel").val();
		//判断是否选择子系统
		if(menusubsystemCode==""){
			$add.prop('disabled', true);
		}else{
			$add.prop('disabled', false);
		}
		//刷新树
		refreshTree(menusubsystemCode);
		//刷新table
		refreshTable(menusubsystemCode);
	});
//	$("#reset").off('click');
}
/**
 * 根据子系统编号刷新树
 * @param syscode 子系统编号
 */
function refreshTree(syscode){
	//获取选择树节点对象
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var node = null;
	if(treeObj!=null){
		node=treeObj.getSelectedNodes();
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
			callback : {//树单击事件
				onClick : function(event, treeId, treeNode) {
					//获取选择子系统code
					var syscode = $("#menuSel").val();
					//刷新table
					refreshTable(syscode,treeNode);
				}
			},view: {
				selectedMulti: false 
			}
		};
	
	$.getJSON(system.getContextPath() + "/sys/menu/treeData", {"subsystemCode" : syscode}, function(data) {
		// 获取树对象
		$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
		var zTree = $.fn.zTree.init($("#ztree"), setting, data);
		//判断刷新树之前是否选择节点
		if(node!=null && node[0]!=null && node[0].id!=null){
			var node1 = zTree.getNodeByParam("id", node[0].id);
			zTree.selectNode(node1);
//			node1.open = true;
			zTree.expandNode(node1, true, true, true,true);
		}
		//zTree.expandAll(true);
		// 获取根节点
		var nodes = zTree.getNodes();
		for (var i = 0; i < nodes.length; i++) {
			// 判断节点下是否有子节点
			if (nodes[i].children) {
				zTree.expandNode(nodes[i], true, false, true);// 展开当前第一个有子级的节点
				zTree.expandNode(nodes[i].children[i], true, false, true);// 展开第一个有子级的节点的子级
				break;
			}
		}
		
	});
}

/**
 * 刷新table
 * @param syscode
 * @param treeNode
 */
function refreshTable(syscode,treeNode){
	//刷新table时 修改删除按钮不可点击
	$modify.prop('disabled', true);
	$remove.prop('disabled', true);
	//清空table值
	$('#table').bootstrapTable("removeAll");
	//判断treeNode是否有值 来刷新table
	if(treeNode!=null){
	var id = treeNode.id == '0' ? '' : treeNode.id;
	$('#table').bootstrapTable("refresh",
		{url : system.getContextPath() + "/sys/menu/data?id=" + id+ "&parentIds=" + treeNode.pIds + "&subsystemCode=" +  syscode
		,query : _params});
	$('#table').bootstrapTable('selectPage',calculatePage(_params));
	}else{
	$('#table').bootstrapTable("refresh",
		{url : system.getContextPath() + "/sys/menu/data?subsystemCode=" +  syscode
		,query : _params});
	$('#table').bootstrapTable('selectPage',calculatePage(_params));
	}
}
/**
 * 初始化数据
 */
function initData(){
	//获取默认子系统code
	var syscode = $("#defaultMenuCode").val();
	//初始化树
	refreshTree(syscode);
	//初始化table
	refreshTable(syscode);
}

//折叠 
var openClose = $("#openClose");
var wrap = $(".box-wrap");
$(".middle").click(function () {
    if (!wrap.hasClass("left-active")) {
        wrap.addClass("left-active");
        openClose.removeClass("close");
        openClose.addClass("open");
    }
}).mouseover(function () {
    if (wrap.hasClass("left-active")) {
        wrap.removeClass("left-active");
        openClose.removeClass("open");
        openClose.addClass("close");
    }
});