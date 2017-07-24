<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<form:form id="assignForm" modelAttribute="role" class="login-form"
	action="${ctx}/sys/role/save" method="post">
	<br/>
	<div class="container-fluid breadcrumb">
		<div class="row">
			<span class="col-sm-6"><h4 id="assignName"></h4></span> 
			<span class="col-sm-3" ><h4 id="assignCode"></h4></span>
		</div>
		<div class="row">
			<span class="col-sm-6" ><h4 id="assignOffice"></h4> </span> 
			<span class="col-sm-3" ><h4 id="assignType"></h4></span> 
			
		</div>
		<div class="row">
			<span class="col-sm-6" ><h4 id="assignScope"></h4></span>
		</div>
		<sys:message content="${message}" />

	</div>
	<br/>
	<div class="breadcrumb">
		<form id="assignRoleForm" action="${ctx}/sys/role/assignrole"
			method="get" class="hide">
			<input type="hidden" id="assignRoleId" name="id" value="${role.id}"/>
			<input type="hidden" id="assignRoleName" /> 
			<input id="idsArr" type="hidden" name="idsArr" value=""/>
		</form>
		<input id="assignButton" class="btn btn-primary" type="button" value="分配用户" />
	</div>
	<table id="contentTable" class="table table-condensed"></table>
</form:form>
<script src="${ctxStatic}/modules/sys/roleAssignModal.js"></script>