var common = function () {

};

//初始化表格
common.prototype.initTable = function (options) {
	var _this = this;

	var $table = $("#" + options.tableId);

	var _toolId = "";
	//表格行是否单选
	var _singleclick = options.singleSelect ? options.singleSelect : false;
	//是否分页
	var _pagination = options.pagination === false ? false : true;
	//是否显示客户端搜索
	var _search = options.search ? options.search : false;
	//加载成功函数switchFormatter
	var _onLoadSuccess = options.onLoadSuccess ? options.onLoadSuccess : null;
	//点击表格行函数
	var _onClickRow = options.onClickRow ? options.onClickRow : null;
	
	var _onCheck = options.onCheck ? options.onCheck : null;
	
	var _onUncheck = options.onUncheck ? options.onUncheck : null;

	var _treeView = options.treeView ? options.treeView : false; //treeView视图字段
	var _treeField = options.treeField ? options.treeField : "name";
	var _treeId = options.treeId ? options.treeId : "id";
	var _treeCollapseAll = options.treeCollapseAll ? options.treeCollapseAll : false; //是否全部展开
	var _showRefresh = options.showRefresh == true ?  true: false;

	//创建按钮工具栏
	var _btnItems = options.btnItems ? options.btnItems : [];
	if (_btnItems.length > 0) {
		_toolId = "#toolBar_" + options.tableId;
		var $toolBar = $('<div id="toolBar_' + options.tableId + '" class="btn-group"></div>');
		$toolBar.appendTo($table.parent());
		
		//获取用户关联的按钮
		var re = _this.getButtonByUser();
		
		//过滤掉无效的定义按钮
		if(re.length > 0){
			_btnItems = _btnItems.filter(function(item){
				return item.btnId && $.inArray(item.btnId,re) > -1;
			})
		}
		
		$.each(_btnItems, function (m, item) {
			_this.initButton($toolBar, item);
		})

	}
	var url = options.url;
	if (url.indexOf("http") == -1 &&  url.indexOf("https") == -1) {
		url = apiUrl + url;
	}

	//将表格初始化BootStrapTable
	$table.bootstrapTable({
		url: url, //请求后台的URL（*）
		method: 'post', //请求方式（*）
		toolbar: _toolId, //工具按钮用哪个容器
		striped: true, //是否显示行间隔色
		cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination: _pagination, //是否显示分页（*）
		//height:_pagination ? 500 : 450,
		sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
		queryParamsType: '',
		pageNumber: 1, //初始化加载第一页，默认第一页
		pageSize: 10, //每页的记录行数（*）
		pageList: [10, 25, 50, 100, 500,1000], //可供选择的每页的行数（*）
		showRefresh: _showRefresh, //是否显示刷新按钮
		showToggle: false, //是否显示详细视图和列表视图的切换按钮
		clickToSelect: true, //是否启用点击选中行
		singleSelect: _singleclick,
		uniqueId: "id", //每一行的唯一标识，一般为主键列
		undefinedText: '',
		queryParams: function (params) {
			var temp = {};
			//搜索框查询条件解析
			if (options.mainSearch) {
				temp = _this.parseObject(options.mainSearch, "field", "id");
			}
			temp.pageSize = options.pagination === false ? -1 : params.pageSize; //页面大小
			temp.currentPage = params.pageNumber <= 0 ? 1 : params.pageNumber; //页码
			
			var newTemp = $.extend({}, options.searchParams(), temp)
			return newTemp;
		}, //传递参数（*）
		treeView: _treeView, //treeView视图
		treeField: _treeField, //treeView视图字段
		treeId: _treeId,
		treeCollapseAll: _treeCollapseAll, //是否全部展开
		columns: options.columns,
		onCheck: function (row, $element) {
			if (_btnItems) {
				_this.getButtonStatus(_btnItems);
			}
			if(_onCheck){
				_onCheck(row,$element);
			}
		},
		onUncheck: function (row, $element) {
			if (_btnItems) {
				_this.getButtonStatus(_btnItems);
			}
			if(_onUncheck){
				_onUncheck(row, $element);
			}
		},
		onLoadSuccess: function (data) {
			if (_onLoadSuccess) {
				_onLoadSuccess(data);
			}
		},
		onClickRow: function (row, $element, field) {
			if (_onClickRow) {
				_onClickRow(row, $element, field);
			}
		},
		responseHandler: function (res) {
			return {
				total: res && res.results ? res.count : 0, //总页数,前面的key必须为"total"
				rows: res && res.results ? res.results : [] //行数据，前面的key要与之前设置的dataField的值一致.
			};
		}
	});

	if (_btnItems) {
		_this.getButtonStatus(_btnItems);
	}
}

