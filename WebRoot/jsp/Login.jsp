<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/jsp/Base.jsp"%>
<%@ page import="tallyBook.dao.CommonSelDao"%>
<%@ taglib uri="/WEB-INF/tld/self" prefix="myself"%>
<%@ page import="myOwnLibrary.taglib.OptionColl"%>
<%
	String basePath = request.getContextPath();
	if (request.getProtocol().compareTo("HTTP/1.0") == 0) {
		response.setHeader("Pragma", "no-cache");
	} else if (request.getProtocol().compareTo("HTTP/1.1") == 0) {
		response.setHeader("Cache-Control", "no-cache");
	}
	CommonSelDao selDao = new CommonSelDao(); 
	//ȫ�����˱�����
	OptionColl bookTypes = selDao.getAllBookTypes();
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript" src="<%=basePath%>/js/flexigrid_my/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/common/tools.js"></script>
		<script type="text/javascript" src="<%=basePath%>/jsp/Login.js"></script>
		<title>��½ϵͳ</title>
		<style type="text/css">
body {
	margin: 0px;
	background-color: #d6e3f7;
} 
</style>  
	</head>
	<body>
		<form action="<%=basePath%>/login!login.action" method="post" id="form1">
			<table>
				<%
				if (request.getSession().getAttribute("pass") == null) {
				%>
				<tr>
					<td style="font-size: 13px">
						�˱�
					</td>
					<td>
						 <myself:select selectColl="<%=bookTypes %>" tagName="bookType"></myself:select>
					</td>
				</tr>
				<tr>
					<td style="font-size: 13px">
						��ѯ����
					</td>
					<td>
						<input type="password" name="pass">
						<input type="checkbox" name="traveler" checked="checked" value="1">ֻ��֧��
						<button onclick="login()"
							style="BORDER: #7EBF4F 1px solid; height: 25px;">
							�ύ
						</button>
					</td>
				</tr> 
				<tr><td colspan='2'>�ƻ���<br>
				1.��Ӳ�ֽ��ı�ʶ(ok)<br>
				2.���Ԥ���ҳ�������ݲ�ѯ<br>
				3.��ӹ�Ʊ���׵Ĵ���(ok)<br>
				4.ת�Ƶ�dwz��ܣ������ƣ�Ȩ�� �˵� <br>
				5.���ѧϰgroovy���� CS�ܹ� grails<br>
				6.���������JBPM��java������spring��ܣ�ibatis���ԭ����<br>
				7.���֧��ռ����ٷֱ�(ok)<br>
				8.��ӱ���ɸѡĳЩ������Ŀ�Ĵ���<br></td></tr>
				<%
				} else {
				%>
				<tr><td colspan="2">
				<button onclick="quit();" style="BORDER: #7EBF4F 1px solid; height: 25px;">ע��ϵͳ</button>
				</td></tr>
				<tr>
					<td style="font-size: 13px">
						�޸�����(ԭ����)
					</td>
					<td>
						<input type="password" name="oldpass" onblur="beforeUpdate()"><span></span>
					</td>
				</tr>
				<tr>
					<td style="font-size: 13px">
						�����룺
					</td>
					<td>
						<input type="password" name="newPass" id="newPass"><span></span>
					</td>
				</tr>
				<tr>
					<td style="font-size: 13px">
						�ظ������룺
					</td>
					<td>
						<input type="password" name="reNewPwd" id="newPass"><span></span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<button onclick="update()"
							style="BORDER: #7EBF4F 1px solid; height: 25px;">
							�ύ
						</button>
					</td>
				</tr>
				<% 
				if("false".equals(session.getAttribute("traveler"))){
					Map map = (Map)session.getAttribute("yearMap");
					String in = (String)session.getAttribute("in");
					String ou = (String)session.getAttribute("out");
					String all = (String)session.getAttribute("all"); %>
				<tr>
					<td colspan="2" style='color:D6E3F7'>
						<h3>2013���ǰ�����ܹ�20���ֽ������ۻ��ܶ�Ϊ��<%=all %>���룺<%=in %>֧��:<%=ou %></h3>
						<h1>ʵʩ�ƻ���2012���ܹ�7���ֽ�2013���ܹ�7.8���ֽ�</h1>
						<h3>2012�Ѿ��ܹ���<%=map.get("2012") %>��</h3>
						<hr>
						<h4>2011��<%=map.get("2011") %>��</h4>
						<h4>2010��<%=map.get("2010") %>��</h4>
						<h4>2009��<%=map.get("2009") %>��</h4>
						<h4>2008��<%=map.get("2008") %>��</h4>
					</td>
				</tr>
				<%} %>
				<%
				}
				%>
			</table>			
		</form>
		<div id="msg" style="display: none;font-size: 13px"></div>   
	</body>
</html>
