<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/jsp/Base.jsp"%>
<%@ page import="myOwnLibrary.util.Util"%>
<%@ page import="tallyBook.pojo.ShopCard"%>
<%
List shopcardlist = (List) request.getAttribute("shopcardlist");
%>
<html>
	<head>
	<base href="_self">
		<title>信用卡记录</title>
		<script type="text/javascript"
			src="<%=appPath%>/js/flexigrid_my/jquery-1.3.2.min.js"></script>
		<script type="text/javascript"
			src="<%=appPath%>/js/flexigrid_my/flexigrid.js"></script>
		<script type='text/javascript' src='<%=appPath%>/dwr/interface/dwrBo.js'></script>
		<LINK rel=stylesheet type=text/css
			href="<%=appPath%>/js/common/red.css">
		<style type="text/css">
body {
	font: 62.5% "Trebuchet MS", sans-serif;
	margin: 0px;
}

.content {
	background-color: #d6e3f7;
}

.content td {
	font-size: 12px;
}

td {
	height: 22px;
	border-bottom: 1px solid black;
	border-right: 1px solid black;
	cursor: default;
}

th {
	height: 20px;
	font-size: 16px;
	font-weight: normal;
	border-bottom: 2px solid black;
	border-right: 1px solid black;
	background-color: #999999
}

table {
	border-top: 1px solid black;
	border-left: 1px solid black;
	font-size: 13px;
	width:100%;
}
input{
	width:40px;
}
</style>
<script type="text/javascript">
function doBackMoney(sno){
	var mon = document.getElementById(sno).value;
	dwrBo.backMoney(sno,mon,backMoney);
}
function backMoney(data){
	alert(data);
	window.close();
}
function deleteCard(sno){
	if (confirm("确定删除?")) {
		dwrBo.deleteCard(sno,afterDelete);
	}
}
function afterDelete(data){
	alert(data);
	window.close();
}
function updateCard(sno){
	openNewWin(window.path+"/CardAction!beforeUpdateCard.action?shopCard="+sno,"dialogWidth=700px;dialogHeight=300px;");  
	window.close();
}	
</script>
	</head>
	<body>
		<div class="content">
			<table>
				<tr>
					<td>
						卡名
					</td>
					<td>
						卡号
					</td>
					<td>
						账单日
					</td>
					<td>
						卡中金额
					</td>
					<td>
						消费金额
					</td>
					<td>
						剩余金额
					</td>
					<td>
						消费次数
					</td>
					<td>
						操作
					</td>
				</tr>
				<%
					if (shopcardlist != null && shopcardlist.size() > 0) {
					int count = shopcardlist.size();
					for (int i = 0; i < count; i++) {
						ShopCard card = (ShopCard) shopcardlist.get(i);
			%>
				<tr>
					<td><a href="<%=card.getCardUrl() %>" target='new'><%=card.getCardDesc()%></a></td>
					<td><%=card.getCardNo()%></td>
					<td><%=card.getCardDeadLine()%></td>
					<td><%=card.getCardMoney()%></td>
					<td><%=card.getSumMoney()%></td>
					<td><%=card.getRetainMoney()%></td>
					<td><%=card.getTimes()%></td>
					<td><input onfocus="this.value='';" id="<%=card.getCardSno() %>" value="金额"><button onclick="doBackMoney(<%=card.getCardSno() %>)">还账</button>
					<button onclick="deleteCard(<%=card.getCardSno() %>)">删除</button>
					<button onclick="updateCard(<%=card.getCardSno() %>)">修改</button></td>
				</tr>
				<%
				}
				}
			%>
			</table>
			<div align="center"><button onclick="window.close();">关闭</button></div>
		</div>
	</body>
</html>
