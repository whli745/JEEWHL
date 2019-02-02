$(function () {
	var groupJson = JEE.getJson("/system/sysDict/findByParentValue", {
		value: "JOB_GROUP"
	}, "value", "name");

	var statusJson = JEE.getJson("/system/sysDict/findByParentValue", {
		value: "JOB_STATUS"
	}, "value", "name");

	//定义搜索区域字段
	var searchValues = [
		{
			id: "textSearchJobName",
			field: "jobName",
			title: "任务名称"
		},
		{
			id: "comboSearchJobGroup",
			field: "jobGroup",
			title: "任务分组",
			comboUrl: "/system/sysDict/findByParentValue",
			comboData: {
				value: "JOB_GROUP"
			},
			comboId: "value",
			comboText: "name"
		},
		{
			id: "comboSearchState",
			field: "state",
			title: "任务状态",
			comboUrl: "/system/sysDict/findByParentValue",
			comboData: {
				value: "JOB_STATUS"
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
					selectNum: 1
				}],
				btnClick: function () {
					var selections = $("#tb_data").bootstrapTable("getSelections");
					if (selections == null || selections.length == 0) {
						JEE.errMsg("请选择需要删除的数据！");
						return;
					}
					var item = selections[0];
					JEE.confirmMsg("是否确认删除数据？", function(){
						$.when(JEE.myAjax(jobUrl+"/scheduler/job/delete",
							{jobName: item.jobName,jobGroup:item.jobGroup})).done(function(result){
							$("#myModal").hide();
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}
			},
			{
				btnId: "btn_resume",
				btnCss: "btn btn-inverse",
				btnText: "立即执行",
				otherOptions: [{
					id: "tb_data",
					selectNum: 1
				}],
				btnClick: function () {
					var selections = $("#tb_data").bootstrapTable("getSelections");
					if (selections == null || selections.length != 1) {
						JEE.errMsg("请选择一个需要执行的定时任务！");
						return;
					}
			
					var item = selections[0];
					JEE.confirmMsg("是否确认立即执行定时任务？", function(){
						$.when(JEE.myAjax(jobUrl+"/scheduler/job/triggerJob",
							{jobName: item.jobName,jobGroup:item.jobGroup})).done(function(result){
							$("#myModal").hide();
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}
			},
			{
				btnId: "btn_resume",
				btnCss: "btn btn-success",
				btnText: "恢复",
				btnClick: function () {
					var selections = $("#tb_data").bootstrapTable("getSelections");
					if (selections == null || selections.length == 0) {
						JEE.confirmMsg("是否确认恢复所有定时任务？", function(){
							$.when(JEE.myAjax(jobUrl+"/scheduler/job/resumeAll")).done(function(result){
								$("#myModal").hide();
								$("#tb_data").bootstrapTable("refresh");
							});
						});
						return;
					}
					if (selections.length != 1){
						JEE.errMsg("请选择需要恢复的一个定时任务！");
						return;
					}

					var item = selections[0];
					JEE.confirmMsg("是否确认恢复定时任务？", function(){
						$.when(JEE.myAjax(jobUrl+"/scheduler/job/resume",
							{jobName: item.jobName,jobGroup:item.jobGroup})).done(function(result){
							$("#myModal").hide();
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}
			},
			{
				btnId: "btn_pause",
				btnCss: "btn btn-yellow",
				btnText: "暂停",
				btnClick: function () {
					var selections = $("#tb_data").bootstrapTable("getSelections");
					if (selections == null || selections.length == 0) {
						JEE.confirmMsg("是否确认暂停所有定时任务？", function(){
							$.when(JEE.myAjax(jobUrl+"/scheduler/job/pauseAll")).done(function(result){
								$("#myModal").hide();
								$("#tb_data").bootstrapTable("refresh");
							});
						});
						return;
					}
					if (selections.length != 1){
						JEE.errMsg("请选择需要暂停的一个定时任务！");
						return;
					}
					var item = selections[0];
					JEE.confirmMsg("是否确认暂停定时任务？", function(){
						$.when(JEE.myAjax(jobUrl+"/scheduler/job/pause",
							{jobName: item.jobName,jobGroup:item.jobGroup})).done(function(result){
							$("#myModal").hide();
							$("#tb_data").bootstrapTable("refresh");
						});
					});
				}
			}
		],
		tableId: "tb_data",
		mainSearch: searchValues,
		url: "/scheduler/job/findByPage",
		searchParams: function () {
			var temp = {};
			return temp;
		},
		columns: [{
				checkbox: true
			},
			{
				field: "jobName",
				title: "任务名称"
			},
			{
				field: "jobGroup",
				title: "任务分组",
				formatter: function (val) {
					if (val){
						return groupJson[val];
					}
				}
			},
			{
				field: "jobClass",
				title: "任务类"
			},
			{
				field: "cronExpression",
				title: "时间表达式"
			},
			{
				field: "startTime",
				title: "开始时间",
				formatter: function (val) {
					if (val){
						var date = new Date(val);
						return date.format("yyyy-MM-dd hh:mm:ss");
					}
				}
			},
			{
				field: "prevTime",
				title: "上次运行时间",
				formatter: function (val) {
					if (val){
						var date = new Date(val);
						return date.format("yyyy-MM-dd hh:mm:ss");
					}
				}
			},
			{
				field: "nextTime",
				title: "下次运行时间",
				formatter: function (val) {
					if (val){
						var date = new Date(val);
						return date.format("yyyy-MM-dd hh:mm:ss");
					}
				}
			},
			{
				field: "jobDescription",
				title: "描述"

			},
			{
				field: "state",
				title: "状态",
                formatter: function (val) {
                    if (val){
                        return statusJson[val];
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
				var url = change == "edit" ? jobUrl+"/scheduler/job/update" : jobUrl+"/scheduler/job/add";
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
					id: "textJobName",
					field: "jobName",
					title: "任务名称",
					disable: change && change == "edit" ? true : false,
					valid: {
						required: true,
						remote: {
							type: "POST",
							url: jobUrl+"/scheduler/job/findByName",
							contentType: "application/json;charset=UTF-8",
							data: {
								jobName: function(){
									return $("#textJobName").val();
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
					id: "comboJobGroup",
					field: "jobGroup",
					title: "任务分组",
					defaultValue: 'DEFAULT',
					comboUrl: "/system/sysDict/findByParentValue",
					comboData: {
						value: "JOB_GROUP"
					},
					comboId: "value",
					comboText: "name",
					disable: change && change == "edit" ? true : false,
					valid: "required"
				},
				{
					id: "textJobClass",
					field: "jobClass",
					title: "任务类",
					disable: change && change == "edit" ? true : false,
					valid: "required"
				},
				{
					id: "textCronExpression",
					field: "cronExpression",
					title: "时间表达式",
					disable: change && change == "edit" ? false : false,
					valid: "required"
				},
				{
					id: "textJobDescription",
					field: "jobDescription",
					title: "任务描述",
					disable: change && change == "edit" ? false : false
				}
			]
		}
	});
}
