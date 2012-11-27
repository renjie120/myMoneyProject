<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/jsp/Base.jsp"%>
<%@ page import="tallyBook.dao.TallyTypeDao"%>
<%@ page import="tallyBook.pojo.TallyType"%>
<%@ page import="tallyBook.dao.CommonSelDao"%>
<%@ page import="myOwnLibrary.util.Util"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	TallyTypeDao tallyTypeDao = new TallyTypeDao();
	CommonSelDao selDao = new CommonSelDao();
	List tallyTypes = selDao.getAllTallyTypes();
	List tallyTypes2 = tallyTypeDao
	.doGetAllTallyTypes("from tallyBook.pojo.TallyType where parentCode is null");
%>
<html>
	<head>
		<title>详细金额分析</title>
		<script type='text/javascript' src='<%=appPath%>/dwr/interface/dwrBo.js'></script>
		<script type='text/javascript' src='<%=appPath%>/jsp/moneyType/MoneyTypeShow.js'></script>
	<style type="text/css">  
    body{ font: 62.5% "Trebuchet MS", sans-serif;margin: 0px;}  
    .content{
    	background-color:#d6e3f7;
    }
    .content td{
    	font-size:12px;
    }
    #tallyTypeImg{
    	background: url(../../js/jquery_ui/themes/smoothness/images/ui-icons_cd0a0a_256x240.png);/* for IE6 */ 
    	width:20px;
    	height:20px;
    	background-position: -32px -128px;
    }
    </style> 
	</head>	
	<body>
		<span id="msg"><font size="2">请在左边选择要修改的类型</font></span>
		<input type="hidden" id="typeSno" value="<%=Util.notBlankStr(request.getParameter("typeSno"),"") %>"/>
		<input type="hidden" id="typeCode" value="<%=Util.notBlankStr(request.getParameter("typeCode"),"") %>"/>
		<input type="hidden" id="moneyTypeValue" value="<%=Util.notBlankStr(request.getParameter("moneyType"),"") %>"/>
		<input type="hidden" id="parentCodValue" value="<%=request.getParameter("parentCode") %>"/>
		<div class="content" style="display:inline;top:0px;left:0px;width:100%;height:100%;">
		<table>
			<tr>
				<td width="100px">
					类型名
				</td>
				<td width="150px">
					<input type="hidden" id="oldDesc" value="<%=Util.notBlankStr(request.getParameter("tallyTypeDesc"),"") %>"/>
					<input type="text" id="tallyTypeDesc" onblur="updateDesc();" value="<%=Util.notBlankStr(request.getParameter("tallyTypeDesc"),"") %>"/>
				</td>
			</tr>
			<tr>
				<td>
					上级类型
				</td>
				<td>					
					<select id="parentType" onchange="updateParent();">
					<option value="-1">请选择</option>
					<%
							for (int i = 0; i < tallyTypes2.size(); i++) {
							TallyType type = (TallyType) tallyTypes2.get(i);
							%>
								<option value="<%=type.getTypeCode()%>"><%=type.getTallyTypeDesc()%></option>
							<%
					}
					%>
				</select>
				</td>
			</tr>
			<tr>
				<td>
					收支类型
				</td>
				<td>					
					<select id="moneyType">
					<option value="-1">请选择</option>
					<option value="1">收入</option>
					<option value="2">支出</option>	
				</select>
				</td>
			</tr>
			<div style="display:none;">
				<select id="allmoneySort">
					<%
							for (int i = 0; i < tallyTypes.size(); i++) {
							TallyType type = (TallyType) tallyTypes.get(i);
					%>
						<option value="<%=type.getTypeCode()%>" type="<%=type.getMoneyType() %>" parent="<%=type.getParentCode() %>"><%=type.getTallyTypeDesc()%></option>
					<%
					}
					%>
				</select></div>
			<tr>
				<td colspan='2'>
					<button id='del' onclick="beforeDel();" style="display:none;">
						删除
					</button>
					<button id="ad" onclick="add();">
						新增
					</button>
					<button id="sv" onclick="save();" style="display:none;">
						保存
					</button>
					<button id="ad" onclick="refresh()">
						刷新
					</button>
				</td>
			</tr>
			<tr name="updateType" style="display:none;">
				<td colspan="2"><span id="delMsg"></span>
				<input type="checkbox" name="deleteDetail"  onclick="checkDelDetails();"><br>
				<button onclick="makesureDel();">确定删除</button>
				<button onclick="init();">取消删除</button></td></tr>
			<tr  name="updateType" style="display:none;"><td>修改类别为:</td>
				<td>				
				<input type='hidden' id='valueofsort' value='${moneySort}'/>
				<input type='text' id='sortdesc' value='${moneyTypeDesc}' readOnly = 'true'/>
				<button id='tallyTypeImg' onclick="getTallyTypeTree('valueofsort','sortdesc')"></button><span></span>
				</td>
			</tr>
			<tr><td colspan="2"><span id="msg2"  style="color:red;fontSize:5px;"></span></td></tr>
		</table>
		</div>
	</body>
</html>
