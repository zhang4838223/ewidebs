/*
 * Created by LYH on 2016/3/23.
 * Update by Yan Xin 2016/05/26
 */

/**
 * 表头自适应
 */
$(window).resize(function() {
	$('#table').bootstrapTable('resetView',{height:$.rlLayout.getBsHeight()});
});

//项目路径
var ctx = system.getContextPath();

/**
 * 页面加载事件
 */
$(document).ready(function() {
	//初始化列表
	initOfficeTable();
	//初始化机构树
	initTree();
	//初始化模态框按钮点击事件
	initModalClick();
	//初始化其它按钮点击事件
	initClick();
});

/**
 * 初始化列表
 */
function initOfficeTable(){
	//列表参数
	var  queryParams = function(params){
		 var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		            limit: params.limit,   //页面大小
		            offset: params.offset,  //页码
		            name:$("#names").val(),//机构名称
		            'area.name':$("#areaNames").val(),//归属区域
		            id:$('#officeId_').val(),
		            parentIds:$('#officeParentIds_').val(),
		            primaryPersonId:$('#primaryPerson1Id').val()
		        };
		 return temp;
	 }
	
	$('#table').bootstrapTable(
			{
				dataType: "json",//请求后台的URL（*）
			    method: "get",//请求方式（*）
			    striped: true,//是否显示行间隔色
			    clickToSelect: true,//是否启用点击选中行
			    height : $.rlLayout.getBsHeight(), //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				pagination : true,//是否分页
				pageSize : 20,//一页显示多少列
				pageList : [20, 50 ],
			    pageNumber: 1,//当前第几页
			    showColumns:true,//表头右边小工具
			    minimumCountColumns:2,//最小只剩2个
			    toolbar:"#toolbar",//把自定义工具放在表头容器
			    showHeader:true,//是否显示表标题
			    detailView:false,//细节视图
			    detailFormatter:"detailFormatter",//细节视图返回的字符串
			    idField:'id',
			    sidePagination:"server",
			    queryParams:queryParams,
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
						},
						{
							width: 400,
							field : 'name',
							title : '机构名称'
						},
						{
							field : 'area.name',
							title : '归属区域'
						},
//						{
//							field : 'code',
//							title : '机构编码'
//						},
						{
							title : '机构类型',
							formatter : function(value, row, index) {
								return getDictLabel(jQuery.parseJSON($('#sys_office_types').text()),
										row.type, '');
							}
						}, {
							width: 600,
							field : 'remarks',
							title : '备注',
							formatter : function(value, row, index) {
								if(row.remarks==null){
									return row.remarks;
								}else{
									return "<div title="+row.remarks+" style='width : 600px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: center;'>"+row.remarks+"</div>";
								}
							}
						}

				]
			});
	
	/**
	 * 修改、删除按钮的禁用
	 */
	$('#table').on('check.bs.table uncheck.bs.table '
			+ 'check-all.bs.table uncheck-all.bs.table', function() {
				$('#remove').prop('disabled', !$('#table').bootstrapTable('getSelections').length);
			    if ($('#table').bootstrapTable('getSelections').length == 1) {
			        $('#modify').prop('disabled', !$('#table').bootstrapTable('getSelections').length);
			    } else {
			        $('#modify').prop('disabled', true);
			    }
		selections = getIdSelections();
	});
}

/**
 * 模态框按钮点击事件
 */
function initModalClick(){
	/**
	 * 新增按钮点击事件
	 */
	$('#add').click(function() {
		showModal("",'新增机构');
	});
	
	/**
	 * 修改按钮点击事件
	 */
	$('#modify').click(function() {
		var obj = $('#table').bootstrapTable('getSelections');
		showModal(obj,'修改机构');
	});

	/**
	 * 删除按钮点击事件
	 */
	$('#remove').click(function() {
		showMessage('提醒',"确定是否删除？",'删除成功!');
	});
	
	//查询按钮
	$('#search').click(function () {
		$('#table').bootstrapTable("removeAll");
		$('#table').bootstrapTable("refresh",{url:ctx + "/sys/office/data"});
		$('#modify').prop('disabled', true);
		$('#remove').prop('disabled', true);
	});
	
	//重置按钮
	$('#reset').click(function () {
		$("#names").val('');
		$("#areaNames").val('');
		$("#primaryPerson1Id").val('');
		$("#primaryPerson1Name").val('');
	});
}

