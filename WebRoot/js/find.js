var host = "";
function search_book(mode,category){
	console.log("search.......");
	var search_text = "";
	var search_category_id = 0;
	var page_size = 20;
	var page = 1;
	// 只用书名搜索
	if (mode == 0) {
		var search_text_in = document.getElementById("search_text");
		search_text = search_text_in.value;
	}
	// 只用标签加名字搜索
	if (mode == 1) {
		search_category_id = category;
		var search_msg_temp_in = document.getElementById("search_msg_temp");
		search_text = search_msg_temp_in.value;
		
	}
	// 使用书名和标签搜索
	if (mode == 2) {
		var search_msg_temp_in = document.getElementById("search_msg_temp");
		var search_category1_temp_in = document
				.getElementById("search_category1_temp");

		search_text = search_msg_temp_in.value;
		search_category_id = search_category1_temp_in.value;
	}
	if (mode == 3) {
		search_category_id = category;
		search_text = "";
	}
	
	
	window.location.href = "${pageContext.request.contextPath }/bookAction_findBookByNameAndCategory2.action?statu=1&page="
			+ page
			+ "&page_size="
			+ page_size
			+ "&search_text="
			+ search_text
			+ "&search_category_id=" + search_category_id;

}


//输入页码改变
function check_page_change() {
	var page = document.getElementById("page").value*1;
	var total_page = document.getElementById("total_page").value*1;
	
	if(page < 1){
		page = 1;
		document.getElementById("page").value = page;
		return;
	}
	if(page > total_page){
		page = total_page;
		document.getElementById("page").value = page;
		return;
	}
	
}

//页面改变
function change_page(mode){
	console.log("change_page.....");
	var search_text = "";
	var search_category1 = "";
	
	var page_size = document.getElementById("page_size").value*1;
	var total_page = document.getElementById("total_page").value*1;
	var page = document.getElementById("page").value*1;
	
	if(mode == 0){
	}else if(mode == 1){
		page--;
		if(page < 1){
			return;
		}
		
	}else if(mode == 2){
		page++;
		if(page > total_page){
			return;
		}
	}

	var search_msg_temp_in = document.getElementById("search_msg_temp");
	var search_category1_temp_in = document
			.getElementById("search_category1_temp");

	search_text = search_msg_temp_in.value;
	search_category1 = search_category1_temp_in.value;
	
	
	window.location.href = "${pageContext.request.contextPath }/bookAction_findBookByNameAndCategory2.action?statu=1&page="
		+ page
		+ "&page_size="
		+ page_size
		+ "&search_text="
		+ search_text
		+ "&search_category1=" + search_category1;
}
