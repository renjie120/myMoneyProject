<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String appPath = request.getContextPath();
	if (request.getProtocol().compareTo("HTTP/1.0") == 0) {
		response.setHeader("Pragma", "no-cache");
	} else if (request.getProtocol().compareTo("HTTP/1.1") == 0) {
		response.setHeader("Cache-Control", "no-cache");
	}
	response.setDateHeader("Expires", 0);
%>
<link type="text/css"
	href="<%=appPath%>/js/jqueryui/css/smoothness/jquery-ui-1.7.custom.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="<%=appPath%>/js/jqueryui/jquery-1.3.2.min.js"></script>
<script type="text/javascript"
	src="<%=appPath%>/js/jqueryui/jquery-ui-1.7.custom.min.js"></script>
<link type="text/css" href="<%=appPath%>/jsp/stock/StockList.css"
	rel="stylesheet" />
<html>
	<head>
		<title>股票交易列表</title>
		<style>
/* 初始化样式 */
div,form,img,ul,ol,li,dl,dt,dd {
	margin: 0;
	padding: 0;
	border: 0;
}

h1,h2,h3,h4,h5,h6 {
	margin: 0;
	padding: 0;
}

h1 {
	margin: 42px 0 20px;
	font-size: 18px;
	text-align: center;
}

.clear {
	clear: both;
}

a {
	color: #000;
	text-decoration: none;
}

a:hover {
	color: #999;
	text-decoration: none;
}

body {
	margin: 0 auto;
	width: 960px;
	background: #282c2f;
	color: #d1d9dc;
	font: 12px 'Lucida Grande', Verdana, sans-serif;
}

#layout {
	margin: 0 auto;
	width: 280px;
	left:10px;
}

.text {
	line-height: 22px;
	padding: 0 6px;
	color: #666;
}

.box h3 {
	font-size: 12px;
	padding-left: 6px;
	line-height: 22px;
	background: #99CC00 url(arrow.gif) no-repeat right -17px;
	font-weight: bold;
	color: #fff;
	border: 1px solid #669900;
	height: 22px;
	cursor: hand;
}

.box h3 a {
	color: #fff;
}

.box h3 a:hover {
	color: #eee;
}

.box {
	position: relative;
	background: #363C41;
	border: 5px solid #4A5055;
}

.ar {
	line-height: 22px;
	position: absolute;
	top: 3px;
	right: 6px;
	display: block;
}
</style> 
<script type="text/javascript">
$(document).ready(function(){
    $("#template").hide();
	$(".box h3").next(".text").hide();
    $(".box h3").toggle(function(){
     $(this).next(".text").animate({height: 'toggle', opacity: 'toggle'}, "slow"); 
		 return false;

    },function(){
		 $(this).next(".text").animate({height: 'toggle', opacity: 'toggle'}, "slow"); 
		 return false;
    });
});
</script>
	</head>
	<body> 
		<div id="layout">
			<div class="box">
				<h3>
					联通
				</h3>
				<div style="display: block;" class="text">
					  dfsdf
				</div>
			</div>
			<br>
			<div class="box">
				<h3>
					中国远洋
				</h3>
				<div style="display: block;" class="text">
				 涨
				</div>
			</div>
			<div id='template' class="display:none">
				<div class="display:none">
					<h3>
						$title
						<a href="#" class="ar"></a>
					</h3>
					<div style="display: block;">
						$content
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
