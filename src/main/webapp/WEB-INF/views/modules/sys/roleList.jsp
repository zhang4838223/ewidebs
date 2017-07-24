<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="defaultx"/>
</head>
<body>
	<div class="container-fluid" style="min-width: 1000px;">
	<div class="col-xs-12">
		<div class="row-fluid">
	        <!--表格头部组件-->
	        <div id="toolbar">
					<button id="append" type="button" class="btn btn-success input-sm" >
					<i class="icon-plus"></i> 新增
					</button>
					<button id="assign" type="button" class="btn btn-primary input-sm"  disabled>
					<i class="icon-pencil"></i> 分配
					</button>
					<button id="modify" type="button"  class="btn btn-primary input-sm"  disabled>
					<i class="icon-pencil"></i> 修改
					</button>
					<button id="remove" type="button" class="btn btn-danger input-sm" disabled>
					<i class="icon-trash"></i> 删除</button>
			</div>	
		    <!--表格-->
		    <table id="table"></table>
	    </div>
	 </div>
	</div>
<input type="hidden" id="ctx" value="${ctx}" />
<div id="sys_data_scope" style="display: none">${fns:toJson(fns:getDictList('sys_data_scope'))}</div>
<div id="sys_role_type" style="display: none">${fns:toJson(fns:getDictList('sys_role_type'))}</div>
<%@include file="/WEB-INF/views/include/js-footer.jsp" %>
<script src="${ctxStatic}/modules/sys/role.js"></script>
</body>
</html>