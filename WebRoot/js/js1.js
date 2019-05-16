var host = "http://localhost:8080/eBookStore/";
var currentPage1 = 1;
var maxPage1 = 2;
var pageSize1 = 12;
var loc1 = "${pageContext.request.contextPath }/testAction_changeNewsBookPage";
var page2categoty =new Array( "经典著作", "社会科学", "自然哲学", "历史地理", "中国文学 ", "美术雕塑", "摄影影视", "书法篆刻 " 
		
		, "医药卫生 ", "建筑工程", "生活休闲", "少儿读物 ");



var ad_index = 0;
var ad_max = 5;
var adClock = null;
window.onload=function(){
	console.log('onload')
	load_ad_btn_value();
	changeAd(0);
	var ad = document.getElementById('ad');
	adClock = setInterval(changeAdImg,5000);
	
	
	show1();
	show2();
	calProductCountAndMoney();
	
	//加载时间
	init_time();
	

};

//加载修改广告的只
function load_ad_btn_value() {
	var ad_btns = document.getElementsByName('change_ad_btn');
	for (var i = 0; i < ad_btns.length; i++) {
		ad_btns[i].value=i+1;
	}
}


//选择广告按钮点击是
function changeAdBtn(obj){
	if(adClock!=null){
		clearInterval(adClock);
	}
	 var vls = obj.value *1 - 1;
	 
	 changeAd(vls);
	 
	 adClock = setInterval(changeAdImg,5000);

}


//鼠标一上去
function change_ad_btn_over(obj) {
	if(adClock!=null){
		clearInterval(adClock);
	}
	 var vls = obj.value *1 - 1;
	 
	 changeAd(vls);
	 
}
//鼠标移出广告时
function change_ad_btn_out(obj) {
	
	if(adClock!=null){
		clearInterval(adClock);
	}
	 var vls = obj.value *1 - 1;
	 
	 changeAd(vls);
	 
	 adClock = setInterval(changeAdImg,5000);
}


//设置广告选择按钮状态
function ad_btn_statu(index) {
	var btns = document.getElementsByName('change_ad_btn');
	for (var i = 0; i < btns.length; i++) {
		btns[i].className="change_ad_btn";
	}
	btns[index].className +=" btn_ad_active";
	
}


//系统计时改变广告
function changeAdImg(){
	ad_index++;
	if(ad_index >= ad_max){
		ad_index = 0;
	}
	
	ad_btn_statu(ad_index);
	var ad_ids = document.getElementsByName('ad_id');
	ad_max = ad_ids.length;
	for (var i = 0; i < ad_ids.length; i++) {
		var ad_div = document.getElementById('ad_item_div_'+ad_ids[i].value);
		ad_div.style.display ="none";
	}
	
	var ad_div = document.getElementById('ad_item_div_'+ad_ids[ad_index].value);
	ad_div.style.display ="block";
}

//通过索引直接改变广告
function changeAd(index) {
	
	if(index <= 0){
		index = 0;
	}
	if(index>=ad_max-1){
		index = ad_max-1;
	}
	
	ad_index = index;
	ad_btn_statu(ad_index);
	var ad_ids = document.getElementsByName('ad_id');
	ad_max = ad_ids.length;
	for (var i = 0; i < ad_ids.length; i++) {
		var ad_div = document.getElementById('ad_item_div_'+ad_ids[i].value);
		ad_div.style.display ="none";
	}
	var ad_div = document.getElementById('ad_item_div_'+ad_ids[index].value);
	ad_div.style.display ="block";
}



function listPage1(param){
	if(param == undefined || param == ""){
		param = "";
	}
//	window.location.href=host+"bookAction_list.action?book_name="+param+"&currentPage="+currentPage1+"&pageSize="+pageSize1;
}
//list1--------------------------------------------
function prePage1(){
	currentPage1--;	
	//检查是否越界
	if(currentPage1 < 1){
		currentPage1 = maxPage1;
	}
	show1();
	console.log(currentPage1);
	
}

function nextPage1(){	
	
	currentPage1++;
	//检查是否越界
	if(currentPage1 > maxPage1){
		currentPage1 = 1;
	}
	show1();
	console.log(currentPage1);
}


function getDataPage1(loc,pageSize,currentPage) {
	$.post(loc,{"pageSize":pageSize,"currentPage":currentPage},function(data){
		console.log(data);
	},"json");
}


function show1() {

	
	console.log("show1");

	var lis = document.getElementsByClassName("lis");
	for (var i = 0; i < lis.length; i++) {
			if( i < currentPage1*pageSize1 && i >= (currentPage1-1)*pageSize1)
			{
				lis[i].style.display="block";
			}else{
				lis[i].style.display="none";
			}
	}

	
}

//--------------------------------------------list1



//list2-------------------------------------------------、
var currentPage2 = 1;
var pageSize2 = 24;
var maxPage2 = 8;
function nextPage2(){
	currentPage2++;
	//检查是否越界
	if(currentPage2 > maxPage2){
		currentPage2 = 1;
	}
	show2();
	console.log(currentPage2);
}

function prePage2(){
	currentPage2--;
	//检查是否越界
	if(currentPage2 < 1){
		currentPage2 = maxPage2;
	}
	show2();
	console.log(currentPage2);
}


function show2index(index,obj) {
	
	var objs = document.getElementsByClassName('active1');
	for (var i = 0; i < objs.length; i++) {
		objs[i].className="";
	}
	currentPage2 = index*1;
	show2();
	obj.className="active1";
}

function show2() {

	
	console.log("show2    "+currentPage2);
	var lis2 = document.getElementsByClassName("list2s");
	for (var i = 0; i < lis2.length; i++) {
			if(currentPage2 == (i+1))
			{
				lis2[i].style.display="block";
			}else{
				lis2[i].style.display="none";
			}
	}
}


//------------------------------------------------list2


