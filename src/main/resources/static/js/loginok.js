function comfundown() {
			var is_weixin = (function() { //判断微信UA
				var ua = navigator.userAgent.toLowerCase();
				if(ua.match(/MicroMessenger/i) == "micromessenger") {
					return true;
				} else {
					return false;
				}
			})();
			var is_qq = (function() { //判断微信UA
				var ua = navigator.userAgent.toLowerCase();
				if(ua.match(/QQ/i) == "qq") {
					return true;
				} else {
					return false;
				}
			})();
			var is_qqbrowser = (function() { //判断QQ浏览器
				var ua = navigator.userAgent.toLowerCase();
				if(ua.match(/mqqbrowser/i) == "mqqbrowser") {
					return true;
				} else {
					return false;
				}
			})();
			var tip = document.getElementById('weixin-tip');
			var u = navigator.userAgent,
				app = navigator.appVersion;
			var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
			var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

			if(isiOS) { //调用ios的链接
				//alert('ios');
				if(is_weixin || is_qq) {
					//alert('ios');
					//tip.style.height = winHeight + 'px'; //兼容IOS弹窗整屏
					tip.style.display = 'block';
				} else {			
						window.location.href = "https://apps.apple.com/cn/app/id1159546710"; /***下载app的地址***/
				}

			} else {
				if(is_weixin || is_qq) {
					tip.style.display = 'block';
				} else {
						window.location.href = "https://download.hrtpayment.com/download/hrtapp_zyb.html"; /***下载app，有安卓同事提供***/
				}

			}
		}
		function comfunopen() {
			var is_weixin = (function() { //判断微信UA
				var ua = navigator.userAgent.toLowerCase();
				if(ua.match(/MicroMessenger/i) == "micromessenger") {
					return true;
				} else {
					return false;
				}
			})();
			var is_qq = (function() { //判断微信UA
				var ua = navigator.userAgent.toLowerCase();
				if(ua.match(/QQ/i) == "qq") {
					return true;
				} else {
					return false;
				}
			})();
			var is_qqbrowser = (function() { //判断QQ浏览器
				var ua = navigator.userAgent.toLowerCase();
				if(ua.match(/mqqbrowser/i) == "mqqbrowser") {
					return true;
				} else {
					return false;
				}
			})();
			var tip = document.getElementById('weixin-tip');
			var u = navigator.userAgent,
				app = navigator.appVersion;
			var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
			var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

			if(isiOS) { //调用ios的链接
				//alert('ios');
				if(is_weixin || is_qq) {
					//alert('ios');
					//tip.style.height = winHeight + 'px'; //兼容IOS弹窗整屏
					tip.style.display = 'block';
				} else {
					//alert('ios启动');
					window.location.href = "hrtZhanYeBao://"; /***启动app的地址***/
					
				}

			} else {
				if(is_weixin || is_qq) {
					tip.style.display = 'block';
				} else {
					//alert('安卓启动');
					window.location.href = "hrtagent://agent"; /***打开app的协议启动app，有安卓同事提供***/
				}

			}
		}
		$("#open").click(function() {
			comfunopen();

		});
		$("#btnNext").click(function() {
			comfundown();

		});				