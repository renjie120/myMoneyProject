$(function(){
	window.path = $('#path').val();
	$('button').width(100);
});

function resetChooseReport(){
	$('#way option:nth-child(1)').attr("selected",true);
}

function  getReport(){
	switch($('#way').val()){
		case '1':test(); break;
		case '2':test2(); break;
		case '3':test3(); break;
	}
}
//点击柱图查看详细信息。
function showDetailsByType(data) {
	//var year = $("#year").val();
	//var month = $("#month").val();
	//dwrBo.getDetailsByTimeAndBigSort(year, month, data, consoleDetails);
}

//是否自定义时间选择
function setDefineTime(obj){ 
	if(obj.checked){
		$('#showdateDiv2').show();
		$('#showdateDiv').hide();
	}else{
		$('#showdateDiv').show();
		$('#showdateDiv2').hide();
	}
}

function showTypes(obj){
	if(obj.value=='fst'){
		$('#firstType').show();
		$('#secondType').hide(); 
	}else{
		$('#firstType').hide();
		$('#secondType').show(); 
	}
}
//回调函数。
function consoleDetails(data) {
	var ans = "";
	var ansBuf = [];
	ansBuf.push("<table style='border:1px black;fontSize:12px;'>");
	for (var i = 0, j = data.length; i < j; i++) {
		ansBuf.push("<tr>");
		for (ii in data[i]) {
			ansBuf.push("<td>");
			ansBuf.push(data[i][ii]);
			ansBuf.push("</td>");
		}
		ansBuf.push("</tr>");
	}
	ansBuf.push("<table>");
	$("#msgDiv").html(ansBuf.join("")).animate({left: 50,opacity:'show'},500); 
}
//查看统计图。
function test() {	
	var queryStr = "year=" + $("#year").val() + "&month=" + $("#month").val();
    //得到当前月份的每天的开支金额折线图	
	$.ajax({url:window.path+"/report!reportByMonth.action", 
	data:queryStr, 
	async:true,
	type:"POST", 
	dataType:"json", 
	error:function (x, textStatus, errorThrown) {
		var chart = new FusionCharts("../../js/fusionchart/Charts/Line.swf", "ChartId", "600", "350");
		 
		chart.setDataXML(x.responseText);
		chart.render("chartdiv");
		
			 //得到当前月份的收入，开支金额饼图
		$.ajax({url:window.path+"/report!reportInAndOutByMonth.action",
			 data:queryStr, 
			 type:"POST", 
			 async:false,
			 dataType:"json", 
			 error:function (x, textStatus, errorThrown) {
				var chart = new FusionCharts("../../js/fusionchart/Charts/Pie2D.swf", "ChartId2", "600", "350");
				chart.setDataXML(x.responseText);
				chart.render("chartdiv2");
			}});
	}});
}

function  test2(){
	var queryStr = "year=" + $("#year").val() + "&month=" + $("#month").val();
    //得到当前月份的分类别的收入
    
     //得到当前月份的小类别的支出
	$.ajax({url:window.path+"/report!reportOutByTallyType.action",
	 data:queryStr, 
	 type:"POST",
	 async:false,
	  dataType:"json", 
	  error:function (y, textStatus, errorThrown) {
		var chart = new FusionCharts("../../js/fusionchart/Charts/Doughnut2D.swf", "ChartId2", "600", "350");
		chart.setDataXML(y.responseText);
		chart.render("chartdiv");
	}});
			
	//得到当前月份的分类别的支出
	$.ajax({url:window.path+"/report!reportOutByType.action", 
		data:queryStr, 
		type:"POST", 
		async:false,
		dataType:"json", 
		error:function (x, textStatus, errorThrown) {	
			var chart = new FusionCharts("../../js/fusionchart/Charts/Column3D.swf", "ChartId2", "600", "350");
			chart.setDataXML(x.responseText);
			chart.render("chartdiv2");
		}});		
}

function test3(){
 	var queryStr = "year=" + $("#year").val() + "&month=" + $("#month").val();
   //得到当前月份的小类别的收入
	$.ajax({url:window.path+"/report!reportInByTallyType.action", 
		data:queryStr, 
		type:"POST",
		async:false, 
		dataType:"json", 
		error:function (x, textStatus, errorThrown) {
			var chart = new FusionCharts("../../js/fusionchart/Charts/Doughnut2D.swf", "ChartId", "600", "350");
			chart.setDataXML(x.responseText);
			chart.render("chartdiv");			
		}});	
		
	$.ajax({url:window.path+"/report!reportInByType.action", 
		data:queryStr, 
		async:false,
		type:"POST", 
		dataType:"json", 
		error:function (x, textStatus, errorThrown) {
			var chart = new FusionCharts("../../js/fusionchart/Charts/Column3D.swf", "ChartId", "600", "350");
			chart.setDataXML(x.responseText);
			chart.render("chartdiv2");			 
		}});
}

