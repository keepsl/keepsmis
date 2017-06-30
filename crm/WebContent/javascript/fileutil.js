function clearfile(file){
	file.value = "";//清空选中文件
 	var browserVersion = window.navigator.userAgent.toUpperCase();
    if (browserVersion.indexOf("MSIE") > -1) {
        obj.file.select();
        document.selection.clear();
    }
    file.outerHTML = file.outerHTML;
}
function checkFileName(file,suf){
	var filename=file.value;
	var strRegex = "(." + suf.split(',').join('|.') + ")$";
	var re=new RegExp(strRegex);
	if (re.test(filename.toLowerCase())){
		return true;
	}else{
	 	file.value = "";//清空选中文件
	 	var browserVersion = window.navigator.userAgent.toUpperCase();
        if (browserVersion.indexOf("MSIE") > -1) {
            obj.file.select();
            document.selection.clear();
        }
        file.outerHTML = file.outerHTML;
	    return false;
	}
}