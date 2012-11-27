<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/jsp/Base.jsp"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>参数配置</title>
    <style type="text/css">  
     body{ font: 62.5% "Trebuchet MS", sans-serif; margin: 0px;}  
    .content{
    	background-color:#d6e3f7;
    }
    .content td{
    	font-size:12px;
    }
    </style>   
   </head>  
  <body>
   	<div class="content" style="display:inline;top:0px;left:0px;width:100%;height:100%;">
	<table>
		<tr><td><span style="color:red">功能说明</span></td><td></td></tr>
		<tr><td></td><td>1.理财系统有简单的权限控制，初始系统密码为1<br>
								2.添加收支信息【添加收支】，并提供查询功能(默认显示本月)【收支列表】<br>
								3.查询指定年份，指定月份的收入，支出详细图表【基本收支图表分析】<br>
								4.查询多个年份，或者同年多个月份之间的收支对比图表【收支对比图表分析】<br>
								5.对收支类别的维护【类型维护】<br>
								6.登陆系统，注销系统，以及登陆密码修改【系统管理】<br></td></tr>
		<tr><td colspan="2"><hr></td></tr>
		<tr><td><span style="color:red">友情链接</span></td><td></td></tr>
		<tr><td></td><td><a href='http://www.baidu.com' target='new'>百度</a><br>
		<a href='http://renjie120.appspot.com' target='new'>我的留言板</a><br>
		<a href='http://blog.sina.com/renjie120' target='new'>我的博客</a></td></tr>
		<tr><td colspan="2"><hr></td></tr>
		<tr><td><span style="color:red">其他说明</span></td><td></td></tr>
		<tr><td></td><td>1.系统某些地方对火狐实现不是很好，主要功能火狐ok，推荐使用ie。</td></tr>
	</table>		
	</div>
  </body>
</html>
