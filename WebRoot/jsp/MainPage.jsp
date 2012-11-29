<%
	String appPath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>http://localhost:1999</title>
<link type="text/css" href="<%=appPath%>/js/jquery_ui/themes/smoothness/jquery-ui-1.7.custom.css" rel="stylesheet" /> 
<script type="text/javascript" src="<%=appPath%>/js/jquery_ui/jquery-1.3.2.js"></script>
<script type="text/javascript" src="<%=appPath%>/js/jquery_ui/ui/jquery-ui-1.7.custom.js"></script>
<script type="text/javascript" src="<%=appPath%>/jsp/MainPage.js"></script>
<style type="text/css">
body{ font: 62.5% "Trebuchet MS", sans-serif; margin: 0px;}
#dialog_link {padding: .4em 1em .4em 20px;text-decoration: none;position: relative;}
#dialog_link span.ui-icon {margin: 0 5px 0 0;position: absolute;left: .2em;top: 50%;margin-top: -8px;}
ul#icons {margin: 0; padding: 0;}
ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
ul#icons span.ui-icon {float: left; margin: 0 4px;}
#tabs div {height:590px;width:100%;overflow-y:hidden;}
</style> 
</head>
<body style="overflow:auto;">
<input type="hidden" id="path" value="<%=appPath%>" />
<div id="tabs">
<ul>
<li><a href="#tabs-0">系统管理</a></li> 
<li><a href="#tabs-8">ext桌面</a></li>
<li><a href="#tabs-1">添加收支</a></li>
<li><a href="#tabs-2">收支列表</a></li>
<li><a href="#tabs-3">基本收支图表分析</a></li>
<li><a href="#tabs-4">收支对比图表分析</a></li>
<li><a href="#tabs-5">类型维护</a></li>
<!--li><a href="#tabs-6">股票交易</a></li-->
<li><a href="#tabs-7">系统说明</a></li>
<li><a href="#tabs-9">详细报表</a></li>
<li><a href="#tabs-10">预算管理</a></li>
<li><a href="#tabs-11">万年历</a></li>
<li><a href="#tabs-12">highcharts示例</a></li>
<li><a href="#tabs-13">交易</a></li>
</ul>
<div id="tabs-0"><iframe src=""></iframe></div>
<div id="tabs-8"><iframe src=""></iframe></div>
<div id="tabs-1"><iframe src=""></iframe></div>
<div id="tabs-2"><iframe src=""></iframe></div>
<div id="tabs-3"><iframe src=""></iframe></div>
<div id="tabs-4"><iframe src=""></iframe></div>
<div id="tabs-5"><iframe src=""></iframe></div>
<!--div id="tabs-6"><iframe src=""></iframe></div-->
<div id="tabs-7"><iframe src=""></iframe></div>
<div id="tabs-9"><iframe src=""></iframe></div>
<div id="tabs-10"><iframe src=""></iframe></div>
<div id="tabs-11"><iframe src=""></iframe></div>
<div id="tabs-12"><iframe src=""></iframe></div>
<div id="tabs-13"><iframe src=""></iframe></div>
</div> 
<jsp:include page="/jsp/stockFoot.jsp"></jsp:include>
</body>
</html>
