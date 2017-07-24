<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<form id="userGroupForm" class="login-form" action="" method="post">
	<div class="row ">
		<div class="col-xs-5  form-group">
			<div class="input-group">
				<label class="input-group-addon" id="basic-addon1"><span class="required">*</span>用户组名称</label> 
				<input type="text" class="form-control input-sm" placeholder="组名" name="name" aria-describedby="basic-addon1" id="names" value="${userGroup.name}" > 
			</div>
		</div>
		<div class="col-xs-5  form-group">
			<div class="input-group">
				<label class="input-group-addon" id="basic-addon1"><span class="required">*</span>用户组编号</label> 
				<input type="text" class="form-control input-sm" placeholder="组编号" name="code" id="codes" aria-describedby="basic-addon1" value="${userGroup.code}"> 
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>归属公司</label>
				<sys:treeselect id="company" name="company.id" value="${userGroup.company.id}"
					labelName="company.name" labelValue="${userGroup.company.name}" title="公司"
					url="/sys/office/treeData?type=1"   allowClear="true" />
			</div>
		</div>
		<div class="col-xs-5  form-group">
			<div class="input-group">
				<label class="input-group-addon" id="basic-addon1"><span class="required">*</span>排序</label> 
				<input type="text" class="form-control input-sm" placeholder="排序" name="sort" id="sort" aria-describedby="basic-addon1" value="${userGroup.sort}"> 
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-5  form-group">
			<div class="input-group">
				<button type="button" id="allotrole"
					class="btn btn-default btn-primary input-sm"
					style="margin-left: 100px;">选择角色</button>
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-12 form-group">
			<div class="input-group">
				<label class="input-group-addon">角色</label>
				<div id="roles" class="buttonx">
				<c:forEach items="${userGroup.roleList}" var="i">
				<button class='btn input-sm' data-uid="${i.id}" type='button' id='${i.id}' style='background-color:#BC4DA0;color:#fff'>${i.name}<i class='icon-remove'></i></button>
				</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-5  form-group">
			<div class="input-group">
				<button type="button" id="allotuser"
					class="btn btn-default btn-primary input-sm"
					style="margin-left: 100px;">选择成员</button>
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-12 form-group">
			<div class="input-group">
				<label class="input-group-addon">成员</label>
				<table id="userTable" class="table table-condensed"></table>
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-10 form-group">
			<div class="input-group">
				<label class="input-group-addon">备注</label>
				<textarea name="remarks" class="form-control" rows="3" cols="100"
					style="resize: none" id="remarks"> ${userGroup.remarks}</textarea>
			</div>
		</div>
	</div>
	<!-- 人员所有ids -->
	<input type="hidden" id="userids" name="userids" />
	<!-- 角色所有ids -->
	<input type="hidden" id="roleids" name="roleids" />
	<!-- 修改id -->
	<input type="hidden" name="id" id="id" value="${userGroup.id}">
</form>

<input type="hidden" id="parentids" />
<!-- 父页面传子页面排除已经选择的用户 -->
<input type="hidden" id="userids" />
<!--选择角色时排除已选的  -->
<input type="hidden" id="noRole" />
<!--修改判断排除name唯一-->
<input type="hidden" id="noName" value="${userGroup.name}"/>
<!--修改判断排除code唯一-->
<input type="hidden" id="noCode" value="${userGroup.code}"/>
<!-- 将${ctx}传递到userGroup.js页面 -->
<script src="${ctxStatic}/modules/sys/userGroupModal.js"></script>