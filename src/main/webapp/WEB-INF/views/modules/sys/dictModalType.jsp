<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--模态框-->
<form id="dictTypeForm" class="login-form" action="" method="post">
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>类型名称</label> 
				<input type="text" class="form-control input-sm" placeholder="类型名称" id="label" name="label" value="${dict.label}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>类型编号</label> 
				<input type="text" class="form-control input-sm" placeholder="类型编号" id="value" name="value" value="${dict.value}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>排序</label> 
				<input type="text" class="form-control input-sm" placeholder="排序" id="sort" name="sort" value="${dict.sort}">
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-8 col-xs-offset-1 form-group">
			<div class="input-group">
				<label class="input-group-addon">描述</label>
				<textarea class="form-control" rows="3" cols="30" style="resize: none" name="remarks" id="remarks">${dict.remarks}</textarea>
			</div>
		</div>
	</div>
	<input type="hidden" id="id" name="id" value="${dict.id}" /> 
	<input type="hidden" id="parentIdModalType" name="parentId" value="${dict.parentId}" /> 
	<input type="hidden" id="dictType" name="dictType" value="TYPE" />
</form>
<!--修改判断排除label唯一-->
<input type="hidden" id="noLabel" value="${dict.label}" />
<!--修改判断排除value唯一-->
<input type="hidden" id="noValue" value="${dict.value}" />
<script src="${ctxStatic}/modules/sys/dictModalType.js"></script>