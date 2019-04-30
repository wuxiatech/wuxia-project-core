//左边标题当前页时的样式,左边列的id都是"main_子页面的body的id
$(function () {
    var id = "main_" + $("body").attr("id");
    var liMian = $("#" + id);
    liMian.parent().parent().attr("class", "active open");
    liMian.attr("class", "active");

    $("#main-container").on("click", "a[data-href],button[data-href]", function () {
        if ($(this).data("alertshow") == false) {
            return;
        }
        var aClick = this;

        if ($(this).data("alert")) {
            bootbox.dialog({
                message: "<span class='bigger-110'>" + $(this).data("alert") + "</span>",
                buttons:
                    {
                        "确定":
                            {
                                "label": "<i class='icon-ok'></i> 确定!",
                                "className": "btn-sm btn-danger",
                                "callback": function () {
                                    if ($(aClick).data("async")) {
                                        $.ajax({
                                            url: $(aClick).data("href"),
                                            success: function () {
                                                bootbox.alert("成功");
                                                if ($(aClick).data("callback")) {
                                                    eval($(aClick).data("callback"));
                                                }
                                            },error:function (resp) {
                                                bootbox.alert(resp.responseText);
                                            }
                                        });
                                    } else {
                                        location.href = $(aClick).data("href");
                                    }
                                }
                            },
                        "取消":
                            {
                                "label": "取消!",
                                "className": "btn-sm",
                                "callback": function () {

                                }
                            }

                    }
            });
        } else if ($(this).data("confirm")) {
            bootbox.confirm($(this).data("confirm"), function (result) {
                if (result)
                    location.href = $(aClick).data("href");
            })
        }else{
            location.href = $(aClick).data("href");
        }

    })
})