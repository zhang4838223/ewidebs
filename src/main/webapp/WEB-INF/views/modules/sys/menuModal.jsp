<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<form id="menuForm" class="" action="" method="post"  >
	<input type="hidden" id="id" name="id" value="${menu.id}"/>
	<div class="row ">
		<div class="col-xs-8 col-xs-offset-1 form-group">
			<div class="input-group">
				<label class="input-group-addon" id="basic-addon1">上级菜单</label>
				<sys:treeselect id="parent" name="parent.id" value="${menu.parent.id}"
					labelName="parent.name" labelValue="${menu.parent.name}" title="菜单"  
					url="/sys/menu/treeData?subsystemCode=${menu.subsystemCode}" allowClear="true" />
			</div>
		</div>
	</div>	
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>菜单名称</label> 
				<input type="text" class="form-control input-sm" placeholder="菜单名称" name="name" id="name" value="${menu.name}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon">链接</label> <input type="text"
					class="form-control input-sm" placeholder="链接" name="href"
					id="href" value="${menu.href}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group  padding_none">
			<div class="input-group">
				<label class="input-group-addon">目标</label> 
				<select id="target" name="target" class="form-control" <c:if test="${menu.target!=null}">value="${menu.target}"</c:if><c:if test="${menu.target==null}">value="2"</c:if>></select>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon">图标</label>
				<sys:iconselect id="icon1" name="icon" value="${menu.icon}" />
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>排序</label> <input type="text"
					class="form-control input-sm" placeholder="排序" name="sort"
					id="sort" value="${menu.sort}"> 
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-8 col-xs-offset-1 form-group">
			<div class="input-group">
				<label class="input-group-addon">可见</label>
				<div class="radio font12">
					<c:forEach items="${fns:getDictList('show_hide')}" var="i">
					<label> 
					<input type="radio" name="isShow" id="isShow" value="${i.value}" <c:if test="${menu.isShow==i.value}">checked</c:if>> ${i.label}
						</label>
					</c:forEach>
				</div>

			</div>
		</div>

	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-1 form-group ">
			<div class="input-group">
				<label class="input-group-addon">权限标识</label> <input type="text"
					class="form-control input-sm" placeholder="权限标识" name="permission"
					id="permission" value="${menu.permission}">
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-8 col-xs-offset-1 form-group">
			<div class="input-group">
				<label class="input-group-addon">备注</label>
				<textarea class="form-control" rows="3" cols="30"
					style="resize: none" name="remarks" id="remarks">${menu.remarks}</textarea>
			</div>
		</div>
	</div>
	<input type="hidden" id="subsystemCode" name="subsystemCode" value="${menu.subsystemCode}"/>
</form>
<!--修改判断排除name唯一-->
<input type="hidden" id="noName" value="${menu.name}"/>
<!--修改判断排除parentId唯一-->
<input type="hidden" id="noParentId" value="${menu.parent.id}"/>
<!--刷新定位parentIds-->
<input type="hidden" id="parentIds" />
<!--刷新定位menuId-->
<input type="hidden" id="menuId"/>
<!--上级菜单排除自己  -->
<input type="hidden" id="noId" value="${menu.id}"/>
<script src="${ctxStatic}/modules/sys/menuModal.js"></script>