/**
 *
 * @param {}
 *            s
 * @return {Boolean}
 */
String.prototype.endWith = function (s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length)
        return false;
    if (this.substring(this.length - s.length) == s)
        return true;
    else
        return false;
    return true;
};
/**
 *
 * @param {}
 *            s
 * @return {Boolean}
 */
String.prototype.startWith = function (s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length)
        return false;
    if (this.substr(0, s.length) == s)
        return true;
    else
        return false;
    return true;
};
/**
 *
 * @param {}
 *            s
 * @return {Boolean}
 */
String.prototype.equals = function (s) {
    if (s == this)
        return true;
    else
        return false;
};
String.prototype.toInt = function () {
    if (this == null || this == "" || this.length == 0)
        return 0;
    else
        return parseInt(this);
};
Array.prototype.contains = function (item) {
    return RegExp("\\b" + item + "\\b").test(this);
};

Array.prototype.indexOf = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val)
            return i;
    }
    return -1;
};
// 删除数组中某个具体元素的值
Array.prototype.removePro = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

function escapeHTML(s) {
    if (isBlank(s))
        return s;
    return s.replace(/&/g, '&amp;').replace(/>/g, '&gt;').replace(/</g, '&lt;').replace(/'/g, '&quot;');
}

function unescapeHTML(s) {
    if (isBlank(s))
        return s;
    return s.replaceAll('&amp;', '&').replaceAll('&gt;', ">").replaceAll('&lt;', "<").replaceAll('&quot;', '\'');
}

String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
};

/**
 *
 * @param {}
 *            str
 * @return {Boolean}
 */
function isBlank(str) {
    if (str == null || $.trim(str) == "") {
        return true;
    }
    return false;
}

/**
 *
 * @param {}
 *            str
 * @return {Boolean}
 */
function isNotBlank(str) {
    return !isBlank(str);
}

//isEqual：判断两个对象是否键值对应相等
function isEqual(a, b) {
    //如果a和b本来就全等
    if (a === b) {
        //判断是否为0和-0
        return a !== 0 || 1 / a === 1 / b;
    }
    //判断是否为null和undefined
    if (a == null || b == null) {
        return a === b;
    }
    //接下来判断a和b的数据类型
    var classNameA = toString.call(a),
        classNameB = toString.call(b);
    //如果数据类型不相等，则返回false
    if (classNameA !== classNameB) {
        return false;
    }
    //如果数据类型相等，再根据不同数据类型分别判断
    switch (classNameA) {
        case '[object RegExp]':
        case '[object String]':
            //进行字符串转换比较
            return '' + a === '' + b;
        case '[object Number]':
            //进行数字转换比较,判断是否为NaN
            if (+a !== +a) {
                return +b !== +b;
            }
            //判断是否为0或-0
            return +a === 0 ? 1 / +a === 1 / b : +a === +b;
        case '[object Date]':
        case '[object Boolean]':
            return +a === +b;
    }
    //如果是对象类型
    if (classNameA == '[object Object]') {
        //获取a和b的属性长度
        var propsA = Object.getOwnPropertyNames(a),
            propsB = Object.getOwnPropertyNames(b);
        if (propsA.length != propsB.length) {
            return false;
        }
        for (var i = 0; i < propsA.length; i++) {
            var propName = propsA[i];
            //如果对应属性对应值不相等，则返回false
            if (!isEqual(a[propName], b[propName])) {
                return false;
            }
        }
        return true;
    }
    //如果是数组类型
    if (classNameA == '[object Array]') {
        if (a.length != b.length) {
            return false;
        }
        var isequal = true;
        $.each(a, function (i, ad) {
            if (!isEqual(ad, b[i])) {
                isequal = false;
                /**
                 * 跳出循环
                 */
                return false;
            }
        })
        return isequal;
        // if (a.toString() == b.toString()) {
        //     return true;
        // }
        // return false;
    }
}

function isIOS() {
    if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {

        return true;

    }
    return false;
}

function isAndroid() {

    if (/(Android)/i.test(navigator.userAgent)) {
        return true;
    }
    return false;
}

/**
 * show model window message
 *
 * @param {}
 *            message
 */
function showMessage(options, buttons) {
    if (options.length < 1)
        return;
    var dialogWindow = null;
    if (options.object) {
        dialogWindow = options.object;
    } else {
        var message = $("<p>").html(options.message).addClass(" pt_20 pr_20 pb_20 pl_20");
        dialogWindow = $("<div>").attr("id", "dialog-message_" + (new Date()).getMilliseconds()).attr("title", options.title ? options.title : "Attention").css("display", "none")
            .append(message);
        $("div.ibm_middle").append(dialogWindow);
    }
    closeDialog = function () {
        dialogWindow.dialog("close");
    };
    $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
        _title: function (title) {
            var $title = this.options.title || '&nbsp;';
            if (("title_html" in this.options) && this.options.title_html == true)
                title.html($title);
            else
                title.text($title);
        }
    }));
    var butts = {};
    if (buttons) {
        $.each(buttons, function (i, fun) {
            var callbacks = $.Callbacks();
            if (fun.autoClose != false) {
                callbacks.add(closeDialog);
            }
            callbacks.add(fun.click);
            fun.click = callbacks.fire;
            butts[i] = fun;
        });
    }
    dialogWindow.dialog({
        modal: true,
        title_html: true,
        title: options.title,
        draggable: false,
        width: options.width ? options.width : 300,
        height: options.height ? options.height : "auto",
        close: function (event, ui) {
            if (options.callback)
                options.callback();
        },
        buttons: butts
    });
    return dialogWindow;
}

