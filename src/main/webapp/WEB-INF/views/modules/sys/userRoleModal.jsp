<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--搜索框-->
<form class="navbar-form search-wrap" role="search" onsubmit="return false;">
	<div class="col-xs-12">
		<span class="form-group "> 
			<span class=" input-sm">角色名称</span>
			<input type="text" id="rolenames" name="rolenames" class="required form-control input-sm " placeholder="角色名称">
		</span> 
		<span class="form-group " style="margin-bottom: 8px">
			<button type="button" id="from_findRole" class="btn btn-default btn-primary input-sm">查询</button>
			<button type="button" id="from_resetRole" class="btn btn-default btn-primary input-sm">重置</button>
		</span>
	</div>
</form>
<table id="roleTable"></table>
<div id="sys_data_scope" style="display: none">${fns:toJson(fns:getDictList('sys_data_scope'))}</div>
<script src="${ctxStatic}/modules/sys/userRoleModal.js"></script>