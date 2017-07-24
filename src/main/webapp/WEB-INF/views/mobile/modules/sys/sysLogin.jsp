<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">   
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html style="overflow: hidden;">
<head>
<meta charset="UTF-8">
<title>${fns:getConfig('productName')}登录</title>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sysLogin/css/sysLogin.css" />
<script type="text/javascript">
$(document).ready(function() {
	$("#loginForm").validate({
		rules : {
			validateCode : {
				remote : "${pageContext.request.contextPath}/servlet/validateCodeServlet"
			}
		},
		messages : {
			username : {
				required : "请填写用户名."
			},
			password : {
				required : "请填写密码."
			},
			validateCode : {
				remote : "验证码不正确.",
				required : "请填写验证码."
			}
		},
		errorLabelContainer : "#messageBox",
		errorPlacement : function(error,
				element) {
			error.appendTo($("#loginError")
					.parent());
		}
	});
});
//如果在框架或在对话框中，则弹出提示并跳转到首页
if (self.frameElement && self.frameElement.tagName == "IFRAME"
		|| $('#left').length > 0 || $('.jbox').length > 0) {
	alert('未登录或登录超时。请重新登录，谢谢！');
	top.location = "${ctx}";
}
</script>		
	</head>
	<body style="background: url(${ctxStatic}/sysLogin/img/login_bg.jpg) repeat center center/100% 100%;">	
	<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">	
		<div class="login">
			<div class="login_logo"><img src="${ctxStatic}/sysLogin/img/login_logo.png"></div>
	 		<div class="login_conter">
	  			<div class="login_conter_text"><img src="${ctxStatic}/sysLogin/img/login_conter_text.png"></div>
	  			<div class="login_reset">
	  				<label class="login_label">
	  					<span>登&nbsp;录&nbsp;名：</span>
	  					<input id="username" name="username" class="form-control required" value="${username}" />
	  				</label>
	  				<label class="login_label">
	  					<span>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
	  					<input type="password" id="password" name="password" class="form-control required" />
	  				</label>
	  				<c:if test="${isValidateCodeLogin}">
						<div class="validateCode">
							<label class="input-label mid" for="validateCode">验&nbsp;证&nbsp;码：</label>
							<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;" />
						</div>
					</c:if>
	  				<div class="login_save">
	  					<label class="pull-left" for="rememberMe" title="下次不需要再登录" style="margin-left:5px;">
	  					<input type="checkbox" id="rememberMe"	name="rememberMe" ${rememberMe ? 'checked' : ''} />
	  					<span>记住登录状态</span>
	  					<i>${message}</i>
	  					<div class="clear"></div>
	  				</div>
					<input type="submit" value="登 录" class="login_btn"/>
	  			</div>
	 		</div>
	 		<div class="footer">
			Copyright &copy; 2016-${fns:getConfig('copyrightYear')} 
			<a href="${pageContext.request.contextPath}${fns:getFrontPath()}">${fns:getConfig('productName')}</a>
			- Powered By <a href="http://www.ewide.net/" target="_blank">武汉易维科技股份有限公司</a>
			${fns:getConfig('version')}
		</div>
		</div>		
	</form>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/basecss/js/jquery.min.js"></script>
	</body>
</html>