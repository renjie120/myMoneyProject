<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/jsp/Base.jsp"%>
<%@ taglib uri="/WEB-INF/tld/self" prefix="myself"%> 
<%@ page import="myOwnLibrary.taglib.OptionColl"%> 
<%@ page import="myOwnLibrary.CommonStockSelDao"%> 
<%
	String title = "添加记录";
	boolean addNew = true;
	CommonStockSelDao selDao = new CommonStockSelDao(); 
	//交易类型
	OptionColl stockTypes = selDao.getStockDealTypes();
	//全部的股票
	OptionColl stocks = selDao.getStocks();
	//股票费用类型
	OptionColl stockDealTypes = selDao.getStockDealTypes();
	//满意度下拉菜单
	OptionColl happyNums = selDao.getHappyNums();
%>
<html>
	<head>
		<title><%=title%></title>
		<script type='text/javascript' src='<%=appPath%>/js/common/date.js'></script>
		<LINK rel=stylesheet type=text/css
			href="<%=appPath%>/js/common/red.css">
		<script type='text/javascript'
			src='<%=appPath%>/dwr/interface/stockBo.js'></script>
		<script language="javascript" type="text/javascript"
			src="<%=appPath%>/js/calendar/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=appPath%>/jsp/stock/AddStock.js"></script>
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
				<input type="hidden" id="dealStockCode"> 
				<input type="hidden" id="dealStockName">
				<tr>
					<td>
						交易类型:
					</td>
					<td>
						<myself:select selectColl="<%=stockTypes %>" tagName="dealType"></myself:select>
					</td>
				</tr>
				<tr>
					<td>
						交易费用类型:
					</td>
					<td>
						<myself:select selectColl="<%=stockDealTypes %>" tagName="configType"></myself:select>
						<span></span>
					</td>
				</tr>
				<tr>
					<td>
						股票
					</td>
					<td>
						<myself:select selectColl="<%=stocks %>" tagName="stockselect" selectFlag="true" onchange="setStock()"></myself:select>
						<span></span> 
					</td>
				</tr> 
				<tr>
					<td>
						交易价格:
					</td>
					<td>
						<input id="dealPrice">
						<span></span>
					</td>
				</tr>
				<tr>
					<td>
						交易手数:
					</td>
					<td>
						<input id="dealStockHunderedNum" onblur='checkOnlyNum(this)'  style='width:40px'>手
						<input id="dealStockNum" type='hidden'>
						<span id='shownum'></span>
					</td>
				</tr>
				<tr>
					<td>
						交易时间:
					</td>
					<td> 
						<input id="dealTime" type="text"  
							readOnly='true'>
						<img onclick="WdatePicker({el:$dp.$('dealTime')})"
							src="<%=appPath%>/js/common/datepicker/images/calendar.gif">
						<span></span> 
					</td>
				</tr> 
				<tr>
					<td>
						理想价格:
					</td>
					<td>
						<input id="dealTinkPrice" >
						<span></span>
					</td>
				</tr>
				<tr>
					<td>
						交易心得
					</td>
					<td>
						<textarea COLS='25' rows="4" id="dealIdea" max="100"></textarea>
						<span></span>
					</td>
				</tr> 
				<tr>
					<td>
						交易满意度:
					</td>
					<td>
						<myself:select selectColl="<%=happyNums %>" tagName="happyNum" defaultValue="3"></myself:select>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left">
						<%
						if ("添加记录".equals(title)) {
						%>
						<input type='hidden' id='addStock' />
						<button onclick="add();">
							添加记录
						</button>
						<button onclick="query();">
							查看记录
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
