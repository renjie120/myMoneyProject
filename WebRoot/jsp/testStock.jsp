<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="myOwnLibrary.util.Util"%>
<%
	String path = request.getContextPath();
	//response.setHeader("Refresh", "3;URL=" + path + "/jsp/Login.jsp");
%>
<html>
	<head>
		<script type="text/javascript"
			src="<%=path%>/js/flexigrid_my/jquery-1.3.2.min.js"></script>
		<script type="text/javascript">
    var timel = null;	   
    $(function(){
      var obj = $('span').eq(1);
      timel = setInterval(function(){    	  		
			obj.text(obj.text()-1); 
			if(obj.text()==0){
				clearInterval(timel);
			}
			},1000 );
    });
    </script>
		<style type="text/css">
body {
	margin: 0px;
	background-color: #d6e3f7;
}
</style>
	</head>
	<body>
		<font size="2"> &nbsp;错误信息: <span
			style="color: red; font-size: 12px;"><%=Util.notBlankStr(request.getAttribute("message"),
							"未登录或者session失效，请重新登录！")%></span>
			<br>
			<br>
			<br>
			<span>3</span>秒钟之后将跳转到登陆界面. 
	</body>
</html>
