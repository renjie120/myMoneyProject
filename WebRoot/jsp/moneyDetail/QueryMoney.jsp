<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/jsp/Base.jsp"%>
<%@ taglib uri="/WEB-INF/tld/self" prefix="myself"%>
<%@ page import="tallyBook.dao.CommonSelDao"%>
<%@ page import="tallyBook.pojo.ShopCard"%>
<%@ page import="myOwnLibrary.taglib.OptionColl"%>
<%
	CommonSelDao selDao = new CommonSelDao();
	List allCards = selDao.getAllCards();
	//�·�
	OptionColl monthColl = selDao.getAllMonthColl();
	//���
	OptionColl yearColl = selDao.getAllYearColl();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>��ѯ��ϸ��Ϣ</title>
		<script type='text/javascript' src='<%=appPath%>/js/common/date.js'></script>
		<LINK rel=stylesheet type=text/css
			href="<%=appPath%>/js/common/red.css">
		<script type='text/javascript'
			src='<%=appPath%>/dwr/interface/dwrBo.js'></script>
		<script language="javascript" type="text/javascript"
			src="<%=appPath%>/js/calendar/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=appPath%>/jsp/moneyDetail/QueryMoney.js"></script>
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

#btn {
	background:
		url(../../js/jquery_ui/themes/smoothness/images/ui-icons_cd0a0a_256x240.png)
		; /* for IE6 */
	width: 20px;
	height: 20px;
	background-position: -32px -128px;
}
</style>
	</head>
	<body>
		<div class="content"
			style="display: inline; top: 0px; left: 0px; width:400px; height: 100%;">
			 	<table width="400" border="0" align=center cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="20%">
							<input type='checkbox' id='aa' checked='true' />
							ѡ���·�:
						</td>
						<td width="80%"> 
							<myself:select selectColl="<%=yearColl%>" tagName="yearId"
								selectFlag='true'></myself:select>
							��
							<myself:select selectColl="<%=monthColl%>" tagName="monthId"
								selectFlag='true'></myself:select>
							��
						</td>
					</tr>
					<tr>
						<td width="20%">
							��ʼʱ��:
						</td>
						<td>
							<input type="text" name="minTime" id="minTime">
							<img onclick="WdatePicker({el:$dp.$('minTime')})"
								src="<%=appPath%>/js/common/datepicker/images/calendar.gif">
						</td>
					</tr>
					<tr>
						<td>
							��ֹʱ��:
						</td>
						<td>
							<input type="text" name="maxTime" id="maxTime">
							<img onclick="WdatePicker({el:$dp.$('maxTime')})"
								src="<%=appPath%>/js/common/datepicker/images/calendar.gif">
						</td>
					</tr>
					<tr>
						<td>
							���ڴ˽��:
						</td>
						<td>
							<input type="text" name="money" id="money">
						</td>
					</tr>
					<tr>
						<td>
							С�ڴ˽��:
						</td>
						<td>
							<input type="text" name="maxmoney" id="maxmoney">
						</td>
					</tr>
					<tr>
						<input type="hidden" name="inOrOutTypeValue"
							value="${inOrOutTypeValue}">
						<td>
							�����֧��:
						</td>
						<td>
							<select id="inOrOutType" style="width: 150px">
								<option value="-1">
									��ѡ��
								</option>
								<option value="1">
									����
								</option>
								<option value="2">
									֧��
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							����;��:
						</td>
						<td>
							<select id="shopCard" style="width: 150px">
								<option value="-1">
									��ѡ��
								</option>
								<%
										if (allCards != null && allCards.size() > 0) {
										for (int i = 0; i < allCards.size(); i++) {
											ShopCard card = (ShopCard) allCards.get(i);
								%>
								<option value="<%=card.getCardSno()%>">
									<%=card.getCardDesc()%></option>
								<%
									}
									}
								%>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							�������:
						</td>
						<td>
							<input type='hidden' id='valueofsort' />
							<input type='text' id='sortdesc' readOnly='true' />
							<button id='btn'
								onclick="getMultiTallyTypeTree('valueofsort','sortdesc')"></button>
							<span></span>
						</td>
					</tr>
					<tr>
						<td>
							����˵��:
						</td>
						<td>
							<input type="text" name="moneyDesc" id="moneyDesc"
								value="${moneyDesc}">
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" id="submit" class="input-button" value="��ѯ" />
					<input type="reset" class="input-button" value="����" />
				</div> 
		</div>
	</body>
</html>
