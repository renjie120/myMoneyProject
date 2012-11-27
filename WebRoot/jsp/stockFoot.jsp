<%@ page language="java" import="java.util.*"%> 
<%
	String basePath = request.getContextPath(); 
%>
<style type="text/css">
body {
	margin: 0px;
	background-color: #d6e3f7;
}
.std td{
 width:50px;
 font-size:2;
}
</style> 
 
<script type="text/javascript"> 
 var appPath = "<%=basePath %>"; 
  var timel = null;	   
    $(function(){  
      timel = setInterval(function(){  
   		  see();
      },1000 );
    });
    
    function see(){
     var obj = $('#num'); 
	     $.ajax({
	        url : appPath+"/jsp/stock.jsp",
	        type : 'POST',
	        dataType : 'json',
	        error:function(x,textStatus, errorThrown){ 
	        	eval(x.responseText); 
	        	var tb = [];
	        	tb.push("<table id='stocks' class='std' ><tr><!--tr><td>名</td><td >价</td><td >赢</td><td >率</td><td >百万</td><td >时间</td></tr-->");
	        	for(var i=0,j=vars.length;i<j;i++){
		     		var elements=vars[i].split(",");  
		     		tb.push("<td>"+elements[0]+"</td>"); 
		        	tb.push("<td>"+elements[3]+"</td>");
		        	tb.push("<td>"+(elements[3]-elements[2]).toFixed(3)+"</td>");
		        	tb.push("<td>"+(100*(elements[3]-elements[2])/elements[2]).toFixed(1)+"%</td>");
		        	tb.push("<td>"+(elements[8]/1000000).toFixed(3)+"</td>");
		        	tb.push("<td>"+elements[31]+"</td>"); 
		        	//document.title=elements[0];
	        	}
	        	tb.push("</tr></table>");
	        	obj.html(tb.join(''));
	        	
	        }
	      });
    }
</script> 
<font size="2"> <span id='num'></span>
</font>
