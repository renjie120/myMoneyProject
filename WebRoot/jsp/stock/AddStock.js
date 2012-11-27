	$(function(){  
		   var d = new Date();
	       $('#dealTime').val(d.asString('yyyy-mm-dd'));
		   $('select').width($('#dealPrice').width());
		   window.returnValue = 'start';
		   //������	            
	       if($('#status').val()=='true'){
	       		var d = new Date();
	       		$('#moneyTime').val(d.asString('yyyy-mm-dd'));
	       }
	       //���²���
	       else{
	       	$('#shopCard option[value='+$('#shopCardValue').val()+']').attr('selected',true);
	       }
	 });  
	
	 //����ı���ֻ������������
	 function checkOnlyNum(obj){
        obj.value = obj.value.replace(/[\D]/g,''); 
        var num = obj.value*100;
        $('#dealStockNum').val(num);
        $('#shownum').html(num+' ��'); 
	 }

	//���ݹ�Ʊ�����˵����ù�Ʊ�����ƺ�id.
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
    	 	//���û�о۽���ĳһ��Ԫ�ؾ�ִ��Ĭ�ϵķ���
    	 	if(!document.activeElement.getAttribute('id')){
    	 		//�����addType���dom�����Զ�ִ��add����.
	    	 	if($('#addType'))
	    	 		add();
	    	 	else
	    	 		update();
    	 	}
    	 }
	}
	
	/****�����ǹ��߷���������������������������**/
	/**������ļ�飬���û����д�����ú����������ʾ.
	 * idstr������id  mustStr��û����Ļ�����ʾ��Ϣ
	 */
	function mustSet(idstr,mustStr){
		var dom = $('#'+idstr);
 		if(dom.val()==""){
 			dom.next().css({color:'red'}).text(mustStr);
 			return false;
 		}
 		//�������ս�������ı�����ʾΪ����
 		else{
 			dom.next('span:first').text('');
 			return true;
 		}
	}
	/**�����󳤶Ȳ��ó�������
	   mustShoter('aaa',100,'aaa�ĳ��Ȳ����Գ���100��')
	 */
	function mustShoter(idstr,maxlen,mustStr){
		var dom = $('#'+idstr);
 		if(dom.val().LengthW()>maxlen){
 			dom.next().css({color:'red'}).text(mustStr);
 			return false;
 		}
 		//�������ս�������ı�����ʾΪ����
 		else{
 			dom.next().text('');
 			return true;
 		}
	}
	
	//��ӹ�Ʊ��Ϣ
 	function add(){ 
 		var stockDetail = {};  
 		if($('#stockselect').val()==-1){
 			alert('����ѡ���Ʊ��Ϣ��');
 			$('#stockselect').focus();
 			 return ;
 		} 
 		if(!mustSet('dealPrice','���׼۸���')){
 			 return ;
 		}
 		if(!mustSet('dealStockNum','���׹������')){
 			 return ;
 		} 
 		if(!mustSet('dealTime','����ʱ����')){
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
 		if(!mustSet('dealStockCode','��Ʊ�ű��')){
 			 return ;
 		}
 		if(!mustSet('dealStockName','��Ʊ���Ʊ��')){
 			 return ;
 		}
 		if(!mustSet('dealPrice','���׼۸���')){
 			 return ;
 		}
 		if(!mustSet('dealStockNum','���׹������')){
 			 return ;
 		} 
 		if(!mustSet('dealTime','����ʱ����')){
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