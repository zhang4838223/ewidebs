		var officeTree;
		var selectedTree;// zTree已选择对象
		
		// 初始化
		$(document).ready(function(){
			var pre_ids = h.find("iframe")[0].contentWindow.pre_ids;
			var ids = h.find("iframe")[0].contentWindow.ids;
			$.modal.setBtnFun([ function() {
				// 删除''的元素
				if(ids[0]==''){
					ids.shift();
					pre_ids.shift();
				}
				if(pre_ids.sort().toString() == ids.sort().toString()){
//					top.$.jBox.tip("未给角色【"+assignRoleName+"】分配新成员！", 'info');
					$.modal.showPrompt({
						title:'提醒',
						content:"未给角色【"+assignRoleName+"】分配新成员！",
						time:1000
					});
					$.modal.setChildData(false);
				};
		    	// 执行保存
		    	var idsArr = "";
		    	for (var i = 0; i<ids.length; i++) {
		    		idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
		    	}
		    	$('#idsArr').val(idsArr);
		    	$.ajax({
					url: ctx+'/sys/role/assignrole?id='+assignRoleId+'&idsArr='+idsArr,
					type: 'get',
					success: function(data){
						$('#contentTable').bootstrapTable("refresh");
					}	
				});
//		    	top.$.jBox.tip("角色【"+assignRoleName+"】分配新成员成功！", 'success');
		    	$.modal.showPrompt({
					title:'提醒',
					content:"角色【"+assignRoleName+"】分配新成员成功！",
					time:1000
				});
		    	$.modal.setChildData(true);
			}, function() {
				h.find("iframe")[0].contentWindow.clearAssign();
				$.modal.setChildData(false);
			}, function() {
			} ]);
			officeTree = $.fn.zTree.init($("#officeTree"), setting, officeNodes);
			selectedTree = $.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
		});

		var setting = {view: {selectedMulti:false,nameIsHTML:true,showTitle:false,dblClickExpand:false},
				data: {simpleData: {enable: true}},
				callback: {onClick: treeOnClick}};
		
		var officeNodes=jQuery.parseJSON(officeList.replace(/'/g, '"'));
	
		var pre_selectedNodes =jQuery.parseJSON(userList.replace(/'/g, '"'));
		
		var selectedNodes =jQuery.parseJSON(userList.replace(/'/g, '"'));
		
		var pre_ids = "${selectIds}".split(",");
		var ids = "${selectIds}".split(",");
		
		// 点击选择项回调
		function treeOnClick(event, treeId, treeNode, clickFlag){
			$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode);
			if("officeTree"==treeId){
				$.get("${ctx}/sys/role/users?officeId=" + treeNode.id, function(userNodes){
					$.fn.zTree.init($("#userTree"), setting, userNodes);
				});
			}
			if("userTree"==treeId){
				// alert(treeNode.id + " | " + ids);
				// alert(typeof ids[0] + " | " + typeof treeNode.id);
				if($.inArray(String(treeNode.id), ids)<0){
					selectedTree.addNodes(null, treeNode);
					ids.push(String(treeNode.id));
				}
			};
			if("selectedTree"==treeId){
				if($.inArray(String(treeNode.id), pre_ids)<0){
					selectedTree.removeNode(treeNode);
					ids.splice($.inArray(String(treeNode.id), ids), 1);
				}else{
					top.$.jBox.tip("角色原有成员不能清除！", 'info');
				}
			}
		};
		function clearAssign(){
			var submit = function (v, h, f) {
			    if (v == 'ok'){
					var tips="";
					if(pre_ids.sort().toString() == ids.sort().toString()){
						tips = "未给角色【${role.name}】分配新成员！";
					}else{
						tips = "已选人员清除成功！";
					}
					ids=pre_ids.slice(0);
					selectedNodes=pre_selectedNodes;
					$.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
			    	top.$.jBox.tip(tips, 'info');
			    } else if (v == 'cancel'){
			    	// 取消
			    	top.$.jBox.tip("取消清除操作！", 'info');
			    }
			    return true;
			};
			tips="确定清除角色【${role.name}】下的已选人员？";
			top.$.jBox.confirm(tips, "清除确认", submit);
		};