/**
 * 初始化查询区域
 * @param {Object} options
 */
common.prototype.initSearch = function (options) {
	var _this = this;
	var $panel = $("#searchPanel");

	if (options.searchValues) {
		if (options.panelTitle) {
			$panel.append('<div class="panel-heading">' + options.panelTitle + '</div>');
		}

		var $panelBody = $('<div class="panel-body"></div>');
		$panelBody.appendTo($panel);
		var $form = $('<form autocomplete="off" class="form-horizontal" role="form" id="noFrom"></form>')
		$form.appendTo($panelBody);

		var i = 0;
		var rules = {};
		var $fromGroup = $('<div class="form-group" id="fromGroup' + i + '"></div>');

		//组合搜索条件
		$.each(options.searchValues, function (m, item) {
			var cols = $fromGroup.find("div").length;
			if (cols == 4) {
				i += 1;
				$fromGroup = $('<div class="form-group" id="fromGroup' + i + '"></div>');
			}
			$fromGroup.appendTo($form);

			//封装表单组件
			_this.getInput(item, $fromGroup, null, 1);

			//表单组件验证规则
			if (item.valid) {
				rules[item.field] = item.valid;
			}
		});
		//赋予表单验证规则
		_this.initValidate($form, rules);

		var $btnFromGroup = $('<div class="form-group" id="fromGroupBtn"></div>');
		$btnFromGroup.append('<div class="col-xs-12 text-right">' +
			'<button type="button" id="btn_search" class="btn btn-sm btn-primary">查询</button>' +
			'<button type="button" id="btn_reset" class="btn btn-sm btn-light">重置</button></div>');
		$btnFromGroup.appendTo($form);

		$("#btn_search").on("click", function () {
			if (Object.keys(rules).length > 0) {
				//手动验证表单
				if ($("#noFrom").valid()) {
					$("#" + options.defaultTable).bootstrapTable("refresh");
				}
			} else {
				$("#" + options.defaultTable).bootstrapTable("refresh");
			}
		});

		$("#btn_reset").on("click", function () {
			$panel.find("input").val("");
			$panel.find("input[type='checkbox']").attr("checked", false);
			$panel.find("select").val("").trigger("change");
			$panel.find("textarea").val("");
			$("#" + options.defaultTable).bootstrapTable("refresh");
		});
	}
}

/**
 * 初始化弹窗
 * @param {Object} options 弹窗属性
 */
common.prototype.initDialog = function (options) {
	var _this = this;

	var $modal = $("#" + options.modalId);

	$modal.empty();

	//定义弹出框
	var $modalDialog = $('<div class="modal-dialog"></div>');
	$modalDialog.appendTo($modal);

	//定义弹出框内容
	var $modalContent = $('<div class="modal-content"></div>');
	$modalContent.appendTo($modalDialog);

	//定义弹出框头部
	if (options.modalTitle) {
		var $modalHeader = $('<div class="modal-header">' +
			'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
			'<h4 class="modal-title">' + options.modalTitle + '</h4></div>');
		$modalHeader.appendTo($modalContent);
	}

	//定义弹出窗主体
	var $modalBody = $('<div class="modal-body"></div>');
	$modalBody.appendTo($modalContent);

	//定义表单
	var formItems = {};
	if (options.modalForm) {
		_this.initForm($modalBody, options.modalForm);
		formItems = options.modalForm.formItems;
	}


	var $modalFooter = $('<div class="modal-footer"></div>');
	$modalFooter.appendTo($modalContent);
	if (options.btnItems) {
		$.each(options.btnItems, function (index, item) {
			_this.initButton($modalFooter, item, formItems)
		});
	}

	//取消按钮
	var $btnCancel = $('<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>');
	$btnCancel.appendTo($modalFooter);
	$modal.modal("show");
}

