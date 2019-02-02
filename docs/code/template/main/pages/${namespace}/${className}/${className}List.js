<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
$(function () {
	//定义搜索区域字段
	var searchValues = [
		<#list table.columns as column>
		<#if !column.pk && column.columnNameLower != 'createBy'
		&& column.columnNameLower != 'createDate' && column.columnNameLower != 'updateBy'
		&& column.columnNameLower != 'updateDate' && column.columnNameLower != 'password'
		&& column.columnNameLower != 'loginIp' && column.columnNameLower != 'loginDate'
		&& column.columnNameLower != 'version'>
		{
			id: "textSearch${column.columnName}",
			field: "${column.columnNameLower}",
			title: "${column.columnAlias!}"
		}<#if column_has_next>,</#if>
		</#if>
		</#list>
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
						$.when(JEE.myAjax("/${namespace}/${classNameLower}/delete", {ids: ids})).done(function(result){
							$("#myModal").hide();
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}
			}
		],
		tableId: "tb_data",
		mainSearch: searchValues,
		url: "/${namespace}/${classNameLower}/findByPage",
		searchParams: function () {
			var temp = {};
			return temp;
		},
		onCheck: function (row, $element) {
		},
		onUncheck: function (row, $element) {
		},
		columns: [{
				checkbox: true
			},
			<#list table.columns as column>
			<#if !column.pk && column.columnNameLower != 'createBy'
			&& column.columnNameLower != 'createDate' && column.columnNameLower != 'updateBy'
			&& column.columnNameLower != 'updateDate' && column.columnNameLower != 'password'
			&& column.columnNameLower != 'loginIp' && column.columnNameLower != 'loginDate'
			&& column.columnNameLower != 'version'>
			{
				field: "${column.columnNameLower}",
				title: "${column.columnAlias!}"
			}<#if column_has_next>,</#if>
			</#if>
			</#list>
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
				var url = change == "edit" ? "/${namespace}/${classNameLower}/update" : "/${namespace}/${classNameLower}/add";
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
			<#list table.columns as column>
			<#if !column.pk && column.columnNameLower != 'createBy'
				&& column.columnNameLower != 'createDate' && column.columnNameLower != 'updateBy'
				&& column.columnNameLower != 'updateDate' && column.columnNameLower != 'password'
				&& column.columnNameLower != 'loginIp' && column.columnNameLower != 'loginDate'
				&& column.columnNameLower != 'version'>
				{
					id: "text${column.columnName}",
					field: "${column.columnNameLower}",
					title: "${column.columnAlias!}",
					disable: change && change == "edit" ? true : false,
					valid: "required"
				}<#if column_has_next>,</#if>
			</#if>
			</#list>
			]
		}
	});
}
