<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>	
<html>
<head>
<title>机构管理</title>
<meta name="decorator" content="defaultx"/>
<%@include file="/WEB-INF/views/include/js-footer.jsp" %>
</head>
<body>
<div class="container-fluid">
  <div class="row">
	<div class="col-xs-2  padding_0 left-layout">
		<!--边栏内容-->
		<div class="row">
		 	<a class="accordion-toggle" style="font-family: 黑体; font-size: 14px; color: #337ab7; text-decoration: none">组织机构
			</a>
			<div id="ztree" class="ztree" style="margin-top: 0px"></div>
		</div>
	</div>
	<!--主体内容-->
	<div class="col-xs-10 right-layout">
	    <!--搜索框-->
	    <div class="row">
	    	<form class="navbar-form search-wrap main_search" role="form">
						<span class="form-group "> 
						<span class=" input-sm">机构名称</span>
							<input type="text" class="form-control input-sm" placeholder="机构名称" value="" id="names" />
						</span>
						<span class="form-group "> 
						<span class=" input-sm">归属区域</span>
							<input type="text" class="form-control input-sm" value="" placeholder="归属区域" id="areaNames" />
						</span>
						<span class="form-group "> 
						<span class=" input-sm">主负责人</span>
							<sys:treeselect id="primaryPerson1" name="primaryPersonId" value="" labelName="primaryPersonName" labelValue="" title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true" cssClass="required form-control" />
						</span>
			            <span class="form-group">
			            	<button type="button" id="search" class="btn btn-info input-sm"><i class="icon-search">查询</i></button>
				            <button type="button" id="reset" class="btn btn-warning input-sm"><i class="icon-refresh">重置</i></button>
				        </span>
			</form>
	    </div>
	   
	    <div class="row">
	    	<shiro:hasPermission name="sys:office:edit">
	        <!--表格头部组件-->
	        <div id="toolbar" class="col-xs-12">
	            <button id=add class="btn btn-success input-sm">
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
<input type="hidden" id="officeId_" value="${office.id}" />
<input type="hidden" id="officeName_" value="${office.name}" />
<input type="hidden" id="officeParentIds_" value="${office.parentIds}" />
<input type="hidden" id="officeId1_" value="${office.id}" />
<input type="hidden" id="officeName1_" value="${office.name}" />
<div id="sys_office_types" style="display: none">${fns:toJson(fns:getDictList('sys_office_type'))}</div>
<script src="${ctxStatic}/modules/sys/office.js"></script>
</body>
</html>