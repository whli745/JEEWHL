$(function () {
	var groupJson = JEE.getJson("/system/sysDict/findByParentValue", { value: "JOB_GROUP"}, "value", "name");
	
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
			title: "任务组",
			comboUrl: "/system/sysDict/findByParentValue",
			comboData: {
				value: "JOB_GROUP"
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
		tableId: "tb_data",
		mainSearch: searchValues,
		url: "/scheduler/jobLog/findByPage",
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
			{
				field: "jobName",
				title: "任务名称"
			},
			{
				field: "jobGroup",
				title: "任务组",
				formatter: function(val){
					if(val){
						return groupJson[val];
					}
				}
			},
			{
				field: "jobClass",
				title: "任务类"
			},
			{
				field: "dateTime",
				title: "执行时间"
			},
			{
				field: "duration",
				title: "总耗时(秒)"
			},
			{
				field: "msg",
				title: "运行信息"
			},
		]
	});
});