<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>数据字典管理</title>
<meta name="decorator" content="defaultx" />
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-2  padding_0 left-layout">
				<!--边栏内容-->
				<div class="row">
					<a class="accordion-toggle"
						style="font-family: 黑体; font-size: 14px; color: #337ab7; text-decoration: none">字典结构
					</a>
				</div>
				<div class="row btnwidth">
					<!--表格头部组件-->
						<button id="addType" class="btn btn-success input-sm  btn_add" >
							<i class="icon-plus"></i>新增
						</button>
						<button id="modifyType" class="btn btn-primary input-sm   btn_update" disabled>
							<i class="icon-pencil"></i>修改
						</button>
						<button id="removeType" class="btn btn-danger input-sm  btn_delete" disabled>
							<i class="icon-trash"></i>删除
						</button>
				</div>
				<div class="row btnwidth">
					<div class="col-xs-7 ">
						<input type="text" id="selectlabel" name="label" class="required form-control input-sm " placeholder="类型名称">
					</div>
					<div class="col-xs-3">
					<button type="button" id="from_findDict" class="btn btn-info input-sm"><i class="icon-search"></i> 查询</button>
					</div>
				</div>
				<div class="row">
					<div id="ztree" class="ztree" style="margin-top: 0px"></div>
				</div>
			</div>
			<div class="col-xs-10 right-layout">
				<!--主体内容-->
				<!--搜索框-->
				<div class="row">
					<form class="navbar-form search-wrap" role="search">
						<span class="form-group "> <span class=" input-sm">类型名称</span>
						<input type="text" id="labels" readonly class="required form-control input-sm ">
						</span> <span class="form-group "> 
						<span class=" input-sm">类型编号</span>
						<input type="text" id="values" readonly class="required form-control input-sm "> 
						<span class=" input-sm">描述</span>
						<input type="text" id="remarkss" readonly class="required form-control input-sm ">
						</span> 
					</form>
				</div>
				<div class="row">
					<!--表格头部组件-->
					<div id="toolbar"  class="col-xs-12">
						<button id="add" class="btn btn-success input-sm btn_add" data-toggle="modal" disabled>
							<i class="icon-plus"></i> 新增
						</button>
						<button id="modify" class="btn btn-primary input-sm btn_update" disabled>
							<i class="icon-pencil"></i> 修改
						</button>
						<button id="remove" class="btn btn-danger input-sm btn_delete" disabled>
							<i class="icon-trash"></i> 删除
						</button>
					</div>
					<div  class="col-xs-12">
					<!--表格-->
					<table id="table" class="table table-condensed"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="parentId" />
	<%@include file="/WEB-INF/views/include/js-footer.jsp"%>
	<script src="${ctxStatic}/modules/sys/dict.js"></script>
</body>
</html>