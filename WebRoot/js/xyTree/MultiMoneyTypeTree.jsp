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
	<script language="javascript" src="<%=basePath%>/js/xyTree/xyTree/myMultiXyTreeTool.js"></script>
    <script>	
    var Content = new xyTree.DivTree('��֧����');
	function init(){	
		$.ajax({
			type : "POST",
			url : "<%=basePath%>/typeConfig!getTallyTypeTree.action",
			async : true,
			success : function(msg) {
					eval("var data="+msg);
					colseArray(data,Content);					
		 			document.getElementById('ceshi3').appendChild(Content.div);
		 			//����Ĳ���������Դ�����������������ϸ��ʾ��
					Content.init();
			}
		});		
	}
	
	//���ض�ѡ��ѡ�еĽڵ㡣
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
	
	//�����ʼ������
	window.onload = init;	
	</script>    
  </head>  
  <body>
   <div id="ceshi3"></div><br/>
   <div align="center">
   <button onclick="returnV()" style="BORDER: #7EBF4F 1px solid; height: 25px;">ȷ��</button>
   </div>
  </body>
</html>
