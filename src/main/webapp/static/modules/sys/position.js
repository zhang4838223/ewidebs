/**
 * 汪涛
 * 2016年5月26日09:51:43
 */
// 表头自适应
$(window).resize(function() {
	$('#table').bootstrapTable('resetView',{height:$.rlLayout.getBsHeight()});
});
//table绑定按钮
var $table = $('#table'),$remove = $('#remove'),$modify = $('#modify'),$add = $('#add');
//table第几页
var _params;
$(document).ready(function() {
	//初始化table
	initTable();
	//初始化按钮
	initButton();
	//初始化模态框按钮
	initButtonModal();
	//初始化table数据
	initData();
});
/**
 * 初始化table数据
 * @returns
 */
function initData(){
	$modify.prop('disabled', true);
	$remove.prop('disabled', true);
	$('#table').bootstrapTable("removeAll");
	$('#table').bootstrapTable("refresh",{url:system.getContextPath()+"/sys/position/data",query : _params});
	$('#table').bootstrapTable('selectPage',calculatePage(_params));
}

/**
 * 选着一行返回id
 */
function getIdSelections() {
    return $.map($table.bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}
/**
 * 初始化table
 */
function initTable(){
	//table参数
	var  queryParams = function(params){
		 var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		            limit: params.limit,   //页面大小
		            offset: params.offset,  //页码
		            positionName: $("#positionName").val(),
		            positionNo: $("#positionNo").val()
		        };
		 _params = temp;
		 return temp;
	}
	$('#table').bootstrapTable({
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
//	    detailFormatter: detailFormatter,//细节  视图返回的字符串
	    showHeader:true,//是否显示表标题
	    detailView:true,//细节视图
	    idField:'id',
	    sidePagination:"server",
	    queryParams:queryParams,
    	columns: [{
			checkbox : true
		},{
			field: 'Number',
			title: '序号',
			width: 15,
			formatter: function (value, row, index) {
			return index+1;
			}
		}, {
			field : 'positionName',
			title : '职位名称'
		}, 
		{
			field : 'positionNo',
			title : '职位编号'

		}, {
			field : 'office.name',
			title : '归属公司'
		}, {
			field : 'remarks',
			width : 400,
			title : '备注',
				formatter : function(value, row, index){
					if(row.remarks==null){
						return row.remarks;
					}else{
						return "<div title="+row.remarks+" style='width : 400px;text-overflow: ellipsis;-moz-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;text-align: center;'>"+row.remarks+"</div>";
					}
				}
		}
		],
		//注册加载子表的事件。注意下这里的三个参数！
        onExpandRow: function (index, row, $detail) {
            InitSubTable(index, row, $detail);
        }
	});
	//初始化子表格
    InitSubTable = function (index, row, $detail) {
        var cur_table = $detail.html('<table></table>').find('table');
        $(cur_table).bootstrapTable({
            url: system.getContextPath()+"/sys/position/data",
            method: 'get',
            clickToSelect: true,
            detailView: false,//父子表
            height : 300, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",
            pageSize: 10,
            sidePagination:"server",
            pageList: [10, 25],
            columns: [{
                checkbox: true
            }, {
    			field : 'positionName',
    			title : '职位名称'
    		}, 
    		{
    			field : 'positionNo',
    			title : '职位编号'

    		}, {
    			field : 'office.name',
    			title : '归属公司'
    		}]
        });
    };
	$table.on('check.bs.table uncheck.bs.table ' +
		    'check-all.bs.table uncheck-all.bs.table', function () {
		    $remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
		    if ($table.bootstrapTable('getSelections').length == 1) {
		        $modify.prop('disabled', !$table.bootstrapTable('getSelections').length);
		    } else {
		        $modify.prop('disabled', true);
		    }
		    selections = getIdSelections();
		});
	
	/*function detailFormatter(index, row) {
//	    var html = [];
//	    html.push('<table class="table"><thead><tr><th>产品</th><th>付款日期</th><th>状态</th></tr></thead><tbody>'); 
//	    $.each(row, function (key, value) {
//	        html.push('<tr class="active"><td>'+key+'</td><td>'+value+'</td><td>'+value+'</td></tr>');
//	    });
//	    html.push('</tbody></table>');
//	    return html.join('');
		var html ='';
		html='<table id="table1" class="table-striped"></table>';
		$('#table1').bootstrapTable({
			url : system.getContextPath()+"/sys/position/data",
			dataType: "json",//请求后台的URL（*）
		    method: "get",//请求方式（*）
		    striped: true,//是否显示行间隔色
		    clickToSelect: true,//是否启用点击选中行
		    height : 300, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			pagination : true,//是否分页
		    minimumCountColumns:2,//最小只剩2个
		    showHeader:true,//是否显示表标题
		    idField:'id',
		    sidePagination:"server",
		    queryParams:queryParams,
	    	columns: [{
				field : 'positionName',
				title : '职位名称'
			}, 
			{
				field : 'positionNo',
				title : '职位编号'

			}, {
				field : 'office.name',
				title : '归属公司'
			}
			]
		});
		return html;
	}*/
}
/**
 * 初始化按钮
 */
function initButton(){
	//查询
	$("#from_findposition").click(function(){
		initData();
	});
	//重置
	$("#reset").click(function(){
		$("#positionNo").val('');
		$("#positionName").val('');
	});
}
/**
 * 初始化模态框按钮
 */
function initButtonModal(){
	//新增
	$add.click(function () {
		$.modal.showModalDialog({
			title : '新增职位',
			showBtn : true,
			btn : [ '确定', '取消'],
			url : system.getContextPath() + "/sys/position/modal",
			yes : function(index, falg) {
				if(falg){
					//关闭模态框
					$.modal.close(index);
					}
			},
			cancel : function(index, childData) {
			}
		});
	});
	//修改
	$modify.click(function () {
		var obj= $('#table').bootstrapTable('getSelections');
		$.modal.showModalDialog({
			/* data:data, */
			title : '修改职位',
			showBtn : true,
			btn : [ '确定', '取消'],
			url : system.getContextPath() + "/sys/position/modal?id="+obj[0].id,
			yes : function(index, falg) {
				if(falg){
					//关闭模态框
					$.modal.close(index);
					}
			},
			cancel : function(index, childData) {
			}
		});
	});
	//删除
	$remove.click(function () {
		var obj = $table.bootstrapTable('getSelections');
		$.modal.showConfirm({
			title : '提示',
			content : "确定删除吗?",
			icon : 2,
			yes : function(index) {
				var id = getIdSelections() + ",";
				$.post(system.getContextPath() + "/sys/position/delete", {"id" : id}, function() {
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
	});
}