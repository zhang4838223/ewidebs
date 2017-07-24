<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>菜单管理</title>
<meta name="decorator" content="defaultx" />
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-2  padding_0 left-layout">
				<!--边栏  -->
				<div class="row">
					<div class="col-xs-12">
						<a class="accordion-toggle"style="font-family: 黑体; font-size: 14px; color: #337ab7; text-decoration: none">菜单结构</a>
					</div>
					<div class="col-xs-12">
					 <select id="menuSel" name="menuSel" class="form-control" value="${menuCode}"></select>
					</div>
					<div class="col-xs-12">
						<div id="ztree" class="ztree" style="margin-top: 0px"></div>
					</div>
				</div>
			</div>
			<div class="col-xs-10 right-layout">
				<!--主体内容-->
				<!--搜索框-->
				<div class="row">
					<form class="navbar-form search-wrap" role="search">
						<span class="form-group"> <span class="input-sm">菜单名称</span>
							<input type="text" id="names" name="name"
							class="required form-control input-sm " placeholder="菜单名称">
						</span> 
						<span class="form-group ">
							<button type="button" id="from_findmenu" class="btn btn-info input-sm"><i class="icon-search"></i> 查询</button>
							<button type="button" id="reset" class="btn btn-warning input-sm"><i class="icon-refresh"></i> 重置</button>
						</span>
					</form>
					
				</div>
				<!--内容-->
				<div class="row">
					<!--表格头部组件-->
					<div id="toolbar">
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
					<table id="table" class="table table-condensed"></table>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="defaultMenuCode" value="${menuCode}">
	<%@include file="/WEB-INF/views/include/js-footer.jsp"%>
	<script src="${ctxStatic}/modules/sys/menu.js"></script>
</body>
</html>