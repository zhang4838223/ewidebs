/**
 * Wanghaozhe
 * 2016-5-22
 * 角色列表
 */
//表头自适应
$(window).resize(function () {
    $('#table').bootstrapTable('resetView',{height:$.rlLayout.getBsHeight()});
});
	
//全局变量
var ctx=system.getContextPath();
//var selections = [];
var size;
var set;

$(document).ready(function() {
		 initTable();//初始化表格
		 initButton();//初始化按钮
});



function initTable(){
	//列表参数
	var  queryParams = function(params){
		 var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		            limit: params.limit,   //页面大小
		            offset: params.offset,  //页码
		            name:$("#names").val(),//角色名称
		            'office.name':$("#officeNames").val()//归属机构
		        };
		 set = params.limit;
		 size = params.offset;
		 return temp;
	 }
	$('#table').bootstrapTable({
		url : ctx+"/sys/role/list",
		dataType: "json",//请求后台的URL（*）
	    method: "get",//请求方式（*）
	    striped: true,//是否显示行间隔色
	    clickToSelect: true,//是否启用点击选中行
	    height : $.rlLayout.getBsHeight(), //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		pagination : true,//是否分页
		pageSize : 10,//一页显示多少列
		pageList : [10, 50 ],
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
	    cookie:true,
	    cookieIdTable:system.getLoginName()+"roleListTable",
	    cookieExpire:"2Y",
	    reorderableColumns:true,
    	columns: [{
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
			field : 'name',
			title : '角色名称'

		},/* {
			field : 'enname',
			title : '英文名称'
		},*/{
			field : 'office.name',
			title : '归属公司'
		}, {
			title : '数据范围',
			filed : 'dataScope',
			formatter:function(value,row,index){
			return getDictLabel(jQuery.parseJSON($('#sys_data_scope').text()), row.dataScope,'');
			}
		}
		]
	});
	//按钮显示逻辑
	$("#table").on('check.bs.table uncheck.bs.table ' +
		    'check-all.bs.table uncheck-all.bs.table', function () {
		$('#remove').prop('disabled', !$("#table").bootstrapTable('getSelections').length);
	    if ($("#table").bootstrapTable('getSelections').length == 1) {
	    	$('#modify').prop('disabled', !$("#table").bootstrapTable('getSelections').length);
		    $('#assign').prop('disabled',!$("#table").bootstrapTable('getSelections').length);
	    } else {
	        $('#modify').prop('disabled', true);
	        $('#assign').prop('disabled', true);
	    }
//		    selections = getIdSelections();
	});
}

function initButton(){
	$('#append').click(function (e) {
		showModal("",'新增角色');
	});
	//修改点击事件绑定
	$('#modify').click(function (e) {
		 var obj = $('#table').bootstrapTable('getSelections');
		 showModal(obj,'修改角色');
	});
	//绑定分配点击事件
	$('#assign').click(function (e) {
		var obj= $('#table').bootstrapTable('getSelections');
		showAssignModal(obj)
	});

	//删除点击事件
	$('#remove').click(function () {
		showMessage('提醒',"确定是否删除？",'删除成功!');
	});
	
	//查询点击事件
	$('#from_findrole').click(function () {
		//刷新table时 修改删除按钮不可点击
		$('#modify').prop('disabled', true);
		$('#assign').prop('disabled', true);
		$('#remove').prop('disabled', true);
		$('#table').bootstrapTable("removeAll");
		$('#table').bootstrapTable("refresh",{url:ctx+"/sys/role/list"});
	});
	
	//重置按钮
	$('#reset').click(function () {
		$("#names").val('');
		$("#officeNames").val('');
	});
}

/**
 * 调用form模态框组件
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
		showBtn : true,
		width : '700',
//		height : '600',
		btn : [ '确定', '取消' ],
		url : ctx + "/sys/role/modal?id="+id,
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
 * 分配角色模态框
 * @param obj
 */
function showAssignModal(obj){
	$.modal.showModalDialog({
		data : obj,
		title : '分配用户',
		width : '900',
		showBtn : true,
		btn : [ '关闭' ],
		url : ctx+"/sys/role/assignModal",
		yes: function(index,childData){
			$.modal.close(index);
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
			doDelete();
			$.modal.close(index);
			$.modal.showPrompt({content:message,time:1000});
		},cancel: function(){
			
		}
	});
}

/**
 * 删除
 */
function doDelete() {
	var id=getIdSelections()+",";
	$.ajax({
		url: system.getContextPath()+'/sys/role/delete',
		type: 'POST',
		async: false,
		data:{"id":id},
		datatype:'json',
		success: function(data){
		  $('#modify').prop('disabled', true);
		  $('#remove').prop('disabled', true);
		  $('#assign').prop('disabled', true);
		  $('#table').bootstrapTable("removeAll");
		  $('#table').bootstrapTable("refresh");
		}
	});
}; 
/**
 * 获取角色列表已选择的勾选
 * @returns
 */
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}
/**
 * 获取分配列表已选择的勾选
 * @returns
 */
function getAssignIdSelections() {
    return $.map($('#contentTable').bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}
