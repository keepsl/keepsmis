var Util = Util || {};
Util.pageSize = function() {
    var e = document.documentElement
      , t = ["clientWidth", "clientHeight", "scrollWidth", "scrollHeight"]
      , n = {};
    for (var a in t)
        n[t[a]] = e[t[a]];
    return n.scrollLeft = document.body.scrollLeft || document.documentElement.scrollLeft,
    n.scrollTop = document.body.scrollTop || document.documentElement.scrollTop,
    n
}
,
Util.lazyLoad = function(e) {
    var t = $("." + e);
    t.each(function() {
        var e = $(this)
          , t = e.attr("data-original");
        $("<img />").one("load", function() {
            e.is("img") ? e.attr("src", t) : e.css("background-image", "url(" + t + ")"),
            setTimeout(function() {
                e.css("opacity", "1")
            }, 15)
        }).one("error", function() {
            e.css("opacity", "1")
        }).attr("src", t)
    })
}
,
Util.getQueryString = function(e) {
    var t = {};
    if (e = e.split("?"),
    e.length > 1) {
        e = e[1],
        e = e.split("&");
        for (var n = 0, a = e.length; n < a; n++) {
            var i = e[n].split("=");
            i.length > 1 && (t["" + i[0]] = i[1])
        }
    }
    return t
}
,
Util.timeCount = function(e, t, n) {
    e = $(e);
    var a = 1*Math.floor((new Date(e.data("endtime"))).getTime() / 1e3)
      , i = 1 * Math.floor((new Date).getTime() / 1e3);
    if (i > a)
        e.html(t);
    else {
        var l = a - i
          , o = Math.floor(l / 86400)
          , c = Math.floor((l - 60 * o * 60 * 24) / 3600)
          , s = Math.floor((l - 60 * o * 60 * 24 - 60 * c * 60) / 60)
          , d = l - 60 * o * 60 * 24 - 60 * c * 60 - 60 * s
          , r = "还剩&nbsp;" + (o > 0 ? "<em>" + o + "</em>天" : "") + (c > 0 ? "<em>" + c + "</em>时" : "") + (s > 0 ? "<em>" + s + "</em>分" : "") + (d >= 0 ? "<em>" + d + "</em>秒" : "") + "&nbsp;结束";
        n && 2 == n && o <= 0 && (r = '<span style="color:#FF2220;font-weight: bold;">即将失效：&nbsp;' + (o > 0 ? "<em>" + o + "</em>天" : "") + (c > 0 ? "<em>" + c + "</em>时" : "") + (s > 0 ? "<em>" + s + "</em>分" : "") + (d >= 0 ? "<em>" + d + "</em>秒" : "") + "</span>"),
        e.html(r)
    }
}
,
Util.sideGenderSwitch = function() {
    var e = $.cookie("gender")
      , t = '<div class="side-gender-switch">';
    t += '<div class="show-switch"></div>',
    t += '<div class="main">',
    t += '<div class="gender-area">',
    t += '<div data-gender="0" class="gender female' + (1 == e ? "" : " active") + '">',
    t += '<div class="pic">',
    t += '<div class="mask"><i></i></div>',
    t += "</div>",
    t += "<p>女生</p>",
    t += "</div>",
    t += '<div data-gender="1" class="gender male' + (1 == e ? " active" : "") + '">',
    t += '<div class="pic">',
    t += '<div class="mask"><i></i></div>',
    t += "</div>",
    t += "<p>男生</p>",
    t += "</div>",
    t += "</div>",
    t += '<div class="qrcode-area">',
    t += '<div class="qrcode"></div>',
    t += "<p>扫一扫领取<br>更多专属一折券</p>",
    t += "</div>",
    t += '<img style="display: none;" src="http://7xlxny.com1.z0.glb.clouddn.com/zhekou/web/gender_switch_expand.png"/>',
    t += "</div>",
    t = $(t),
    t.find(".show-switch").on("click", function() {
        t.toggleClass("active")
    });
    var n = Util.getQueryString(window.location.href);
    t.find(".gender").on("click", function() {
        var e = $(this);
        if (!e.hasClass("active")) {
            var t = e.data("gender");
            ga("send", "event", "gender_switch", "click", "gender_switch_to_" + ("1" == $.cookie("gender") ? "female" : "male")),
            $.cookie("gender", "" + t, {
                path: "/"
            }),
            window.location.href = "/" + (n.channel ? "?channel=" + n.channel : "")
        }
    }),
    $("body").append(t)
}
,
Util.scrollToTop = function(e) {
    var t = $("body,html");
    e < 20 ? t.scrollTop(0) : t.stop(!0, !0).animate({
        scrollTop: 0
    }, e)
}
,
Util.sideFixedMenu = function(e, t) {
    if (e) {
        t || (t = 2e3);
        var n = $(document)
          , a = ($("body"),
        e.width())
          , i = Math.floor(a / 2) + 40
          , l = "";
        l += '<div class="side-fixed-menu" style="margin-left: ' + i + 'px;">',
        l += '<div class="menu-item" id="backToTop" style="display: none;">',
        l += '<i class="back-to-top"></i>',
        l += "<p>返回顶部</p>",
        l += "</div>",
        l += '<a target="_blank" href="/history/' + (globalChannel ? "?channel=" + globalChannel : "") + '"><div class="menu-item" id="toHis">',
        l += '<i class="history"></i>',
        l += "<p>浏览记录</p>",
        l += "</div></a>",
        l += "</div>";
        var o = $(l);
        o.find("#backToTop").on("click", function() {
            Util.scrollToTop(100)
        }),
        o.appendTo($("body")),
        $(window).on("scroll", function() {
            var e = n.scrollTop();
            e > t ? o.find("#backToTop").fadeIn(100) : o.find("#backToTop").fadeOut(100)
        })
    }
}
,
Util.zkItemTimeCount = function(e) {
    var t = null;
    $(".zk-item").unbind("mouseenter").unbind("mouseleave"),
    $(".zk-item").on("mouseenter", function() {
        clearInterval(t),
        t = null;
        var n = $(this).find(".time-count");
        Util.timeCount(n, "优惠券已过期", e),
        t = setInterval(function() {
            Util.timeCount(n, "优惠券已过期", e)
        }, 1e3)
    }).on("mouseleave", function() {
        clearInterval(t),
        t = null
    })
}
,
Util.createCouponList = function(e, t, n, a) {
    a = a || "未知";
    for (var i = "<div>", l = 0, o = e.length; l < o; l++) {
        var c = e[l]
          , s = ""
          , d = "";
        switch (1 * c.platform_id) {
        case 1:
            s = "淘宝",
            d = "http://7xlxny.com1.z0.glb.clouddn.com/zhekou/web/platform_taobao.png";
            break;
        case 2:
            s = "天猫",
            d = "http://7xlxny.com1.z0.glb.clouddn.com/zhekou/web/platform_tmall.png"
        }
        i += '<div class="zk-item">',
        i += '<div class="img-area">',
        i += '<a target="_blank" href="/zk/' + c.coupon_id + "/" + (n && 27 != n ? "?channel=" + n : "") + '">',
        i += '<div data-ga-event="商品_右上角领券:点击:' + a + '" class="lq">',
        i += '<div class="lq-t">',
        i += '<p class="lq-t-d1">领优惠券</p>',
        i += '<p class="lq-t-d2">省<span>' + c.gap_price + "</span>元</p>",
        i += "</div>",
        i += '<div class="lq-b"></div>',
        i += "</div>",
        i += '<div class="bottom-info">',
        i += '<p data-endtime="' + c.coupon_end_time + '" class="time-count"></p>',
        i += "</div>",
        i += '<img data-ga-event="商品_图片:点击:' + a + '" class="lazy new" data-original="' + c.thumbnail_pic + '" alt="' + c.title + '">',
        i += "</a>",
        i += "</div>",
        i += '<p class="title-area elli">',
        1 == c.post_free && (i += '<span class="post-free">包邮</span>'),
        i += c.title + "</p>",
        i += '<div class="raw-price-area">现价：&yen;' + c.raw_price + '<p class="sold">' + c.subtitle + "</p></div>",
        i += '<div class="info">',
        i += '<div class="price-area">',
        i += '<span class="price">&yen;<em class="number-font">' + c.coupon_price.toString().split(".")[0] + "</em>",
        i += '<em class="decimal">' + (c.coupon_price.toString().split(".").length > 1 ? "." + c.coupon_price.toString().split(".")[1] : "") + "</em><i></i></span>",
        i += "</div>",
        i += '<div class="buy-area">',
        i += '<a data-ga-event="商品_立即领券:点击:' + a + '" rel="nofollow" target="_blank" href="' + c.url + '">',
        i += '<span class="coupon-amount">去' + s + "</span>",
        i += '<span class="btn-title">火速领券</span>',
        i += "</a>",
        i += "</div>",
        i += '<div class="platform-area"><img src="' + d + '">' + s + "</div>",
        i += "</div>",
        i += "</div>"
    }
    i += "</div>",
    i = $(i),
    i.find("[data-ga-event]").on("click", function() {
        var e = $(this)
          , t = e.attr("data-ga-event")
          , n = t.split(":");
        "undefined" != typeof ga && n.length >= 3 && ga("send", "event", n[0], n[1], n[2])
    }),
    t.append(i),
    Util.lazyLoad("lazy.new"),
    $(".lazy.new").removeClass("new"),
    Util.zkItemTimeCount()
}
,
Util.getK9CouponList = function(e, t, n, a, i) {
    function l() {
        s && $.ajax({
            url: "/getK9CouponList",
            type: "GET",
            dataType: "json",
            data: {
                type: a,
                channel: n,
                page: o,
                pagesize: c
            },
            beforeSend: function() {
                s = 0,
                t.html("努力加载中...")
            },
            success: function(a) {
                1 == a.code && (Util.createCouponList(a.data, e, n, i),
                a.data.length >= c ? (o++,
                s = 1,
                t.html("查看更多优惠券")) : t.html("没有更多了"))
            }
        })
    }
    var o = 1
      , c = 20
      , s = 1;
    $(document).on("scroll", function() {
        if (o % 4 > 0) {
            var e = $(document).scrollTop()
              , t = Util.pageSize().scrollHeight
              , n = Util.pageSize().clientHeight;
            e + n >= t - 800 && l()
        }
    }),
    t.on("click", function() {
        l()
    })
}
,
Util.getBottomOperElements = function(e) {
    function t(t) {
        for (var a = '<div class="footer-element-area">', i = 0, l = t.length; i < l; i++) {
            var o = t[i].extend;
            n.channel && !o.match(/channel=/) && (o += o.match(/\?/) ? "&channel=" + n.channel : "?channel=" + n.channel),
            a += '<div data-i="' + (i + 1) + '" class="footer-element-item">',
            a += '<a target="_blank" href="' + o + '"><img src="' + t[i].pic + '"></a>',
            a += "</div>"
        }
        a += "</div>",
        a = $(a),
        a.find(".footer-element-item").on("click", function() {
            ga("send", "event", "底部运营位", "点击", "底部运营位_" + $(this).data("i"))
        }),
        e.after(a)
    }
    $.ajax({
        url: "/getOperElements",
        type: "GET",
        dataType: "json",
        data: {},
        beforeSend: function() {},
        success: function(e) {
            1 == e.code && t(e.data.footer_element)
        }
    });
    var n = Util.getQueryString(window.location.href)
}
,
$(function() {
    Util.lazyLoad("lazy"),
    Util.zkItemTimeCount(),
    Util.getBottomOperElements($(".more-coupon")),
    $("[data-ga-event]").click(function() {
        var e = $(this)
          , t = e.attr("data-ga-event")
          , n = t.split(":");
        "undefined" != typeof ga && n.length >= 3 && ga("send", "event", n[0], n[1], n[2])
    })
});
