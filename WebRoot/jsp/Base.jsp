<%@ page contentType="text/html;charset=GBK"%>
<%
	String appPath = request.getContextPath();
	 if(request.getProtocol().compareTo("HTTP/1.0")==0){
	  	response.setHeader("Pragma","no-cache");
	  }else if(request.getProtocol().compareTo("HTTP/1.1")==0){
	  	response.setHeader("Cache-Control","no-cache");
	  } 
	  response.setDateHeader("Expires",0);
%>

<!-- 禁止 windows 主题风格 -->
<meta http-equiv="MSThemeCompatible" content="no" />
<!-- 公用js -->
<script type='text/javascript' src='<%=appPath%>/js/common/tools.js'></script>

<!-- 引入ext -->
<LINK rel=stylesheet type=text/css href="<%=appPath%>/js/ext-2.0.2/resources/css/ext-all.css"/> 
<script type="text/javascript" src="<%=appPath%>/js/ext-2.0.2/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=appPath%>/js/ext-2.0.2/ext-all.js"></script> 

<!-- 引入jquery -->  
<script type="text/javascript" src="<%=appPath%>/js/flexigrid_my/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=appPath%>/js/common/jquery-ui-1.7.custom.min.js"></script> 
 

<!-- 引入highcharts --> 
<script type="text/javascript" src="<%=appPath%>/js/highcharts-2.2.5/highcharts.js"></script> 
<script type="text/javascript" src="<%=appPath%>/js/highcharts-2.2.5/modules/exporting.js"></script>

<!-- 引入一个公用的修改window.alert js文件 -->
<script type='text/javascript' src='<%=appPath%>/js/common/newAlert.js'></script>

<!-- 引入dwr -->
<script type='text/javascript' src='<%=appPath%>/dwr/engine.js'></script>
<!-- 引入fusionchart -->
<script type="text/javascript" src="<%=appPath%>/js/fusionchart/FusionCharts.js"></script>
<LINK rel=stylesheet type=text/css href="<%=appPath%>/js/fusionchart/Style.css"/>
<style type="text/css">  
    body{ font: 62.5% "Trebuchet MS", sans-serif; margin: 0px;}  
    .content{
    	background-color:#d6e3f7;
    }
    .content td{
    	font-size:12px;
    }
    button{
    	BORDER: #7EBF4F 1px solid; height: 25px
    }
    </style>   
<script type="text/javascript">
 var appPath = "<%=appPath %>"; 
 Ext.BLANK_IMAGE_URL = appPath+"/js/ext-2.0.2/resources/images/default/s.gif";
 $(function(){
 	$(window.body).css('margin','0px');
 });
 
 function debugObjectInfo(obj){
        traceObject(obj);
        
        function traceObject(obj){ 
                var str = '';
                if(obj.tagName&&obj.name&&obj.id)
                str="<table border='1' width='100%'><tr><td colspan='2' bgcolor='#ffff99'>traceObject 　　tag: &lt;"+obj.tagName+"&gt;　　 name = \""+obj.name+"\" 　　id = \""+obj.id+"\" </td></tr>"; 
                else{
                        str="<table border='1' width='100%'>"; 
                }
                var key=[]; 
                for(var i in obj){ 
                        key.push(i); 
                } 
                key.sort(); 
                for(var i=0;i<key.length;i++){ 
                        var v= new String(obj[key[i]]).replace(/</g,"&lt;").replace(/>/g,"&gt;"); 
                        str+="<tr><td valign='top'>"+key[i]+"</td><td>"+v+"</td></tr>"; 
                } 
                str=str+"</table>"; 
                writeMsg(str); 
        } 
        function trace(v){ 
                var str="<table border='1' width='100%'><tr><td bgcolor='#ffff99'>"; 
                str+=String(v).replace(/</g,"&lt;").replace(/>/g,"&gt;"); 
                str+="</td></tr></table>"; 
                writeMsg(str); 
        } 
        function writeMsg(s){ 
                traceWin=window.open("","traceWindow","height=600, width=800,scrollbars=yes"); 
                traceWin.document.write(s); 
        } 
}
</script>