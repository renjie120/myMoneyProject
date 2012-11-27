	$(function(){  
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
	
	document.onkeydown=defaultFun;
	function defaultFun(event){
		 event = event ? event : (window.event ? window.event : null);    
    	 if (event.keyCode==13){
    	 	//���û�о۽���ĳһ��Ԫ�ؾ�ִ��Ĭ�ϵķ���
    	 	if(!document.activeElement.getAttribute('id')){
	    	 	if($('#addType'))
	    	 		add();
	    	 	else
	    	 		update();
    	 	}
    	 }
	}
	
 	function add(){
 		var moneyDetail = {};
 		var oMoney = $('#money');
 		if(oMoney.val()==""){
 			oMoney.next().css({color:'red'}).text("����������!");
 			return ;
 		}
 		//�������ս�������ı�����ʾΪ����
 		else{
 			oMoney.next().text('');
 		}
 		var oMoneyType = $('#valueofsort');
 		var typedesc = $('#sortdesc');
 		if(oMoneyType.val()==""){
 			typedesc.next().next().css({color:'red'}).text("����ѡ�����!");
 			return ;
 		}else{
 			typedesc.next().next().text('');
 		}
 		var desc = $('#moneyDesc');
 		if(desc.val().LengthW()>100){
 			desc.next().css({color:'red'}).text("���ó���100�ַ�,���ڳ���Ϊ"+desc.val().LengthW()+"!");
 			return ;
 		}else{
 			desc.next().text('');
 		}
 		moneyDetail.money = oMoney.val();
 		moneyDetail.booktype = $('#booktype').val();
 		moneyDetail.splitMonths = $('#splitMonths').val();
 		moneyDetail.moneyTime = Date.fromString($('#moneyTime').val(),'yyyy-mm-dd');
 		moneyDetail.moneyType = $('#valueofsort').val();
 		moneyDetail.shopCard = $('#shopCard').val();
 		moneyDetail.moneyDesc = desc.val();
 		dwr.engine.setAsync(false);
 		dwrBo.saveMoneyDetail(moneyDetail,afterSave);
 	}
 	function afterSave(data){
 		//$('input').val('');
 		$('#moneyDesc').val('');
 		$('#shopCard').val('-1');
 		$('#mes').show().text(data).fadeOut(1000);
 		//var d = new Date();
	    //$('#moneyTime').val(d.asString('yyyy-mm-dd'));
 		window.returnValue = 'changed';
 	}
 	
 	function afterUpdate(data){
 		$('input').val('');
 		$('#moneySort').val('-1');
 		window.returnValue = 'changed';
 		window.close();
 	}
 	
 	function distill(){ 
 		$('button').attr('disabled',true);
 		$('#mes').show().text("������ȡ����Ⱥ�...");
 		dwrBo.distillFromServer(afterDistill);
 	}
 	
 	function afterDistill(msg){ 
 		$('#mes').show().text(msg).fadeOut(1000);
 		$('button').attr('disabled',false);
 	}
 	
 	function update(){ 
 		var moneyDetail = {};
 		var oMoney = $('#money');
 		if(oMoney.val()==""){
 			oMoney.next().css({color:'red',fontSize:12}).text("����������!");
 			return ;
 		}
 		//�������ս�������ı�����ʾΪ����
 		else{
 			oMoney.next().text('');
 		}
 		var oMoneyType = $('#moneySort');
 		if(oMoneyType.val()==""){
 			oMoneyType.next().css({color:'red',fontSize:12}).text("����ѡ�����!");
 			return ;
 		}else{
 			oMoneyType.next().text('');
 		}
 		var desc = $('#moneyDesc');
 		if(desc.val().LengthW()>300){
 			desc.next().css({color:'red',fontSize:12}).text("���ó���300�ַ�,���ڳ���Ϊ"+desc.val().LengthW()+"!");
 			return ;
 		}else{
 			desc.next().text('');
 		} 
 		moneyDetail.moneySno = $('#moneySno').val();
 		moneyDetail.booktype = $('#booktype').val();
 		moneyDetail.splitMonths = $('#splitMonths').val();
 		moneyDetail.money = oMoney.val();
 		moneyDetail.shopCard = $('#shopCard').val();
 		moneyDetail.moneyTime = Date.fromString($('#moneyTime').val(),'yyyy-mm-dd');
 		moneyDetail.moneyType = $('#valueofsort').val();
 		moneyDetail.moneyDesc = desc.val();
 		dwrBo.updateMoneyDetail(moneyDetail,afterUpdate);
 	}