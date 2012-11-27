var queryStr = '';
var sumIn = 0.0;
var sumOut = 0.0;
function getQueryStr() {
	var vMintime = $('#minTime').val();
	var vMaxTime = $('#maxTime').val();
	var vMaxmoney = $('#maxmoney').val();
	var vInOrOutTypeValue = $('#inOrOutTypeValue').val();
	var vMoney = $('#money').val();
	var vCard = $('#shopCard').val();
	var vMoneySortValue = $('#moneySortValue').val();
	var vMoneyDesc = $('#moneyDesc').val();

	queryStr = '?minTime=' + vMintime + '&maxTime=' + vMaxTime + '&maxmoney='
			+ vMaxmoney + '&shopCard=' + vCard + '&inOrOutTypeValue='
			+ vInOrOutTypeValue + '&money=' + vMoney + '&moneySort='
			+ vMoneySortValue + '&moneyDesc=' + vMoneyDesc;
	 
}

$(function() {
	getQueryStr();

	window.path = $('#path').val();
	$("#flex1").flexigrid({
		url : window.path + '/grid!initMoneyDetailGrid.action' + queryStr,
		dataType : 'json',
		colModel : [{
					display : '���',
					name : 'moneySno',
					width : 100,
					align : 'center'
				}, {
					display : 'ʱ��',
					name : 'moneyTime',
					width : 100,
					align : 'center'
				}, {
					display : '���',
					name : 'money',
					width : 100,
					align : 'center',
					sortable : true
				}, {
					display : '���',
					name : 'moneyType',
					width : 100,
					align : 'left'
				}, {
					display : '����',
					name : 'moneyDesc',
					width : 350,
					align : 'left'
				}, {
					display : '���ѿ�',
					name : 'shopCardStr',
					width : 50,
					align : 'center'
				}, {
					display : '����',
					name : 'inorout',
					width : 50,
					align : 'center'
				}],
		searchitems : [{
					display : 'ʱ��',
					name : 'moneyTime'
				}, {
					display : '���',
					name : 'money',
					isdefault : true
				}, {
					display : '���',
					name : 'moneyType'
				}, {
					display : '����',
					name : 'moneyDesc'
				}],
		buttons : [{
					name : 'ɾ��',
					bclass : 'delete',
					onpress : testButton
				}, {
					name : '�޸�',
					bclass : 'edit',
					onpress : testButton
				}, {
					name : '�ز�',
					bclass : 'add',
					onpress : testButton
				}, {
					name : '�ۺϲ�ѯ',
					bclass : 'add',
					onpress : testButton
				}, {
					name : '���ü�¼',
					bclass : 'add',
					onpress : testButton
				}, {
					name : '����',
					bclass : 'add',
					onpress : testButton
				}],
		usepager : true,
		title : '��֧��Ϣ��ѯ�б�',
		useRp : true,
		modal : true,// ʹ���ɰ�
		page : 1,
		rp : 40,
		pagestat : '��ʾ��{from}��{to}�� �ܹ�{total}��',
		procmsg : '���ڼ������Ժ�',
		showTableToggleBtn : true,
		showToggleBtn : true,
		nomsg : 'û���ҵ���Ϣ',
		width : 1000,
		height : 450,
		resizable : false,
		checkbox : true,
		onSuccess : function() {
			// initMoneySort();
			// ���������һ��ͳ����Ϣ����Ϣ
			var tableGrid = $('#flex1');
			// �õ�����
			var tdSize = $('tr:nth-child(1)', tableGrid).children().size();
			// ��������
			var sum = 0.0;
			$('tr td:nth-child(4)', tableGrid).each(function() {
						sum += parseFloat($(this).text());
					});
			// ����ж�ѡ������ľ�Ҫע�⡣
			$('tr:nth-child(1)', tableGrid)
					.before('<tr><td colspan='
							+ tdSize
							+ ' id="_sum_bar"><input type="select" style="display:none">�����ܼƣ�<span id="allIn"></span>'
							+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;֧���ܼƣ�<span id="allOut"></span>'
							+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ϼƣ�<span id="allSum"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
							+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="display:none" id="checkedSumBar">ѡ��<span id="checkedSumOut"></span>(֧��)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="checkedSumIn"></span>(����)</span>');
			$.ajax({
						url : window.path + "/grid!getSumMoney.action"
								+ queryStr,
						type : 'POST',
						dataType : 'json',
						error : function(x, textStatus, errorThrown) {
							afterSum(x.responseText);
						}
					});
		}
	});
});

// ����еĵ����¼�
function setRowOnclick() {
	$('table[id=flex1] tr').click(function() {
		var money = $('div', this).eq(2).text();
		var sort = $('div', this).eq(6).text();
		if (money != '' && !isNaN(money)) {
			if ($('input', this).attr('checked')) {
				if (sort == '+') {
					sumIn += parseFloat(money);
				} else {
					sumOut += parseFloat(money);
				}
			} else {
				if (sort == '+') {
					sumIn -= parseFloat(money);
				} else {
					sumOut -= parseFloat(money);
				}
			}
			$('#checkedSumOut').html("<font color='red'>" + sumOut + "</font>");
			$('#checkedSumIn').html("<font color='green'>" + sumIn + "</font>");
			$('#checkedSumBar').show();
		}
	});
}

// ����ͳ�Ƶ�������Ϣ
function afterSum(data) {
	var strs = data.split('$');
	$('#allIn').append("<font color='green'>" + strs[0] + "</font>");
	$('#allOut').append("<font color='red'>" + strs[1] + "</font>");
	$('#allSum').append("<font color='black' bold>" + strs[2] + "</font>");
	var avg = parseFloat(strs[3]) * 1.5;;
	var ans = '';
	$('#flex1 tr td:nth-child(4)').each(function() {
				if ($(this).next().next().next().text() == '��') {
					var v = parseFloat(this.innerText);
					if (v >= avg) {
						$(this).css('color', 'red').css('fontWeight', 'bold');
					}
				}
			});
	// ����е����¼�
	setRowOnclick();
	// ��ӱ����е�checkbox�ĵ���¼�
	setAllCheckOnclick();
}

/**
 * ����ڱ�ͷ��checkbox������¼�����
 */
function setAllCheckOnclick() {
	var chk = $('thead input');
	// ���û��ѡ�񣬾����ͳ������ġ�ѡ��ͳ��
	chk.click(function() {
		if (!$(this).attr('checked')) {
			sumOut = 0.0;
			sumIn = 0.0;
			$('#checkedSumOut').html('0');
			$('#checkedSumIn').html('0');
		}
		// ��ֻͳ�Ʊ�ҳ��������Ϣ.
		else {
			sumOut = 0.0;
			sumIn = 0.0;
			$('table[id=flex1] tr').each(function() {
						var money = $('div', this).eq(2).text();
						var sort = $('div', this).eq(6).text();
						if (money != '' && !isNaN(money)) {
							if (sort == '+') {
								sumIn += parseFloat(money);
							} else {
								sumOut += parseFloat(money);
							}
						}
					});
			$('#checkedSumOut').html("<font color='red'>" + sumOut + "</font>");
			$('#checkedSumIn').html("<font color='green'>" + sumIn + "</font>");
			$('#checkedSumBar').show();
		}
	});
}

var actions = "";
function testButton(com, grid) {
	switch (com) {
		case '���' :
			var ans = openNewWin(window.path + "/jsp/AddMoney.jsp",
					"dialogWidth=400px;dialogHeight=300px;");
			if (ans != 'start')
				$('#flex1').flexReload();
			break;
		case '����' :
			location.href = window.path + "/grid!reSetQuery.action";
			$('#flex1').flexReload();
			break;
		case '����' :
			alert('����');
			window.result2.location.href = window.path + "/grid!exportExcel.action";
			break;
		case '����ά��' :
			location.href = window.path + "/jsp/moneyType/MoneyTypeMain.jsp";
			$('#flex1').flexReload();
			break;
		case '�ز�' :
			$('#flex1').flexReload();
			break;
		case 'ͳ�Ʒ���' :
			location.href = window.path + "/jsp/report/baseReport.jsp";
			break;
		case '�Աȷ���' :
			location.href = window.path + "/jsp/report/contrastReport.jsp";
			break;
		case '��������' :
			openNewWin(window.path + "/jsp/Config.jsp",
					"dialogWidth=400px;dialogHeight=300px;");
			break;
		case '���ü�¼' :
			openNewWin(window.path + "/grid!getShopCardInfos.action",
					"dialogWidth=700px;dialogHeight=300px;");
			break;
		case 'ɾ��' :
			var selected_count = $('.trSelected', grid).length;
			if (selected_count == 0) {
				alert('��ѡ��һ����¼!');
				return;
			}
			names = '';
			$('.trSelected td:nth-child(2) div', grid).each(function(i) {
						if (i)
							names += ',';
						names += $(this).text();
					});
			if (confirm("ȷ��ɾ����¼[" + names + "]?")) {
				names = names.replace(/[,]/g, '#');
				delMoneys(names);
			}
			break;
		case '�޸�' :
			var selected_count = $('.trSelected', grid).length;
			if (selected_count == 0) {
				alert('��ѡ��һ����¼!');
				return;
			}
			if (selected_count > 1) {
				alert('��Ǹֻ��ͬʱ�޸�һ����¼!');
				return;
			}
			var id = $('.trSelected td:nth-child(2) div', grid).text();
			var ans = openNewWin(window.path
							+ "/grid!beforeUpdateMoneyDetail.action?moneySno="
							+ id, "dialogWidth=400px;dialogHeight=300px;");
			if (ans != 'start')
				$('#flex1').flexReload();
			break;
		case '�ۺϲ�ѯ' :
			 openNewWin(window.path + "/jsp/moneyDetail/QueryMoney.jsp",
					"dialogWidth=400px;dialogHeight=300px;"); 
			break;
	}
}

// ɾ��
function delMoneys(str) {
	$.ajax({
				url : window.path + "/grid!deleteMoney.action",
				data : "moneySno=" + str,
				type : 'POST',
				dataType : 'json',
				error : function(x, textStatus, errorThrown) {
					if (x.responseText.indexOf('�ɹ�') != -1) {
						alert(x.responseText);
						$('#flex1').flexReload();
					}
				}
			});
}

function getTallyTypeTree(idDom, nameDom) {
	var ans = openNewWin(appPath + "/js/xyTree/MoneyTypeTree.jsp",
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

// ���û��ͨ������֧���˵����о������������˵������ã�������������á�
function initMoneySort() {
	var ms = $('#moneySort');
	if ($('option', ms).size() == 1) {
		$('#allmoneySort option[type]').each(function() {
			ms.append("<option value='" + this.value + "'>" + this.text
					+ "</option>");
		});
	}
}