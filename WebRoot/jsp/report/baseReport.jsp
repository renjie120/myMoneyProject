<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page  import="tallyBook.dao.CommonSelDao" %>
<%@ include file="/jsp/Base.jsp"%>
<%
CommonSelDao dao = new CommonSelDao();
List years = dao.getAllYears();
List months = dao.getAllMonths();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type='text/javascript'
			src='<%=appPath%>/dwr/interface/dwrBo.js'></script>
		<script type='text/javascript'
			src='<%=appPath%>/jsp/report/baseReport.js'></script>
	<LINK rel=stylesheet type=text/css href="<%=appPath%>/js/common/red.css">   
	<script language="javascript" type="text/javascript" src="<%=appPath%>/js/calendar/WdatePicker.js"></script>
		<title>��֧����ͳ��ͼ</title>
	</head>
	<body>
		<input type="hidden" id="path" value="<%=appPath%>">
		<input type="hidden" name="yearValue" value="${yearValue}">
		<input type="hidden" name="monthValue" value="${monthValue}"> 
		<table WIDTH='100%'>
			<tr><td colspan="5">
				<table width="100%"><tr><td>
					<div id="showdateDiv">
					ѡ�����
					<select id="year" style="width: 150px" onchange="resetChooseReport();">
						<%if(years!=null){ 
							for(int i=0;i<years.size();i++){
									Object[] objs = (Object[])years.get(i);
						%>
							<option value="<%=objs[0].toString() %>"><%= objs[1].toString()%></option>
						<%} }%>
					</select>
					&nbsp;&nbsp; ѡ���·�
					<select id="month" style="width: 150px" onchange="resetChooseReport();">
						<option value="-1">
							��ѡ��
						</optiozn>
						<%if(months!=null){ 
							for(int i=0;i<months.size();i++){
									Object[] objs = (Object[])months .get(i);
						%>
							<option value="<%=objs[0].toString() %>"><%= objs[1].toString()%></option>
						<%} }%>
					</select>
					</div>
					<div id="showdateDiv2" style="display:none">
					�Զ���ʱ��:<input type="checkbox" onclick="setDefineTime(this);">
					<table WIDTH="100%"><tr  WIDTH="100%">
					<td>
						��ʼʱ��:
					</td>
					<td>
						<input type="text" name="minTime" id="minTime">
						<img onclick="WdatePicker({el:$dp.$('minTime')})"
							src="<%=appPath%>/js/common/datepicker/images/calendar.gif">
					</td> 
					<td>
						��ֹʱ��:
					</td>
					<td>
						<input type="text" name="maxTime" id="maxTime">
						<img onclick="WdatePicker({el:$dp.$('maxTime')})"
							src="<%=appPath%>/js/common/datepicker/images/calendar.gif">
					</td>
				</tr></table>
					</div>
					</td><td>  
					ѡ��ͼ������
					<select onchange="getReport();" id="way">
						<option value="0">
							��ѡ��
						</option>
						<option value="1">
							֧����ϸ ��֧�Ա�
						</option>
						<option value="2">
							֧��ͳ��
						</option>
						<option value="3">
							����ͳ��
						</option>
					</select>
					</td></tr></table></td></tr>
				<tr>
				<td colspan="3">
					<div id="chartdiv" align="center"></div>
				</td>
				<td colspan="2">
					<div id="chartdiv2" align="center"></div>
				</td>
			</tr>
		</table>
		<div id="msgDiv">
		</div>
	</body>
</html>
