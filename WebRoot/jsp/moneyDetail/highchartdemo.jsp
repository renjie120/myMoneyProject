<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/jsp/Base.jsp"%>
<%@ page  import="tallyBook.dao.CommonSelDao" %>
<%@ page import="tallyBook.dao.TallyTypeDao"%>
<%@ page import="tallyBook.pojo.TallyType"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	TallyTypeDao tallyTypeDao = new TallyTypeDao();
	CommonSelDao dao = new CommonSelDao();
	List years = dao.getAllYears();
%>
<html>
	<head> 
	<script type='text/javascript'
			src='<%=appPath%>/js/gridTree/hashMap.js'></script>
	     <script type='text/javascript'
			src='<%=appPath%>/dwr/interface/dwrBo.js'></script>
		<script type='text/javascript'
			src='<%=appPath%>/jsp/moneyDetail/highchartdemo.js'></script>
		<link rel="stylesheet" type="text/css"
			href="<%=appPath%>/jsp/moneyDetail/mySimpliTable.css">
		<title>收支对比分析图</title>
	</head>
	<body style="fontSize:10px;">
		<input type="hidden" id="path" value="<%=appPath%>">
		<table>
			<tr>
				<input type="hidden" name="yearValue" value="${yearValue}">
				<input type="hidden" name="monthValue" value="${monthValue}">
				<td colspan="5">
					统计大类
					<select id="tp" style="width: 150px" onchange="changeTallyTypes()">
						<option value="2">
							支出
						</option>
						<option value="1">
							收入
						</option>
					</select>
					查看类别
					<select id="bigTypeSort" style="width: 150px" onchange="changeTypeSort()">
						<option value="1">
							大类
						</option>
						<option value="2">
							小类
						</option>
					</select>
					&nbsp;&nbsp;
					<button onclick="reportByYears()">
						年度分类别对比
					</button>   
					<button onclick='addPie()' id='addPie'>添加饼图</button>
					<button onclick='addColumn()' id='addColumn'>添加列图</button>
					<button onclick='addBar()' id='addBar'>添加bar图</button>
					<button onclick='addLine()' id='addLine'>添加线图</button>
					<button onclick='setData()' id='removeS'>修改数据系列</button>
					<button onclick='removeSeries()' id='removeS'>移除数据系列</button>
					<button onclick='setWH(1)'>+</button>
					<button onclick='setWH(-1)'>-</button> 
					<button onclick='setReset()'>还原</button>
					name:<input id='sname' value='2008'><button  onclick='hidePic()' >隐藏图形</button>
					<button  onclick='showPic()'>显示图形</button>
					</td>
			</tr> 
			 
		</table> 
				<div id="hightChartDiv" style='z-index:999;'>
				</div> 
	</body>
</html>
