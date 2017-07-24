<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<form id="officeForm" class="login-form" method="post" action="">
<input type="hidden" id="id" name="id" value="${office.id}" />
	<div class="row">
		<div class="col-xs-5  form-group">
			<div class="input-group">
				<label class="input-group-addon" id="basic-addon1"><span class="required">*</span>归属公司</label>
				<sys:treeselect id="parent" name="parent.id" value="${office.parent.id}"
					labelName="parent.name" labelValue="${office.parent.name}" title="机构"
					url="/sys/office/treeData?type=1" extId="${office.id}"
					cssClass="required form-control"
					allowClear="${office.currentUser.admin}" />
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group">
				<label class="input-group-addon" id="basic-addon2"><span class="required">*</span>归属区域</label>
				<sys:treeselect id="area" name="area.id" value="${office.area.id}"
					labelName="area.name" labelValue="${office.area.name}" title="区域"
					url="/sys/area/treeData" cssClass="required" />
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>机构名称</label>
				<input type="text" class="form-control input-sm" placeholder="机构名称" id="name"
					name="name" value="${office.name}">
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>机构类型</label> 
				<select name="type" id="type" class="form-control" value="${office.type }"></select>
			</div>
		</div>
	</div>
	<div class="row ">
		<%-- <div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon "><span class="required">*</span>机构级别</label> 
				<select name="grade" id="grade" class="form-control" value="${office.grade }"></select>
			</div>
		</div> --%>
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon"><span class="required">*</span>排序</label>
				<input type="text" class="form-control input-sm" placeholder="排序" id="sort" name="sort" value="${office.sort}">
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group">
				<label class="input-group-addon">是否可用</label> 
				<select name="useable" id="useable" class="form-control" value="${office.useable }"></select>
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon">主负责人</label>
				<sys:treeselect id="primaryPerson" name="primaryPerson.id"
					value="${office.primaryPerson.id}" labelName="primaryPerson.name"
					labelValue="${office.primaryPerson.name}" title="主负责人"
					url="/sys/office/treeData?type=3" allowClear="true"
					notAllowSelectParent="true"
					cssClass="required form-control" />

			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group ">
				<label class="input-group-addon">副负责人</label>
				<sys:treeselect id="deputyPerson" name="deputyPerson.id"
					value="${office.deputyPerson.id}" labelName="deputyPerson.name"
					labelValue="${office.deputyPerson.name}" title="副负责人"
					url="/sys/office/treeData?type=3" allowClear="true"
					notAllowSelectParent="true"
					cssClass="required form-control" />

			</div>
		</div>

	</div>
	<div class="row">
		<div class="col-xs-5 form-group">
			<div class="input-group ">
				<label class="input-group-addon">联系地址</label>
				<input type="text" class="form-control input-sm" placeholder="联系地址" id="address" name="address" value="${office.address}">
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group">
				<label class="input-group-addon">邮政编码</label>
				<input type="text" class="form-control input-sm" placeholder="邮政编码" id="zipCode" name="zipCode" value="${office.zipCode}">
			</div>
		</div>
	</div>

	<div class="row">
		<%-- <div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon">负责人</label>
				<input type="text" class="form-control input-sm" placeholder="负责人" id="master" name="master" value="${office.master}">
			</div>
		</div> --%>
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon">电话</label>
				<input type="text" class="form-control input-sm" placeholder="电话" id="phone" name="phone" value="${office.phone}">
			</div>
		</div>
		<div class="col-xs-5 form-group col-xs-offset-1">
			<div class="input-group">
				<label class="input-group-addon">邮箱</label>
				<input type="text" class="form-control input-sm" placeholder="邮箱" id="email" name="email" value="${office.email}">
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-5 form-group">
			<div class="input-group">
				<label class="input-group-addon">传真</label>
				<input type="text" class="form-control input-sm" placeholder="传真" id="fax" name="fax" value="${office.fax}">
			</div>
		</div>
	</div>
	<div class="row ">
		<div class="col-xs-11 form-group">
			<div class="input-group">
				<label class="input-group-addon">备注</label>
				<textarea class="form-control" id="remarks" name="remarks" rows="4"
					cols="30" style="resize: none">${office.remarks}</textarea>
			</div>
		</div>
	</div>
<!-- 页面初始化机构名称 -->
<input type="hidden" id="oldName" name="oldName" value="${office.name}" />
</form>
<!--判断是否存在根节点-->
<input type="hidden" id="count" value="${count}"/>
<!--修改判断排除parentId唯一-->
<input type="hidden" id="noParentId" value="${office.parent.id}"/>
<script src="${ctxStatic}/modules/sys/officeModal.js"></script>
