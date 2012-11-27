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
			src='<%=appPath%>/jsp/report/contrastReport.js'></script>
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
					<select id="bigTypeSort" style="width: 150px">
						<option value="1">
							����
						</option>
						<option value="2">
							С��
						</option>
					</select>
					&nbsp;&nbsp; 
					ѡ�����
					<select id="year" style="width: 150px"  onchange="changeYears()">
						<%if(years!=null){ 
							for(int i=0;i<years.size();i++){
									Object[] objs = (Object[])years.get(i);
						%>
							<option value="<%=objs[0].toString() %>"><%= objs[1].toString()%></option>
						<%} }%>
					</select>&nbsp;&nbsp;
					<br> 
					<button onclick="reportByYears()">
						��ȷ����Ա�
					</button>&nbsp;&nbsp;
					<button onclick="reportByMonths()">
						�¶ȷ����Ա�
					</button>&nbsp;&nbsp;
					<button onclick="setup()" id="setup">
						��ʾ����
					</button>&nbsp;&nbsp;
					<button  onclick='initShowTable()' id="showtable" style='display:none;'>
						��ʾ���
					</button>	 
					</td>
			</tr>
			<tr style="display:none">
				<td colspan="5" ><input type="checkbox" style="width: 20px" name="AnimateChart" checked=true >����Ч��		
					<input type="checkbox" style="width: 20px" name="showValue" checked=true >��ʾ��ֵ
					<input type="checkbox" style="width: 20px" name="showChangeNow" onclick="changeNowShow()" checked=true >��ʱ��ʾ		
					<button onclick="show()" disabled id="btnshow">��ʾ����ͼ��</button>
				</td>
			</tr>
			<tr>
				<!-- ����������ѡ����ģ��div -->
				<div id="checkboxModle" style="display: none;">
					<input type="checkbox" style="width: 20px" name="types" value="$1" checked=true onclick="clickChkbox();">
					<font size="2">$2</font>
				</div>
				<td colspan="5" align="left">
					<div id="buttons"
						style="width: 60%; align: left;display:none; fontSize:10px;color:red;">
						ѡ�����<button onclick="choose()">ȫѡ</button><button onclick="dischoose()">ȫ��ѡ</button>
					</div>					
					<div id="tallyTypes"
						style="width: 60%; align: left;display:none;">
					</div>
				</td>
			</tr>
			<tr>
				<!-- �������·�ѡ����ģ��div -->
				<div id="checkboxModle2" style="display: none;">
					<input type="checkbox" style="width: 20px" name="categoryId" value="$1" checked=true onclick="clickChkbox2();">
					<font size="2">$2</font>
				</div>
				<td colspan="5" align="left">	
					<span id="showCategories" style="color:red;"></span>			
					<div id="months"
						style="width: 60%; align: left;display:none;">
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="5">
					<div id="chartdiv" align="center"></div>
					<script language="JavaScript"> 
				      var chart1 = new FusionCharts("../../js/fusionchart/Charts/MSColumnLine3D.swf", "fusionchart", "1200", "400", "0", "1"); 
				      chart1.setDataXML("<chart></chart>");
				      chart1.render("chartdiv");
				   </script> 
				</td>
			</tr>
		</table>  
		<div id="msgDiv" style="width: 800px; height: 100px;display:none"> 
			Сֵ��
			<input id='smallTag' value='3000' width="25px">
			<input value='9999999' id='bigTag' width="25px" style='display: none'>
			<button onclick="showColor()">
				���
			</button> 
			<div id='mytable'></div>
		</div>     
	</body>
</html>
