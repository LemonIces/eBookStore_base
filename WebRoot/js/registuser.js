window.onload=function(){
	console.log('regist onload');
	
};

//校验用户名
function checkUsername() {
	//清空提示信息
	var errTd = document.getElementById("errTd");;
	errTd.innerHTML = "";
	
	var username = document.getElementsByName("username")[0].value;
	if(username == null || username ==undefined || username.trim()==""){
		errTd.innerHTML="用户名为空!";
		return false;
	}
	var reg = "^[a-zA-Z0-9]{5,20}$";
	if(!username.match(reg)){
		errTd.innerHTML="用户名必须为5-20个字符!";
		return false;
	}
}

//校验密码
function checkPwd() {
	//清空提示信息
	var errTd = document.getElementById("errTd");;
	errTd.innerHTML = "";
	
	var password = document.getElementsByName("password")[0].value;
	if(password == null || password ==undefined || password.trim()==""){
		errTd.innerHTML="密码为空!";
		return false;
	}
	var reg = "^[a-zA-Z0-9]{5,20}$";
	if(!password.match(reg)){
		errTd.innerHTML="密码必须为5-20个字符!";
		return false;
	}
	
}


//校验确认密码
function checkPwd1() {
	//清空提示信息
	var errTd = document.getElementById("errTd");;
	errTd.innerHTML = "";
	
	var password1 = document.getElementsByName("password1")[0].value;
	if(password1 == null || password1 ==undefined || password1.trim()==""){
		errTd.innerHTML="确认密码为空!";
		return false;
	}
	if(password != password1){
		errTd.innerHTML="两次密码不一致!";
		return false;
	}
	var reg = "^[a-zA-Z0-9]{5,20}$";
	if(!password1.match(reg)){
		errTd.innerHTML="确认密码必须为5-20个字符!";
		return false;
	}
	
}

//校验邮箱密码
function checkEamil() {
	//清空提示信息
	var errTd = document.getElementById("errTd");;
	errTd.innerHTML = "";
	
	var email = document.getElementsByName("email")[0].value;
	if(email == null || email ==undefined || email.trim()==""){
		errTd.innerHTML="邮箱为空!";
		return false;
	}
	
	else if(email != null && email !=undefined && !email.match("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$")){
		errTd.innerHTML="邮箱格式错误!";
		return false;
	}
	
}

function checkForm(){
	//清空提示信息
	var errTd = document.getElementById("errTd");;
	errTd.innerHTML = "";
	
	var username = document.getElementsByName("username")[0].value;
	if(username == null || username ==undefined || username.trim()==""){
		errTd.innerHTML="用户名为空!";
		return false;
	}
	var reg = "^[a-zA-Z0-9]{5,20}$";
	if(!username.match(reg)){
		errTd.innerHTML="用户名必须为5-20个字符!";
		return false;
	}
	var password = document.getElementsByName("password")[0].value;
	if(password == null || password ==undefined || password.trim()==""){
		errTd.innerHTML="密码为空!";
		return false;
	}
	reg = "^[a-zA-Z0-9]{5,20}$";
	if(!password.match(reg)){
		errTd.innerHTML="密码必须为5-20个字符!";
		return false;
	}
	var password1 = document.getElementsByName("password1")[0].value;
	if(password1 == null || password1 ==undefined || password1.trim()==""){
		errTd.innerHTML="确认密码为空!";
		return false;
	}
	if(password != password1){
		errTd.innerHTML="两次密码不一致!";
		return false;
	}
	reg = "^[a-zA-Z0-9]{5,20}$";
	if(!password1.match(reg)){
		errTd.innerHTML="确认密码必须为5-20个字符!";
		return false;
	}
	var email = document.getElementsByName("email")[0].value;
	if(email == null || email ==undefined || email.trim()==""){
		errTd.innerHTML="邮箱为空!";
		return false;
	}
	
	else if(email != null && email !=undefined && !email.match("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$")){
		errTd.innerHTML="邮箱格式错误!";
		return false;
	}
	
	var checkcode = document.getElementsByName("check_code")[0].value;
	if(checkcode == null || checkcode ==undefined || checkcode.trim()==""){
		errTd.innerHTML="验证码为空!";
		return false;
	}
	if(!checkcode.match(reg)){
		errTd.innerHTML="验证码必须为4个字符!";
		return false;
	}
	return true;
}


function changeCode(){
	console.log("changeCode....");
	var code_img = document.getElementById('code_img');
	code_img.src="${pageContext.request.contextPath}/testAction_verifyCodeServlet?a="+new Date().getTime();;
	
}



function sendMsg(obj) {
	console.log(obj);

	//清空提示信息
	var errTd = document.getElementById("errTd");;
	errTd.innerHTML = "";	
	
	
	var flag = true;
	var username = document.getElementsByName("username")[0].value;
	if(username == null || username ==undefined || username.trim()==""){
		errTd.innerHTML="用户名不能为空!";
		flag = false;
	}
	var reg = "^[a-zA-Z0-9]{5,20}$";
	if(!username.match(reg)){
		errTd.innerHTML="用户名必须为5-20个字符!";
		return false;
	}

	var email = document.getElementsByName("email")[0].value;
	if(email == null || email ==undefined || email.trim()==""){
		errTd.innerHTML="邮箱不能为空!";
		return false;
	}
	else if(email != null && email !=undefined && !email.match("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$")){
		errTd.innerHTML="邮箱格式不正确!";
		return false;
	}
	obj.disabled = "disabled";
	obj.value="(60s)";
	clocks(60);
	
	//发送消息
	$.post("${pageContext.request.contextPath}/userAction_sendRegistCheckCode",
				{
					"username":username,"email":email
				}, function(data) {
					console.log(data['state']);
					if(data["state"] == "true"){
						alert("验证码发送成功！");
					}else{
						console.log(data['error']);
						alert("验证码发送失败！"+ data["error"]);
						errTd.innerHTML= data['error'];
					}
					
					
			}, "json");
	}



function clocks(sed) {
	if(tId!=null && tId != undefined){
		clearInterval(tId);
	}
	var count = sed;
	var tId= setInterval(()=>{
		//计算秒数
		count--;
		document.getElementById("send_btn").value="("+count+"s)";
		if(count <= 0){
			//停止计时
			clearInterval(tId);
			tId = null;
			document.getElementById("send_btn").disabled = "";
			document.getElementById("send_btn").value = "重新发送";
		}
	}, 1000);
}


