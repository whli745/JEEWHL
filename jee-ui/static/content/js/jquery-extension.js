(function ($) {
	//备份jquery的ajax方法   
	var _ajax = $.ajax;

	//重写jquery的ajax方法   
	$.ajax = function (opt) {
		//备份opt中error和success方法   
		var fn = {
			error: function (XMLHttpRequest, textStatus, errorThrown) {},
			success: function (data, textStatus) {},
			beforeSend: function (XHR) {}
		}
		if (opt.error) {
			fn.error = opt.error;
		}
		if (opt.success) {
			fn.success = opt.success;
		}
		if (opt.beforeSend) {
			fn.beforeSend = opt.beforeSend;
		}
		//扩展增强处理   
		var _opt = $.extend(opt, {
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				fn.error(XMLHttpRequest, textStatus, errorThrown);
			},
			success: function (data, textStatus) {
				fn.success(data, textStatus);
			},
			beforeSend: function (XHR) {
				XHR.setRequestHeader("Authorization", sessionStorage.getItem("token"));
				fn.beforeSend(XHR);
			}
		});
		_ajax(_opt);
	};
})(jQuery);
