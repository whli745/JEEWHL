$(function () {
	//定义搜索区域字段
	var searchValues = [
		{
			id: "textSearchNo",
			field: "no",
			title: "编码"
		},
		{
			id: "textSearchName",
			field: "name",
			title: "名称"
		},
		{
			id: "textSearchRemark",
			field: "remark",
			title: "备注信息"
		},
		{
			id: "comboSearchEnable",
			field: "enable",
			title: "启用",
			comboUrl: "/system/sysDict/findByParentValue",
			comboData: {
				value: "YES_NO"
			},
			comboId: "value",
			comboText: "name"
		}
	];

	//初始化搜索区域
	JEE.initSearch({
		panelTitle: "查询",
		searchValues: searchValues,
		defaultTable: "tb_data" //关联表格id
	});

	//初始化表格
	JEE.initTable({
		btnItems: [{
				btnId: "btn_add",
				btnCss: "btn",
				btnText: "新增",
				btnClick: function () {
					showDialog();
				}

			},
			{
				btnId: "btn_edit",
				btnCss: "btn btn-primary",
				btnText: "编辑",
				btnClick: function () {
					var selections = $("#tb_data").bootstrapTable("getSelections");
					if (selections == null || selections.length != 1) {
						JEE.errMsg("请选择一条需要修改的数据！");
						return;
					}
					showDialog("edit");
				},
				otherOptions: [{
					id: "tb_data",
					selectNum: 1
				}]

			},
			{
				btnId: "btn_delete",
				btnCss: "btn btn-danger",
				btnText: "删除",
				otherOptions: [{
					id: "tb_data",
					selectMinNum: 1
				}],
				btnClick: function () {
					var selections = $("#tb_data").bootstrapTable("getSelections");
					if (selections == null || selections.length == 0) {
						JEE.errMsg("请选择需要删除的数据！");
						return;
					}
					var ids = [];
					$.each(selections, function (i, item) {
						ids.push(item.id);
					});
					
					JEE.confirmMsg("是否确认删除数据？", function(){
						$.when(JEE.myAjax("/system/sysRole/delete", {ids: ids})).done(function(result){
							$("#myModal").hide();
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}

			}
		],
		tableId: "tb_data",
		mainSearch: searchValues,
		url: "/system/sysRole/findByPage",
		//pagination: false,
		singleSelect:true,
		searchParams: function () {
			var temp = {};
			return temp;
		},
		columns: [{
				checkbox: true
			},
			{
				field: "no",
				title: "角色编码"
			},
			{
				field: "name",
				title: "角色名称"
			},
			{
				field: "enable",
				title: "启用",
				formatter: function (val, row, index) {
					if (val == 1) {
						return '<span class="label label-success arrowed">是</span>';
					} else if (val == 0) {
						return '<span class="label label-danger arrowed-in">否</span>';
					}
				}
			},
			{
				field: "remark",
				title: "备注信息"
			}
		],
		onCheck: function (row, $element) {
			initZtree({roleId: row.id});
		},
		onUncheck: function (row, $element) {
			initZtree();
		},
	});
	
	//授权按钮点击事件
	$("#btn_grant_menu").on("click",function(){
		var selectRole = $("#tb_data").bootstrapTable('getSelections')[0];
		if(!selectRole){
			JEE.errMsg("请选择需要授权的角色");
			return;
		}
		
		//获取所有的选中节点
		var treeObj=$.fn.zTree.getZTreeObj("menuTree");
		var nodes=treeObj.getCheckedNodes(true);
		if(nodes.length <= 0){
			JEE.errMsg("请选择至少一条菜单");
			return;
		}
		 
		var array = new Array();
	 　　for(var i=0;i<nodes.length;i++){
			array.push(nodes[i].id);
	 　　}
		
		JEE.myAjax("/system/sysRole/grantByRole",{id:selectRole.id,menuIds:array});
	});
	
	initZtree();
});

//弹出框
function showDialog(change) {
	var defaultTable = change == "edit" ? "tb_data" : null;
	JEE.initDialog({
		modalId: "myModal",
		modalTitle: change && change == "edit" ? "编辑" : "新增",
		btnItems: [{
			btnId: "btn_save",
			btnCss: "btn btn-primary",
			btnText: "保存",
			formId: "dataForm",
			//showConfirm:true,
			btnClick: function (data) {
				if (change == 'edit') {
					data.id = $("#" + defaultTable).bootstrapTable('getSelections')[0].id;
				}
				var url = change == "edit" ? "/system/sysRole/update" : "/system/sysRole/add";
				$.when(JEE.myAjax(url, data)).done(function (result) {
					if (result) {
						$("#myModal").modal("hide");
						$("#tb_data").bootstrapTable("refresh");
					}
				})
			}
		}],
		modalForm: {
			formId: "dataForm",
			defaultTable: defaultTable,
			formItems: [
				{
					id: "textNo",
					field: "no",
					title: "角色编码",
					disable: change && change == "edit" ? true : false,
					valid: {
						required: true,
						remote: {
							type: "POST",
							url: apiUrl+"/system/sysRole/findByNo",
							contentType: "application/json;charset=UTF-8",
							data: {
								no: function(){
									return $("#textNo").val();
								}
							},
							dataFilter:function(data,type){
								var json = JSON.parse(data);
								if(json.message){
									return true;
								}else if(data){
									return false;
								}
								return true;
							}
						}
					}
				},
				{
					id: "textName",
					field: "name",
					title: "角色名称",
					disable: change && change == "edit" ? false : false,
					valid: "required"
				},
				{
					id: "textRemark",
					field: "remark",
					title: "备注信息",
					disable: change && change == "edit" ? false : false
				},
				{
					id: "switchEnable",
					field: "enable",
					title: "启用",
					disable: change && change == "edit" ? false : false
				}
			]
		}
	});
}

function initZtree(data){
	JEE.getZTree("menuTree","/system/sysMenu/findByTree",data);
}