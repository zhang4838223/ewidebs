<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<title>子系统管理</title>
<meta name="decorator" content="defaultx" />
</head>
<body>
	<div class="container-fluid" style="min-width: 1000px;">
		<div class="row">
	<div class="col-xs-12  right-layout">
			<div class="row">
			<form class="navbar-form search-wrap" role="search">
				<span class="form-group "> 
				<span class=" input-sm">子系统编号</span>
				<input type="text" id="systemCodes" name="systemCode" class="required form-control input-sm " placeholder="子系统编号">
				</span>
				<span class="form-group "> 
				<span class=" input-sm">子系统名称</span>
				<input type="text" id="systemNames" name="systemName" class="required form-control input-sm " placeholder="子系统名称">
				</span>
				<span class="form-group">
					<button type="button" id="from_findsubsystem" class="btn btn-info input-sm"><i class="icon-search"></i> 查询</button>
					<button type="button" id="reset" class="btn btn-warning input-sm"><i class="icon-refresh"></i> 重置</button>
				</span>
			</form>
		</div>
		<!--内容-->
		<div class="row">
			<!--表格头部组件-->
			<div id="toolbar" class="col-xs-12">
				<button id="add" class="btn btn-success input-sm">
					<i class="icon-plus"></i> 新增
				</button>
				<button id="modify" class="btn btn-primary input-sm" disabled>
					<i class="icon-pencil"></i> 修改
				</button>
				<button id="remove" class="btn btn-danger input-sm" disabled>
					<i class="icon-trash"></i> 删除
				</button>
			</div>
			<!--表格-->
				<div class="col-xs-12">
			<table id="table" class="table table-condensed"></table>
			</div>
		</div>
	</div>
	</div>
	</div>
	<%@include file="/WEB-INF/views/include/js-footer.jsp"%>
	<script src="${ctxStatic}/modules/sys/subsystem.js"></script>
</body>
</html>