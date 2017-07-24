/**
 * Wanghaozhe
 * 2016-5-22
 * 角色模态框
 */
//表头自适应
$(window).resize(function () {
    $('#table').bootstrapTable('resetView',{height:$.rlLayout.getBsHeight()});
});

var tree,tree2;
var menuMap = jQuery.parseJSON($("#menuMap").html());//组装的子系统为key的菜单map的json数据
var sysCode = "BASIC_PLATFORM";//默认是基层服务平台的syscode
var menuFinalString ="";//菜单树的节点id最终字符串，逗号隔开
$(document).ready(function() {
	//初始化下拉框
	initSelect();
	//初始化菜单
	initMenu("append");
	//初始化明细的部门树
	initOfficeTree();
	//初始化按钮
	initButton();
	//初始化验证
	verify();
});
/**
 *初始化下拉框
 */
function initSelect(){
//	$.fn.select2.defaults.set('amdBase', system.getStaticPath()+'/jquery-select2/select2-4.0.3/dist/js/');
//	$.fn.select2.defaults.set('amdLanguageBase', system.getStaticPath()+'/jquery-select2/select2-4.0.3/dist/js/i18n/');
	$("#roleType").bindDict({
		select2Option : {
			allowClear : false,
			placeholder : '角色类型'
		},
		dictTypeName:"sys_role_type"
	});
	
	$("#useable").bindDict({
		select2Option : {
			allowClear : false,
			placeholder : '是否可用'
		},
		dictTypeName:"yes_no"
	});
	
	$("#dataScope").bindSelect({
		select2Option : {
			allowClear : false,
			placeholder : '数据范围'
		},
		url : system.getContextPath() + "/sys/dict/scopeList"
	});
	
	$("#dataScope").change(function(){
		refreshOfficeTree(tree2);
	});
	
	$("#menuSel").bindSelect({
		select2Option : {
			allowClear : false,
			placeholder : '子系统菜单'
		},
		url : system.getContextPath() + "/sys/menu/subsystemList"
	});
	
	//下拉框绑定事件
	$("#menuSel").change(function(){
		initMenuBySub();
	
	});	
	
}
/**
 * 初始化菜单
 */
