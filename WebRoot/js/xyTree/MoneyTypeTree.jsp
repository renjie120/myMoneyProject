<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <title>��֧����ѡ����</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/js/xyTree/xyTree/xtree.css" />
    <!-- ���պܶ���ֽ��飬���е��ļ���ӦUTF��8���룬��sorry����GBK -->
    <!-- Ϊ��ʹ���޸�ѡ��������˳�� -->
    <script src="<%=basePath%>/js/xyTree/xyTree/TreeNormal.js"></script>
    <script src="<%=basePath%>/js/xyTree/xyTree/NodeNormal.js"></script>
    <script src="<%=basePath%>/js/xyTree/xyTree/DivTreeNormal.js"></script>

    <!-- Ϊ��ʹ�ô���ѡ��������˳�� -->
    <script src="<%=basePath%>/js/xyTree/xyTree/DivTree.js"></script>
    <script src="<%=basePath%>/js/xyTree/xyTree/Tree.js"></script>
    <script src="<%=basePath%>/js/xyTree/xyTree/Node.js"></script>
	
	<script language="javascript" src="<%=basePath%>/js/common/jquery-1.3.2.js"></script>
	<script language="javascript" src="<%=basePath%>/js/gridTree/hashMap.js"></script>
	<script language="javascript" src="<%=basePath%>/js/xyTree/xyTree/myXyTreeTool.js"></script>
    <script>	
    var Content = new xyTree.DivTreeNormal('��֧����');
    
	function init(){	
	
		$.ajax({
			type : "POST",
			url : "<%=basePath%>/typeConfig!getTallyTypeTree.action",
			async : true,
			success : function(msg) { 
					eval("var data="+msg);
					//����ʹ���Լ��ķ����������ڵ����װ��
					colseArray(data,Content);					
		 			document.getElementById('ceshi3').appendChild(Content.div);
					Content.init(clickNode);
			}
		});		
	}
	
	//����ڵ���õĻص�������
	function clickNode(node){
	  var s =  node.typeCode +"$"+node.tallyTypeDesc;
	  window.returnValue = s;
	  window.close();
	}
	//�����ʼ������
	window.onload = init;	
	</script>    
  </head>  
  <body>
   <div id="ceshi3"></div><br />
  </body>
</html>
