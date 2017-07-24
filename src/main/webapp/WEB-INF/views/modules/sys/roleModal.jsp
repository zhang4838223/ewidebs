<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/treeview.jsp" %>
<form id="roleForm" class="login-form" action="" method="post">
	<div class="row">
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>归属公司</label>
				<sys:treeselect id="office" name="office.id"
					value="${role.office.id}" labelName="office.name"
					labelValue="${role.office.name}" title="公司"
					url="/sys/office/treeData?type=1"
					cssClass="required form-control" />
			</div>
		</div>
		<div class="col-xs-5 form-group">
				<div class="input-group">
					<label class="input-group-addon">角色类型</label>
					<select name="roleType" id="roleType" class="form-control" value="${role.roleType }"></select>
				</div>
		</div>
	</div>
		
	<div class="row">
		<div class="col-xs-5  form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>角色名称</label>
				<div class="controls">
					<input id="name" name="name" type="text"  maxlength="50"
						class="form-control input-sm" value="${role.name }" placeholder="角色名称"/>
				</div>
			</div>
		</div>
		<div class="col-xs-5  form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>角色编号</label>
				<div class="controls">
					<input id="roleCode" name="roleCode" type="text"  maxlength="50"
						class="form-control input-sm" value="${role.roleCode}" placeholder="角色编号"/>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-xs-5  form-group">
			<div class="input-group">
				<label class="input-group-addon">是否可用</label>
				<select name="useable" id="useable" class="form-control" value="${role.useable }"></select>
			</div>
		</div>
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>数据范围</label>
				<select name="dataScope" id="dataScope" class="form-control" value="${role.dataScope }"></select>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon">子系统</label>
				<select id="menuSel" name="menuSel" class="form-control" value="${role.menuSel }" ></select>
			</div>
		</div>
	</div>
	

	<div class="form-group">
		<div class="input-group">
			<label class="input-group-addon">角色授权</label>
			<div class="controls">
				<div id="menuTree" class="ztree"
					style="margin-top: 3px; float: left;"></div>
				<div id="officeTree" class="ztree"
					style="margin-left: 100px; margin-top: 3px; float: left;"></div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-xs-11  form-group">
			<div class="input-group">
				<label class="input-group-addon">备注</label>
				<textarea id="remarks" name="remarks" rows="3" cols="30" style="resize: none"
					maxlength="200" class="form-control">${role.remarks }</textarea>
			</div>
		</div>
	</div>
<input id="oldRoleCode" name="oldRoleCode" type="hidden" value="${role.roleCode}"/>	
<!-- 读取的角色名称 -->
<input id="oldName" name="oldName" type="hidden" value="${role.name }"/>
<!-- 角色ID -->
<input type="hidden" id="id" name="id" value="${role.id }"/>
<!-- 角色授权菜单 -->
<input type="hidden" id="menuIds" name="menuIds" value="${role.menuIds}"/>
<!-- 角色授权机构 -->
<input type="hidden" id="officeIds" name="officeIds" value="${role.officeIds}"/>
<!-- 菜单 -->
<input type="hidden" id="menuList" value="${menuList}" />
<!-- 子系统map菜单 -->
<div style="display:none" id="menuMap">${menuMap}</div>
<!-- 机构 -->
<input type="hidden" id="officeList" value="${officeList}" />
</form>
<script src="${ctxStatic}/modules/sys/roleModal.js"></script>