var addTabs = function (options) {
    //debugger;
    $(".nav-tabs .active").removeClass("active");
    $(".tab-content .active").removeClass("active");
	var childCount = $(".nav-tabs li").length;
    //如果TAB不存在，创建一个新的TAB
    if (!$("#tab_" + options.id)[0]) {
		//判断标签是否超出限定
        if(childCount >= 10){
        	var dropdown = $(".nav-tabs li.dropdown");
        	if(!dropdown || dropdown.length == 0){
        		var _li = $("<li class='dropdown'></li>")
        		_li.append("<a data-toggle='dropdown' class='dropdown-toggle' href='#'>"
        		 +"<i class='ace-icon glyphicon glyphicon-list bigger-110 width-auto'></i>"
        		 +"<i class='ace-icon fa fa-caret-down bigger-110 width-auto'></i></a>");
        		_li.append("<ul class='dropdown-menu dropdown-info'></ul>");
        		$(".nav-tabs").append(_li);
        	}
        }
        //创建新TAB的title
        var title = '<li id="tab_' + options.id + '"><a href="#content_' + options.id + '" aria-controls="' + options.id + '" role="tab" data-toggle="tab"><i class="' + options.icon + '"></i>' + options.name;
        title += ' <i class="glyphicon glyphicon-remove-sign" tabClose="' + options.id + '"></i>';
        title += '</a></li>';
        //是否指定TAB内容
        if (options.content) {
            content = '<div class="tab-pane" id="content_' + options.id + '">' + options.content + '</div>';
        } else {//没有内容，使用IFRAME打开链接
            content = '<div class="tab-pane" id="content_' + options.id + '"><iframe id="iframe_' + options.id + '" src="' + options.href +
                '" width="100%" height="100%" onload="changeFrameHeight(this)" frameborder="0" border="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe></div>';
        }
        //加入TABS
		if(childCount >= 10){
			$(".nav-tabs li.dropdown ul").append(title);
		}else{
			$(".nav-tabs").append(title);
		}
        $(".tab-content").append(content);
    }
    //激活TAB
	if(childCount >= 10){
		$(".nav-tabs li.dropdown").addClass('active');
	}
    $("#tab_" + options.id).addClass('active');
    $("#content_" + options.id).addClass("in active");
};
var changeFrameHeight = function (that) {
    //$(that).height(that.body.scrollHeight);
    //$(that).height(document.documentElement.clientHeight-94);
    $(that).parent(".tab-pane").height(document.documentElement.clientHeight-94);
}
var closeTab = function (id) {
	var flag = false;
	//判断隐藏的标签是否最后一个
	var dropdown = $(".nav-tabs li.dropdown");
	if(dropdown.length > 0 && $(".nav-tabs li.dropdown ul li").length == 1){
		flag = true;
	}
    //如果关闭的是当前激活的TAB，激活他的前一个TAB
    if ($(".nav-tabs li.active").attr('id') == "tab_" + id || $(".nav-tabs li.dropdown ul li.active").attr('id') == "tab_" + id) {
		if(flag){
			$(".nav-tabs li.dropdown").prev().addClass('active');
			$(".nav-tabs li.dropdown").remove();
		}else{
			$("#tab_" + id).prev().addClass('active');
		}
        $("#content_" + id).prev().addClass('active');
    }else if($(".nav-tabs li.dropdown ul li").attr('id') == "tab_" + id) {
		if(flag){
			$(".nav-tabs li.dropdown").remove();
		}
	}
    //关闭TAB
    $("#content_" + id).remove();
    $("#tab_" + id).remove();
};
$(function () {
      $(".nav-tabs").on("click", "[tabClose]", function (e) {
        var id = $(this).attr("tabClose");
        closeTab(id);
    });

    window.onresize = function () {
        var target = $(".tab-content .active iframe");
        changeFrameHeight(target);
    }

    //tab页向左向右移动
    $('.nav-my-tab .leftbackward').click(function () {
        var strLeft = $('.nav-my-tab .middletab .nav-tabs').css('left');
        var iLeft = parseInt(strLeft.replace('px', ''));
        if (iLeft >= 0) {
            return;
        }
        else {
            debugger;
            var totalWidth = 0;
            var lis = $(".nav-tabs li");
            for (var i = 0; i < lis.length; i++) {
                var item = lis[i];
                totalWidth -= $(item).width();
                if (iLeft > totalWidth) {
                    iLeft += $(item).width();
                    break;
                }
            }
            if (iLeft > 0) {
                iLeft = 0;
            }
            $(".nav-my-tab .middletab .nav-tabs").animate({left: iLeft + 'px'});
        }
    });

    $('.nav-my-tab .rightforward').click(function () {
        var strLeft = $('.nav-my-tab .middletab .nav-tabs').css('left');
        var iLeft = parseInt(strLeft.replace('px', ''));
        var totalWidth = 0;
        $.each($(".nav-tabs li"), function (key, item) {
            totalWidth += $(item).width();
        });
        var tabsWidth = $(".nav-my-tab .middletab").width();
        if (totalWidth > tabsWidth) {
            debugger;
            if (totalWidth - tabsWidth <= Math.abs(iLeft)) {
                return;
            }
            var lis = $(".nav-tabs li");
            totalWidth = 0;
            for (var i = 0; i < lis.length; i++) {
                var item = lis[i];
                totalWidth -= $(item).width();
                if (iLeft > totalWidth) {
                    iLeft -= $(item).width();
                    break;
                }
            }
            $(".nav-my-tab .middletab .nav-tabs").animate({left: iLeft + 'px'});
        }
    });
});