/**
 * 初始化表单
 * @param {Object} formId 表单ID
 * @param {Object} formData 表单默认数据
 * @param {Object} formItems 表单控件Json数组
 * @param {Object} modalBody modal body
 */
common.prototype.initForm = function ($el, modalForm) {
	var _this = this;
	//定义表单
	var $form = $('<form autocomplete="off" class="form-horizontal" role="form" id="' + modalForm.formId + '"></form>');
	if(modalForm.multipart){
		$form.attr("enctype","multipart/form-data");
	}
	$form.appendTo($el);
	var rows = {};
	if (modalForm.defaultTable) {
		rows = $("#" + modalForm.defaultTable).bootstrapTable("getSelections")[0] ? $("#" + modalForm.defaultTable).bootstrapTable(
			"getSelections")[0] : {};
	}

	//表单验证条件
	var rules = {};
	var num = 0;
	var $formGroup = $('<div class="form-group" id="editFromGroup0"></div>');
	$formGroup.appendTo($form);
	//创建表单组件
	$.each(modalForm.formItems, function (m, item) {
		if ($("#editFromGroup" + num).children("div").length == 2 || item.inCss) {
			num += 1;
			$formGroup = $('<div class="form-group" id="editFromGroup' + num + '"></div>');
			$formGroup.appendTo($form);
		}

		//封装组件
		_this.getInput(item, $formGroup, rows);

		//表单组件验证规则
		if (item.valid) {
			rules[item.field] = item.valid;
		}
	});
	//赋予表单验证规则
	_this.initValidate($form, rules);
}

/**
 * 初始化表单控件
 * @param {Object} item 表单控件对象
 * @param {Object} _formGroup formGroup选择器
 * @param {Object} _formData 
 */
common.prototype.getInput = function (item, _formGroup, _formData, inCss) {
	var _this = this;
	var _inputCss = inCss ? 2 : 4;
	var _labelCss = inCss ? 1 : 2;
	var require = '';

	if (item.valid) {
		var type = typeof item.valid; //判断验证规则类型
		if (type == "string") {
			if (item.valid == "required") {
				require = '<span style="color:red;">*</span>';
			}
		} else if (type == "object") {
			for (var key in item.valid) {
				if (key == "required") {
					require = '<span style="color:red;">*</span>';
					break;
				}
			}
		}
	}

	//定义输入控件标题
	var $label = $('<label for="' + item.id + '" class="control-label col-xs-' + _labelCss + '">' + item.title + require +
		'</label>');
	$label.appendTo(_formGroup);

	var $div = $('<div class="col-xs-' + _inputCss + '"></div>');
	$div.appendTo(_formGroup)

	//定义控件默认值
	var defaultValue;
	if (item.defaultValue) {
		defaultValue = item.defaultValue;
	} else if (_formData) {
		defaultValue = _formData[item.field];
	}

	//根据不同类型定义控件
	if (item.id.indexOf('text') > -1) {
		var $input = $('<input type="text" class="form-control" id="' + item.id + '" name="' + item.field + '"/>');
		$div.append($input);

		$input.attr("value", defaultValue);
		$input.attr("disabled", item.disable ? item.disable : false);
	} else if (item.id.indexOf('area') > -1) {
		var $input = $('<textarea class="form-control" style="resize: none;" id="' + item.id + '" name="' + item.field +
			'"></textarea>');
		$div.append($input);
		$input.html(defaultValue);
		$input.attr("disabled", item.disable ? item.disable : false);
	} else if (item.id.indexOf('combo') > -1) {
		var $input = $('<select class="form-control" style="width:150px;height: 34px;" id="' + item.id + '" name="' + item.field +
			'"></select>');
		$div.append($input);
		
		if(item.multiple){
			$input.attr("multiple",true);
		}

		if (item.comboUrl) {
			var comboData = item.comboData ? item.comboData : {};
			$.when(_this.myAjax(item.comboUrl, comboData)).done(function (data) {
				_this.initSelect($input, data,item.comboId, item.comboText, defaultValue);
			});
		}
		//自定义选择框事件
		if (item.comboChange) {
			$input.on("change", item.comboChange);
		}
		$input.attr("disabled", item.disable ? item.disable : false);
	} else if (item.id.indexOf('icon') > -1) {
		var $input = $('<button type="button" class="btn btn-white" id="' + item.id + '" name="' + item.field +
			'" role="iconpicker"></button>');
		$div.append($input);

		$input.iconpicker({
			iconset: 'fontawesome',
			selectedClass: 'btn-success',
			unselectedClass: '',
			icon: defaultValue
		});
		$input.attr("disabled", item.disable ? item.disable : false);
	} else if (item.id.indexOf('switch') > -1) {
		var $input = $('<input type="checkbox" id="' + item.id + '" name="' + item.field + '"/>');
		$div.append($input);
		//$input.attr("value", defaultValue);
		$input.bootstrapSwitch({
			onText: "是",
			offText: "否",
			onColor: "success",
			offColor: "danger",
			size: "small",
			state: defaultValue,
			onSwitchChange: function (event, state) {
				if (state == true) {
					$input.attr("value", 1);
				} else {
					$input.attr("value", 0);
				}
			}
		});
		$input.attr("disabled", item.disable ? item.disable : false);
	}else if (item.id.indexOf('file') > -1) {
		var $input = $('<input type="file" class="form-control" id="' + item.id + '" name="' + item.field + '"/>');
		$div.append($input);

		$input.attr("value", defaultValue);
		$input.attr("disabled", item.disable ? item.disable : false);
	}
}

