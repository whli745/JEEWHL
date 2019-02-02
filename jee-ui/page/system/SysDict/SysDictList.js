$(function () {
	//定义搜索区域字段
	var searchValues = [
		{
			id: "textSearchName",
			field: "name",
			title: "标签名称"
		},
		{
			id: "textSearchValue",
			field: "value",
			title: "数据值"
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
				otherOptions: [
					{
						id: "tb_data",
						selectNum: 1
					},
					{
						id: "tb_data",
						isEdit: true
					},
				]

			},
			{
				btnId: "btn_delete",
				btnCss: "btn btn-danger",
				btnText: "删除",
				otherOptions: [
					{
						id: "tb_data",
						selectMinNum: 1
					},
					{
						id: "tb_data",
						isEdit: true
					},],
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
						$.when(JEE.myAjax("/system/sysDict/delete", {ids: ids})).done(function(result){
							$("#myModal").hide();
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}

			}
		],
		tableId: "tb_data",
		mainSearch: searchValues,
		url: "/system/sysDict/findByPage",
		//pagination:false,
		treeView: true,
		//treeField: 'value',
		searchParams: function () {
			var temp = {};
			return temp;
		},
		columns: [{
				checkbox: true
			},
			{
				field: "name",
				title: "标签名"
			},
			{
				field: "value",
				title: "数据值"
			},
			
			{
				field: "sort",
				title: "排序"
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
				field: "edit",
				title: "前端可编辑",
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
		onLoadSuccess: function (data) {

		}
	});
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
				var url = change == "edit" ? "/system/sysDict/update" : "/system/sysDict/add";
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
			formItems: [{
					id: "comboParentId",
					field: "parentId",
					title: "父级字典",
					disable: false,
					comboUrl: "/system/sysDict/findAll",
					comboId: "id",
					comboText: "name"
				},
				{
					id: "textValue",
					field: "value",
					title: "数据值",
					disable: change && change == "edit" ? true : false,
					valid: "required"
				},
				{
					id: "textName",
					field: "name",
					title: "标签名",
					disable: change && change == "edit" ? false : false,
					valid: "required"
				},
				{
					id: "textSort",
					field: "sort",
					title: "排序",
					disable: change && change == "edit" ? false : false,
					valid: {
						required: true,
						remote: {
							type: "POST",
							url: apiUrl+"/system/sysDict/findByParentIdAndSort",
							data: {
								parentId: function(){
									return $("#comboParentId").val();
									},
								sort: function(){
									return $("#textSort").val();
									},
								id: function(){
									if (change == 'edit') {
										return $("#" + defaultTable).bootstrapTable('getSelections')[0].id;
									}
								}
							},
							dataFilter:function(data,type){
								if(data){
									return false;
								}
								return true;
							}
						}
					} 
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
				},
				{
					id: "switchEdit",
					field: "edit",
					title: "前端可编辑",
					disable: change && change == "edit" ? false : false
				}
			]
		}
	});
}
