<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/jsp/Base.jsp"%>
<%@ page import="myOwnLibrary.util.*"%>
<%
	List details = (List) request.getAttribute("details");
	List sum = (List) request.getAttribute("sum");
	String year = (String) request.getAttribute("year");
	String month = (String) request.getAttribute("month");
	String type = (String) request.getAttribute("type");
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
		<title>��֧����ͳ��ͼ</title>
	</head>
	<body>
	<input type='hidden' id='path' value='<%=appPath%>'/>
		<div style="height: 500px">
			<table width="93%">
				<thead>
					<th width="30px">
						���
					</th>
					<th width="100px">
						ʱ��
					</th>
					<th width="50px">
						���
					</th>
					<th>
						����
					</th>
					<th width="80px">
						����
					</th> 
					<th width="100px">
						����
					</th> 
				</thead>
				<tbody>
					<tr>
						<td colspan="6">
							<%=year %>��<%=month %>��&nbsp;&nbsp;&nbsp;�ܼƣ�<%=sum.get(0)%>&nbsp;&nbsp;&nbsp; </td>
					</tr>
					<%
							if (details != null)
							for (int i = 0; i < details.size(); i++) {
								Object[] results = (Object[]) details.get(i);
					%>
					<tr style="height: 20px">
						<td><%=i + 1%></td>
						<td><%=results[0]%></td>
						<td><%=results[1]%></td>
						<td><%=Util.notBlank((String) results[2], "-")%></td>
						<td><%=results[3]%></td>
						<td><button onclick='update("<%=results[4]%>")'>�޸�</button><button onclick='del("<%=results[4]%>")'>ɾ��</button></td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</body>
</html>
