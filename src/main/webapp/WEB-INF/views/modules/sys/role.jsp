<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="defaultx"/>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 right-layout">
				<!--主体内容-->
				<!--搜索框-->
				<div class="row">
					<form class="navbar-form search-wrap" role="search">
						<span class="form-group "> <span class=" input-sm">角色名称</span>
							<input type="text" id="names" class="required form-control input-sm " placeholder="角色名称">
						</span> 
						<span class="form-group "> <span class=" input-sm">归属公司</span>
							<input type="text" id="officeNames" class="required form-control input-sm " placeholder="归属机构">
						</span>
						<span class="form-group " >
							<button type="button" id="from_findrole" class="btn btn-info input-sm"><i class="icon-search"></i> 查询</button>
							<button type="button" id="reset" class="btn btn-warning input-sm"><i class="icon-refresh"></i> 重置</button>
						</span>
					</form>
				</div>
				<div class="row">
					<shiro:hasPermission name="sys:role:edit">
			        <!--表格头部组件-->
			        <div id="toolbar">
							<button id="append" class="btn btn-success input-sm">
								<i class="icon-plus"></i> 新增
							</button>
							<button id="assign" class="btn btn-primary input-sm" disabled>
								<i class="icon-pencil"></i> 分配
							</button>
							<button id="modify" class="btn btn-primary input-sm" disabled>
								<i class="icon-pencil"></i> 修改
							</button>
							<button id="remove" class="btn btn-danger input-sm" disabled>
								<i class="icon-trash"></i> 删除
							</button>
					</div>
					</shiro:hasPermission>
			        <!--表格-->
			        <table id="table" class="table table-condensed"></table>
			    </div>
			 </div>
	 	</div>
	 </div>
<div id="sys_data_scope" style="display: none">${fns:toJson(fns:getDictList('sys_data_scope'))}</div>
<div id="sys_role_type" style="display: none">${fns:toJson(fns:getDictList('sys_role_type'))}</div>
<%@include file="/WEB-INF/views/include/js-footer.jsp" %>
<script src="${ctxStatic}/modules/sys/role.js"></script>
</body>
</html>