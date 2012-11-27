<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String lastUrl = (String)request.getSession().getAttribute("lastUrl");
if(lastUrl!=null&&!"".equals(lastUrl))
	response.setHeader("Refresh","0;URL="+lastUrl);
%>
