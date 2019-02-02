$(function () {
	//定义搜索区域字段
	var searchValues = [{
			id: "textSearchParentId",
			field: "parentId",
			title: "父级区域"
		},
		{
			id: "textSearchName",
			field: "name",
			title: "名称"
		},
		{
			id: "textSearchSort",
			field: "sort",
			title: "排序"
		},
		{
			id: "textSearchCode",
			field: "code",
			title: "区域编码"
		},
		{
			id: "textSearchType",
			field: "type",
			title: "区域类型"
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
						$.when(JEE.myAjax("/system/sysArea/delete", {ids: ids})).done(function(result){
							$("#myModal").hide();
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}
			}
		],
		tableId: "tb_data",
		mainSearch: searchValues,
		url: "/system/sysArea/findByPage",
		searchParams: function () {
			var temp = {};
			return temp;
		},
		columns: [{
				checkbox: true
			},
			{
				field: "name",
				title: "名称"
			},
			{
				field: "sort",
				title: "排序"
			},
			{
				field: "code",
				title: "区域编码"
			},
			{
				field: "type",
				title: "区域类型"
			},
			{
				field: "remark",
				title: "备注信息"
			},
			{
				field: "enable",
				title: "启用"
			}
		]
	});
});

//弹出框
function showDialog(change, formData) {
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
				var url = change == "edit" ? "/system/sysArea/update" : "/system/sysArea/add";
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
					title: "父级区域",
					comboUrl: "/system/sysArea/findAll",
					comboId: "id",
					comboText: "name"
				},
				{
					id: "textCode",
					field: "code",
					title: "区域编码",
					disable: change && change == "edit" ? true : false,
					valid: "required"
				},
				{
					id: "textName",
					field: "name",
					title: "名称",
					disable: change && change == "edit" ? false : false,
					valid: "required"
				},
				{
					id: "textSort",
					field: "sort",
					title: "排序",
					disable: change && change == "edit" ? false : false,
					valid: "required"
				},
				{
					id: "textType",
					field: "type",
					title: "区域类型",
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
					title: "启用"
				}
			]
		}
	});
}
