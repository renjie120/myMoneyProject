<%@ include file="/jsp/Base.jsp"%>
<HTML>
<head>
<script type="text/javascript"
			src="<%=appPath%>/jsp/moneyType/MoneyTypeMain.js"></script>
<style type='text/css'>
.table{
	width:400;
	height:2; 
	background-color:red;
	border-color:yellow;
	border:0px;
}
</style>
</head>
<body style="overflow:show;margin:0px;" onload='init()'>
<TABLE  height="100%" width='100%'>
<TR >
	<TD style="margin:0px;"">
		<iframe id='left' src='MoneyTypeList.jsp' style="width:100%;height:100%;z-index: -1;border:0px;margin:0px;"  scrolling="no"></iframe>		
	</TD>
	<td style="margin:0px;width:0px;" id="mid">
		<div style='height:100%;background-color:red;width:8px;margin:0px;'
		  onmousedown="downToResize(this,event,'left','right');"
		  onmouseover="this.style.cursor='col-resize';"
		  onmousemove="moveToResize(this,event);"
		  onmouseout="this.style.cursor='default';"
		  onmouseup="upToResize(this,event,'left','right');"></div></td>
	<TD style="margin:0px;">
		<iframe id='right' src='MoneyTypeShow.jsp' style="height:100%;z-index: -1;width:100%;border:0px;margin:0px;"  scrolling="no" >
		</iframe></TD>
</TR>
</TABLE>
</body>
</HTML>