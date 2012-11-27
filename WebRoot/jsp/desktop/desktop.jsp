<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/jsp/Base.jsp"%>
<html>
<head> 
<title>我的桌面例子</title> 
    <!-- DESKTOP -->
    <script type="text/javascript" src="js/StartMenu.js"></script>
    <script type="text/javascript" src="js/TaskBar.js"></script>
    <script type="text/javascript" src="js/Desktop.js"></script>
    <script type="text/javascript" src="js/App.js"></script>
    <script type="text/javascript" src="js/Module.js"></script>
    <script type="text/javascript" src="myWindowSample.js"></script>

    <link rel="stylesheet" type="text/css" href="css/desktop.css" />
</head>
<body scroll="no">

<div id="x-desktop">
    <a href="http://extjs.com" target="_blank" style="margin:5px; float:right;"><img src="images/powered.gif" /></a>

    <dl id="x-shortcuts">
        <dt id="grid-win-shortcut">
            <a href="#"><img src="images/s.gif" />
            <div>表格展示</div></a>
        </dt>
        <!--dt id="acc-win-shortcut">
            <a href="#"><img src="images/s.gif" />
            <div>添加信息</div></a>
        </dt-->
        <dt id="self-win-shortcut">
            <a href="#"><img src="images/s.gif" />
            <div>添加理财信息</div></a>
        </dt>
    </dl>
</div>

<div id="ux-taskbar">
	<div id="ux-taskbar-start"></div>
	<div id="ux-taskbuttons-panel"></div>
	<div class="x-clear"></div>
</div>

</body>
</html>
