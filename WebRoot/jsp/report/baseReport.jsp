<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page  import="tallyBook.dao.CommonSelDao" %>
<%@ page import="tallyBook.dao.TallyTypeDao"%>
<%@ page import="tallyBook.pojo.TallyType"%>
<%@ page import="tallyBook.dao.CommonSelDao"%>
<%@ page import="myOwnLibrary.util.Util"%>
<%@ include file="/jsp/Base.jsp"%>
<%
CommonSelDao dao = new CommonSelDao();
List years = dao.getAllYears();
List months = dao.getAllMonths();

	TallyTypeDao tallyTypeDao = new TallyTypeDao();
	CommonSelDao selDao = new CommonSelDao();
	List tallyTypes = selDao.getAllTallyTypes();
	List tallyTypes2 = tallyTypeDao
	.doGetAllTallyTypes("from tallyBook.pojo.TallyType where parentCode is null");

	
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
					<br>
					&nbsp;&nbsp; ѡ���·� 
						<input type='checkbox' name='year'   onchange="checkAllYear(this)"  value="-1"  checked>ȫ��
						<%if(months!=null){ 
							for(int i=0;i<months.size();i++){
									Object[] objs = (Object[])months .get(i);
						%>
								<input type='checkbox' name='month'  month='1'  onchange="resetChooseReport()"   value="<%=objs[0].toString() %>"  checked><%= objs[1].toString()%>�� 
						<%} }%> 			
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
					</td></tr>
					<tr><td colspan="5">	
					<table width='100%'><tr><td>
					<select onchange='showTypes(this)' id='lxtp'>
					<option value='fst'>һ������</option>
					<option value='sec'>��������</option></select><input type='checkbox' onchange="checkAll(this)" checked>ѡ��ȫ��
					</td></tr>
					<tr><td>
					<div style='width:800px' id='firstType'><font size="2"> 
					<%for(Object o:tallyTypes2){
							TallyType tp  =(TallyType)o; 
							%>
						<input type='checkbox' name='tp'   onchange="resetChooseReport()"  value="<%=tp.getTypeCode()%>" checked><%=tp.getTallyTypeDesc()%>
						<%  
						}
					%></font>
					</div>
					<div  style='width:800px;display:none' id='secondType' ><font size="2"> 
					<%for(Object o:tallyTypes){
							TallyType tp  =(TallyType)o;
							%>
						<input type='checkbox' name='tp'   onchange="resetChooseReport()" value="<%=tp.getTypeCode()%>" checked><%=tp.getTallyTypeDesc()%>
						<%  
						}
					%></font>
					</div>
					</td></tr></table>
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
