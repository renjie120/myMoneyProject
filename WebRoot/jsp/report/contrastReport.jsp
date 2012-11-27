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
					<select id="bigTypeSort" style="width: 150px">
						<option value="1">
							大类
						</option>
						<option value="2">
							小类
						</option>
					</select>
					&nbsp;&nbsp; 
					选择年份
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
						年度分类别对比
					</button>&nbsp;&nbsp;
					<button onclick="reportByMonths()">
						月度分类别对比
					</button>&nbsp;&nbsp;
					<button onclick="setup()" id="setup">
						显示配置
					</button>&nbsp;&nbsp;
					<button  onclick='initShowTable()' id="showtable" style='display:none;'>
						显示表格
					</button>	 
					</td>
			</tr>
			<tr style="display:none">
				<td colspan="5" ><input type="checkbox" style="width: 20px" name="AnimateChart" checked=true >动画效果		
					<input type="checkbox" style="width: 20px" name="showValue" checked=true >显示数值
					<input type="checkbox" style="width: 20px" name="showChangeNow" onclick="changeNowShow()" checked=true >即时显示		
					<button onclick="show()" disabled id="btnshow">显示最新图表</button>
				</td>
			</tr>
			<tr>
				<!-- 下面是类型选择框的模板div -->
				<div id="checkboxModle" style="display: none;">
					<input type="checkbox" style="width: 20px" name="types" value="$1" checked=true onclick="clickChkbox();">
					<font size="2">$2</font>
				</div>
				<td colspan="5" align="left">
					<div id="buttons"
						style="width: 60%; align: left;display:none; fontSize:10px;color:red;">
						选择类别：<button onclick="choose()">全选</button><button onclick="dischoose()">全反选</button>
					</div>					
					<div id="tallyTypes"
						style="width: 60%; align: left;display:none;">
					</div>
				</td>
			</tr>
			<tr>
				<!-- 下面是月份选择框的模板div -->
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
			小值：
			<input id='smallTag' value='3000' width="25px">
			<input value='9999999' id='bigTag' width="25px" style='display: none'>
			<button onclick="showColor()">
				标记
			</button> 
			<div id='mytable'></div>
		</div>     
	</body>
</html>