function initMenu(zNodes){
	if(zNodes == "append"){
		// 用户-菜单
	    zNodes=jQuery.parseJSON($('#menuList').val().replace(/'/g, '"'));
	}
	var setting = {
		check : {
			enable : true,
			nocheckInherit : true
		},
		view : {
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick : function(id, node) {
				tree.checkNode(node, !node.checked, true, true);
				return false;
											}				
		}};
	// 初始化菜单树结构
	tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
	
//	tree.expandAll(true);
	var nodes = tree.getNodes();
	// 不选择父节点
	tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
	for (var i = 0; i < nodes.length; i++) {
		// 判断节点下是否有子节点
		if (nodes[i].children != undefined && nodes[i].children) {
			tree.expandNode(nodes[i], true, false, true);// 展开当前第一个有子级的节点
			tree.expandNode(nodes[i].children[i], true, false, true);// 展开第一个有子级的节点的子级
			// zTree.expandNode(nodes[3].children[i], true, false, true);//
			// 展开第一个有子级的节点的子级
			break;
		}
	}
	// 点击子系统下拉框默认选择节点
//	var ids = $('#menuIds').val().split(",");
//	var hasSelected = menuMap[sysCode];
	var ids = menuMap[sysCode];
	if(ids){
		for(var i=0; i<ids.length; i++) {
			var node = tree.getNodeByParam("id", ids[i]);
			try{tree.checkNode(node, true, false);}catch(e){}
		}	
	}
	
}

/**
 * 初始化机构树
 */
function initOfficeTree(){
	var zNodes2="";
	if($('#officeList').val()!=''&&$('#officeList').val()!=undefined&&$('#officeList').val()!=null){
		// 用户-机构
		 zNodes2=jQuery.parseJSON($('#officeList').val().replace(/'/g, '"'));
	}
	var setting = {
			check : {
				enable : true,
				nocheckInherit : true
			},
			view : {
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				beforeClick : function(id, node) {
					tree.checkNode(node, !node.checked, true, true);
					return false;
		}}};
	// 初始化树结构
	tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
	// 不选择父节点
	tree2.setting.check.chkboxType = { "Y" : "ps", "N" : "s" }; 
	var nodes2 = tree2.getNodes();
	for (var i = 0; i < nodes2.length; i++) {
		// 判断节点下是否有子节点
		if (nodes2[i].children) {
			tree2.expandNode(nodes2[i], true, false, true);// 展开当前第一个有子级的节点
			tree2.expandNode(nodes2[i].children[i], true, false, true);// 展开第一个有子级的节点的子级
			// zTree.expandNode(nodes[3].children[i], true, false, true);//
			// 展开第一个有子级的节点的子级
			break;
		}
	}
	// 刷新（显示/隐藏）机构
	refreshOfficeTree(tree2);
}

/**
 * 刷新树节点
 * @param tree2
 */
function refreshOfficeTree(tree2){
	//数据范围为按明细设置时，显示机构选择。
	if($("#dataScope").val()==9){
		$("#officeTree").show();
	}else{
		$("#officeTree").hide();
		tree2.checkAllNodes();
	}
}

/**
 * 初始化按钮
 */
function initButton(){
	var parentData = $.modal.getParentData();
	if(parentData.length>0){
		modify(parentData);
	}
	$.modal.setBtnFun([ function() {
		 //对应yes
		$('#roleForm').data('bootstrapValidator').validate();  
        if(!$('#roleForm').data('bootstrapValidator').isValid()){ 
        	$.modal.setChildData(false);
            return ;  
        }
        save();
        $.modal.setChildData(true);
	}, function() {
		//对应no
	}]);
}
/**
 * 验证
 */
function verify(){
	$('#roleForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	'office.name': {
        		validators: {
                    notEmpty: {
                        message: '请选择归属机构！'
                    }
                }
        	},
        	name: {
                validators: {
                    notEmpty: {
                        message: '请填写角色名称！'
                    },
                    callback: {
	                	message: '角色名称已存在!',
	                	callback: function(value, validator) {
	                		return true;
	                	}
	                },
	                charLength:{
	                	message: '输入的角色名称太长！',
	                	maxLength:20
	                }
//                    remote: {
//	                	message: '角色名称已存在!',
//	                	type: 'GET',
//	                	async:false,
//	                	url:ctx+'/sys/role/checkName',
//	                	data:{
//	                		name:'name',
//	                		oldName:$("#oldName").val()
//	                	}
//	                }
                }
            },
            roleCode:{
            	validators:{
            		notEmpty:{
            			message:'请填写角色编号'
            		},
            		callback: {
  	                	message: '角色编号已存在!',
  	                	callback: function(value, validator) {
  	                		return true;
  	                	}
  	                },
  	                charLength:{
	                	message: '输入的角色编号太长！',
	                	maxLength:20
	                }
            	}
            },
            dataScope: {
        		validators: {
                    notEmpty: {
                        message: '请选择数据范围！'
                    }
                }
        	},
	        remarks:{
	        	validators: {
	        		charLength:{
	                	message: '输入的备注太长！',
	                	maxLength:200
	                }
	        	}
	        }
        }
	});
	
	//验证角色名称是否唯一
	$("#name").blur(function(){
		if($("#name").val()!=$("#oldName").val()){
			checkRoleName();
		}
	  });
	
	//验证角色编号是否唯一
	$("#roleCode").blur(function(){
		if($("#roleCode").val()!=$("#oldRoleCode").val()){
			$.ajax({
				url: ctx+'/sys/role/checkRoleCode',
				type: 'GET',
				async: false,
				data:{'roleCode':$("#roleCode").val(),'oldRoleCode':$("#oldRoleCode").val()},
				datatype:'json',
				success: function(data){
					if('true'==data){
						$('#roleForm').data('bootstrapValidator').updateStatus('roleCode', 'VALID','callback');
					}else{
						$('#roleForm').data('bootstrapValidator').updateStatus('roleCode', 'INVALID','callback');
					}
				}
			});
		}
	  });
	
	//提示定位
    var prompt = $(".form-group .help-block")
    $("<div>").addClass("tooltip-arrow").appendTo(prompt.parent());
    prompt.css({"right": -15, "top": -27});
    $(".tooltip-arrow").show().css({"right": 13, "top": 0});
}

/**
 * 修改页面设置默认参数
 * @param parentData
 */
function modify(parentData){
   	$('#officeId').val(parentData[0].office.id);
   	$('#officeName').val(parentData[0].office.name);
	if(parentData[0].dataScope == 9){
		$("#officeTree").show();
	}
	// 默认选择节点
	var ids = $('#menuIds').val().split(",");
	for(var i=0; i<ids.length; i++) {
		var node = tree.getNodeByParam("id", ids[i]);
		try{tree.checkNode(node, true, false);}catch(e){}
	}
	var ids2 =  $('#officeIds').val().split(",")
	for(var i=0; i<ids2.length; i++) {
		var node = tree2.getNodeByParam("id", ids2[i]);
		try{tree2.checkNode(node, true, false);}catch(e){}
	}
}
/**
 * 提交表单
 */
function save() {
	recordTicked(sysCode);
	fixMenuData();
	$("#menuIds").val(menuFinalString);
	//获取机构树的选择项
	var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
	for(var i=0; i<nodes2.length; i++) {
		ids2.push(nodes2[i].id);
	}
	$("#officeIds").val(ids2);
	$.ajax({
		type : "POST",
		url : ctx + "/sys/role/save",
		processData : true,
		data : $("#roleForm").serialize(),
		success : function(data) {
			$.modal.showPrompt({
				title:'提醒',
				content:data,
				time:1000
			});
			$('#modify').prop('disabled', true);
			$('#remove').prop('disabled', true);
			$('#assign').prop('disabled', true);
			$('#table').bootstrapTable("removeAll");
			//var queryParam = $("#table").bootstrapTable("getOptions").queryParams();
			console.info(size+"===="+set);
			var toPage = Math.ceil((set+1)/size);
			console.info(toPage);
			$('#table').bootstrapTable(
					"refresh",
					{
						url : ctx+"/sys/role/list",
						query : {limit:size,offset:set,name:$("#names").val(),'office.name':$("#officeNames").val()}
					});
			$('#table').bootstrapTable('selectPage',toPage);
		}
	});
}
/**
 * 根据子系统id取菜单
 */
function initMenuBySub(){
	var menuSubsystemId = $("#menuSel option:selected").val();
	$.getJSON(system.getContextPath() + "/sys/role/getMenuList", {"subsystemCode" : menuSubsystemId}, function(data) {
		recordTicked(sysCode);
		sysCode = menuSubsystemId;
		if(data==null){
			initMenu('');
		}else{
			initMenu(data);
		}
	});
}

/**
 * 归属机构组件回调函数
 * @param v
 * @param nodes
 */
function officeTreeselectCallBack(v,nodes){
	if (v=="ok"){
		var office = $("#officeName").val();
			if(office != '' && office != null){
				  $('#roleForm').data('bootstrapValidator').updateStatus('office.name', 'VALID',null);
			}else{
				$('#roleForm').data('bootstrapValidator').updateStatus('office.name', 'INVALID',null);
			}
			checkRoleName();	
	}
}
function checkRoleName(){
	var officeId = $("#officeId").val();
	$.ajax({
		url: ctx+'/sys/role/checkName',
		type: 'GET',
		async: false,
		data:{'name':$("#name").val(),'oldName':$("#oldName").val(),'officeId':officeId},
		datatype:'json',
		success: function(data){
			if('true'==data){
				$('#roleForm').data('bootstrapValidator').updateStatus('name', 'VALID','callback');
			}else{
				$('#roleForm').data('bootstrapValidator').updateStatus('name', 'INVALID','callback');
			}
		}
	});
}


///**菜单树的点击事件绑定的函数
// * @param event
// * @param treeId
// * @param treeNode
// */
//function zMenuOnCheck(event, treeId, treeNode) {
//    if(treeNode.checked == true){ //勾选
//    	addOrReduceNodeMenuId("add",treeNode);
//    }
//    if(treeNode.checked == false){
//    	addOrReduceNodeMenuId("reduce",treeNode);
//    }
//};
//
///**
// * @param flag
// * @param treeNode
// */
//function addOrReduceNodeMenuId(flag,treeNode){
//	var menuIdArray = menuIdString.split(",");
//	if(!treeNode.isParent){//无子节点
//		var el = treeNode.id;
//		if(flag == "add"){//增加勾选
//			if($.inArray(el,menuIdArray)==-1){//假如这个菜单在menuIdString里不存在，添加这个菜单id
//				if(menuIdString == ""){
//					menuIdString = el;
//				}else{
//					menuIdString = menuIdString + "," + el;
//				}
//			}
//		}
//		if(flag == "reduce"){//去掉勾选
//			if($.inArray(el,menuIdArray)!=-1){
//				var menuStr = "";
//				for(var i=0;i<menuIdArray.length;i++){
//					if(menuIdArray[i]!=el){
//						if(menuStr == ""){
//							menuStr = menuIdArray[i];
//						}else{
//							menuStr = menuStr + "," + menuIdArray[i];
//						}
//					}
//				}
//				menuIdString = menuStr;
//			}
//		}
//    }else{
//    	getAllChild(treeNode);
//    	var nodesArray = strArray; //所有已勾选或者已不勾选的菜单
//    	var menuStr = "";//去除勾选的临时变量
//    	$.each(nodesArray,function(i, el){
//    		if(flag == "add"){//增加勾选
//    			if($.inArray(el,menuIdArray)==-1){//假如这个菜单在menuIdString里不存在，添加这个菜单id
//    				if(menuIdString == ""){
//    					menuIdString = el;
//    				}else{
//    					menuIdString = menuIdString + "," + el;
//    				}
//    			}
//    		}
//    		if(flag == "reduce"){//去掉勾选
//    			if($.inArray(el,menuIdArray)!=-1){//假如这个菜单在menuIdString里存在，去掉这个菜单id
//    				for(var i=0;i<menuIdArray.length;i++){
//    					if(menuIdArray[i]!=el){
//    						if(menuStr == ""){
//    							menuStr = menuIdArray[i];
//    						}else{
//    							menuStr = menuStr + "," + menuIdArray[i];
//    						}
//    					}
////    					if(menuIdArray[i]==el){
////    						menuIdArray = menuStr.split(",");
////    					}
//    				}
//    				menuIdArray = menuStr.split(",");
//    				menuIdString = menuStr;
//    				menuStr = "";
//    			}
//    		}
//    	});
//    	strArray = {};
//    }
//}
//
//
//
///**取得node节点的所有子节点id，封装到strArray
// * @param node
// */
//function getAllChild(node){
//	strArray.push(node.id);
//	if(undefined == node.children||null==node.children){
//	}else{
//		if(node.children instanceof Array){
//			for(var i=0;i<node.children.length;i++){
//				 getAllChild(node.children[i]);
//			}
//		}else{
//			 getAllChild(node.children);
//		}
//	}
//}

/**
* 记录菜单已选勾选
*/

function recordTicked(menuSubsystemId){
	if(menuSubsystemId == ""||menuSubsystemId==undefined||menuSubsystemId==null){
		menuSubsystemId = "BASIC_PLATFORM";
	}
	nodes = tree.getCheckedNodes(true);
	var nodeIdArray = new Array();
	$.each(nodes,function(i, el){
		el = el.id;
		nodeIdArray.push(el);
	});
	menuMap[menuSubsystemId] = nodeIdArray;
}

function fixMenuData(){
	for(var key in menuMap){
		if(key != null){
			var list = menuMap[key];
			for(var menuStr in list){
				if(menuFinalString == ""){
					menuFinalString = list[menuStr];
				}else{
					menuFinalString = menuFinalString + "," + list[menuStr];
				}
			}
		}
		
	}
}
function clear(){
	menuMap = "";
	sysCode = "";
}