/**
 * 初始化按钮
 * @param {Object} $el 选择器标签
 * @param {Object} option 按钮属性
 */
common.prototype.initButton = function ($el, option, data) {
	var _this = this;
	var $btn = $('<button id="' + option.btnId + '" type="button" class="' + option.btnCss + '">' + option.btnText +
		'</button>');
	$el.append($btn);
	$btn.on("click", function () {
		if (option.formId) {
			if (option.btnClick) {
				//手动验证表单
				if ($("#" + option.formId).valid()) {
					if (option.showConfirm) {
						layer.confirm('确定' + option.btnText + '数据吗？', {
								btn: ["确认", "取消"],
								icon: 0,
								title: "提示"
							},
							function () {
								option.btnClick(_this.parseObject(data, 'field', 'id'))
							});
					} else {
						option.btnClick(_this.parseObject(data, 'field', 'id'))
					}
				}
			}
		} else {
			if (option.btnClick) {
				if (option.showConfirm) {
					layer.confirm('确定' + option.text + '数据吗？', {
							btn: ["确认", "取消"],
							icon: 0,
							title: "提示"
						},
						function () {
							option.btnClick();
						});
				} else {
					option.btnClick();
				}
			}
		}
	});
}

/**
 * 表单验证初始化
 * @param {Object} form 表单选择器
 * @param {Object} rules 验证规则
 */
common.prototype.initValidate = function (form, rules) {
	form.validate({
		errorElement: "em",
		errorPlacement: function (error, element) {
			// Add the `help-block` class to the error element
			error.addClass("help-block");

			if (element.prop("type") === "checkbox") {
				error.insertAfter(element.parent("label"));
			} else {
				error.insertAfter(element);
			}
		},
		highlight: function (element, errorClass, validClass) {
			$(element).parent("div").addClass("has-error").removeClass("has-success");
		},
		unhighlight: function (element, errorClass, validClass) {
			$(element).parent("div").addClass("has-success").removeClass("has-error");
		},
		rules: rules
	});
}

//初始化图标控件
common.prototype.initIcon = function (selector) {
	$("#" + selector).iconpicker({
		arrowPrevIconClass: 'glyphicon glyphicon-chevron-left',
		arrowNextIconClass: 'glyphicon glyphicon-chevron-right',
		footer: true,
		header: true,
		icon: '',
		iconset: 'fontawesome',
		rows: 4,
		search: true,
		searchText: 'Search',
		selectedClass: 'btn-success',
		unselectedClass: 'btn-white'
	});
}

