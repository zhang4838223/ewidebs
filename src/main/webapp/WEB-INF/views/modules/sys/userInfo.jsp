<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>个人信息</title>
<meta name="decorator" content="defaultx" />
</head>
<body>
	<form id="userInfoForm" class="loose-form" method="post">

		<div class="row">
			<div class="col-xs-7 form-group col-xs-offset-2">
				<div class="input-group">
					<legend contenteditable="false">个人信息</legend>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-3 form-group col-xs-offset-2">
				<div class="input-group">
					<label class="input-group-addon">归属公司</label> <input readonly
						type="text" class="form-control input-sm" placeholder="归属公司"
						aria-describedby="basic-addon1" value="${user.company.name}">
				</div>
			</div>
			<div class="col-xs-3 form-group col-xs-offset-1">
				<div class="input-group">
					<label class="input-group-addon">归属部门</label> <input readonly
						type="text" class="form-control input-sm" placeholder="归属部门"
						aria-describedby="basic-addon1" value="${user.office.name}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-3 form-group col-xs-offset-2">
				<div class="input-group">
					<label class="input-group-addon">姓名</label> <input readonly
						type="text" class="form-control input-sm" placeholder="姓名"
						aria-describedby="basic-addon1" name="name" value="${user.name}">
				</div>
			</div>
			<div class="col-xs-3 form-group col-xs-offset-1">
				<div class="input-group">
					<label class="input-group-addon">用户类型</label> <input readonly
						type="text" class="form-control input-sm" placeholder="用户类型"
						aria-describedby="basic-addon1" name="userType"
						value="${fns:getDictLabel(user.userType, 'sys_user_type', '无')}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-3 form-group col-xs-offset-2">
				<div class="input-group">
					<label class="input-group-addon">用户角色</label> <input readonly
						type="text" class="form-control input-sm" placeholder="用户角色"
						aria-describedby="basic-addon1" value="${user.roleNames}">
				</div>
			</div>
			<div class="col-xs-3 form-group col-xs-offset-1">
				<div class="input-group">
					<label class="input-group-addon">上次登录</label> <input readonly
						type="text" class="form-control input-sm" placeholder="上次登录"
						aria-describedby="basic-addon1"
						value="IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.oldLoginDate}" type="both" dateStyle="full"/>">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-3 form-group col-xs-offset-2">
				<div class="input-group">
					<label class="input-group-addon">电话</label> <input type="text"
						class="form-control input-sm" placeholder="电话"
						aria-describedby="basic-addon1" name='phone' value="${user.phone}">
				</div>
			</div>
			<div class="col-xs-3 form-group col-xs-offset-1">
				<div class="input-group">
					<label class="input-group-addon">手机</label> <input type="text"
						class="form-control input-sm" placeholder="手机"
						aria-describedby="basic-addon1" name='mobile'
						value="${user.mobile}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-3 form-group col-xs-offset-2">
				<div class="input-group">
					<label class="input-group-addon">备注</label>
					<textarea name="remarks" class="form-control" rows="3" cols="100"
						style="resize: none" name="remarks"> ${user.remarks}</textarea>
				</div>
			</div>
			<div class="col-xs-3 form-group col-xs-offset-1">
				<div class="input-group">
					<label class="input-group-addon">邮箱</label> <input type="text"
						class="form-control input-sm" placeholder="邮箱"
						aria-describedby="basic-addon1" name='email' value="${user.email}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-1 form-group col-xs-offset-5">
				<div class="input-group">
					<button type="button" id="submitForm" class="btn btn-primary">
						<i class="icon-file"></i> 保存
					</button>
				</div>
			</div>
		</div>
	</form>
	<%@include file="/WEB-INF/views/include/js-footer.jsp"%>
	<script src="${ctxStatic}/modules/sys/userInfo.js"></script>
</body>
</html>