	$(function(){  
		   var d = new Date();
	       $('#dealTime').val(d.asString('yyyy-mm-dd'));
		   $('select').width($('#dealPrice').width());
		   window.returnValue = 'start';
		   //是新增	            
	       if($('#status').val()=='true'){
	       		var d = new Date();
	       		$('#moneyTime').val(d.asString('yyyy-mm-dd'));
	       }
	       //更新操作
	       else{
	       	$('#shopCard option[value='+$('#shopCardValue').val()+']').attr('selected',true);
	       }
	 });  
	
	 //检查文本框只允许填入数字
	 function checkOnlyNum(obj){
        obj.value = obj.value.replace(/[\D]/g,''); 
        var num = obj.value*100;
        $('#dealStockNum').val(num);
        $('#shownum').html(num+' 股'); 
	 }

	//根据股票下拉菜单设置股票的名称和id.
	function setStock(){ 
		var vv = $('#stockselect');
		if(vv.val()!=-1){
			$('#dealStockName').val($('option[selected=true]',vv[0]).text()); 
			$('#dealStockCode').val(vv.val()); 
		}else{
			$('#dealStockName').val(''); 
			$('#dealStockCode').val(''); 
		}
	}
	document.onkeydown=defaultFun;
	function defaultFun(event){
		 event = event ? event : (window.event ? window.event : null);    
    	 if (event.keyCode==13){
    	 	//如果没有聚焦到某一个元素就执行默认的方法
    	 	if(!document.activeElement.getAttribute('id')){
    	 		//如果有addType这个dom，就自动执行add方法.
	    	 	if($('#addType'))
	    	 		add();
	    	 	else
	    	 		update();
    	 	}
    	 }
	}
	
	/****下面是工具方法。。。。。。。。。。。。**/
	/**必填项的检查，如果没有填写就设置后面的文字提示.
	 * idstr：必填id  mustStr：没有填的话，提示信息
	 */
	function mustSet(idstr,mustStr){
		var dom = $('#'+idstr);
 		if(dom.val()==""){
 			dom.next().css({color:'red'}).text(mustStr);
 			return false;
 		}
 		//否则就清空金额后面的文本框显示为‘’
 		else{
 			dom.next('span:first').text('');
 			return true;
 		}
	}
	/**检查最大长度不得超过多少
	   mustShoter('aaa',100,'aaa的长度不可以超过100！')
	 */
	function mustShoter(idstr,maxlen,mustStr){
		var dom = $('#'+idstr);
 		if(dom.val().LengthW()>maxlen){
 			dom.next().css({color:'red'}).text(mustStr);
 			return false;
 		}
 		//否则就清空金额后面的文本框显示为‘’
 		else{
 			dom.next().text('');
 			return true;
 		}
	}
	
	//添加股票信息
 	function add(){ 
 		var stockDetail = {};  
 		if($('#stockselect').val()==-1){
 			alert('必须选择股票信息！');
 			$('#stockselect').focus();
 			 return ;
 		} 
 		if(!mustSet('dealPrice','交易价格必填！')){
 			 return ;
 		}
 		if(!mustSet('dealStockNum','交易股数必填！')){
 			 return ;
 		} 
 		if(!mustSet('dealTime','交易时间必填！')){
 			 return ;
 		}    
 		stockDetail.dealPrice = $('#dealPrice').val();  
 		stockDetail.dealStockCode = $('#dealStockCode').val(); 
 		stockDetail.dealStockName = $('#dealStockName').val();  
 		stockDetail.stockTypes = $('#dealType').val();
 		stockDetail.dealStockNum = $('#dealStockNum').val(); 
 		stockDetail.dealTime =Date.fromString($('#dealTime').val(),'yyyy-mm-dd'); 
 		stockDetail.configType = $('#configType').val();  
 		stockDetail.dealIdea = $('#dealIdea').val();
 		stockDetail.happyNum = $('#happyNum').val(); 
 		stockDetail.dealType = $('#dealType').val();  
 		stockDetail.dealTinkPrice = $('#dealTinkPrice').val();  
 		stockBo.saveStock(stockDetail,afterSave);  
 	}  
 	
 	function afterSave(data){
 		$('input').val('');
 		$('#dealPrice').val(''); 
 		$('#dealTinkPrice').val('');
 		$('#dealIdea').val(''); 
 		$('#mes').show().text(data).fadeOut(1000);
 		var d = new Date();
	    $('#dealTime').val(d.asString('yyyy-mm-dd'));
 		window.returnValue = 'changed';
 	}
 	
 	function afterUpdate(data){
 		$('input').val('');
 		$('#dealPrice').val('');
 		$('#dealStockName').val('');
 		$('#dealStockNum').val('');
 		$('#dealTinkPrice').val('');
 		$('#dealIdea').val('');  
 		$('#mes').show().text(data).fadeOut(1000);
 		var d = new Date();
	    $('#dealTime').val(d.asString('yyyy-mm-dd'));
 		window.returnValue = 'changed';
 		window.close();
 	}
 	
 	function query(){
 		location.href =  'StockList.jsp';
 	}
 	
 	function update(){
 		var stockDetail = {}; 
 		if(!mustSet('dealStockCode','股票号必填！')){
 			 return ;
 		}
 		if(!mustSet('dealStockName','股票名称必填！')){
 			 return ;
 		}
 		if(!mustSet('dealPrice','交易价格必填！')){
 			 return ;
 		}
 		if(!mustSet('dealStockNum','交易股数必填！')){
 			 return ;
 		} 
 		if(!mustSet('dealTime','交易时间必填！')){
 			 return ;
 		}   
 		stockDetail.dealPrice = $('#dealPrice').val();  
 		stockDetail.dealStockCode = $('#dealStockCode').val(); 
 		stockDetail.dealStockName = $('#dealStockName').val(); 
 		stockDetail.stockTypes = $('#dealType').val();
 		stockDetail.dealStockNum = $('#dealStockNum').val(); 
 		stockDetail.dealTime =Date.fromString($('#dealTime').val(),'yyyy-mm-dd'); 
 		stockDetail.configType = $('#configType').val(); 
 		stockDetail.dealIdea = $('#dealIdea').val();
 		stockDetail.happyNum = $('#happyNum').val(); 
 		stockDetail.dealType = $('#dealType').val(); 
 		stockDetail.dealTinkPrice = $('#dealTinkPrice').val();  
 		stockBo.updateStock(stockDetail,afterUpdate);  
 	}