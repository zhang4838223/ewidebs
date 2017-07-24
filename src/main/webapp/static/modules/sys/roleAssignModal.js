/**
 * Wanghaozhe
 * 2016-5-22
 * 角色模态框
 */
$(document).ready(function() {
	var obj= $.modal.getParentData();
	//初始化列表
	initTable(obj);
	//初始化按钮
	initButton(obj);
	//初始化角色信息显示
	initRole(obj);
});
/**
 * 初始化按钮
 */
function initButton(obj){
	 $("#assignButton").click(function(){
			var assignRoleId = $('#assignRoleId').val();
			var assignRoleName = $('#assignRoleName').val();
			var idsArr = $('#idsArr').val();
			top.$.jBox.open("iframe:"+ctx+"/sys/role/usertorole?id="+assignRoleId, "分配用户",1100,$(top).height()-240,{
				buttons:{"确定分配":"ok", "清除已选":"clear", "关闭":true}, bottomText:"通过选择部门，然后为列出的人员分配角色。",submit:function(v, h, f){
					var pre_ids = h.find("iframe")[0].contentWindow.pre_ids;
					var ids = h.find("iframe")[0].contentWindow.ids;
					//nodes = selectedTree.getSelectedNodes();
					if (v=="ok"){
						// 删除''的元素
						if(ids[0]==''){
							ids.shift();
							pre_ids.shift();
						}
						if(pre_ids.sort().toString() == ids.sort().toString()){
							top.$.jBox.tip("未给角色【"+assignRoleName+"】分配新成员！", 'info');
							return false;
						};
				    	// 执行保存
				    	var idsArr = "";
				    	for (var i = 0; i<ids.length; i++) {
				    		idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
				    	}
				    	$('#idsArr').val(idsArr);
				    	$.ajax({
							url: ctx+'/sys/role/assignrole',
							type: 'get',
							data:{'id':assignRoleId,'idsArr':idsArr},
							success: function(data){
								$('#contentTable').bootstrapTable("removeAll");
								$('#contentTable').bootstrapTable("refresh",{url:ctx+"/sys/role/assign?id="+obj[0].id});
							}
						});
				    	top.$.jBox.tip("角色【"+assignRoleName+"】分配新成员成功！", 'success');
				    	return true;
					} else if (v=="clear"){
						h.find("iframe")[0].contentWindow.clearAssign();
						return false;
	                }
				}, loaded:function(h){
					$(".jbox-content", top).css("overflow-y","hidden");
				}
			});
		});
}
/**
 * 初始化列表
 */
function initTable(obj){
	$('#contentTable').bootstrapTable({
		url : ctx+"/sys/role/assign?id="+obj[0].id,
		dataType: "json",//请求后台的URL（*）
	    method: "get",//请求方式（*）
	    striped: true,//是否显示行间隔色
	    clickToSelect: true,//是否启用点击选中行
	    height: 350, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	    showColumns:true,//表头右边小工具
	    minimumCountColumns:2,//最小只剩2个
	    showHeader:true,//是否显示表标题
	    columns: [
	              {
					field: 'Number',
					title: '序号',
					width: 15,
					formatter: function (value, row, index) {
					return index+1;
					}
	             }, 
				{
					field : 'company.name',
					title : '归属公司'
				}, {
					field : 'office.name',
					title : '归属部门'
				},
				{
					field : 'loginName',
					title : '登录名'
				},
				{
					field : 'name',
					title : '姓名'
				},
				{
					field : 'phone',
					title : '电话'
				},
				{
					field : 'mobile',
					title : '手机'
				},
				{
					title: '操作',
					formatter:function(value,row,index){
						return '<a href="javascript:void(0)" onClick="extract(\''+row.id+'\',\''+obj[0].id+'\')">移除</a>';
						}
				}
	           ]
	}); 
	
	//加载分配角色table
//	$('#contentTable').bootstrapTable("refresh",{url:ctx+"/sys/role/assign?id="+obj[0].id});
	
//	 var queryParams2 = function(params){
//		 var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
//		            id: '1'
//		        };
//		 return temp;
//	 }
}
/**
 * 初始化角色信息显示
 */
function initRole(obj){
	$('#assignRoleId').val(obj[0].id);
	$('#assignRoleName').val(obj[0].name);
   	$('#assignModal').modal('show');
   	$('#assignName').html('角色名称:'+obj[0].name);
	$('#assignOffice').html('归属机构:'+obj[0].office.name);
	$('#assignCode').html('角色编号:'+"");
	$("#id").val(obj[0].id);
	var type = getDictLabel(jQuery.parseJSON($('#sys_role_type').text()), obj[0].roleType);
	$('#assignType').html('角色类型:'+ type);
	var scope = getDictLabel(jQuery.parseJSON($('#sys_data_scope').text()), obj[0].dataScope);
	$('#assignScope').html('数据范围:'+ scope);
}

/**
 * 移除操作
 * @param userId
 * @param roleId
 */
function extract(userId,roleId){
	$.modal.showConfirm ({
		title:"提醒",
		content:"是否移除人员",
		icon:2, 
		yes: function(index){
			$.ajax({
				url: ctx+'/sys/role/outrole?userId='+userId+'&roleId='+roleId,
				type: 'POST',
				async: true,
				success: function(data){
					$.modal.close(index);
//					$.modal.showPrompt({content:data,time:2000});
					$.modal.showPrompt({
						title:'提醒',
						content:data,
						time:2000
					});
					$('#contentTable').bootstrapTable("removeAll");
					$('#contentTable').bootstrapTable("refresh",{url:ctx+"/sys/role/assign?id="+roleId});
				}
			});
		},cancel: function(){
			
		}
	});
}
