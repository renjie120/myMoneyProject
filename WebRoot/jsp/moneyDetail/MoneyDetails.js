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
					display : '序号',
					name : 'moneySno',
					width : 100,
					align : 'center'
				}, {
					display : '时间',
					name : 'moneyTime',
					width : 100,
					align : 'center'
				}, {
					display : '金额',
					name : 'money',
					width : 100,
					align : 'center',
					sortable : true
				}, {
					display : '类别',
					name : 'moneyType',
					width : 100,
					align : 'left'
				}, {
					display : '描述',
					name : 'moneyDesc',
					width : 350,
					align : 'left'
				}, {
					display : '消费卡',
					name : 'shopCardStr',
					width : 50,
					align : 'center'
				}, {
					display : '类型',
					name : 'inorout',
					width : 50,
					align : 'center'
				}],
		searchitems : [{
					display : '时间',
					name : 'moneyTime'
				}, {
					display : '金额',
					name : 'money',
					isdefault : true
				}, {
					display : '类别',
					name : 'moneyType'
				}, {
					display : '描述',
					name : 'moneyDesc'
				}],
		buttons : [{
					name : '删除',
					bclass : 'delete',
					onpress : testButton
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : testButton
				}, {
					name : '重查',
					bclass : 'add',
					onpress : testButton
				}, {
					name : '综合查询',
					bclass : 'add',
					onpress : testButton
				}, {
					name : '信用记录',
					bclass : 'add',
					onpress : testButton
				}, {
					name : '导出',
					bclass : 'add',
					onpress : testButton
				}],
		usepager : true,
		title : '收支信息查询列表',
		useRp : true,
		modal : true,// 使用蒙板
		page : 1,
		rp : 40,
		pagestat : '显示第{from}到{to}行 总共{total}条',
		procmsg : '正在加载请稍候',
		showTableToggleBtn : true,
		showToggleBtn : true,
		nomsg : '没有找到信息',
		width : 1000,
		height : 450,
		resizable : false,
		checkbox : true,
		onSuccess : function() {
			// initMoneySort();
			// 下面是添加一行统计信息的信息
			var tableGrid = $('#flex1');
			// 得到列数
			var tdSize = $('tr:nth-child(1)', tableGrid).children().size();
			// 计算总数
			var sum = 0.0;
			$('tr td:nth-child(4)', tableGrid).each(function() {
						sum += parseFloat($(this).text());
					});
			// 如果有多选框，下面的就要注意。
			$('tr:nth-child(1)', tableGrid)
					.before('<tr><td colspan='
							+ tdSize
							+ ' id="_sum_bar"><input type="select" style="display:none">收入总计：<span id="allIn"></span>'
							+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支出总计：<span id="allOut"></span>'
							+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;合计：<span id="allSum"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
							+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="display:none" id="checkedSumBar">选择：<span id="checkedSumOut"></span>(支出)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="checkedSumIn"></span>(收入)</span>');
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

// 添加行的单击事件
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

// 输入统计的收入信息
function afterSum(data) {
	var strs = data.split('$');
	$('#allIn').append("<font color='green'>" + strs[0] + "</font>");
	$('#allOut').append("<font color='red'>" + strs[1] + "</font>");
	$('#allSum').append("<font color='black' bold>" + strs[2] + "</font>");
	var avg = parseFloat(strs[3]) * 1.5;;
	var ans = '';
	$('#flex1 tr td:nth-child(4)').each(function() {
				if ($(this).next().next().next().text() == '—') {
					var v = parseFloat(this.innerText);
					if (v >= avg) {
						$(this).css('color', 'red').css('fontWeight', 'bold');
					}
				}
			});
	// 添加行单击事件
	setRowOnclick();
	// 添加标题行的checkbox的点击事件
	setAllCheckOnclick();
}

/**
 * 添加在表头的checkbox点击的事件处理
 */
function setAllCheckOnclick() {
	var chk = $('thead input');
	// 如果没有选择，就清空统计里面的“选择”统计
	chk.click(function() {
		if (!$(this).attr('checked')) {
			sumOut = 0.0;
			sumIn = 0.0;
			$('#checkedSumOut').html('0');
			$('#checkedSumIn').html('0');
		}
		// 就只统计本页的数据信息.
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
		case '添加' :
			var ans = openNewWin(window.path + "/jsp/AddMoney.jsp",
					"dialogWidth=400px;dialogHeight=300px;");
			if (ans != 'start')
				$('#flex1').flexReload();
			break;
		case '重置' :
			location.href = window.path + "/grid!reSetQuery.action";
			$('#flex1').flexReload();
			break;
		case '导出' :
			alert('导出');
			window.result2.location.href = window.path + "/grid!exportExcel.action";
			break;
		case '类型维护' :
			location.href = window.path + "/jsp/moneyType/MoneyTypeMain.jsp";
			$('#flex1').flexReload();
			break;
		case '重查' :
			$('#flex1').flexReload();
			break;
		case '统计分析' :
			location.href = window.path + "/jsp/report/baseReport.jsp";
			break;
		case '对比分析' :
			location.href = window.path + "/jsp/report/contrastReport.jsp";
			break;
		case '参数配置' :
			openNewWin(window.path + "/jsp/Config.jsp",
					"dialogWidth=400px;dialogHeight=300px;");
			break;
		case '信用记录' :
			openNewWin(window.path + "/grid!getShopCardInfos.action",
					"dialogWidth=700px;dialogHeight=300px;");
			break;
		case '删除' :
			var selected_count = $('.trSelected', grid).length;
			if (selected_count == 0) {
				alert('请选择一条记录!');
				return;
			}
			names = '';
			$('.trSelected td:nth-child(2) div', grid).each(function(i) {
						if (i)
							names += ',';
						names += $(this).text();
					});
			if (confirm("确定删除记录[" + names + "]?")) {
				names = names.replace(/[,]/g, '#');
				delMoneys(names);
			}
			break;
		case '修改' :
			var selected_count = $('.trSelected', grid).length;
			if (selected_count == 0) {
				alert('请选择一条记录!');
				return;
			}
			if (selected_count > 1) {
				alert('抱歉只能同时修改一条记录!');
				return;
			}
			var id = $('.trSelected td:nth-child(2) div', grid).text();
			var ans = openNewWin(window.path
							+ "/grid!beforeUpdateMoneyDetail.action?moneySno="
							+ id, "dialogWidth=400px;dialogHeight=300px;");
			if (ans != 'start')
				$('#flex1').flexReload();
			break;
		case '综合查询' :
			 openNewWin(window.path + "/jsp/moneyDetail/QueryMoney.jsp",
					"dialogWidth=400px;dialogHeight=300px;"); 
			break;
	}
}

// 删除
function delMoneys(str) {
	$.ajax({
				url : window.path + "/grid!deleteMoney.action",
				data : "moneySno=" + str,
				type : 'POST',
				dataType : 'json',
				error : function(x, textStatus, errorThrown) {
					if (x.responseText.indexOf('成功') != -1) {
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

// 如果没有通过收入支出菜单进行具体类型下拉菜单的设置，就这里进行设置。
function initMoneySort() {
	var ms = $('#moneySort');
	if ($('option', ms).size() == 1) {
		$('#allmoneySort option[type]').each(function() {
			ms.append("<option value='" + this.value + "'>" + this.text
					+ "</option>");
		});
	}
}