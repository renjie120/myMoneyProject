function login(){
	document.forms[0].submit();
	document.forms[0].target="_self";
}

$(function(){  
	   $('select').width(100);
 }); 
	 


document.onkeydown=defaultFun;
//默认的执行函数是点击【登陆】按钮
function defaultFun(event){
	 event = event ? event : (window.event ? window.event : null);    
   	 if (event.keyCode==13){
   	 	var pp = $('[name=pass]');
   	 	if(pp&&pp.text()!='')
   	 		login();
   	 }
}
	
function update(){
	var oldps = $('input[name=oldpass]');
	var nps = $('input[name=newPass]');
	var rnps = $('input[name=reNewPwd]');
	var ms = $('#msg');
	if(oldps.val()==''){
		oldps.next().show().css({color:'red'}).text("输入旧密码！").fadeOut(1000);
		oldps[0].focus();
		return ;
	}
	if(nps.val()==''){
		nps.next().show().css({color:'red'}).text("输入新密码！").fadeOut(1000);
		nps[0].focus();
		return ;
	}
	if(nps.val().LengthW()>15){
		nps.next().css({color:'red'}).text("密码不得超出15位!").fadeOut(1000);
		nps[0].focus();
		return ;
	}else{
		nps.next().text('');
	}
	if(nps.val()!=rnps.val()){
		rnps.next().show().css({color:'red'}).text("两次输入密码不一样").fadeOut(1000);
		rnps[0].focus();
		return ;
	}
	
	var queryStr = '?pass='+nps.val();
	$.ajax({
        url : appPath+"/login!repassword.action"+queryStr,
        type : 'POST',
        dataType : 'json',
        error:function(x,textStatus, errorThrown){
        	ms.show().css({color:'red'}).text("密码修改成功").fadeOut(1000);
        }
      });
}

function beforeUpdate()
{
	var oldps = $('input[name=oldpass]');
	var queryStr = '?pass='+oldps.val();
	$.ajax({
       url : appPath+"/login!beforeUpdate.action"+queryStr,
       type : 'POST',
       dataType : 'json',
       error:function(x,textStatus, errorThrown){
       		if(x.responseText!=''){
	       		oldps.next().show().css({color:'red'}).text(x.responseText).fadeOut(1000); 	
	       		oldps[0].focus();
       		}
       }
     });
}

function quit(){
	$.ajax({
        url : appPath+"/login!logout.action",
        type : 'POST',
        dataType : 'json',
        error:function(x,textStatus, errorThrown){
        	window.location = appPath+"/jsp/Login.jsp";  	
        }
      });
} 