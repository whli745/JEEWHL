$(function () {
	//定义搜索区域字段
	var searchValues = [{
			id: "textSearchName",
			field: "name",
			title: "名称"
		},
		{
			id: "textSearchType",
			field: "type",
			title: "机构类型"
		},
		{
			id: "textSearchMaster",
			field: "master",
			title: "负责人"
		},
		{
			id: "textSearchPhone",
			field: "phone",
			title: "电话"
		},
		{
			id: "textSearchFax",
			field: "fax",
			title: "传真"
		},
		{
			id: "textSearchEmail",
			field: "email",
			title: "邮箱"
		},
		{
			id: "textSearchDeputyPerson",
			field: "deputyPerson",
			title: "副负责人"
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
						$.when(JEE.myAjax("/system/sysOffice/delete", {ids: ids})).done(function(result){
							$("#myModal").hide();
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}
			}
		],
		tableId: "tb_data",
		mainSearch: searchValues,
		url: "/system/sysOffice/findByPage",
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
				field: "type",
				title: "机构类型"
			},
			{
				field: "master",
				title: "负责人"
			},
			{
				field: "phone",
				title: "电话"
			},
			{
				field: "fax",
				title: "传真"
			},
			{
				field: "email",
				title: "邮箱"
			},
			{
				field: "deputyPerson",
				title: "副负责人"
			},
			{
				field: "remark",
				title: "备注信息"
			},
			{
				field: "enable",
				title: "启用",
				formatter: function (val) {
					if (val == 1) {
						return '<span class="label label-success arrowed">是</span>';
					} else if (val == 0) {
						return '<span class="label label-danger arrowed-in">否</span>';
					}
				}
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
				var url = change == "edit" ? "/system/sysOffice/update" : "/system/sysOffice/add";
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
					title: "父级编号",
					comboUrl: "/system/sysOffice/findAll",
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
						required:true,
						remote:{
							type:"post",
							url:"",
							data:{
								parentId:function(){return $("#comboParentId").val();},
								sort:function(){return $("#textSort").val();}
							}
						}
					}
				},
				{
					id: "textType",
					field: "type",
					title: "机构类型",
					disable: false,
					valid: "required"
				},
				{
					id: "textMaster",
					field: "master",
					title: "负责人",
					disable:false,
					valid: "required"
				},
				{
					id: "textPhone",
					field: "phone",
					title: "电话",
					disable: false,
					valid: "required"
				},
				{
					id: "textFax",
					field: "fax",
					title: "传真",
					disable: false
				},
				{
					id: "textEmail",
					field: "email",
					title: "邮箱",
					disable: false
				},
				{
					id: "textDeputyPerson",
					field: "deputyPerson",
					title: "副负责人",
					disable: false
				},
				{
					id: "textRemark",
					field: "remark",
					title: "备注信息",
					disable: false
				},
				{
					id: "switchEnable",
					field: "enable",
					title: "启用",
					disable: false
				}
			]
		}
	});
}
