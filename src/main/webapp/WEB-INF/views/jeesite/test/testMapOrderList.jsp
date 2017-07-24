<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>区域管理</title>
<meta name="decorator" content="defaultx" />
</head>
<body>
	<div class="container-fluid">
		<table id="table" class="table table-condensed"></table>
	</div>
	<%@include file="/WEB-INF/views/include/js-footer.jsp"%>
	<script>
	var queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			name : $("#names").val(),
			code : $("#codes").val(),
			type : $("#typeSel").val(),
		};
		return temp;
	}
	$('#table').bootstrapTable(
			{
				dataType : "json",// 请求后台的URL（*）
				url : system.getContextPath() + "/sys/area/data.do",
				method : "get",// 请求方式（*）
				striped : true,// 是否显示行间隔色
				clickToSelect : true,// 是否启用点击选中行
				height : 200,//$.rlLayout.getBsHeight(), // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				pagination : true,// 是否分页
				pageSize : 5,// 一页显示多少列
				pageList : [ 5, 10 ],
				showRefresh: false, // 开启刷新功能
				pageNumber : 1,// 当前第几页
				sidePagination : "server",
				//showColumns : true,// 表头右边小工具
				minimumCountColumns : 2,// 最小只剩2个
				toolbar : "#toolbar",// 把自定义工具放在表头容器
				showHeader : true,// 是否显示表标题
				detailView : false,// 细节视图
				smartDisplay: true, // 智能显示 pagination 和 cardview 等
				//queryParams : queryParams,
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
						}, {
							field : 'sort',
							title : '排序'
						}, 

				]
			});
	</script>
</body>
</html>