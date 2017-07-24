<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--修改模态框-->
<form id="positionForm" class="login-form" action="" method="post">
	<input type="hidden" id="id" name="id" value="${position.id}" />
	<div class="row">
		<div class="col-xs-10 form-group ">
			<div class="input-group">
				<label class="input-group-addon" id="basic-addon2"><span class="required">*</span>归属公司</label>
				<sys:treeselect id="office" name="office.id"
					value="${position.office.id}" labelName="office.name"
					labelValue="${position.office.name}" title="公司"
					url="/sys/office/treeData?type=1" allowClear="true" />
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-10  form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>职位名称</label> 
				<input id="updateNames" name="positionName"  class="required form-control" value="${position.positionName}"/> 
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-10  form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>职位编号</label> 
				<input id="updateNo" name="positionNo"   class="required form-control" value="${position.positionNo}"/> 
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-10  form-group">
			<div class="input-group">
				<label class="input-group-addon">备注</label>
				<textarea id="remarks" name="remarks" class="form-control"  rows="3" cols="30" style="resize: none">${position.remarks}</textarea>
			</div>
		</div>
	</div>
</form>
<!--修改判断排除positionName唯一-->
<input type="hidden" id="noName" value="${position.positionName}"/>
<!--修改判断排除officeId唯一-->
<input type="hidden" id="noofficeId" value="${position.office.id}"/>
<!--修改判断排除positionNo唯一-->
<input type="hidden" id="noCode" value="${position.positionNo}"/>
<script src="${ctxStatic}/modules/sys/positionModal.js"></script>