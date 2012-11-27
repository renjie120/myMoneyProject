var AAA = '';
$(function(){
	AAA = $('#path').val(); 
	$('#showStatisBtn').toggle(function(){
	 	$('tr[commonData=1]').hide();
	 	$('#showStatisBtn').html("��ʾȫ��");
	 },function(){
	 	$('tr[commonData=1]').show();
	 	$('#showStatisBtn').html("����ʾͳ��");
	 }) ;
});  

function showAllSpan()
{
	 $('span[time]').toggle() ; 
}
 
//������ɫ��ʶ
function showColor(){
	var small = $('#smallTag').val();
	var big = $('#bigTag').val(); 
	if(small==''||isNaN(small))  {$('#smallTag').val(0);small=0;}
	if(big==''||isNaN(big))  {$('#bigTag').val(9999999);big=9999999;}  
	$('td[tag=true]').each(function(){
	 	var num = $(this).text()*1.0; 
	 	$("a",this).css('color','black');
	 	if(num>small&&num<big){  
	 		$("a",this).css('color','red');
	 	}
	 });
}
//������ϸ�ı���չʾ��Ϣ.
function openType(type){
	location.href= AAA+'/detailReport!reportDetail.action?bigType='+type;
}

//���ĳһ������������֮����ת��С������ϸ����չʾҳ��.
function openBigDetail(year,month,type){  
	location.href= AAA+'/detailReport!reportDetailByTimeAndType.action?year='+year+"&month="+month
		+"&bigType="+type; 
}

//������ϸ��С�����Ϣ��ʱ��ı���.
function openSmallDetail(year,month,type){  
	openNewWin(AAA+'/detailReport!reportDetailByTimeAndSmallType.action?year='+year+"&month="+month
		+"&smallType="+type, "dialogWidth=850px;dialogHeight=400px;");  
} 

//����ͳ��ÿ���������֧��Ϣ.�õ�ÿ����������ļ��֧��Ϣ.
function openYearDetail(year){ 
	openNewWin(AAA
							+ "/detailReport!reportInYearPage.action?year="
							+ year, "dialogWidth=850px;dialogHeight=400px;"); 
}

//����ͳ��ÿ���������֧��Ϣ.�õ�ÿ����������ļ��֧��Ϣ.
function openMonthDetail1(year,month){ 
	openNewWin(AAA
							+ "/detailReport!reportInMonthPage.action?year="
							+ year+"&month="+month, "dialogWidth=850px;dialogHeight=400px;"); 
}

//�õ�һ�������ĳ������ͳ��������Ϣ.
function getEachMonthByType(year,type,typeName){
 	openNewWin(AAA
							+ "/detailReport!getEachMonthByType.action?year="
							+ year+"&smallType="+type+"&bigType="+typeName, "dialogWidth=850px;dialogHeight=400px;"); 
}

//����ͳ��ÿ���������֧��Ϣ.�õ�ÿ����������ļ��֧��Ϣ.
function openMonthDetail2(year,month,bigType){ 
	openNewWin(AAA
							+ "/detailReport!reportInMonthPage.action?year="
							+ year+"&month="+month+"&bigType="+bigType, "dialogWidth=850px;dialogHeight=400px;"); 
}

//������°�ť������������Ϣ��ҳ��.s
function update(id){
	var ans =openNewWin(AAA
							+ "/grid!beforeUpdateMoneyDetail.action?moneySno="
							+ id, "dialogWidth=400px;dialogHeight=300px;");
	if (ans != 'start')
				location.reload();
}

/**
  * willcheck:Ҫ���д���ı�����(�����еļ��ϼ���)
  * colnum:Ҫ���кϲ���Ԫ�����
  */
function coltogather(willcheck,colnum){ 
	var alltext = [],togotherNum = [],oldnum = [],id=1,lasttext=null,rmflag=1; 
	//���е����ݽ���ɨ��,�õ�����Ĳ��ظ�������,�Լ��������ݵ���Ŀ,Ȼ�����ݴ˽����������rowspan����
	willcheck.each(function(){
	      var _rmnum = this.getAttribute('rmnum');
		  if(!_rmnum)_rmnum=0;
	      var trdom= $('td:eq('+(colnum-_rmnum)+')',this)
		  var text = $(trdom).text();  
		  //�����һ�м�¼�ı�Ϊ��,˵���ǵ�һ��
		  if(lasttext==null) {
			   lasttext = text;
		  }else {
			  //�����ǰ�к���һ�м�¼һ��,˵��Ҫ���кϲ�,�ϲ��ĵ�Ԫ����Ŀ�ͼ�1
			  if(lasttext!=text){  
				   togotherNum.push(id);
				   lasttext = text;
				   id = 1;
			  } else{
			      id++;
			  }
		  }
		  
	 });   
	 togotherNum.push(id); 
	 //����allnum�����е����ݵ�oldnum����
	 $.each(togotherNum, function(i, n){
         oldnum[i] =n;
     }); 
     var index = 0,len = togotherNum.length;
	 //���н��д���,����ָ���е�rowspan����,�Լ���Ҫ�ϲ��ĵ�Ԫ��remove!
	 willcheck.each(function() {
				// �õ�һ�����Ա�ʾ�����Ѿ����Ƴ��˼���td
				var _rmnum = this.getAttribute('rmnum');
				if (!_rmnum)
					_rmnum = 0;
				var tddom = $('td:eq(' + (colnum - _rmnum) + ')', this)
				if (togotherNum[index] == oldnum[index]) {
					tddom.attr('rowSpan', togotherNum[index]);
					if(togotherNum[index]>1)
					  togotherNum[index] = togotherNum[index] - 1;
					 else
					   index++;
				} else { 
					if (togotherNum[index] == 0) {
						index++;
					    tddom.attr('rowSpan', togotherNum[index]);
					} else {
				    	tddom.remove(); 
						if(--togotherNum[index]==0){
							index++;
						}
					}
					// �Ƴ���td֮��,Ҫ��tr�������һ�����Ա�ʾ�Ѿ��Ƴ���td����Ŀ
					if (_rmnum == 0) {
						$(this).attr('rmnum', 1);
					} else {
						$(this).attr('rmnum', 1 + _rmnum * 1);
					}
				}
			});   
	 //�������
	 alltext = null;
	 togotherNum = null;
	 oldnum = null; 
}

function replaceSameYear(){
	coltogather($('#reportTable tr'),0); 
	showColor();
} 

//���ɾ����ť������ɾ����¼�Ĳ���.
function del(id){
	if (confirm("ȷ��ɾ����¼[" + id + "]?")) {
		$.ajax({
				url : AAA + "/grid!deleteMoney.action",
				data : "moneySno=" + id,
				type : 'POST',
				dataType : 'json',
				error : function(x, textStatus, errorThrown) {
					if (x.responseText.indexOf('�ɹ�') != -1) {
						alert(x.responseText);
						location.reload();
					}
				}
			});
	}
}