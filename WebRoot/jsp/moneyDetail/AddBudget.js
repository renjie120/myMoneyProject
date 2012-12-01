	$(function(){   
		   window.returnValue = 'start'; 
		   window.path = appPath;    
	 });  
	var queryStr = '';
	document.onkeydown=defaultFun;
	function defaultFun(event){
		 event = event ? event : (window.event ? window.event : null);    
    	 if (event.keyCode==13){
    	 	//如果没有聚焦到某一个元素就执行默认的方法
    	 	if(!document.activeElement.getAttribute('id')){
	    	 	if($('#addType'))
	    	 		add();
	    	 	else
	    	 		update();
    	 	}
    	 }
	}
	
	function resetChooseReport(){
	}
	function test(idDom, nameDom) { 
		var ans = openNewWin(appPath + "/js/xyTree/MultiMoneyTypeTree.jsp",
				"Maximize=yes;center=yes;dialogWidth=300px;dialogHeight=300px;help=no");
		var arr = [];
		if (typeof ans != 'undefined') {
			if (ans != 'undefined' && ans.indexOf('$') != -1) {
				arr = ans.split('$');
				document.getElementById(idDom).value = arr[0];
				document.getElementById(nameDom).value = arr[1];
			}
		}
	}

	function getQueryStr() {
		var budgetName = $('#budgetName').val();
		var moneyType = $('#moneyType').val();  
		queryStr = '?budgetName=' + budgetName + '&moneyType=' + moneyType ;
	}
	
 	function add(){ 
 		var ms = $('#mes');
 		if($('#budgetName').val()==''||$('#moneyType').val()==''){
 			alert('必填信息未填写！');
 			return false;
 		}
 		getQueryStr();  
 		$.ajax({
        url : appPath+"/BudgetAction!updateBudgetMoneyTypeRel.action"+queryStr,
        type : 'POST', 
        dataType : 'json',
        success:function(x){
        	alert(x)
        },
        error:function(x,textStatus, errorThrown){
	        	ms.show().css({color:'red'}).text(x.responseText).fadeOut(2000);
	        	location.reload();
	        }
	    });
 	}
 	
 	function weishenme(y,m,obj){ 
 			if(confirm("确定要删除么？")){
			var _y = y*1;
 			var _m =  m*1;   
			 $.ajax({
					url : appPath+"/BudgetAction!deleteBudgetDetail.action?year="+_y+"&month="+_m, 
					success:function(x){ 
					     $('tr[trid='+obj+']').remove();
					} 
				});  
			}
 	}
 	
 	function addDetail(){    
 		var queryStr = [];
 		$('#budgettypes input').each(function(){
 			queryStr.push($(this).attr('name')+","+$(this).val()+";");
 		});   
		queryStr.push("&year="+$('#year').val()+"&month="+$('#month').val());
 		 $.ajax({
	        url : appPath+"/BudgetAction!saveBudgetDetail.action?budgetName="+queryStr.join(''), 
	        success:function(x){
	        	alert(x); 
				location.reload();
	        } 
	    });  
 	}
 	
 	
 	function afterSave(data){
 		$('#budgetName').val('');
 		$('#moneyType').val('');
 		$('#moneyTypeDesc').val(''); 
 		window.returnValue = 'changed';
 	}
 	
 	function afterUpdate(data){
 		$('#budgetName').val('');
 		$('#moneyType').val('');
 		$('#moneyTypeDesc').val(''); 
 		window.returnValue = 'changed';
 		window.close();
 	}
 	
 	function del(){
		 var ms = $('#mes');
 		if($('input[type=checkbox]:checked').size()<1)
 			alert('请选择要删除的项！')
 		else{
 			if(confirm("确定要删除么？")){
 				 $.ajax({
			        url : appPath+"/BudgetAction!deleteBudgetMoneyTypeRel.action?budgetId="+getCheckedItemValue('ck'),
			        type : 'POST',
			       // dataType : 'json',
			        success:function(x){
						ms.show().css({color:'red'}).text(x).fadeOut(1000);
			        	location.reload();
			        },
				     error:function(x,textStatus, errorThrown){
				        	ms.show().css({color:'red'}).text(x.responseText).fadeOut(1000);
				        	location.reload();
				        }
				    }); 
 			}
 		} 
 	}
 	
 	function getCheckedItemValue(nm){
 		var result = '';
 		$('[name='+nm+"]:checked").each(function(){
 			result+=","+this.value;
 		}); 
 		result = result.substr(1);
 		return result
 	} 
 	function update(){  
 		var ms = $('#mes');
 		getQueryStr(); 
 		$.ajax({
        url : appPath+"/BudgetAction!updateBudgetMoneyTypeRel.action"+queryStr,
        type : 'POST',
        dataType : 'json',
        error:function(x,textStatus, errorThrown){
	        	ms.show().css({color:'red'}).text(x.responseText).fadeOut(1000);
	        }
	    });
 	}