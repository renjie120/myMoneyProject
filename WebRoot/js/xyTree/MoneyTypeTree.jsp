<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <title>收支类型选择树</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/js/xyTree/xyTree/xtree.css" />
    <!-- 按照很多高手建议，所有的文件都应UTF－8编码，我sorry，用GBK -->
    <!-- 为了使用无复选框树，无顺序 -->
    <script src="<%=basePath%>/js/xyTree/xyTree/TreeNormal.js"></script>
    <script src="<%=basePath%>/js/xyTree/xyTree/NodeNormal.js"></script>
    <script src="<%=basePath%>/js/xyTree/xyTree/DivTreeNormal.js"></script>

    <!-- 为了使用带复选框树，无顺序 -->
    <script src="<%=basePath%>/js/xyTree/xyTree/DivTree.js"></script>
    <script src="<%=basePath%>/js/xyTree/xyTree/Tree.js"></script>
    <script src="<%=basePath%>/js/xyTree/xyTree/Node.js"></script>
	
	<script language="javascript" src="<%=basePath%>/js/common/jquery-1.3.2.js"></script>
	<script language="javascript" src="<%=basePath%>/js/gridTree/hashMap.js"></script>
	<script language="javascript" src="<%=basePath%>/js/xyTree/xyTree/myXyTreeTool.js"></script>
    <script>	
    var Content = new xyTree.DivTreeNormal('收支类型');
    
	function init(){	
	
		$.ajax({
			type : "POST",
			url : "<%=basePath%>/typeConfig!getTallyTypeTree.action",
			async : true,
			success : function(msg) { 
					eval("var data="+msg);
					//下面使用自己的方法进行树节点的组装。
					colseArray(data,Content);					
		 			document.getElementById('ceshi3').appendChild(Content.div);
					Content.init(clickNode);
			}
		});		
	}
	
	//点击节点调用的回调函数。
	function clickNode(node){
	  var s =  node.typeCode +"$"+node.tallyTypeDesc;
	  window.returnValue = s;
	  window.close();
	}
	//定义初始化程序
	window.onload = init;	
	</script>    
  </head>  
  <body>
   <div id="ceshi3"></div><br />
  </body>
</html>
