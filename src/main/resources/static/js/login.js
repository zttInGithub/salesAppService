//接受传的参数
function GetInfo(info) {
	var regn = new RegExp("(^|&)" + info + "=([^&]*)(&|$)");
	var ri = window.location.search.substr(1).match(regn);
	if(ri != null) {
		return decodeURI(ri[2])
	}
}
var AppId = GetInfo("appId");
var unno = GetInfo("unno");
var userId = GetInfo("userId");
var agentName = GetInfo("agentName");
$("#agentName").html(agentName);

//验证验证码mobileNo
$("#btnMobileNo").click(function() {
	var cardPhone = $("#phoneNo").val();
	var reg = /^[1][3,4,5,7,8,9][0-9]{9}$/;
	if(cardPhone == '') {
		settimeWarn(".error_wrap");
		$(".errorp").html("请输入登录手机号");
		return false;
	} else if(!reg.test(cardPhone)) {

		settimeWarn(".error_wrap");
		$(".errorp").html("手机号有误,请核对后再次输入");
		return false;
	} else {
		var countdown = 60;

		function settime(obj) {
			if(countdown == 0) {
				obj.removeAttribute("disabled");
				obj.value = "发送验证码";
				countdown = 60;
				return;

			} else {
				obj.setAttribute("disabled", true);
				obj.value = countdown + 's';
				countdown--;
			}
			setTimeout(function() {
				settime(obj);
			}, 1000)
		}
		var obj = document.getElementById("btnMobileNo");
		obj.setAttribute("disabled", true);
		$.ajax({
			"url": serurl + "SalesAppService/app/myInfo/sendMsg",
			"type": "POST",
			"contentType": "application/json",
			"data": JSON.stringify({
				"mobile": $("#phoneNo").val(),
				"unno": unno,
				"agentName": agentName
			}),
			"dataType": "json",
			"success": function(data) {
				console.log(data);
				if(data.returnCode == '00') {
					obj.value = countdown;
					settime(obj);
					settimeWarn(".error_wrap");
					$(".errorp").html(data.returnMsg);
				} else {
					settimeWarn(".error_wrap");
					$(".errorp").html(data.returnMsg);
				}
			}

		});
	}

});
//协议
$("#agreez").click(function() {
	if($(this).hasClass('able')) {
		$(this).removeClass('able').addClass('unable');
	} else {
		$(this).removeClass('unable').addClass('able');
	}
});
$("#btnNext").click(function() {
	var cardPhone = $("#phoneNo").val();
	var reg = /^[1][3,4,5,7,8,9][0-9]{9}$/;
	if(cardPhone == '') {
		settimeWarn(".error_wrap");
		$(".errorp").html("请输入登录手机号");
		return false;
	} else if(!reg.test(cardPhone)) {
		settimeWarn(".error_wrap");
		$(".errorp").html("手机号有误,请核对后再次输入");
		return false;
	} else {

		$(".warntxt").html("");
	}
	var pwd = $("#pwd").val();
	if(pwd == '') {
		settimeWarn(".error_wrap");
		$(".errorp").html("请输入登录密码");
		return false;
	}

	var reg = new RegExp(/^[0-9]{6}$/);
	var identifyCode = $("#mobileNo").val();
	if(identifyCode == '') {
		settimeWarn(".error_wrap");
		$(".errorp").html("请输入获取的验证码");
		return false;
	} else if(!reg.test(identifyCode)) {
		settimeWarn(".error_wrap");
		$(".errorp").html("您输入的验证码有误");
		return false;
	} else {

		$(".warntxt").html("");
	}
	if(!$("#agreez").hasClass('able')) {
		settimeWarn(".error_wrap");
		$(".errorp").html("请勾选同意相关协议");
		return false
	}
	$.ajax({
		"url": serurl + "SalesAppService/app/myInfo/updateNewUnno",
		"type": "POST",
		"contentType": "application/json",
		"data": JSON.stringify({
			"mobile": $("#phoneNo").val(),
			"msg": $("#mobileNo").val(),
			"password": $.md5($("#pwd").val()),
			"userId": userId,
			"unno": unno,
			"agentName": agentName,
			"AppId": AppId
		}),
		"dataType": "json",
		"beforeSend": function(resp) {
			$(".loading").show();
		},
		"success": function(data) {
			$(".loading").hide();
			console.log(data);
			if(data.returnCode == '00') {
				// location.href = "loginOk.html";
				location.href = serurl+"SalesAppService/loginOk";
			} else {
				settimeWarn(".error_wrap");
				$(".errorp").html(data.returnMsg);
			}
		}

	});

});