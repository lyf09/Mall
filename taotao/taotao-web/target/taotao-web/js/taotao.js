var TT = TAOTAO = {
	checkLogin : function(){
		var _token = $.cookie("TT_TOKEN");/*jquery的插件*/
		if(!_token){
			return ;
		}
		$.ajax({//http://sso.taotao.com/service/user/
			url : "http://ssoquery.taotao.com/user/" + _token,
			dataType : "jsonp", /*跨域请求sso，加载资源，请求只能是get*/
			type : "GET",
			success : function(data){
					var html =data.username+"，欢迎来到淘淘！<a href=\"http://www.taotao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});