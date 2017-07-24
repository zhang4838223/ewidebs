<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<form:form id="userForm" modelAttribute="user" method="post">
	<%-- <div class="row">
		<div class="col-xs-5  form-group">
			<div class="input-group">
				<label class="input-group-addon" id="basic-addon1">头像</label>
				<form:hidden id="nameImage" path="photo" htmlEscape="false"
					maxlength="255" class="input-xlarge" value="${user.photo }" />
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
		</div>
	</div> --%>
	<div class="row ">
		<div class="col-xs-5 form-group ">
			<div class="input-group">
				<label class="input-group-addon" id="basic-addon2"><span class="required">*</span>归属公司</label>
				<sys:treeselect id="company" name="company.id" 
					value="${user.company.id}" labelName="company.name" 
					labelValue="${user.company.name}" title="公司"
					url="/sys/office/treeData?type=1" cssClass="required form-control" allowClear="true"/>
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>归属部门</label>
				<sys:treeselect id="office" name="office.id" 
					value="${user.office.id}" labelName="office.name" 
					labelValue="${user.office.name}" title="部门"
					url="/sys/office/treeData?type=2&parentId=${user.company.id}" cssClass="required form-control"  
					notAllowSelectParent="true" allowClear="true"/>
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-11 form-group">
			<div class="input-group">
				<label class="input-group-addon">兼职部门</label>
				<sys:treeselect id="userOffice" name="userOffice" value=""
					labelName="userOffice.name" labelValue="" title="兼职部门"
					url="/sys/office/treeData?type=2" 
					cssClass="required form-control"
					notAllowSelectParent="true" checked="true" allowClear="true"
					cssStyle="width:100%" />
			</div>
		</div>

	</div>
	<div class="row ">
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>姓名</label> 
				<input type="text" class="form-control input-sm" placeholder="姓名" id="name" name="name" value="${user.name }">
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group">
				<label class="input-group-addon "><span class="required">*</span>登录名</label> 
				<input type="text" class="form-control input-sm" placeholder="登录名" id="loginName" name="loginName" value="${user.loginName}">
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon"><c:if test="${empty user || user.id==''}"><span class="required">*</span></c:if>密码</label> 
				<input type="password" class="form-control input-sm" placeholder="密码" id="newPassword" name="newPassword">
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group">
				<label class="input-group-addon"><c:if test="${empty user || user.id==''}"><span class="required">*</span></c:if>确认密码</label>
				<input type="password" class="form-control input-sm" placeholder="确认密码" id="confirmNewPassword" name="confirmNewPassword">
				<span id="passwordMsg" style="display: none"><font color="red">注:留空为不修改</font></span>
			</div>
		</div>

	</div>
	<div class="row">
		<div class="col-xs-5 form-group">
			<div class="input-group ">
				<label class="input-group-addon">邮箱</label>
				<input type="text" class="form-control input-sm" placeholder="邮箱" id="email" name="email" data-bv-field="email" value="${user.email }">
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group ">
				<label class="input-group-addon">电话</label>
				<input type="text" class="form-control input-sm" placeholder="电话" id="phone" name="phone" value="${user.phone }">
			</div>
		</div>

	</div>

	<div class="row">
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon">手机</label>
				<input type="text" class="form-control input-sm" placeholder="手机" id="mobile" name="mobile" value="${user.mobile }">
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group">
				<label class="input-group-addon">是否允许登录</label>
				<select name="loginFlag" id="loginFlag" class="form-control" value="${user.loginFlag }"></select>
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon">用户类型</label>
				<select name="userType" id="userType" class="form-control" value="${user.userType }"></select>
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group">
				<label class="input-group-addon">用户性别</label>
				<select name="sex" id="sex" class="form-control" value="${user.sex }"></select>
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-5 form-group ">
			<div class="input-group">
				<label class="input-group-addon">职位</label>
				<select name="position.id" id="position" class="form-control" value="${user.position.id }"></select>
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>工号</label>
				<input type="text" class="form-control input-sm" placeholder="工号" id="no" name="no" value="${user.no }">
			</div>
		</div>
	</div>
	<div class="row">
	<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>排序</label>
				<input type="text" class="form-control input-sm" placeholder="排序" id="sort" name="sort" value="${user.sort}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-9 form-group">
			<div class="input-group">
				<label class="input-group-addon">角色</label>
				<div id="roles" style="border: 1px solid #ccc; min-height: 30px;" name="roles">
				</div>
			</div>
		</div>
		<div class="col-xs-2  form-group">
			<div class="input-group">
				<button type="button" id="allotrole"
					class="btn btn-default btn-primary input-sm"
					>选择角色</button>
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-11 form-group">
			<div class="input-group">
				<label class="input-group-addon">备注</label>
				<textarea class="form-control" id="remarks" name="remarks" rows="3"
					cols="30" style="resize: none">${user.remarks }</textarea>
			</div>
		</div>
	</div>
<input id="id" name="id" type="hidden" value="${user.id }">
<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
<input id="oldNo" name="oldNo" type="hidden" value="${user.no}"> 
<input type="hidden" id="roleids" name="roleids" />
</form:form>
<script src="${ctxStatic}/modules/sys/userModal.js"></script>