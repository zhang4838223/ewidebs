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
		<div class="row">
			<div class="col-xs-2  padding_0 left-layout">
				<!--边栏内容-->
				<div class="row">
					<a class="accordion-toggle" style="font-family: 黑体; font-size: 14px; color: #337ab7; text-decoration: none">区域结构
					</a>
					<div id="ztree" class="ztree" style="margin-top: 0px"></div>
				</div>
			</div>
			<div class="col-xs-10 right-layout">
			<!--主体内容-->
				<!--搜索框-->
				<div class="row">
					<form class="navbar-form search-wrap " role="search">
						<span class="form-group "> 
						<span class=" input-sm">区域名称</span>
							<input type="text" id="names" name="name" class="required form-control input-sm " placeholder="区域名称">
						</span> 
						<span class="form-group "> 
						<span class=" input-sm">区域编码</span>
							<input type="text" id="codes" name="code" class="required form-control input-sm " placeholder="区域编码">
						</span> 
						<span class="form-group "> 
						<span class=" input-sm">区域类型 </span>
							<select id="typeSel" style="width:162px;" name="type" class="form-control" ></select>
						</span> 
						<span class="form-group " >
							<button type="button" id="from_findarea" class="btn btn-info input-sm"><i class="icon-search"></i> 查询</button>
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
					<div class="col-xs-12">
					<!--表格-->
					<table id="table" class="table table-condensed"></table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 初始化table区域类型名称 -->
	<div id="sys_area_types" style="display: none">${fns:toJson(fns:getDictList('sys_area_type'))}</div>
	<%@include file="/WEB-INF/views/include/js-footer.jsp"%>
	<script src="${ctxStatic}/modules/sys/area.js"></script>
</body>
</html>