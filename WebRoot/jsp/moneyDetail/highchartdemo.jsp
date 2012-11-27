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
		<title>��֧�Աȷ���ͼ</title>
	</head>
	<body style="fontSize:10px;">
		<input type="hidden" id="path" value="<%=appPath%>">
		<table>
			<tr>
				<input type="hidden" name="yearValue" value="${yearValue}">
				<input type="hidden" name="monthValue" value="${monthValue}">
				<td colspan="5">
					ͳ�ƴ���
					<select id="tp" style="width: 150px" onchange="changeTallyTypes()">
						<option value="2">
							֧��
						</option>
						<option value="1">
							����
						</option>
					</select>
					�鿴���
					<select id="bigTypeSort" style="width: 150px" onchange="changeTypeSort()">
						<option value="1">
							����
						</option>
						<option value="2">
							С��
						</option>
					</select>
					&nbsp;&nbsp;
					<button onclick="reportByYears()">
						��ȷ����Ա�
					</button>   
					<button onclick='addPie()' id='addPie'>��ӱ�ͼ</button>
					<button onclick='addColumn()' id='addColumn'>�����ͼ</button>
					<button onclick='addBar()' id='addBar'>���barͼ</button>
					<button onclick='addLine()' id='addLine'>�����ͼ</button>
					<button onclick='setData()' id='removeS'>�޸�����ϵ��</button>
					<button onclick='removeSeries()' id='removeS'>�Ƴ�����ϵ��</button>
					<button onclick='setWH(1)'>+</button>
					<button onclick='setWH(-1)'>-</button> 
					<button onclick='setReset()'>��ԭ</button>
					name:<input id='sname' value='2008'><button  onclick='hidePic()' >����ͼ��</button>
					<button  onclick='showPic()'>��ʾͼ��</button>
					</td>
			</tr> 
			 
		</table> 
				<div id="hightChartDiv" style='z-index:999;'>
				</div> 
	</body>
</html>
