<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分配角色</title>
	<meta name="decorator" content="defaultx"/>
	<%@include file="/WEB-INF/views/include/js-footer.jsp" %>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	
</head>
<body>
	<div id="assignRole" class="row col-xs-12 " style="heigth:0px;padding-bottom:100%">
		<div class="col-xs-4" style="border-right: 1px solid #A8A8A8;height:100%;overflow-y:auto;">
			<p>所在部门：</p>
			<div id="officeTree" class="ztree"></div>
		</div>
		<div class="col-xs-3" style="border-right: 1px solid #A8A8A8;height:100%;overflow-y:auto;">
			<p>待选人员：</p>
			<div id="userTree" class="ztree"></div>
		</div>
		<div class="col-xs-3" style="border-right: 1px solid #A8A8A8;height:100%;overflow-y:auto;padding-left:16px;border-left: 1px solid #A8A8A8;">
			<p>已选人员：</p>
			<div id="selectedTree" class="ztree"></div>
		</div>
	</div>
	<script type="text/javascript">
	
	var officeTree;
	var selectedTree;//zTree已选择对象
	
	// 初始化
	$(document).ready(function(){
		officeTree = $.fn.zTree.init($("#officeTree"), setting, officeNodes);
		selectedTree = $.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
	});

	var setting = {view: {selectedMulti:false,nameIsHTML:true,showTitle:false,dblClickExpand:false},
			data: {simpleData: {enable: true}},
			callback: {onClick: treeOnClick}};
	
	var officeNodes=[
            <c:forEach items="${officeList}" var="office">
            {id:"${office.id}",
             pId:"${not empty office.parent?office.parent.id:0}", 
             name:"${office.name}"},
            </c:forEach>];

	var pre_selectedNodes =[
		        <c:forEach items="${userList}" var="user">
		        {id:"${user.id}",
		         pId:"0",
		         name:"<font color='red' style='font-weight:bold;'>${user.name}</font>"},
		        </c:forEach>];
	
	var selectedNodes =[
	        <c:forEach items="${userList}" var="user">
	        {id:"${user.id}",
	         pId:"0",
	         name:"<font color='red' style='font-weight:bold;'>${user.name}</font>"},
	        </c:forEach>];
	
	var pre_ids = "${selectIds}".split(",");
	var ids = "${selectIds}".split(",");
	
	//点击选择项回调
	function treeOnClick(event, treeId, treeNode, clickFlag){
		$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode);
		if("officeTree"==treeId){
			$.get("${ctx}/sys/role/users?officeId=" + treeNode.id, function(userNodes){
				$.fn.zTree.init($("#userTree"), setting, userNodes);
			});
		}
		if("userTree"==treeId){
			//alert(treeNode.id + " | " + ids);
			//alert(typeof ids[0] + " | " +  typeof treeNode.id);
			if($.inArray(String(treeNode.id), ids)<0){
				selectedTree.addNodes(null, treeNode);
				ids.push(String(treeNode.id));
			}
		};
		if("selectedTree"==treeId){
				selectedTree.removeNode(treeNode);
				ids.splice($.inArray(String(treeNode.id), ids), 1);
		}
	};
	function clearAssign(){
		 var submit = function (v, h, f) {
		    if (v == 'ok'){
				var tips="已选人员清除成功！";
				ids=[];
				$.fn.zTree.init($("#selectedTree"), setting, null);
		    	top.$.jBox.tip(tips, 'info');
		    } else if (v == 'cancel'){
		    	top.$.jBox.tip("取消清除操作！", 'info');
		    }
		    return true;
		};
		tips="确定清除已选人员？";
		top.$.jBox.confirm(tips, "清除确认", submit); 
	};
	</script>
</body>
</html>
