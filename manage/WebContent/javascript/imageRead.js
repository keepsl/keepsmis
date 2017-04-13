function previewImage(obj) {
    // var allowExtention = ".jpg,.bmp,.gif,.png";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;
    var extention = obj.file.value.substring(obj.file.value.lastIndexOf(".") + 1).toLowerCase();
    var browserVersion = window.navigator.userAgent.toUpperCase();
    if (obj.regex.indexOf(extention) > -1) {
        if (obj.file.files) {//HTML5实现预览，兼容chrome等
            if (window.FileReader) {
                google(obj);
            }
            else if (browserVersion.indexOf("SAFARI") > -1) {
                getele(obj.textId).val(obj.file.value);
               // alert("苹果浏览器暂不支持在线预览功能");
            }
        } else if (browserVersion.indexOf("MSIE") > -1) {
            if (browserVersion.indexOf("MSIE 6") > -1) {//ie6
                IE6(obj);
            } else {//ie[7-9]
                IE7_9(obj, browserVersion);
            }
        } else if (browserVersion.indexOf("FIREFOX") > -1) {//firefox
            firefox(obj, browserVersion);
        } else {
            getele(obj.imgId).attr("src", fileObj.value);
        }
    } else {
       /* alert("仅支持" + obj.regex + "为后缀名的文件!");
        document.getElementById(obj.imgId).src=""; 
        obj.file.value = "";//清空选中文件
        if (browserVersion.indexOf("MSIE") > -1) {
            obj.file.select();
            document.selection.clear();
        }
        obj.file.outerHTML = obj.file.outerHTML;*/
        resetFile(obj.file,obj.imgId);
    }
}

function resetFile(file,id){
	document.getElementById(id).src="../skins/template/img/01.png"; 
    file.value = "";//清空选中文件
    var browserVersion = window.navigator.userAgent.toUpperCase();
    if (browserVersion.indexOf("MSIE") > -1) {
        file.select();
        document.selection.clear();
    }
    file.outerHTML = file.outerHTML;
}
//HTML5实现预览，兼容chrome,google等
function google(obj) {
    var reader = new FileReader();
    reader.onload = function (e) {
        if (obj && obj.width && obj.height) {
            var image = new Image();
            image.src = e.target.result;
            //判断上传图片分辨率 是否和需求分辨率一致
            /*if (image.width == obj.width && image.height == obj.height) {
                getele(obj.imgId).attr("src", e.target.result).show();
                //判断是否上传框的值是否需要填充到其它文本中
                setTextValue(obj, e.target.result);
            } else {
                obj.file.value = "";
                alert("图片分辨率不符合,当前图片分辨率为:" + image.width + "*" + image.height);
            }*/
            getele(obj.imgId).attr("src", e.target.result).show();
            //判断是否上传框的值是否需要填充到其它文本中
            setTextValue(obj, e.target.result);
        } else {
            getele(obj.imgId).attr("src", e.target.result).show();
            setTextValue(obj, e.target.result);
        }

    };
    reader.readAsDataURL(obj.file.files[0]);
}

function IE6(obj) {
    if (obj && obj.width && obj.height) {
        var image = new Image();
        image.src = obj.file.value;
        //判断上传图片分辨率 是否和需求分辨率一致
        /*if (image.width == obj.width && image.height == obj.height) {
            getele(obj.imgId).css("width", _obj.width).css("height", obj.height).attr("src", obj.file.value).show();
            //判断是否上传框的值是否需要填充到其它文本中
            setTextValue(obj, obj.file.value);
        } else {
            obj.file.value = "";
            alert("图片分辨率不符合,当前图片分辨率为:" + image.width + "*" + image.height);
        }*/
        getele(obj.imgId).css("width", _obj.width).css("height", obj.height).attr("src", obj.file.value).show();
        //判断是否上传框的值是否需要填充到其它文本中
        setTextValue(obj, obj.file.value);
    } else {
        getele(obj.imgId).attr("src", obj.file.value).show();
        setTextValue(obj, obj.file.value);
    }

}

