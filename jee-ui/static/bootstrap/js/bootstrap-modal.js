(function ($) {
    'use strict';

    var Modal = $.fn.modal.Constructor,
        _show = Modal.prototype.show;

    Modal.prototype.show = function (_relatedTarget) {
        var that = this
        var e = $.Event('show.bs.modal', { relatedTarget: _relatedTarget })

        this.$element.trigger(e)

        if (this.isShown || e.isDefaultPrevented()) return

        this.isShown = true

        this.checkScrollbar()
        this.setScrollbar()
        this.$body.addClass('modal-open')

        this.escape()
        this.resize()

        this.$element.on('click.dismiss.bs.modal', '[data-dismiss="modal"]', $.proxy(this.hide, this))

        this.$dialog.on('mousedown.dismiss.bs.modal', function () {
            that.$element.one('mouseup.dismiss.bs.modal', function (e) {
                if ($(e.target).is(that.$element)) that.ignoreBackdropClick = true
            })
        })

        this.backdrop(function () {
            var transition = $.support.transition && that.$element.hasClass('fade')

            if (!that.$element.parent().length) {
                that.$element.appendTo(that.$body)
            }

            that.$element
                .show()
                .scrollTop(0)

            that.adjustDialog()

            if (transition) {
                that.$element[0].offsetWidth
            }

            that.$element.addClass('in')

            that.enforceFocus()
            //设置窗体居中
            that.$element.children().eq(0).css({
                "margin-top": function () {
                    return (that.$element.height() - that.$element.children().eq(0).height() - 40) / 2 + "px";
                }
            });
            var e = $.Event('shown.bs.modal', { relatedTarget: _relatedTarget })

            transition ?
                that.$dialog
                    .one('bsTransitionEnd', function () {
                        that.$element.trigger('focus').trigger(e)
                    })
                    .emulateTransitionEnd(Modal.TRANSITION_DURATION) :
                that.$element.trigger('focus').trigger(e)
        })
    };

    var guid = function (prefix) {
        var date = new Date();
        return prefix + '_' + date.valueOf();
    };

    var mouseStartPoint = { "left": 0, "top": 0 };
    var mouseEndPoint = { "left": 0, "top": 0 };
    var mouseDragDown = false;
    var oldP = { "left": 0, "top": 0 };
    var moveTartet;

    $.modal = function (options, param, param2) {
        if (typeof options == 'string') {
            return $.modal.methods[options](this, param, param2);
        }
        options = $.extend({}, $.modal.defaults, options || {});

        var modal = $('<div></div>');
        modal.addClass('modal');
        modal.addClass('fade');
        modal.attr('id', options.id || guid('modal'));
        modal.attr('role', 'dialog');
        modal.attr('tabindex', '-1');

        var dialog = $('<div></div>');
        dialog.addClass('modal-dialog');
        if (options.size == 'large') {
            dialog.addClass('modal-lg');
        }
        if (options.size == 'small') {
            dialog.addClass('modal-sm');
        }
        if (options.width) {
            dialog.css('width', options.width + 'px');
        }
        dialog.attr('role', 'document');

        var header = $('<div></div>');
        header.addClass('modal-header');
        header.css('cursor', 'move');



        var close = $('<button></button>');
        close.attr('type', 'button');
        close.attr('data-dismiss', 'modal');
        close.attr('aria-label', 'Close');
        close.addClass('close');

        var span = $('<span></span>');
        span.attr('aria-hidden', 'true');
        span.text('×');

        close.append(span);

        var title = $('<h4></h4 >');
        title.addClass('modal-title');
        title.text(options.title);

        header.append(close);
        header.append(title);

        var content = $('<div></div>');
        content.addClass('modal-content');

        content.append(header);

        if (options.url) {
            if (options.isframe) {
                var iframe = $('<iframe></iframe>');
                iframe.attr('frameborder', '0');
                iframe.attr('src', options.url);
                iframe.css({ 'width': '100%', height: '100%' });
                modal.append(iframe);
            }
            else {
                var html = $('<div></div>');
                html.load(options.url, options.data, function (response, status, xhr) {
                    if (status == 'success') {
                        content.append(html);
                        dialog.append(content);
                        modal.append(dialog);
                        $('body').append(modal);
                        modal.on('show.bs.modal', options.show);
                        modal.on('hidden.bs.modal', function (event) {
                            options.hidden(event);
                            modal.remove();
                        })
                        modal.modal(options);
                    }
                    else {
                        //if (options.data && $(this).find('form').length > 0) {
                        //    $(this).find('form').setValue(options.data);
                        //}
                        toastr.error(response);
                    }
                });
            }
        }

        $(document).on("mousedown", ".modal-header", function (e) {
            if ($(e.target).hasClass("close")) return;
            mouseDragDown = true;
            moveTartet = $(this).parent();
            mouseStartPoint = { "left": e.clientX, "top": e.clientY };
            oldP = moveTartet.offset();
        });

        $(document).on("mouseup", function (e) {
            mouseDragDown = false;
            moveTartet = undefined;
            mouseStartPoint = { "left": 0, "top": 0 };
            oldP = { "left": 0, "top": 0 };
        });
        $(document).on("mousemove", function (e) {
            if (!mouseDragDown || moveTartet == undefined) return;
            var mousX = e.clientX;
            var mousY = e.clientY;
            if (mousX < 0) mousX = 0;
            if (mousY < 0) mousY = 25;
            mouseEndPoint = { "left": mousX, "top": mousY };
            var width = moveTartet.width();
            var height = moveTartet.height();
            mouseEndPoint.left = mouseEndPoint.left - (mouseStartPoint.left - oldP.left);//移动修正，更平滑
            mouseEndPoint.top = mouseEndPoint.top - (mouseStartPoint.top - oldP.top);
            moveTartet.offset(mouseEndPoint);
        });
    };
    $.modal.methods = {
        refresh: function (target, param) {
            var modal = $('body').find('.modal:last');
            modal.data()['bs.modal'].options.refresh(param);
            modal.modal('hide');
        },
        refreshbyid: function (target, id, param) {
            var modal = $('#' + id);
            modal.data()['bs.modal'].options.refresh(param);
            modal.modal('hide');
        }
    };
    $.modal.defaults = $.extend({}, $.fn.modal.defaults, {
        title: 'Modal',
        url: '',
        size: 'default',
        width: null,
        backdrop: 'static',
        data: null,
        refresh: function (param) { },
        show: function (event) { },
        hidden: function (event) { }
    });
})(jQuery);