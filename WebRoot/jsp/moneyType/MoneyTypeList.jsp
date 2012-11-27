<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/jsp/Base.jsp"%>

<html>
	<head>
		<title>消费记录维护列表</title>
		<link rel="stylesheet" type="text/css" href="<%=appPath%>/js/gridTree/GridTree2.css">
		<script type="text/javascript"
			src="<%=appPath%>/js/gridTree/hashMap.js"></script>
		<script type="text/javascript"
			src="<%=appPath%>/js/gridTree/GridTree.All.js"></script>
		<script type="text/javascript"
			src="<%=appPath%>/jsp/moneyType/MoneyTypeList.js"></script>
	</head>
	<body onload="test()" style="margin:0px;">
		<input type="hidden" id="path" value="<%=appPath%>" />
		<button onclick="closeAll()">收缩全部</button>&nbsp;&nbsp;
		<button onclick="openAll()">展开全部</button>
		<div id='newtableTree'
			style='width:100%; height:90%; border: 1px solid #000099; overflow-x: visible; overflow-y: scroll;'>
			正在装载，请等待。。。
		</div>
	</body>
</html>
