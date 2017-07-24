<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<form id="subsystemForm" class="login-form" action="" method="post">
	<input type="hidden" id="id" name="id" value="${subsystem.id}" />
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group">
			<div class="input-group">
				<label class="input-group-addon" id="basic-addon1"><span class="required">*</span>子系统编号</label> 
				<input type="text" class="form-control input-sm" placeholder="子系统编号" name="systemCode" id="systemCode" value="${subsystem.systemCode}"> 
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>子系统名称</label> 
				<input type="text" class="form-control input-sm" placeholder="子系统名称" name="systemName" id="systemName" value="${subsystem.systemName}"> 
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon">链接地址</label> 
				<input type="text" class="form-control input-sm" placeholder="链接地址" name="url" id="url" value="${subsystem.url}">
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-8 col-xs-offset-1 form-group">
			<div class="input-group">
				<label class="input-group-addon">备注信息</label>
				<textarea class="form-control" rows="3" cols="30" style="resize: none" name="remarks" id="remarks">${subsystem.remarks}</textarea>
			</div>
		</div>
	</div>
</form>
<!--修改判断排除systemName唯一-->
<input type="hidden" id="noName" value="${subsystem.systemName}"/>
<!--修改判断排除systemCode唯一-->
<input type="hidden" id="noCode" value="${subsystem.systemCode}"/>
<script src="${ctxStatic}/modules/sys/subsystemModal.js"></script>