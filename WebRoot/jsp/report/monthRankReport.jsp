<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/jsp/Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String xml = (String) request.getAttribute("xml");
	String sum = (String) request.getAttribute("sum");
	String smallType = (String) request.getAttribute("smallType");
	String year = (String) request.getAttribute("year");
	List newTopN = (List) request.getAttribute("newTopN");
%>
<html>
	<head>
		<title>�¶������������ͼ</title>
		<LINK rel=stylesheet type=text/css
			href="<%=appPath%>/jsp/report/easytable.css">
		<script type='text/javascript'
			src='<%=appPath%>/jsp/report/detailReport.js'></script>
	</head>
	<body style="fontSize: 10px;">
		<div id="chartdiv" style='float: left; width: 300px; height: 300px;'></div>
		<div id="topndiv" style='float: right; width: 400px; height: 300px;'>
			<table width='100%'>
				<thead>
					<th>
						�¶�
					</th>
					<th>
						���
					</th>
					<th>
						�ٷֱ�
					</th>
					<th>
						��ֵ
					</th>
					<th>
						��Ŀ
					</th>
				</thead>
				<tbody>
				<tr>
						<td colspan="5">�ܼƣ�<%=sum %>
						</td></tr>
					<%
							if (newTopN != null) {
							for (int i = 0; i < newTopN.size(); i++) {
								Object[] objs = (Object[]) newTopN.get(i);
					%>
					<tr>
						<td>
							<%
							if ("-1".equals(year)) {
							%>
							<a
								href='javascript:openSmallDetail("<%=objs[6]%>","<%=objs[1]%>","<%=smallType%>")'>
								<%=objs[6]%>��<%=objs[1]%>��</a>
							<%
							} else {
							%><a
								href='javascript:openSmallDetail("<%=objs[6]%>","<%=objs[1]%>","<%=smallType%>")'><%=objs[1]%>
								</a>
							<%
							}
							%>
						</td>
						<td><%=objs[2]%></td>
						<td><%=objs[4] + "%"%></td>
						<td><%=objs[3]%></td>
						<td><%=objs[5]%></td>
					</tr>
					<%
						}
						}
					%>
				</tbody>
			</table>
		</div>
		<script language="JavaScript"> 
		var  xmlstr= "<%=xml%>";
        var chart1 = new FusionCharts("<%=appPath%>/js/fusionchart/Charts/Bar2D.swf", "fusionchart", "400", "300", "0", "1"); 
        chart1.setDataXML("<%=xml%>");
        chart1.render("chartdiv");
   </script>
	</body>
</html>
