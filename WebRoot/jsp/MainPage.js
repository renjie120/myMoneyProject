$(function(){ 
	var p = $('#path').val();
	var now = new Date();
	var firstOfThisMonth = now.getFullYear()+'-'+ (now.getMonth()+1)+'-01';
	var firstOfNextMonth = now.getFullYear()+'-'+ (now.getMonth()+2)+'-01';
	//定义连接的数组
	var hrefs = [p+'/jsp/Login.jsp',
			p+'/jsp/desktop/desktop.jsp',
			p+'/jsp/moneyDetail/AddMoney.jsp',
			p+'/grid!reQuery.action?minTime='+firstOfThisMonth+"&maxTime="+firstOfNextMonth,
			p+'/jsp/report/baseReport.jsp',
			p+'/jsp/report/contrastReport.jsp',
			p+'/jsp/moneyType/MoneyTypeMain.jsp',	
			//p+'/jsp/stock/AddStock.jsp',			
			p+'/jsp/Config.jsp',
			p+'/detailReport!reportDetail.action',
			p+'/jsp/moneyDetail/AddBudget.jsp',
			p+'/jsp/moneyDetail/calendar.jsp',
			p+'/jsp/moneyDetail/highchartdemo.jsp',
			//p+'/jsp/moneyDetail/myiframe.jsp',
			p+'/jsp/moneyDetail/stockdetail.jsp'];
	$('#tabs').tabs();
	$("#tabs div iframe:nth-child(1)").attr('src',hrefs[0]).height('100%').width('100%');
	//设置每个div对应的下面的iframe的连接地址.
	$('#tabs div').each(function(i){
	$(this).attr('src',hrefs[i]);
	});
	$('#tabs li a').click(function(i){
	var div = $($(this).attr('href'));
	var href = div.attr('src');
	$("iframe",div).attr('src',href).height('100%').width('100%');
	});
});