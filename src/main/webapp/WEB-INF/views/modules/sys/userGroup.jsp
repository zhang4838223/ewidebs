<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户组管理</title>
<meta name="decorator" content="defaultx" />
<%@include file="/WEB-INF/views/include/js-footer.jsp"%>
</head>
<body>
	<div class="container-fluid">
	<!--搜索框-->
		<div class="row">
	<div class="col-xs-12  right-layout">
				<div class="row">
					<form class="navbar-form search-wrap main_search" role="search">
						<span class="form-group ">
						 <span class=" input-sm">用户组名称</span>
							<input type="text" id="name" name="name" class="required form-control input-sm " placeholder="用户组名称">
						</span> 
						<span class="form-group "> 
						<span class=" input-sm">用户组编号</span>
							<input type="text" id="code" name="code" class="required form-control input-sm " placeholder="用户组编号">
						</span> 
						<span class="form-group ">
						 <span class=" input-sm">归属公司</span>
							<sys:treeselect id="companys" name="company.id" value="" labelName="company.name" labelValue="" title="公司" url="/sys/office/treeData?type=1" allowClear="true" />
						</span> 
						<span class="form-group ">
							<button type="button" id="from_finduserGroup" class="btn btn-info input-sm">
								<i class="icon-search"></i> 查询
							</button>
							<button type="button" id="reset" class="btn btn-warning input-sm">
								<i class="icon-refresh"></i> 重置
							</button>
						</span>
					</form>
				</div>
		
		<!--内容-->
		<div class="row">
			<!--表格头部组件-->
			<div id="toolbar" class="col-xs-12">
				<button id="add" class="btn btn-success input-sm" >
					<i class="icon-plus"></i> 新增
				</button>
				<button id="modify" class="btn btn-primary input-sm" disabled>
					<i class="icon-pencil"></i> 修改
				</button>
				<button id="remove" class="btn btn-danger input-sm" disabled>
					<i class="icon-trash"></i> 删除
				</button>
			</div>
			<div class="col-xs-12">
			<!--表格-->
			<table id="table" class="table table-condensed"></table>
			</div>
		</div>
	</div>
	</div>
		</div>
	<!--选择角色时排除已选的  -->
	<input type="hidden" id="noRole" />
	<script src="${ctxStatic}/modules/sys/userGroup.js"></script>
</body>
</html>