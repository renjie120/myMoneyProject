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
	//全部的账本类型
	OptionColl bookTypes = selDao.getAllBookTypes();
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript" src="<%=basePath%>/js/flexigrid_my/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/common/tools.js"></script>
		<script type="text/javascript" src="<%=basePath%>/jsp/Login.js"></script>
		<title>登陆系统</title>
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
						账本
					</td>
					<td>
						 <myself:select selectColl="<%=bookTypes %>" tagName="bookType"></myself:select>
					</td>
				</tr>
				<tr>
					<td style="font-size: 13px">
						查询密码
					</td>
					<td>
						<input type="password" name="pass">
						<input type="checkbox" name="traveler" checked="checked" value="1">只看支出
						<button onclick="login()"
							style="BORDER: #7EBF4F 1px solid; height: 25px;">
							提交
						</button>
					</td>
				</tr> 
				<tr><td colspan='2'>计划：<br>
				1.添加拆分金额的标识(ok)<br>
				2.添加预算的页面根据年份查询<br>
				3.添加股票交易的处理(ok)<br>
				4.转移到dwz框架！逐步完善：权限 菜单 <br>
				5.坚持学习groovy测试 CS架构 grails<br>
				6.工作流框架JBPM，java基础，spring框架，ibatis框架原理！！<br>
				7.添加支出占收入百分比(ok)<br>
				8.添加报表筛选某些大类项目的处理<br></td></tr>
				<%
				} else {
				%>
				<tr><td colspan="2">
				<button onclick="quit();" style="BORDER: #7EBF4F 1px solid; height: 25px;">注销系统</button>
				</td></tr>
				<tr>
					<td style="font-size: 13px">
						修改密码(原密码)
					</td>
					<td>
						<input type="password" name="oldpass" onblur="beforeUpdate()"><span></span>
					</td>
				</tr>
				<tr>
					<td style="font-size: 13px">
						新密码：
					</td>
					<td>
						<input type="password" name="newPass" id="newPass"><span></span>
					</td>
				</tr>
				<tr>
					<td style="font-size: 13px">
						重复新密码：
					</td>
					<td>
						<input type="password" name="reNewPwd" id="newPass"><span></span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<button onclick="update()"
							style="BORDER: #7EBF4F 1px solid; height: 25px;">
							提交
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
						<h3>2013年底前至少攒够20万现金！现在累积总额为：<%=all %>收入：<%=in %>支出:<%=ou %></h3>
						<h1>实施计划：2012年攒够7万现金，2013年攒够7.8万现金！</h1>
						<h3>2012已经攒够：<%=map.get("2012") %>！</h3>
						<hr>
						<h4>2011：<%=map.get("2011") %>！</h4>
						<h4>2010：<%=map.get("2010") %>！</h4>
						<h4>2009：<%=map.get("2009") %>！</h4>
						<h4>2008：<%=map.get("2008") %>！</h4>
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
