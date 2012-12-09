<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/jsp/Base.jsp"%>
<%@ page import="myOwnLibrary.util.Util"%>
<%@ page import="tallyBook.dao.CommonSelDao"%>
<%@ page import="tallyBook.pojo.ShopCard"%>
<%
	String title = "添加记录";
	boolean addNew = true;
	CommonSelDao selDao = new CommonSelDao();
	List allCards = selDao.getAllCards();
	if (request.getAttribute("message") != null) {
		title = request.getAttribute("message").toString();
		addNew = false;
	}
	String bookType = Util.changeToNotNull((String) session
			.getAttribute("booktype"));
%>
<html>
	<head>
		<title><%=title%></title>
		<script type='text/javascript' src='<%=appPath%>/js/common/date.js'></script>
		<LINK rel=stylesheet type=text/css
			href="<%=appPath%>/js/common/red.css">
		<script type='text/javascript'
			src='<%=appPath%>/dwr/interface/dwrBo.js'></script>
		<script language="javascript" type="text/javascript"
			src="<%=appPath%>/js/calendar/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=appPath%>/jsp/moneyDetail/AddMoney.js"></script>
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

#tallyTypeImg {
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
		<input type='hidden' id='status' value="<%=addNew%>">
		<div class="content"
			style="display: inline; top: 0px; left: 0px; width: 100%; height: 100%;">
			<table>
				<input id="moneySno" type="hidden" value="${moneySno}">
				<input id="booktype" value="<%=bookType%>" type="hidden">
				<tr>
					<td>
						收支时间:
					</td>
					<td>
						<input id="moneyTime" type="text" value="${moneyTime}"
							readOnly='true'>
						<img onclick="WdatePicker({el:$dp.$('moneyTime')})"
							src="<%=appPath%>/js/common/datepicker/images/calendar.gif">
						<span></span>
					</td>
				</tr>
				<tr>
					<td>
						收支金额:
					</td>
					<td>
						<input id="money" value="${money}" onfocus="this.select();">
						<span></span>
					</td>
				</tr>
				<tr>
					<td>
						收支类别:
					</td>
					<td>
						<input type='hidden' id='valueofsort' value='${moneySort}' />
						<input type='text' id='sortdesc' value='${moneyTypeDesc}'
							readOnly='true' />
						<button id='tallyTypeImg'
							onclick="getTallyTypeTree('valueofsort','sortdesc')"></button>
						<span></span>
					</td>
				</tr>
				<% 
				if(request.getAttribute("splitNo")==null){ %>
				<tr>
					<td>
						拆分成: 
					</td>
					<td>
						<select id="splitMonths" style="width: 150px">
							<option value="-1">
								请选择
							</option>
							<option value="3">
								3个月
							</option>
							<option value="2">
								2个月
							</option>
							<option value="4">
								4个月
							</option>
							<option value="5">
								5个月
							</option>
							<option value="6">
								6个月
							</option>
						</select>
					</td>
				</tr>
				<%}else{ %>
					<tr>
					<td>
						未拆分总额:
					</td>
					<td>
						<input type='hidden' id='splitMonths' name='splitMonths' value='-1'/>
						<input type='hidden' id='splitNo' value='${splitNo}'/>
						<input type='text'  value='${splitNoStr}'
							readOnly='true' />
					</td>
				</tr> 
				<%} %>
				<tr>
					<td>
						收支说明:
					</td>
					<td>
						<textarea COLS='25' rows="4" id="moneyDesc" max="100">${moneyDesc}</textarea>
						<span></span>
					</td>
				</tr>
				<tr>
					<td>
						消费途径:
					</td>
					<td>
						<input type='hidden' id='shopCardValue' value='${shopCard}' />
						<select id="shopCard" style="width: 150px">
							<option value="-1">
								请选择
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
						<a href="<%=appPath%>/jsp/moneyDetail/Card.jsp">增加卡片</a>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left">
						<%
						if ("添加记录".equals(title)) {
						%>
						<input type='hidden' id='addType' />
						<button onclick="add();">
							添加记录
						</button> 
						<%
						} else {
						%>
						<button onclick="update();">
							修改记录
						</button>
						<%
						}
						%>
						<button type="button" onclick="syn()" >
							同步
						</input>
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
