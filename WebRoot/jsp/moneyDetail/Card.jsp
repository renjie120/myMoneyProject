<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/jsp/Base.jsp"%>
<%@ page import="myOwnLibrary.util.Util"%>
<%@ page import="tallyBook.pojo.ShopCard"%>
<%
	String title = "���";
	boolean addNew = true;
	if (request.getAttribute("message") != null) {
		title = request.getAttribute("message").toString();
		addNew = false;
	}
%>
<html>
	<head>
		<title>���ÿ�</title>
		<script type="text/javascript"
			src="<%=appPath%>/js/flexigrid_my/jquery-1.3.2.min.js"></script>
		<script type="text/javascript"
			src="<%=appPath%>/js/flexigrid_my/flexigrid.js"></script>
		<script type='text/javascript'
			src='<%=appPath%>/dwr/interface/dwrBo.js'></script>
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
</style>
		<script type="text/javascript">
function checkIsNum(textId,msg)
{
        obj = document.getElementById(textId);
        if(isNaN(obj.value)){
                alert('['+msg+']����Ϊ����.');
                obj.focus();
                return false;
        }
        else
                return true;
}
function add(){
	var cardInfo = {};
	var cardDescO = $('#cardDesc');
	if(cardDescO.val()==""){
		cardDescO.next().css({color:'red'}).text("���뿨Ƭ��!");
		return ;
	}
	else{
		cardDescO.next().text('');
	}
	var cardDeadLineO = $('#cardDeadLine');
	if(cardDeadLineO.val()!=''){
		checkIsNum('cardDeadLine','�˵���');
	}
	var cardNoO = $('#cardNo');
	if(cardNoO.val()==''){
		cardNoO.next().css({color:'red'}).text("������д����!");
		return ;
	}
	else{
		cardNoO.next().text('');
		checkIsNum('cardNo','����'); 			
	}
	var cardUrlO = $('#cardUrl'); 
	cardInfo.cardDesc = cardDescO.val();
	cardInfo.cardDeadLine = cardDeadLineO.val();
	cardInfo.cardNo = cardNoO.val();
	cardInfo.cardUrl = cardUrlO.val();
	dwrBo.saveCard(cardInfo,afterSave);
}

function afterSave(data){
	alert(data);
	window.history.back();
}

function update(){
	var cardInfo = {};
	var cardSnoval = $('#cardSno').val();
	var cardDescO = $('#cardDesc');
	if(cardDescO.val()==""){
		cardDescO.next().css({color:'red'}).text("���뿨Ƭ��!");
		return ;
	}
	else{
		cardDescO.next().text('');
	}
	var cardDeadLineO = $('#cardDeadLine');
	if(cardDeadLineO.val()!=''){
		checkIsNum('cardDeadLine','�˵���');
	}
	var cardNoO = $('#cardNo');
	if(cardNoO.val()==''){
		cardNoO.next().css({color:'red'}).text("������д����!");
		return ;
	}
	else{
		cardNoO.next().text('');
		checkIsNum('cardNo','����'); 			
	}
	var cardUrlO = $('#cardUrl'); cardSnoval
	cardInfo.cardDesc = cardDescO.val();
	cardInfo.cardSno = cardSnoval;
	cardInfo.cardDeadLine = cardDeadLineO.val();
	cardInfo.cardNo = cardNoO.val();
	cardInfo.cardUrl = cardUrlO.val();
	dwrBo.updateCard(cardInfo,afterUpdate);
}

function afterUpdate(d){
	alert(d);
	window.close();
}
</script>
	</head>
	<body>
		<div class="content"
			style="display: inline; top: 0px; left: 0px; width: 100%; height: 100%;">
			<table>
				<input id="cardSno" type="hidden" name="cardSno" value="${cardSno}">
				<tr>
					<td>
						��Ƭ��:
					</td>
					<td>
						<input id="cardDesc" type="text" value="${cardDesc}">
						<span></span>
					</td>
				</tr>
				<tr>
					<td>
						�˵���:
					</td>
					<td>
						<input id="cardDeadLine" name="cardDeadLine"
							value="${cardDeadLine}">
						<span></span>
					</td>
				</tr>
				<tr>
					<td>
						����:
					</td>
					<td>
						<input id="cardNo" name="cardNo" value="${cardNo}">
						<span></span>
					</td>
				</tr>
				<tr>
					<td>
						���ӵ�ַ:
					</td>
					<td>
						<input id="cardUrl" name="cardUrl" value="${cardUrl}">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left">
						<%
						if ("���".equals(title)) {
						%>
						<input type='hidden' id='addType' />
						<button onclick="add();">
							���
						</button>
						<%
						} else {
						%>
						<button onclick="update();">
							�޸�
						</button>
						<%
						}
						%>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<span style="color: red" id="mes"></span>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