/**
 * ajax封装
 * @param {Object} url 查询url
 * @param {Object} data 查询条件数据
 * @param {Object} contentType 上下文类型
 * @param {Object} method 请求方式(get,post)
 * @param {Object} async 是否异步
 */
common.prototype.myAjax = function (url, data, contentType, method, async) {
	var def = $.Deferred();
	var _this = this;
	
	if (url.indexOf("http") == -1 &&  url.indexOf("https") == -1) {
		url = apiUrl + url;
	}
	var myType = method ? method : "post";
	var myData = data ? JSON.stringify(data) : JSON.stringify({});
	var myContentType = contentType ? contentType : "json";
	var myAsync = async === false ? false : true;
	$.ajax({
		url: url,
		type: myType,
		data: myData,
		contentType: "application/" + myContentType + ";charset=UTF-8",
		dataType: "json",
		async: myAsync,
		success: function (data) {
			if (data instanceof Array) {
				def.resolve(data);
			} else {
				if(data.succeed){
					if (data.message) {
						_this.successMsg(data.message);
					}
					def.resolve(data.results ? data.results : "true");
				}else if(data.message){
					if("-10005" == data.code){
						_this.confirmMsg(data.message,function(){
							window.open("../../page/login.html","_top"); 
						});
					}else{
						_this.errMsg(data.message);
					}
				}else{
					def.resolve(data);
				}
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			_this.errMsg("请求错误");
		}
	});
	return def.promise();
}

/**
 * 初始化选择框
 * @param {Object} $el 标签选择器
 * @param {Object} data 标签Json数组
 * @param {Object} type select类型(1 树形结构 其它普通)
 * @param {Object} comboId select value字段
 * @param {Object} comboText select text字段
 * @param {Object} defaultValue select默认值
 */
common.prototype.initSelect = function ($el, data, comboId, comboText, defaultValue) {
	$el.empty();
	$el.append('<option value="">请选择...</option>');
	$.each(data, function (n, sitem) {
		var $option = $("<option></option>");
		if (sitem.parentId) {
			$option.attr("parent", sitem.parentId);
		}
		$option.attr("value", sitem[comboId]);
		$option.text(sitem[comboText]);
		if (defaultValue && (defaultValue == sitem[comboId])) {
			$option.attr("selected", "selected");
		}
		$el.append($option);
	});
	$el.select2tree();
}

/**
 * 将查询结果转换为Json数据
 * @param {Object} url 访问url
 * @param {Object} data 查询条件
 * @param {Object} key Json对象key字段
 * @param {Object} value Json对象value字段
 */
common.prototype.getJson = function (url, data, key, value) {
	var _this = this;
	var selectJson = {};
	var data = data ? data : {};
	$.when(_this.myAjax(url, data)).done(function (result) {
		if (result && result.length > 0) {
			$.each(result, function (i, item) {
				selectJson[item[key]] = item[value];
			})
		}
	});
	return selectJson;
}

/**
 * 解析Json数组中的对象
 * @param {Object} array Json数组
 * @param {Object} filed Json对象key字段
 * @param {Object} id Json对象value字段
 */
common.prototype.parseObject = function (array, filed, id) {
	var newObj = {};
	array.forEach(function (item, index) {
		if (item[id].indexOf('radio') != -1) {
			newObj[item[filed]] = $('input:radio[name="' + item[filed] + '"]:checked').val()
		} else if (item[id].indexOf('txt') != -1) {
			newObj[item[filed]] = $('#' + item[id]).text()
		} else if (item[id].indexOf('switch') != -1) {
			newObj[item[filed]] = $('#' + item[id]).attr("value");
		} else if (item[id].indexOf('file') != -1) {
			newObj[item[filed]] = new FormData($('#' + item[id] + '_form')[0])
		} else if (item[id].indexOf('combo') != -1) {
			if ($('#' + item[id]).val() == '请选择' || $('#' + item[id]).val() == ' ' || $('#' + item[id]).val() == '') {
				newObj[item[filed]] = null;
			} else {
				newObj[item[filed]] = $('#' + item[id]).val();
			}
		} else if (item[id].indexOf('icon') != -1) {
			newObj[item[filed]] = $('#' + item[id] + ' input[name="' + item[filed] + '"]').val();
		} else {
			newObj[item[filed]] = $('#' + item[id]).val()
		}
	})
	return newObj
}

/**
 * 判断按钮是否可用
 * @param {Object} btnValues 按钮Json数组
 */
common.prototype.getButtonStatus = function (btnValues) {
	$.each(btnValues, function (index, item) {
		if (item.otherOptions) {
			var otherOption = true;
			$.each(item.otherOptions, function (index1, item1) {
				if (typeof item1 == 'boolean') {
					alert(item1)
					otherOption = otherOption && item1
				} else if (item1.selectNum) {
					otherOption = otherOption && ($('#' + item1.id).bootstrapTable('getSelections').length == item1.selectNum)
				} else if (item1.selectMinNum) {
					otherOption = otherOption && ($('#' + item1.id).bootstrapTable('getSelections').length >= item1.selectMinNum)
				} else if (item1.textId) {
					otherOption = otherOption && ($('#' + item1.textId).val() != '')
				} else if(item1.isEdit){
					var selects = $('#' + item1.id).bootstrapTable('getSelections');
					$.each(selects,function(index2,item2){
						console.log("item2.edit == "+item2.edit);
						if(item2.edit != true && item2.edit != 1){
							otherOption = otherOption && false;
							return;
						}
					});
				} else if (item1.noSelect) {
					var rows = $('#' + item1.id).bootstrapTable('getSelections')
					rows.forEach(function (row, rowindex, rowarray) {
						item1.noselect.forEach(function (list, number, array) {
							list.nolist.forEach(function (select, selectindex, selectarray) {
								if (row[list.title] == select) {
									otherOption = otherOption && false
									return;
								}
							})
						})
					})
				}
				$('#' + item.btnId).prop('disabled', !otherOption);
			})
		}
	});
}

/**
 * 由用户获取按钮权限
 */
common.prototype.getButtonByUser = function () {
	var _this = this;
	var reponse;
	var url = window.document.location.pathname;
	$.when(_this.myAjax("/system/sysMenu/getButtons", {
		href: url.substring(url.lastIndexOf("/") + 1)
	},null,null,false)).done(function (results) {
		reponse = results.map(function (item) {
			return item.href;
		});
	});
	
	return reponse;
}

/**
 * 成功提示框
 * @param {Object} successMsg  成功消息
 */
common.prototype.successMsg = function (successMsg) {
	layer.msg(successMsg, {
		icon: 6,
		time: 1000
	});
}

/**
 * 错误提示框
 * @param {Object} errMsg  错误信息
 */
common.prototype.errMsg = function (errMsg) {
	layer.msg(errMsg, {
		icon: 5,
		time: 1000
	});
}

/**
 * 确认消息框
 * @param {Object} confirmMsg 确认框消息
 * @param {Object} url  确认按钮提交URL
 * @param {Object} data 提交时的参数
 * @param {Object} defaultTable  关联的表ID
 * @param {Object} defaultModal  关联的弹出窗ID
 */
common.prototype.confirmMsg = function (confirmMsg, confirmFunction) {
	var _this = this;
	layer.confirm(confirmMsg, {
			btn: ["确认", "取消"],
			icon: 0,
			title: "提示"
		},
		function () {
			if(confirmFunction){
				confirmFunction();
			}
		});
}

common.prototype.getZTree = function(el,url,otherParam){
	var _this = this;
	var setting = {
		data: {
            simpleData : {
                enable : true//是否之用简单数据
                //idKey : 'id', //对应json数据中的ID
                //pIdKey : 'parentId' //对应json数据中的父ID
            },
        },
		check: {
			enable: true
		}
	}
	$.when(_this.myAjax(url, otherParam)).done(function(data){
		if(data){
			$.fn.zTree.init($("#"+el), setting,data);
		}
	})
}

var JEE = new common();
