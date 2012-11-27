<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/jsp/Base.jsp"%>
<%@ page import="myOwnLibrary.util.*"%>
<%
	List allYearAndMonths = (List) request
			.getAttribute("allYearAndMonths");
	List allTypes = (List) request.getAttribute("allTypes");
	Map timeToMoney = (Map) request.getAttribute("timeToMoney");
	Map yearAndTypeToMoney = (Map) request
			.getAttribute("yearAndTypeToMoney");
	Map yearToMoney = (Map) request
			.getAttribute("yearToMoney");  
	Map timeToInAndOut = (Map) request.getAttribute("timeToInAndOut");
	Map yearToInAndOut = (Map) request.getAttribute("yearToInAndOut");
	Map typeToPer = (Map) request.getAttribute("typeToPer");
	Map typeToMoney = (Map) request.getAttribute("typeToMoney");
	String nolinktitle = (String) request.getAttribute("nolinktitle");
	String allInAndOut = (String) request.getAttribute("allInAndOut");
	String bigType = (String) request.getAttribute("bigType");
	Map allTypeName = (Map) request.getAttribute("allTypeName");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<LINK rel=stylesheet type=text/css
			href="<%=appPath%>/js/common/red.css">
		<LINK rel=stylesheet type=text/css
			href="<%=appPath%>/jsp/report/easytable.css">
		<script language="javascript" type="text/javascript"
			src="<%=appPath%>/js/calendar/WdatePicker.js"></script>
		<script type='text/javascript'
			src='<%=appPath%>/jsp/report/detailReport.js'></script>
		<title>收支基础统计图</title>
	</head>
	<body onload='replaceSameYear()'>
		<%
				if (!"false".equals(session.getAttribute("traveler"))) {
				out.print("<h1>没有权限查看！</h1>");
				return;
			}
		%>
		<input type='hidden' id='path' value='<%=appPath%>' />
		<div style="height: 500px">
			小值：
			<input id='smallTag' value='1000' width="25px">
			<input value='9999999' id='bigTag' width="25px" style='display: none'>
			<button onclick="showColor()">
				标记
			</button>
			<button onclick="showAllSpan()">
				显示收支
			</button>
			<button   id='showStatisBtn'>
				仅显示统计
			</button>
			<table width="93%" height="100%" id='reportTable'>
				<thead>
					<th>
						年份
					</th>
					<th>
						月份
					</th>
					<%
							if (allTypes != null)
							for (int i = 0; i < allTypes.size(); i++) {
								String tp = ((String) allTypes.get(i));
								if ("true".equals(nolinktitle)) {
					%><th><%=allTypeName.get(tp) + "(" + tp + ")"%>
					</th>
					<%
					} else {
					%>
					<th>
						<a href='javascript:openType("<%=tp%>");'><%=allTypeName.get(tp) + "(" + tp + ")"%></a>
					</th>
					<%
							}
							}
					%>
				</thead>
				<tbody>
					<tr statis='1'>
						<td>
							<a href='javascript:openYearDetail("-1")'>全部</a>
							<br>
							<span time="all" style='display: none'><%=allInAndOut%></span>
						</td>
						<td></td>
						<%
							//输出全部的类型作为标题行.
							if (allTypes != null)
								for (int i = 0; i < allTypes.size(); i++) {
									String tp = ((String) allTypes.get(i));
						%>
						<td>
							<%=typeToMoney.get(tp) + "("
							+ typeToPer.get(tp) + "%)"%>
						</td>
						<%
						}
						%>
					</tr>
					<%
						String _lastYear = null;
						//按照时间输出全部的金额信息.
						if (allYearAndMonths != null)
							for (int i = 0; i < allYearAndMonths.size(); i++) {
								String yearAndMonth = (String) allYearAndMonths.get(i);
								String[] time = yearAndMonth.split(",");
								String _year = time[0];
								//输出年的总计..
								if (!_year.equals(_lastYear)) {
							_lastYear = _year;
							Double __i = (Double)yearToMoney.get(_year+"in");
							Double __o = (Double)yearToMoney.get(_year+"out"); 
					%><tr >
						<td><%=_year %>小计</td> 
						<td><%=__i==null?"-":Util.multiply(Util.divide(__o,__i,4),100)+"%"%></td> 
						<%
									for (int ii = 0; ii < allTypes.size(); ii++) {
									String _ty_ = (String) allTypes.get(ii); 
									Double m = (Double)yearAndTypeToMoney.get(_year
											+ "," + _ty_);
										
									Double sum = (Double)yearToMoney.get(_year); 
						%>
						<td>
						<%=m==null||sum==null?0:m+"("+Util.multiply(Util.divide(m,sum,4),100)+"%)"%></td>
						<%
						}
						%>
					</tr>
					<%
					}
					%>
					<tr commonData='1'>
						<td>
							<%
							if ("true".equals(nolinktitle)) {
							%>
							<%=time[0]%>
							<%
							} else {
							%><a href='javascript:openYearDetail("<%=time[0]%>")'><%=time[0]%>
							</a>
							<br>
							<span time="<%=time[0]%>" style='display: none'><%=yearToInAndOut.get(time[0])%></span>
							<%
							}
							%>
						</td>
						<td>
							<%
							if ("true".equals(nolinktitle)) {
							%>
							<a
								href='javascript:openMonthDetail2("<%=time[0]%>","<%=time[1]%>","<%=bigType%>")'><%=time[1]%>月</a>
							<br>
							<span time="<%=time[0]%>,<%=time[1]%>" style='display: none'><%=(String) timeToInAndOut.get(yearAndMonth)%></span>
						</td>
						<%
						} else {
						%>
						<a
							href='javascript:openMonthDetail1("<%=time[0]%>","<%=time[1]%>")'><%=time[1]%>月</a>
						<br>
						<span time="<%=time[0]%>,<%=time[1]%>" style='display: none'><%=(String) timeToInAndOut.get(yearAndMonth)%></span>
						</td>
						<%
						}
						%>
						<%
								for (int ii = 0; ii < allTypes.size(); ii++) {
								String tp = ((String) allTypes.get(ii));
								String money = (String) timeToMoney.get(yearAndMonth
										+ "," + tp);
						%>
						<td tag='true'
							title="<%=time[0]%>年<%=time[1]%>月--<%=allTypeName.get(tp)%>">
							<%
							if (money != null) {
							%>
							<%
							if ("true".equals(nolinktitle)) {
							%>
							<a
								href='javascript:openSmallDetail("<%=time[0]%>","<%=time[1]%>","<%=tp%>")'><%=money%></a>
							<%
							} else {
							%>
							<a
								href='javascript:openBigDetail("<%=time[0]%>","<%=time[1]%>","<%=tp%>")'><%=money%></a>
							<%
							}
							%>
							<%
							} else {
							%>
							0
							<%
							}
							%>
						</td>
						<%
						}
						%>
					</tr>
					<%
					}
					%>
				</tbody>
				</div>
	</body>
</html>
