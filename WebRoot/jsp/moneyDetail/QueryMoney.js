$(function() {
			pW = window.dialogArguments;
			$("#submit").unbind("click"); 
			//Ĭ��Ϊֻ��ʾ������ݻ����·ݲ�ѯ��¼
			$('#minTime').hide().val('');
			$('#maxTime').hide().val('');
			//�����Ƿ�������������ʱ����ʼʱ��Σ�������ѡ����ݻ����·�.
			$('input[type=checkbox]').click(function(){
				$('#yearId').attr('disabled',!this.checked).val('-1');
				$('#monthId').attr('disabled',!this.checked).val('-1');
				//�������ѡ��ʱ�����ʾ��������ʱ�����Сʱ��
				if(!this.checked){ 
					$('#minTime').show().val('');
					$('#maxTime').show().val('');
				}else{
					$('#minTime').hide().val('');
					$('#maxTime').hide().val('');
				}
			});
			$("#submit").click(function() {
				var moneySortValue = $('#valueofsort').val();
				var inoroutrealValue = $('#inOrOutType').val();
				if (inoroutrealValue == '-1') {
					inoroutrealValue = '';
				} 
				var vMintime = $('#minTime').val();
				var vMaxTime = $('#maxTime').val();
				var vyear = $('#yearId').val();
				var vmonth = $('#monthId').val();
				var vMaxmoney = $('#maxmoney').val();
				var vCard = $('#shopCard').val();
				var vInOrOutTypeValue = inoroutrealValue;
				var vMoney = $('#money').val();
				var vMoneySortValue = $('#valueofsort').val();
				var vMoneyDesc = $('#moneyDesc').val();
				if (vMintime != '' || vMaxTime != '' || vmonth != '-1' ||vyear != '-1' ||vMaxmoney != ''
						|| vInOrOutTypeValue != '' || vMoney != ''
						|| vMoneySortValue != '' || vMoneyDesc != ''
						|| vCard != '') { 
					var queryStr = '?minTime=' + vMintime + '&maxTime='
							+ vMaxTime + '&maxmoney=' + vMaxmoney
							+ '&year=' + vyear
							+ '&month=' + vmonth
							+ '&shopCard=' + vCard + '&inOrOutTypeValue='
							+ vInOrOutTypeValue + '&money=' + vMoney
							+ '&moneySort=' + vMoneySortValue + '&moneyDesc='
							+ vMoneyDesc;
					pW.location.href = window.path + '/grid!reQuery.action' + queryStr;
				}   
				window.close(); 
			});
		});  