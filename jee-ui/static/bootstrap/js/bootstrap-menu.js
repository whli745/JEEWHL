(function ($) {
	$.fn.bootstrapMenu = function (options) {
		options = $.extend({}, $.fn.bootstrapMenu.defaults, options || {});
		var target = $(this);
		target.addClass('nav');
		target.addClass('nav-list');
		if (options.data) {
			init(target, options.data);
		} else {
			if (!options.url) return;
			$.when(JEE.myAjax(options.url, options.param, null, null, false))
				.done(function (data) {
					init(target, data);
				});
		}

		function init(target, data) {
			$.each(data, function (i, item) {
				var li = $('<li id="li_' + item.id + '"></li>');
				var a = $('<a></a>');
				if (item.icon) {
					if (item.icon.indexOf("fa-") == 0) {
						item.icon = "fa " + item.icon;
					} else if (item.icon.indexOf("glyphicon-") == 0) {
						item.icon = "glyphicon " + item.icon;
					}
				}
				a.data("item", item);
				var icon = $('<i></i>');
				icon.addClass("menu-icon").addClass(item.icon);
				var text = $('<span></span>');
				text.text(item.name).addClass('menu-text');
				a.append(icon);
				a.append(text);
				if (item.menus && item.menus.length > 0) {
					a.attr('href', '#');
					a.addClass('dropdown-toggle');
					var arrow = $('<b></b>');
					arrow.addClass('arrow').addClass('fa fa-angle-down');
					a.append(arrow);
					li.append(a);
					var menus = $('<ul></ul>');
					menus.addClass('submenu');
					init(menus, item.menus);
					li.append(menus);
				} else {
					if (item.href) {
						if (item.target) {
							if(item.target == "TAB"){
								a.attr('href', "#");
								a.on("click", function () {
									$(".nav-list").find("li").removeClass("active");
									$(this).parents("li:eq(0)").addClass("active");
									addTabs(a.data("item"));
								});
							} else if(item.target == "HTML") {
								var url = item.href;
								if (url.indexOf("http") == -1 &&  url.indexOf("https") == -1) {
									url = apiUrl + url;
								}
								a.attr('href', url);
								a.attr('target', '_blank');
							}else{
								a.attr('href', "#");
							}
						}else{
							a.attr('href', "#");
						}
					} else {
						a.attr('href', "#");
					}
					li.append(a);
				}
				target.append(li);
			});
		}
	}

	$.fn.bootstrapMenu.defaults = {
		url: null,
		param: null,
		data: null
	};
})(jQuery);