/**
 *
 */
function isArray(obj) {
    return Object.prototype.toString.call(obj) === '[object Array]';
}

debug = true;

// helper for console logging
function log(msg) {
    if (!debug)
        return;
    msg == undefined ? '[jquery.form] ' + Array.prototype.join.call(arguments, '') : msg;
    if (window.console && window.console.log) {
        window.console.log(msg);
    } else if (window.opera && window.opera.postError) {
        window.opera.postError(msg);
    }
};

// 你需要获取值的js文件名
function getScriptArgs(fileName) {
    // 获取到所有<script>对象
    var scripts = document.getElementsByTagName("script");
    var resultJson = {};
    for (var i = 0; i < scripts.length; i++) {
        var src = scripts[i].src;
        // 取得你想要的js文件名
        if (src.indexOf(fileName) !== -1) {
            // 获取js文件名后面的所有参数
            src = src.substring(src.lastIndexOf(fileName + "?") + (fileName.length + 1));
            var array = src.split("&");
            // 将参数一个一个遍历出来
            for (var j = 0; j < array.length; j++) {
                var finalObj = array[j].split("=");
                resultJson[finalObj[0]] = finalObj[1];
            }
        }
    }
    return resultJson;
};

function flashChecker() {
    var hasFlash = 0; // 是否安装了flash
    var flashVersion = 0; // flash版本
    if (document.all) {
        var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
        if (swf) {
            hasFlash = 1;
            VSwf = swf.GetVariable("$version");
            flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0]);
        }
    } else {
        if (navigator.plugins && navigator.plugins.length > 0) {
            var swf = navigator.plugins["Shockwave Flash"];
            if (swf) {
                hasFlash = 1;
                var words = swf.description.split(" ");
                for (var i = 0; i < words.length; ++i) {
                    if (isNaN(parseInt(words[i])))
                        continue;
                    flashVersion = parseInt(words[i]);
                }
            }
        }
    }
    return {
        f: hasFlash,
        v: flashVersion
    };
}

// 弹窗提示
function msgAlert(message, title, doSometing) {
    $("<button>").info({
        title: title,
        message: message,
        model: 1,
        success: doSometing
    });
}


/*
 * 顶部位置弹窗
 */

// 信息提示
function topMsgInfo(message) {
    $("<button>").info({
        message: message,
        model: 2,
        position: "top",
        css: "info"
    });
}

// 警告提示
function topMsgWarn(message) {
    $("<button>").info({
        message: message,
        model: 2,
        position: "top",
        css: "warn"
    });
}

// 错误提示
function topMsgError(message) {
    $("<button>").info({
        message: message,
        model: 2,
        position: "top",
        css: "error"
    });
}

// 成功提示
function topMsgSuccess(message) {
    $("<button>").info({
        message: message,
        model: 2,
        position: "top",
        css: "success"
    });
}


/*
 * 中间位置弹窗
 */

// 信息提示
function msgInfo(message) {
    $("<button>").info({
        message: message,
        model: 2,
        css: "info"
    });
}

// 警告提示
function msgWarn(message) {
    $("<button>").info({
        message: message,
        model: 2,
        css: "warn"
    });
}

// 错误提示
function msgError(message) {
    $("<button>").info({
        message: message,
        model: 2,
        css: "error"
    });
}

// 成功提示
function msgSuccess(message) {
    $("<button>").info({
        message: message,
        model: 2,
        css: "success"
    });
}

