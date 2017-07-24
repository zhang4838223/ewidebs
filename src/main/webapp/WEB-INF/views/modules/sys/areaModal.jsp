<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<form id="areaForm" name="loginForm" class="login-form" method="post">
	<input type="hidden" id="id" name="id" value="${area.id}" />
	<div class="row ">
		<div class="col-xs-8 col-xs-offset-1 form-group">
			<div class="input-group">
				<label class="input-group-addon" id="basic-addon1"><span class="required">*</span>上级区域</label>
				<sys:treeselect id="parent" name="parent.id"
					value="${area.parent.id}" labelName="parent.name"
					labelValue="${area.parent.name}" title="区域"
					url="/sys/area/treeData" 
					allowClear="true" />
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>区域名称</label> 
				<input type="text" class="form-control input-sm" placeholder="区域名称" id="name" name="name" value="${area.name}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>区域编码</label> 
				<input type="text" class="form-control input-sm" placeholder="区域编码" id="code" name="code" value="${area.code}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group padding_none">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>区域类型</label>
				<select id="type" name="type" class="form-control" value="${area.type}"></select>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>排序</label> 
				<input type="text" class="form-control input-sm" placeholder="排序" id="sort" name="sort" value="${area.sort}">
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-8 col-xs-offset-1 form-group">
			<div class="input-group">
				<label class="input-group-addon">备注</label>
				<textarea class="form-control" id="remarks" name="remarks" rows="3" cols="30" style="resize: none">${area.remarks}</textarea>
			</div>
		</div>
	</div>
</form>
<!--判断是否存在根节点-->
<input type="hidden" id="count" value="${count}"/>
<!--修改判断排除name唯一-->
<input type="hidden" id="noName" value="${area.name}"/>
<!--修改判断排除code唯一-->
<input type="hidden" id="noCode" value="${area.code}"/>
<!--修改判断排除parentId唯一-->
<input type="hidden" id="noParentId" value="${area.parent.id}"/>
<!--选择节点的parentIds-->
<input type="hidden" id="parentIds" value=""/>
<!--选择节点的areaId-->
<input type="hidden" id="areaId" value=""/>

<!--上级区域排除自己  -->
<input type="hidden" id="noId" value="${area.id}"/>
<script src="${ctxStatic}/modules/sys/areaModal.js"></script>