/**
 * 其它按钮点击事件
 */
function initClick(){
	/**
	 * 刷新树按钮点击事件
	 */
	$('#refreshTree').click(function () {
		initTree();
	});
}

/**
 * 调用模态框组件
 * @param obj 传入模态框的参数
 * @param title 模态框头部显示
 */
function showModal(obj,title){
	var id="";
	if(obj.length>0){
		id = obj[0].id; 
	}
	$.modal.showModalDialog({
		data : obj,
		title : title,
		width : '800',
		showBtn : true,
		btn : [ '确定', '取消' ],
		url : ctx + "/sys/office/modal?id="+id,
		success : function() {
		},
		error : function() {
		},yes: function(index,childData){
			if(childData){
				$.modal.close(index);
			}
		}
	});
}

/**
 * 提示组件
 * @param title 提示框头部显示
 * @param content 提示信息
 * @param message 成功消息
 */
function showMessage(title,content,message){
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var obj = $('#table').bootstrapTable('getSelections');
	var message1='';
	if(obj.length>0){
		for(var i=0;i<obj.length;i++){
			var value = treeObj.getNodeByParam("id", obj[i].id, null);
			if(value!=null && value.children!=null &&value.children.length>0){
				if(message1!='')message1+='、';
				message1+='“'+obj[i].name+'”';
			}
		}
	}
	if(message1!=''){
		content=message1+'的下级机构会被级联删除，请确认！';
	}
	$.modal.showConfirm ({
		title:title,
		content:content,
		icon:2, 
		yes: function(index){
			del();
			$.modal.close(index);
			$.modal.showPrompt({content:message,time:1000});
		},cancel: function(){
			
		}
	});
}

/**
 * 删除事件
 */
function del() {
	$.ajax({
		type : "POST",
		url : ctx + "/sys/office/delete?id=" + getIdSelections() + ",",
		success : function() {
			$('#modify').prop('disabled', true);
			$('#remove').prop('disabled', true);
			initTree();
		}
	});
}

/**
 * 获取列表选中行ID
 * @returns
 */
function getIdSelections() {
	return $.map($('#table').bootstrapTable('getSelections'), function(row) {
		return row.id
	});
}

/**
 * 列表细节视图
 * @param index
 * @param row
 * @returns
 */
function detailFormatter(index, row) {
	var html = [];
	$.each(row, function(key, value) {
		html.push('<p><b>' + key + ':</b> ' + value + '</p>');
	});
	return html.join('');
}

/**
 * 初始化机构树
 */
function initTree(){
	var setting = {
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : '0'
				}
			},
			callback : {
				onClick : function(event, treeId, treeNode) {
					var id = treeNode.id == '0' ? '' : treeNode.id;
//					if(treeNode.getParentNode()!=null){
					var type=treeNode.type;
					$('#officeId_').val(id);
					$('#officeName_').val(treeNode.name);
					$('#officeParentIds_').val(treeNode.pIds);
					if(type==1){
						$('#officeId1_').val(treeNode.id);
						$('#officeName1_').val(treeNode.name);
					}else if(type==2){
						$('#officeId1_').val(treeNode.getParentNode().id);
						$('#officeName1_').val(treeNode.getParentNode().name);
					}
					
//					}else{
//						$("#officeId_").val('');
//						$("#officeName_").val('');
//						$("#officeParentIds_").val('');
//					}
					$('#table').bootstrapTable("removeAll");
					$('#table').bootstrapTable("refresh",{url : ctx + "/sys/office/data"});
					$('#modify').prop('disabled', true);
					$('#remove').prop('disabled', true);
				}
			},view: {
				selectedMulti: false 
			}
		};
	refreshTree(setting);
}

/**
 * 刷新机构树并刷新列表
 * @param setting
 */
function refreshTree(setting) {
	$('#modify').prop('disabled', true);
	$('#remove').prop('disabled', true);
	$("#officeId_").val('');
	$("#officeName_").val('');
	$("#officeParentIds_").val('');
	$.getJSON(system.getContextPath()+"/sys/office/treeData?isAll=true",function(data){
		$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
	});
	$('#table').bootstrapTable("refresh",{url : ctx + "/sys/office/data"});
}
