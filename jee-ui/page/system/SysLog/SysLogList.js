$(function () {
	
	var typeJson = JEE.getJson("/system/sysDict/findByParentValue", { value: "OPERATION_TYPE"}, "value", "name");
	
	//定义搜索区域字段
	var searchValues = [
		{
			id: "textSearchType",
			field: "type",
			title: "操作类型",
			comboUrl: "/system/sysDict/findByParentValue",
			comboData: { value: "OPERATION_TYPE"},
			comboId: "value",
			comboText: "name"
		},
		{
			id: "textSearchTableName",
			field: "tableName",
			title: "操作SQL"
		},
		{
			id: "textSearchOperationDetail",
			field: "operationDetail",
			title: "操作参数"
		},
		{
			id: "textSearchRequestUri",
			field: "requestUri",
			title: "请求URI"
		},
		{
			id: "textSearchIp",
			field: "ip",
			title: "操作ip"
		},
		{
			id: "textSearchHostName",
			field: "hostName",
			title: "操作主机"
		},
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
		url: "/system/sysLog/findByPage",
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
				field: "type",
				title: "操作类型",
				formatter: function(val){
					if(val){
						return typeJson[val];
					}
				}
			},
			{
				field: "tableName",
				title: "操作SQL"
			},
			{
				field: "operationDetail",
				title: "操作参数"
			},
			{
				field: "requestUri",
				title: "请求URI"
			},
			{
				field: "ip",
				title: "操作ip"
			},
			{
				field: "hostName",
				title: "操作主机"
			},
			{
				field: "createDate",
				title: "操作时间"
			},
		]
	});
});