function IE7_9(obj, browserVersion) {
    obj.file.select();
	window.parent.document.body.focus();
    if (browserVersion.indexOf("MSIE 9") > -1)
    	window.parent.document.body.focus();
        obj.file.blur();//不加上document.selection.createRange().text在ie9会拒绝访问
    var newPreview = document.getElementById(obj.divId + "New");
    if (newPreview == null) {
        newPreview = document.createElement("div");
        newPreview.setAttribute("id", obj.divId + "New");
        //设置图片显示边框
        //  newPreview.style.border = "solid 1px #d2e2e2";
    }
    var imagePath = document.selection.createRange().text;
    newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + imagePath + "')";
    newPreview.style.border="1px solid olive";
    newPreview.style.marginBottom="10";
    //newPreview.style.marginTop="10";
    //获取图片像素并设置显示预览图大小
    var img = getele(obj.imgId)[0];
    img.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=)";
    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = imagePath;
    var _width = $(img).width();
    var _height = $(img).height();
    if (obj.width && obj.height) {
    	newPreview.style.width = obj.width + "px";
        newPreview.style.height = obj.height + "px";
        if (obj.width == _width && obj.height == _height) {
            var tempDivPreview = document.getElementById(obj.divId);
            tempDivPreview.parentNode.insertBefore(newPreview, tempDivPreview);
            tempDivPreview.style.display = "none";
            setTextValue(obj, imagePath);
        } else {
        	var tempDivPreview = document.getElementById(obj.divId);
            tempDivPreview.parentNode.insertBefore(newPreview, tempDivPreview);
            tempDivPreview.style.display = "none";
            setTextValue(obj, imagePath);
            /*obj.file.value = "";
            obj.file.select();
            document.selection.clear()
            alert("图片分辨率不符合,当前图片分辨率为:" + _width + "*" + _height);*/
        }
    } else {
    	newPreview.style.width = _width + "px";
        newPreview.style.height = _height + "px";
        var tempDivPreview = document.getElementById(obj.divId);
        tempDivPreview.parentNode.insertBefore(newPreview, tempDivPreview);
        tempDivPreview.style.display = "none";
        setTextValue(obj, imagePath);
    }
}
//火狐浏览器
function firefox(obj, browserVersion) {
    var firefoxVersion = parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);
    if (firefoxVersion < 7) {//firefox7以下版本
        if (obj.width && obj.height) {
            var image = new Image();
            image.src = obj.file.files[0].getAsDataURL();
            var _width = image.width;
            var _height = image.height;
            getele(obj.imgId).css("width", obj.width).css("height", obj.height).attr("src", obj.file.files[0].getAsDataURL()).show();
            setTextValue(obj, obj.file.files[0].getAsDataURL());
        }
    } else {//firefox7.0+
        if (obj.width && obj.height) {
            var image = new Image();
            image.src = obj.file.files[0].getAsDataURL();
            var _width = image.width;
            var _height = image.height;
            getele(obj.imgId).css("width", obj.width).css("height", obj.height).attr("src", window.URL.createObjectURL(obj.file.files[0])).show();
            setTextValue(obj, window.URL.createObjectURL(obj.file.files[0]));
            /*
            if (obj.width == _width && obj.height == _height) {
                getele(obj.imgId).css("width", _obj.width).css("height", obj.height).attr("src", window.URL.createObjectURL(obj.file.files[0])).show();
                setTextValue(obj, window.URL.createObjectURL(obj.file.files[0]));
            } else {
                obj.file.value = "";
                alert("图片分辨率不符合,当前图片分辨率为:" + _width + "*" + _height);
            }*/
        }
    }
}

//方便Jquery获取元素
function getele(id) {
    return $("#" + id);
}

//设置显示图片路径文本
function setTextValue(obj, val) {
    if (obj.textId) {
        getele(obj.textId).val(val);
    }
}
//obj 对象参数
function lookImage() {
    var file;  //上传文本框
    var textId; //显示本地路径的文本框ID
    var imgId;//图片ID
    var divId;//图片外层DIV  ID
    //验证分辨率
    var width;//宽
    var height;//高
    var regex;//图片后缀  .png,.jpg 等
}