if (typeof jQuery != 'undefined') {
    (function ($) {

        jQuery.fn.info = function (options) {

            // 弹窗模式
            var model = "show_message_model_";

            options = jQuery.extend({
                model: "1", // 模式编号，现有1、2两种模式
                close: true, // 模式1是否启用关闭按钮
                title: "", // 模式1的标题
                message: "", // 提示信息
                css: "info", // 模式2的提示类型，info、warn、error、success四种类型
                click: false,	//是否需要点击
                position: "center", //模式2显示位置top、center两种
                success: function (data) { // 模式1点击确定后继续执行方法
                    return data;
                }
            }, options || {});

            var obj = $(this);
            obj.click(function () {

                $("#" + model + options.model).show();

                if (options.model == "1") {
                    // 模式1
                    if (options.close == false) {
                        $("#show_message_close").hide();
                    }
                    $("#show_message_title").text(options.title);
                    $("#show_message_text").text(options.message);
                    $("#showMessageButton").click();

                    $("#show_message_ok").unbind("click");
                    // 模式1点击“确定”触发事件
                    $("#show_message_ok").click(function () {
                        options.success(true);
                        $("#show_message_close").click();
                    });
                    obj.unbind("click");
                    obj.unbind("info");
                } else if (options.model = "2") {
                    // 模式2
                    $("#alert_message_text").text(options.message);
                    $(".model_2").attr("class", "model_2 alert fade in one alert_" + options.css + "_message").find("i").attr("class", "alert_" + options.css + "_icon");

                    if (options.position == "center") {
                        $(".model_2").css("top", "20%");
                    }


                    var liveTime = 2000;
                    if (options.message.length > 20)
                        liveTime = 3000;
                    else if (options.message.length > 30) {
                        liveTime = 3500;
                    } else if (options.message.length > 50) {
                        liveTime = 5000;
                    } else if (options.message.length > 80) {
                        liveTime = 7000;
                    }
                    setTimeout(function () {
                        $("#" + model + options.model).fadeOut("fast");
                        obj.unbind("click");
                        obj.unbind("info");
                    }, liveTime);
                }
            });

            if (options.click) {

            } else {
                $(this).click();
            }
        };
    })(jQuery);
} else if (typeof Zepto != 'undefined') {
    (function ($) {


    })(Zepto);
}

/**
 * 用于表单提交
 *
 * @param url
 *            提交地址
 * @param array
 *            json对象：{"key1":"value1","key2":"value2"...} key:与后台参数名相同
 */
function doForm(url, array) {
    var formredwin = document.createElement("form");
    formredwin.method = 'POST';
    document.body.appendChild(formredwin);
    formredwin.action = url;
    // 遍历穿过来的json数组
    for (var key in array) {
        var input = document.createElement("input");
        input.type = "hidden";
        input.name = key;
        input.id = key;
        input.value = array[key];
        formredwin.appendChild(input);
    }
    formredwin.submit();
    formredwin.parentNode.removeChild(formredwin);
}

/**
 * 用于表单提交(新窗口打开)
 *
 * @param url
 *            提交地址
 * @param array
 *            json对象：{"key1":"value1","key2":"value2"...} key:与后台参数名相同
 */
function doForm_blank(url, array) {
    var formredwin = document.createElement("form");
    formredwin.method = 'POST';
    formredwin.target = "_blank";
    document.body.appendChild(formredwin);
    formredwin.action = url;
    // 遍历穿过来的json数组
    for (var key in array) {
        var input = document.createElement("input");
        input.type = "hidden";
        input.name = key;
        input.id = key;
        input.value = array[key];
        formredwin.appendChild(input);
    }
    formredwin.submit();
    formredwin.parentNode.removeChild(formredwin);
}

function utf16to8(str) {
    var out, i, len, c;
    out = "";
    len = str.length;
    for(i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
        }
    }
    return out;
}

if (typeof $ != 'undefined') {
    $.wechat = {
        init: function (options) {
            var url = location.href.split('#')[0];
            options = $.extend({
                url: '/auth/wechat/jsauth',
                debug: false, //
                params: {url: url},
                jsApiList: [],
                success: function (data) { // 回调
                    return data;
                }
            }, options || {});
            $.post(options.url, options.params, function (data) {
                var appId = data.appId;
                var timestamp = data.timestamp;
                var nonceStr = data.nonceStr;
                var signature = data.signature;
                wx.config({
                    debug: options.debug, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                    appId: appId, // 必填，公众号的唯一标识
                    timestamp: timestamp, // 必填，生成签名的时间戳
                    nonceStr: nonceStr, // 必填，生成签名的随机串
                    signature: signature,// 必填，签名，见附录1
                    jsApiList: options.jsApiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                });
                options.success(data);
            })
        }
    }
    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        var properties = [];
        $.each(a, function () {
            if (this.name.indexOf(".") > 0) {
                var name = this.name.substring(0, this.name.indexOf("["));
                var inx = this.name.substring(this.name.indexOf("[") + 1, this.name.indexOf("]"));
                var rname = this.name.substring(this.name.indexOf(".") + 1, this.name.length);
                console.log(name + " " + inx + " " + rname);

                /**
                 * 如果已存在
                 */
                if (o[name]) {
                    var p = o[name][inx] || {};
                    p[rname] = this.value || '';
                    o[name][inx] = p;

                } else {
                    /**
                     * 如果不存在
                     */
                    o[name] = [];
                    var p = {};
                    p[rname] = this.value || '';
                    o[name][inx] = p;
                }


                // console.log(o[name]);
                return true;
            }

            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        })
        if (properties.length > 0) {

        }
        return o;
    };
}