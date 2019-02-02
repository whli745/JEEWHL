/**
 * Created by whli on 2018/6/12.
 */
//页面加载完成执行
$(function () {
	//获取所有的菜单
	var targetJson = JEE.getJson("/system/sysDict/findByParentValue", {
		value: "MENU_TYPE"
	}, "value", "name");
	
	//定义搜索区域字段
	var searchValues = [
		{
			id: "textSearchName",
			field: "name",
			title: "菜单名称"
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
						$.when(JEE.myAjax("/system/sysMenu/delete", {ids: ids})).done(function(result){
							$("#myModal").hide();
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}

			}
		],
		tableId: "tb_data",
		url: "/system/sysMenu/findByPage",
		treeView: true,
		//pagination:false,
		mainSearch: searchValues,
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
				field: "href",
				title: "链接"
			},
			{
				field: "sort",
				title: "排序"
			},
			{
				field: "target",
				title: "目标",
				formatter: function (val) {
					if (val) {
						return targetJson[val];
					}
				}
			},
			{
				field: "icon",
				title: "图标",
				formatter: function (val) {
					if (val && val.indexOf("fa-") == 0) {
						return '<span class="fa ' + val + '"></span>';
					} else if (val && val.indexOf("glyphicon-") == 0) {
						return '<span class="glyphicon ' + val + '"></span>';
					}
					return "";
				}
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
		]
	});
});

//弹出框
function showDialog(change) {
	var defaultTable = change == "edit" ? "tb_data" : null;
	JEE.initDialog({
		modalId: "myModal",
		modalTitle: change == "edit" ? "编辑" : "新增",
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
				var url = change == "edit" ? "/system/sysMenu/update" : "/system/sysMenu/add";
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
					title: "上级菜单",
					disable: false,
					comboUrl: "/system/sysMenu/findAll",
					comboId: "id",
					comboText: "name"
				},
				{
					id: "textName",
					field: "name",
					title: "名称",
					disable: false,
					valid: "required"
				},
				{
					id: "textSort",
					field: "sort",
					title: "排序",
					disable: false,
					valid: {
						required: true,
						remote: {
							type: "POST",
							url: apiUrl+"/system/sysMenu/findByParentIdAndSort",
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
							}
						}
					} 
				},
				{
					id: "textHref",
					field: "href",
					title: "链接",
					disable: false,
				},
				{
					id: "comboTarget",
					field: "target",
					title: "目标",
					comboUrl: "/system/sysDict/findByParentValue",
					comboData: {
						value: "MENU_TYPE"
					},
					comboId: "value",
					comboText: "name"
				},
				{
					id: "iconIcon",
					field: "icon",
					title: "图标",
					disable: false,
				},
				{
					id: "areaRemark",
					field: "remark",
					inCss: 1,
					title: "备注信息",
					disable: false,
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
