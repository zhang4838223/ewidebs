<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
<meta name="decorator" content="defaultx" />
<%@include file="/WEB-INF/views/include/js-footer.jsp"%>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-2  padding_0 left-layout">
				<!--边栏内容-->
				<div class="row">
					<a class="accordion-toggle"
						style="font-family: 黑体; font-size: 14px; color: #337ab7; text-decoration: none">组织机构
					</a>
					<div id="ztree" class="ztree" style="margin-top: 0px"></div>
				</div>
			</div>
			<!--主体内容-->
			<div class="col-xs-10 right-layout">
				<!--搜索框-->
				<div class="row">
					<form class="navbar-form search-wrap main_search" role="search">
						<span class="form-group "> 
						<span class=" input-sm">归属公司</span>	
							<sys:treeselect id="searchCompany" name="company.id" value="" labelName="company.name" labelValue="" title="公司" url="/sys/office/treeData?type=1" cssClass="required form-control" />
						</span> 
						<span class="form-group "> 
						<span class=" input-sm">归属部门</span>
							<sys:treeselect id="searchOffice" name="office.id" value="" labelName="office.name" labelValue="" title="部门" url="/sys/office/treeData?type=2&parentId=${user.company.id}" cssClass="required form-control" notAllowSelectParent="true" />
						</span> 
						<span class="form-group "> 
						<span class=" input-sm">工号</span>
							<input type="text" class="form-control input-sm" placeholder="工号" id="searchNo" name="no">
						</span> 
						<span class="form-group ">
						 <span class=" input-sm">登录名</span>
							<input type="text" class="form-control input-sm" placeholder="登录名" value="" id="searchLoginName">
						</span>
						 <span class="form-group "> 
						<span class=" input-sm">姓名</span>
							<input type="text" class="form-control input-sm" value="" placeholder="姓名" id="searchName">
						</span> 
						<span class="form-group ">
							<button type="button" id="search" class="btn btn-info input-sm">
								<i class="icon-search"></i> 查询
							</button>
							<button type="button" id="reset" class="btn btn-warning input-sm">
								<i class="icon-refresh"></i> 重置
							</button>
						</span>
					</form>
				</div>
				<div class="row">
					<shiro:hasPermission name="sys:user:edit">
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
					</shiro:hasPermission>
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
	<input type="hidden" id="parentids" />
	<!-- 选中的树节点的ID和NAME -->
	<input type="hidden" id="office_id" />
	<input type="hidden" id="officeId_" />
	<input type="hidden" id="officeName_" />
	<!-- 选中的树节点的上级ID和上级NAME -->
	<input type="hidden" id="pOfficeId" />
	<input type="hidden" id="pOfficeName" />
	<div id="sys_data_scope" style="display: none">${fns:toJson(fns:getDictList('sys_data_scope'))}</div>
	<script src="${ctxStatic}/modules/sys/user.js"></script>
</body>
</html>