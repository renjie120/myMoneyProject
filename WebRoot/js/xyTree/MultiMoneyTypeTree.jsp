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
	<script language="javascript" src="<%=basePath%>/js/xyTree/xyTree/myMultiXyTreeTool.js"></script>
    <script>	
    var Content = new xyTree.DivTree('收支类型');
	function init(){	
		$.ajax({
			type : "POST",
			url : "<%=basePath%>/typeConfig!getTallyTypeTree.action",
			async : true,
			success : function(msg) {
					eval("var data="+msg);
					colseArray(data,Content);					
		 			document.getElementById('ceshi3').appendChild(Content.div);
		 			//下面的参数里面可以传入两个参数。。详细见示例
					Content.init();
			}
		});		
	}
	
	//返回多选框选中的节点。
	function returnV(){
	  var a =Content.getNodesAll();
	  var ans = '';
	  var ansCode = '';
	  if(a.length>0){
	  	forEach(a,function(ii,node){
	  		ans+=node.tallyTypeDesc;
			ansCode+=node.typeCode;
			if(ii<a.length-1){
				ans+=',';ansCode+=',';
			}
		});
	  }
	  window.returnValue = ansCode+"$"+ans;
	  window.close();
	}
	
	//定义初始化程序
	window.onload = init;	
	</script>    
  </head>  
  <body>
   <div id="ceshi3"></div><br/>
   <div align="center">
   <button onclick="returnV()" style="BORDER: #7EBF4F 1px solid; height: 25px;">确定</button>
   </div>
  </body>
</html>
