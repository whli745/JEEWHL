$(function () {
	
	//定义搜索区域字段
	var searchValues = [{
			id: "textSearchKey",
			field: "key",
			title: "流程标识"
		},
		{
			id: "textSearchName",
			field: "name",
			title: "流程名称"
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
				btnId: "btn_import",
				btnCss: "btn",
				btnText: "导入",
				spanCss: "glyphicon glyphicon-plus",
				btnClick: function () {
					showDialog();
				}

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
					
					JEE.confirmMsg("该操作为级联,是否确认删除数据？", function(){
						$.when(JEE.myAjax(oaUrl+"/activiti/basProcess/deleteProcess", {deploymentId: selections[0].deploymentId})).done(function(result){
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}
			}
		],
		tableId: "tb_data",
		mainSearch: searchValues,
		url: oaUrl+"/activiti/basProcess/findByPage",
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
				title: "流程标识"
			},
			{
				field: "name",
				title: "流程名称"
			},
			{
				field: "resourceName",
				title: "XML资源",
				formatter: function(val,row,index){
					return [
						'<a href="'+oaUrl+'/activiti/basProcess/findResource'+
						'?resourceType=xml&processDefinitionId='+row.id+'"'+
						'target="_blank">'+val+'</a>'
					].join("");
				}
			},
			{
				field: "diagramResourceName",
				title: "图片资源",
				formatter: function(val,row,index){
					return [
						'<a href="'+oaUrl+'/activiti/basProcess/findResource'+
						'?resourceType=image&processDefinitionId='+row.id+'"'+ 
						'target="_blank">'+val+'</a>'
					].join("");
				}
			},
			{
				field: "version",
				title: "版本"
			},
			{
				field: "description",
				title: "流程描述"
			}
		]
	});
});

//弹出框
function showUploadDialog(change) {
	JEE.initDialog({
		modalId: "myModal",
		modalTitle: "上传文件",
		btnItems: [{
			btnId: "btn_save",
			btnCss: "btn btn-primary",
			btnText: "上传",
			formId: "dataForm",
			btnClick: function (data) {
				var form = new FormData(document.getElementById("dataForm"));
				$.ajax({  
				   type: "POST",  
				   url:oaUrl+"/activiti/basProcess/importProcess",  
				   data:form, 
				   // 下面三个参数要指定，如果不指定，会报一个JQuery的错误 
				   cache: false,
				   contentType: false,
				   processData: false,
				   async: false,  
				   success: function(data) {
					   if(data && data.results){
						   if (data.message) {
						   		layer.msg(data.message, {
						   			icon: 6,
						   			time: 1000
						   		});
						   	}
					    } else {
						   	if("-10005" == data.code){
						   		JEE.confirmMsg(data.message,function(){
						   			window.open("../../../ui/login.html","_top"); 
						   		});
						   	}else{
						   		layer.msg(data.message, {
						   			icon: 5,
						   			time: 1000
						   		});
						   	}
						}
						$("#myModal").modal("hide");
						$("#tb_data").bootstrapTable("refresh");
				   }  
			   });
			}
		}],
		modalForm: {
			formId: "dataForm",
			defaultTable: "",
			multipart:true,
			formItems: [
				{
					id: "textKey",
					field: "key",
					title: "流程标识",
					valid: "required",
					disable: false
				},
				{
					id: "textName",
					field: "name",
					title: "流程名称",
					valid: "required",
					disable: false
				},
				{
					id: "fileUploadFile",
					field: "uploadFile",
					title: "请选择文件",
					valid: "required",
					disable: false
				}
			]
		}
	});
}
