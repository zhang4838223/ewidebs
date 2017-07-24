/*
 * Created by LYH on 2016/3/23.
 * Update by Yan Xin 2016/05/26
 */

/**
 * 表头自适应
 */
$(window).resize(function () {
    $('#table').bootstrapTable('resetView',{height:$.rlLayout.getBsHeight()});
});

/**
 * 页面加载事件
 */
$(document).ready(function() {
	//初始化列表
	initUserTable();
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
function initUserTable(){
	//列表参数
	var  queryParams = function(params){
		 var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		            limit: params.limit,   //页面大小
		            offset: params.offset,  //页码
		            name: $('#searchName').val(),
		            loginName: $('#searchLoginName').val(),
		            //companyId: $('#searchCompanyId').val(),
		            officeId: $('#searchOfficeId').val(),
		            no: $('#searchNo').val(),
		            id : $("#companyId").val(), //roleTable所属机构查询角色
		            parentIds:$("#parentids").val(),//roleTable
		            'office.id':$('#office_id').val(),
		            'company.name':$('#officeName_').val()
		        };
		 return temp;
	 }
	
	$('#table').bootstrapTable({
//		 url:system.getContextPath()+"/sys/user/listData?officeId="+$('#officeId_').val()+"&officeName="+$('#officeName_').val(),  
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
		        field: 'company.name',
		        title: '归属公司'
	    	},{
		        field: 'office.name',
		        title: '归属部门'
		    },{
		        field: 'loginName',
		        title: '登录名'
		    },{
		    	field: 'name',
		    	title: '姓名'
		    },{
		    	field: 'phone',
		        title: '电话'
		    },{
		    	field: 'mobile',
		        title: '手机'
		    }
	   
	]
	});
	
	/**
	 * 修改、删除按钮的禁用
	 */
	$('#table').on('check.bs.table uncheck.bs.table ' +
		    'check-all.bs.table uncheck-all.bs.table', function () {
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
	 * 修改按钮点击事件
	 */
	$('#modify').click(function () {
		var obj= $('#table').bootstrapTable('getSelections');
		showModal(obj,'修改用户');
	});

	/**
	 * 新增按钮点击事件
	 */
	$('#add').click(function () {
		showModal("",'新增用户');
	});

	/**
	 * 删除按钮点击事件
	 */
	$('#remove').click(function () {
		showMessage('提醒',"确定是否删除？",'删除成功!');
	});
}

/**
 * 其它按钮点击事件
 */
function initClick(){
	//查询按钮
	$('#search').click(function () {
		searchButton();
	});
	//重置按钮
	$('#reset').click(function () {
		resetButton();
	});
	//刷新树
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
//		height : '700',
		showBtn : true,
		btn : [ '确定', '取消' ],
		url : system.getContextPath() + "/sys/user/modal?id="+id,
		success : function() {
			if(id==""){//如果为新增时
				$('#newPassword').val('123456');
				$('#confirmNewPassword').val('123456');
			}else{
				$('#passwordMsg').css('display','block');
			}
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
	$.modal.showConfirm ({
		title:title,
		content:content,
		icon:2, 
		yes: function(index){
			del();
			$.modal.close(index);
		},cancel: function(){
			
		}
	});
}

/**
 * 获取列表选中行ID
 * @returns
 */
function getIdSelections() {
    return $.map($('#table').bootstrapTable('getSelections'), function (row) {
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
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}

/**
 * 删除事件
 */
function del(){
	 $.ajax({
		  type: "POST",
		  url: system.getContextPath()+"/sys/user/delete?id="+getIdSelections()+",",
		  success: function(data){
//					  $("#modalTable").modal("hide");
			  $.modal.showPrompt({
					title:'提醒',
					content:data,
					time:2000
				});
			  $('#modify').prop('disabled', true);
			  $('#remove').prop('disabled', true);
			  $('#table').bootstrapTable("removeAll");
			  $('#table').bootstrapTable("refresh");
		  }
		 });
}

/**
 * 重置按钮点击事件
 */
function resetButton(){
	$("#searchCompanyId").val('');
	$("#searchCompanyName").val('');
	$("#searchOfficeName").val('');
	$("#searchNo").val('');
	$("#searchName").val('');
	$("#searchLoginName").val('');
	$("#searchName").val('');
	$("#office_id").val('');
}

/**
 * 查询按钮点击事件
 */
function searchButton(){
	$('#table').bootstrapTable("removeAll");
	$('#table').bootstrapTable("refresh",{url:system.getContextPath()+"/sys/user/listData"});
	$('#modify').prop('disabled', true);
	$('#remove').prop('disabled', true);
}

/**
 * 初始化机构树
 */
function initTree(){
	var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
		callback:{onClick:function(event, treeId, treeNode){
			var id = treeNode.id == '0' ? '' :treeNode.id;
			$("#office_id").val(id);
			if(treeNode.type=='1'){
				$("#pOfficeId").val(id);
				$("#pOfficeName").val(treeNode.name);
				if(typeof(searchCompanyId)!='undefined'){
					$('#searchCompanyId').val(id);
					$('#searchCompanyName').val(treeNode.name);
					$('#searchOfficeId').val('');
					$('#searchOfficeName').val('');
				}
				$("#officeId_").val('');
				$("#officeName_").val('');
			}else{
				$("#pOfficeId").val(treeNode.getParentNode().id);
				$("#pOfficeName").val(treeNode.getParentNode().name);
				$("#officeId_").val(id);
				$("#officeName_").val(treeNode.name);
				if(typeof(searchOfficeId)!='undefined'){
					$('#searchOfficeId').val(id);
					$('#searchOfficeName').val(treeNode.name);
					$("#searchCompanyId").val(treeNode.getParentNode().id);
					$("#searchCompanyName").val(treeNode.getParentNode().name);
				}
			}
			$('#table').bootstrapTable("removeAll");
			$('#table').bootstrapTable("refresh",{url:system.getContextPath()+"/sys/user/listData"});
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
function refreshTree(setting){
	resetButton();
	$("#pOfficeId").val('');
	$("#pOfficeName").val('');
	$("#officeId_").val('');
	$("#officeName_").val('');
	$.getJSON(system.getContextPath()+"/sys/office/treeData",function(data){
	$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
});
$('#table').bootstrapTable("refresh",{url:system.getContextPath()+"/sys/user/listData"});
}

/**
 * 搜索框内归属公司组件回调函数
 * @param v
 * @param nodes
 */
function searchCompanyTreeselectCallBack(v,nodes){
	if(v=='ok'){
		var companyId=$('#searchCompanyId').val();
		$("#searchOfficeUrl").val("/sys/office/treeData?type=2&parentId="+$('#searchCompanyId').val());
		$('#office_id').val(companyId);
		//同步选中树中节点
		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		var node=treeObj.getNodeByParam("id",companyId);
		treeObj.selectNode(node);
	}else if (v=="clear"){
		$("#searchOfficeUrl").val("/sys/office/treeData?type=2");
		$('#office_id').val(companyId);
		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		treeObj.cancelSelectedNode();
    }
	$('#table').bootstrapTable("refresh",{url:system.getContextPath()+"/sys/user/listData"});
	$('#modify').prop('disabled', true);
	$('#remove').prop('disabled', true);
	$('#searchOfficeId').val('');
	$('#searchOfficeName').val('');
}

/**
 * 搜索框内归属部门组件回调函数
 * @param v
 * @param nodes
 */
function searchOfficeTreeselectCallBack(v,nodes){
	if(v=='ok'){
		if(nodes.length>0){
			forChange(nodes[0].id);
		}
		var officeId=$('#searchOfficeId').val();
		$('#office_id').val(officeId);
		//同步选中树中节点
		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		var node=treeObj.getNodeByParam("id",officeId);
		treeObj.selectNode(node);
	}else if (v=="clear"){
		var companyId=$('#searchCompanyId').val();
		$('#office_id').val(companyId);
		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		var node=treeObj.getNodeByParam("id",companyId);
		treeObj.selectNode(node);
    }
	$('#table').bootstrapTable("refresh",{url:system.getContextPath()+"/sys/user/listData"});
	$('#modify').prop('disabled', true);
	$('#remove').prop('disabled', true);
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
			$("#searchCompanyId").val(data.parentId);
			$("#searchCompanyName").val(data.name);
			$("#searchOfficeUrl").val("/sys/office/treeData?type=2&parentId="+data.parentId);
		}
	});
}