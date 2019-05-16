
/**
 * 确认后跳转到loc网址
 * @param loc 网址
 * @param title 标题
 * @returns
 */
function confirm_href(loc,title) {
	console.log("confirm_href....."+loc);
	if(confirm(title)){
		console.log(loc);
		window.location.href=loc;
	}
}
/**
 * 点击确认后返回true
 * @param title 标题
 * @returns
 */
function confirm_btn_tf(title) {
	if(confirm(title)){
		return true;
	}
	return false;
}

/**
 * 返回
 * @returns
 */
function back_btn() {
	window.history.back(); 
}

/**
 * 返回
 * @param title 标题
 * @returns
 */
function confirm_back_btn() {
	if(confirm("确定要返回吗？")){
		window.history.back(); 
	}
}