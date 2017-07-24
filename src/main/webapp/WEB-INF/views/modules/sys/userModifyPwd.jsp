<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>修改密码</title>
<meta name="decorator" content="defaultx" />
</head>
<body>
	<form id="userModifyPwdForm" class="loose-form" method="post">
		<div class="row">
			<div class="col-xs-3 form-group col-xs-offset-4">
				<div class="input-group">
					<legend contenteditable="false">修改密码</legend>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-3 form-group col-xs-offset-4">
				<div class="input-group">
					<label class="input-group-addon">旧密码</label> <input type="password"
						class="form-control input-sm" placeholder="旧密码"
						aria-describedby="basic-addon1" name="oldPassword">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-3 form-group col-xs-offset-4">
				<div class="input-group">
					<label class="input-group-addon">新密码</label> <input type="password"
						class="form-control input-sm" placeholder="新密码"
						aria-describedby="basic-addon1" name="newPassword">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-3 form-group col-xs-offset-4">
				<div class="input-group">
					<label class="input-group-addon">确认新密码</label> <input
						type="password" class="form-control input-sm" placeholder="确认新密码"
						aria-describedby="basic-addon1" name="confirmNewPassword">
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
	<script src="${ctxStatic}/modules/sys/userModifyPwd.js"></script>
</body>
</html>