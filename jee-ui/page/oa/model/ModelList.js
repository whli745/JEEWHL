$(function () {
	
	//定义搜索区域字段
	var searchValues = [{
			id: "textSearchKey",
			field: "key",
			title: "模型标识"
		},
		{
			id: "textSearchName",
			field: "name",
			title: "模型名称"
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
				spanCss: "glyphicon glyphicon-plus",
				btnClick: function () {
					showDialog();
				}

			},
			{
				btnId: "btn_edit",
				btnCss: "btn btn-primary",
				btnText: "编辑",
				spanCss: "lyphicon glyphicon-pencil",
				btnClick: function () {
					var selections = $("#tb_data").bootstrapTable("getSelections");
					if (selections == null || selections.length != 1) {
						JEE.errMsg("请选择一条需要修改的数据！");
						return;
					}
					window.open(oaUrl+"/modeler.html?modelId="+selections[0].id,"_blank");
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
				spanCss: "glyphicon glyphicon-remove",
				otherOptions: [{
					id: "tb_data",
					selectNum: 1
				}],
				btnClick: function () {
					var selections = $("#tb_data").bootstrapTable("getSelections");
					if (selections == null || selections.length != 1) {
						JEE.errMsg("请选择需要删除的数据！");
						return;
					}
					
					if(selections[0].deploymentId){
						JEE.errMsg("该流程模型已布署，无法删除！");
						return;
					}
					
					
					JEE.confirmMsg("是否确认删除数据？", function(){
						$.when(JEE.myAjax(oaUrl+"/activiti/basModel/deleteModel", {id: selections[0].id})).done(function(result){
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}
			},
			{
				btnId: "btn_deploy",
				btnCss: "btn btn-info",
				btnText: "发布",
				spanCss: "glyphicon glyphicon-remove",
				otherOptions: [{
					id: "tb_data",
					selectMinNum: 1
				}],
				btnClick: function () {
					var selections = $("#tb_data").bootstrapTable("getSelections");
					if (selections == null || selections.length != 1) {
						JEE.errMsg("请选择需要发布的流程模型！");
						return;
					}
					
					JEE.confirmMsg("是否确认发布流程模型？", function(){
						$.when(JEE.myAjax(oaUrl+"/activiti/basModel/deployModel", {id: selections[0].id})).done(function(result){
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}
			}
		],
		tableId: "tb_data",
		mainSearch: searchValues,
		url: oaUrl+"/activiti/basModel/findByPage",
		//pagination: false,
		showRefresh:true,
		searchParams: function () {
			var temp = {};
			return temp;
		},
		columns: [{
				checkbox: true
			},
			{
				field: "key",
				title: "模型标识"
			},
			{
				field: "name",
				title: "模型名称"
			},
			{
				field: "description",
				title: "模型描述",
				formatter: function (val, row, index) {
					var metaInfo = row.metaInfo;
					if(metaInfo){
						var json = eval('(' + metaInfo + ')');
						return json.description;
					}
				}
			},
			{
				field: "deploymentId",
				title: "是否布署",
				formatter: function (val, row, index) {
					if(val){
						return '<span class="label label-success arrowed">是</span>';
					}
					return '<span class="label label-danger arrowed-in">否</span>';
				}
			}
		]
	});
});

//弹出框
function showDialog(change, formData) {
	var defaultTable = "tb_data";
	JEE.initDialog({
		modalId: "myModal",
		modalTitle: "新增",
		btnItems: [{
			btnId: "btn_save",
			btnCss: "btn btn-primary",
			btnText: "保存",
			formId: "dataForm",
			//showConfirm:true,
			btnClick: function (data) {
				var url = oaUrl+"/activiti/basModel/addModel";
				$.when(JEE.myAjax(url, data)).done(function (result) {
					if (result) {
						window.open(oaUrl+"/modeler.html?modelId="+result,"_blank");
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
					id: "textKey",
					field: "key",
					title: "模型标识",
					disable: false,
					valid: "required"
				},
				{
					id: "textName",
					field: "name",
					title: "模型名称",
					disable: false,
					valid: "required"
				},
				{
					id: "areaDescription",
					field: "description",
					title: "模型描述",
					disable: false
				}
			]
		}
	});
}
