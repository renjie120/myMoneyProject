$(function(){
	window.path = $('#path').val(); 
//	window.setTimeout(reportByMonths,10);
//	window.setTimeout(a,20);
//	setup();setup();
}); 
//function a(){
//	$('#showNewestReport').click();
//}
var showTable= true;
var showNestReport = true;
var data = new Array();
var chartLoaded = false;
var showCheckMsgDiv = false;
//图表对象
var chartObj = null;
//全部的数据内容信息
var dataSet = [];
//每个类别的平均数
var avgs = [];
//每个类别的总和
var sums = [];
//类别的id的集合
var typeIds = [];
//类别名称的集合
var typeNames = [];
//报表类型，year是按年份，month是按月份.
var reportType = 'year';
//报表类型，big是按大类，small是按小类.
var reportType2 = 'big';
//全部的分类信息
var sortType = [];

//改变分类之后重新显示小类别的选择框
function changeTallyTypes()
{
	var t = $('#tp').val();
}

function changeYears(){
}

function callBack(data){
	var ansBuf = []; 
	var chkmodleStr = $('#checkboxModle').html();
	for (var i = 0, j = data.length; i < j; i++) {
		var newStr = chkmodleStr.replace(/\$1/g,data[i]['typeCode']);
		newStr = newStr.replace(/\$2/g,data[i]['tallyTypeDesc']);
		ansBuf.push(newStr); 
	}
	$('#tallyTypes').html(ansBuf.join(''));
}

function setup(){
	if(showCheckMsgDiv){
		$("tr:gt(0):not(:last)").fadeOut(10);		
		showCheckMsgDiv = false;
		$('#setup').text('显示配置');
	}else{
		$("tr:gt(0):not(:last)").fadeIn(10);
		showCheckMsgDiv = true;
		$('#setup').text('隐藏配置');
	}
}

//根据最新的数据显示图表
function show(){
	if (chartLoaded){
       chartObj.setDataXML(generateXML($('input[name=AnimateChart]').attr('checked'),$('input[name=showValue]').attr('checked')));
    }
}
//全选按钮
function choose(){
	$('#tallyTypes input[name=types]').each(function(i){
		$(this).attr('checked',true);
	});	
	clickChkbox();
}

//全不选按钮
function dischoose(){
	$('#tallyTypes input[name=types]').each(function(i){
		$(this).attr('checked',false);
	});	
	clickChkbox();
}

//是否立即显示
function changeNowShow(){
	if($('input[name=showChangeNow]').attr('checked')==false){
		$('#btnshow').attr('disabled',false);	
	}else{
		$('#btnshow').attr('disabled',true);
	}
}

//点击小类别多选框调用的js方法。主要根据选择的小类别自动的形成xml数据。
function clickChkbox(){
	if($('input[name=showChangeNow]').attr('checked')==false){
		return ;
	}
    if (chartLoaded){
       chartObj.setDataXML(generateXML($('input[name=AnimateChart]').attr('checked'),$('input[name=showValue]').attr('checked')));
    }
}

//点击了选择年份或者选择月份之后查询显示图表
function clickChkbox2(){
	if($('input[name=showChangeNow]').attr('checked')==false){
		return ;
	}
    if (chartLoaded){
       chartObj.setDataXML(generateXML($('input[name=AnimateChart]').attr('checked'),$('input[name=showValue]').attr('checked')));
    }
}

//得到年份之间的对比数据
function reportByYears(){
	var t = $('#tp').val();
	var tt = $('#bigTypeSort').val();
	var method = 'reportMoneyWithBigTypeAndYear';
	reportType2 = 'big';
	if(tt=='2'){
		reportType2 = 'small';
		method = 'reportMoneyWithTypeAndYear';
	}
	$.ajax({url:window.path+"/constractReport!"+method+".action?type="+t,
		 type:"POST", 
		 async:false,
		 dataType:"json", 
		 error:function (x, textStatus, errorThrown) {
		 	$('#showtable').show();
		 	$('#showNewestReport').show();
		 	reportType = 'year';   
		 	var strs = x.responseText.split(/\$\$/g); 
		 	chartObj.setDataXML(strs[0]);	
		 	//得到数据内容信息
		 	eval("dataSet="+strs[1]);
		 	//根据返回的数据信息添加类别到tallyTypes div中去！
		 	addCheckBox()
		 	//得到分类的信息。
		 	eval("sortType="+strs[2]);
		 	eval("avgs="+strs[3]);
		 	eval("sums="+strs[4]);
		 	eval("typeIds="+strs[5]);
		 	addCategories()
		 	$('#showCategories').html('选择年份');
	}});
}


//得到所选年份的对比数据
function reportByMonths(){
	var y = $('#year').val();
	var t = $('#tp').val();
	var tt = $('#bigTypeSort').val();
	var method = 'reportMoneyWithBigTypeAndMonth';
	reportType2 = 'big';
	if(tt=='2'){
		reportType2 = 'small';
		method = 'reportMoneyWithTypeAndMonth';
	}
	$.ajax({url:window.path+"/constractReport!"+method+".action?year="+y+"&type="+t,
		 type:"POST", 
		 async:false,
		 dataType:"json", 
		 error:function (x, textStatus, errorThrown) {
		 	$('#showtable').show();
		 	$('#showNewestReport').show();
		 	reportType = 'month';   
		 	var strs = x.responseText.split(/\$\$/g); 
		 	chartObj.setDataXML(strs[0]);	
		 	//得到数据内容信息
		 	eval("dataSet="+strs[1]);		 	
		 	addCheckBox();
		 	//得到分类的信息。
		 	eval("sortType="+strs[2]);
		 	eval("avgs="+strs[3]);
		 	eval("sums="+strs[4]);
		 	eval("typeIds="+strs[5]);
		 	addCategories();
		 	$('#showCategories').html('选择月份');
	}});
}

//根据返回的数据信息添加类别到tallyTypes div中去！
function addCheckBox(){
	var ansBuf = [];
	typeNames = [];
	var chkmodleStr = $('#checkboxModle').html();
	for(var i=0,j=dataSet.length;i<j;i++){
		var dataStr = dataSet[i].join('$$').split(/\$\$/);
		var newStr = chkmodleStr.replace(/\$1/g,dataStr[0]);
		newStr = newStr.replace(/\$2/g,dataStr[0]);
		ansBuf.push(newStr);
		typeNames.push(dataStr[0]);
	}
	$('#buttons').show(); 
	$('#tallyTypes').html(ansBuf.join(''));
}

//根据返回的数据设置可以动态改变大类别的checkbox
function addCategories(){
	var ansBuf = [];
	var chkmodleStr = $('#checkboxModle2').html();
	for(var i=0,j=sortType.length;i<j;i++){
		var newStr = chkmodleStr.replace(/\$1/g,i);
		newStr = newStr.replace(/\$2/g,sortType[i]);
		ansBuf.push(newStr);
	}
	$('#buttons').show();
	$('#months').html(ansBuf.join('')); 
}

//在图表进行第一次加载的时候自动执行这里的函数。
function FC_Rendered(domId) {
	if (domId == "fusionchart") {
		$('#tallyTypes').show();
		$('#months').show();
		chartLoaded = true;
		chartObj = getChartFromId(domId);
	}
	return true;
}

//根据两个数组进行xml字符串的拼接
function generateXML(animate,showValue) {
	var checkCategories = [];
	$('#months input[name=categoryId]').each(function(i){ 
		  checkCategories.push(this.checked); 
	}); 
	var ans = [];
	ans.push("<chart caption='对比分析图' formatNumberScale='0' numberPrefix='$' animation='" + ((animate == true) ? "1" : "0") + "' ");
	ans.push(showValue== true? (" showValues='1' ") : (" showValues='0' "));
	ans.push( ">");
	var ans2 = [];
	for(var st=0,jj=sortType.length;st<jj;st=st+1){
		if(checkCategories[st]){
			ans2.push("<dataset seriesName='");
			ans2.push(sortType[st]);
			ans2.push("'>");
			$('#tallyTypes input[name=types]').each(function(i){		
				if($(this).attr('checked')){
					ans2.push("<set value='");
					//看对应的category是不是已经选择！
					ans2.push(dataSet[i][st+1]);
					ans2.push("'/>");
				}
			});	
			ans2.push("</dataset>");
		}
	}	
	ans.push(ans2.join('')); 
	//下面添加平均值
	ans.push("<dataSet seriesName='平均值' showValues='0' parentYAxis='s'>");
	var count = $('#months input[name=categoryId]:checked').size();  
	$('#tallyTypes input[name=types]').each(function(i){
			if($(this).attr('checked')){  
				var sum = 0.0;   
				for(ii=1,jj=dataSet[i].length;ii<jj;ii++){
					if(checkCategories[ii-1])
						sum+=parseFloat(dataSet[i][ii]); 
				}  
				var avg = 0;
				if(count!=0)
				  avg = (sum/count).toFixed(2);
				else
				  avg = 0;
				ans.push("<set value='"); 
				ans.push(avg);
				ans.push("'/>");
			}  
		});	 
	ans.push("</dataSet>")
	//下面添加的是种类
	var ans1 = [];	
	ans1.push("<categories>");
	$('#tallyTypes input[name=types]').each(function(i){
		if($(this).attr('checked')){
			ans1.push("<category name='");
			ans1.push(dataSet[i][0]);
			ans1.push("' />");
		}
	});	
	ans1.push("</categories>");
	ans.push(ans1.join(''));
	ans.push("</chart>"); 
	return ans.join('');
} 
 
function _showTable(){
	var str = [];
	var tps = [];
	//进行按照年度/月度的累计计算.
	var stats = new HashMap();
	str.push('<table width="800px" class="mytable"><tr>');
	str.push("<th>类型</th>");
	$(sortType).each(function(i){
		str.push("<th>"+sortType[i]+"</th>");
		stats.put(sortType[i],0);
	}); 
	str.push("<th>平均数</th>");
	str.push("<th>总和</th>");
	stats.put('all',0);
	str.push("</tr>"); 
	
	$(dataSet).each(function(i){
		tps.push(dataSet[i][0]);
	}); 
	  
	$(tps).each(function(i){
		str.push("<tr>");
		var arr = dataSet[i];
		str.push("<td>"+tps[i]+"</td>");
		$(arr).each(function(ii){
			if(ii>0){
				//按照类别计算累计和.
				var _i = stats.get(sortType[ii-1])*1;
				_i=_i+1*arr[ii];
				stats.put(sortType[ii-1],_i);
				if(reportType=='year'){
					str.push("<td tag='true'><a href='javaScript:openYearDetail("+sortType[ii-1]+")'  style='cursor:pointer'>"+arr[ii]+"</a></td>");
				}
				else{
					var _y = $('#year').val(); 
					if(reportType2=='big')
						str.push("<td tag='true'><a  style='cursor:pointer' href='javaScript:openBigDetail("+_y+","+sortType[ii-1]+",\""+typeIds[i]+"\")'>"+arr[ii]+"</a></td>");
					else
						str.push("<td tag='true'><a  style='cursor:pointer' href='javaScript:openSmallDetail("+_y+","+sortType[ii-1]+",\""+typeIds[i]+"\")'>"+arr[ii]+"</a></td>");
				}
			}
		}); 
		str.push("<td tag='true'>"+avgs[i]+"</td>");
		str.push("<td tag='true'>"+sums[i]+"</td>");
		//按照类别计算累计和.
		var _i = stats.get('all')*1;
		_i=_i+1*sums[i];
		stats.put('all',_i);
		str.push("</tr>");
	}); 
	
	str.push("<tr><td>累计</td>");
	$(sortType).each(function(i){
		str.push("<td >"+stats.get(sortType[i]).toFixed(2)+"</td>");
	}); 
	str.push("<td>-</td>");
	str.push("<td >"+stats.get('all').toFixed(2)+"</td>");
	str.push("</tr>");
	
	str.push("</table>");
	$('#mytable').html(str.join(''));
}
 
//进行颜色标识
function showColor(){
	var small = $('#smallTag').val();
	var big = $('#bigTag').val(); 
	if(small==''||isNaN(small))  {$('#smallTag').val(0);small=0;}
	if(big==''||isNaN(big))  {$('#bigTag').val(9999999);big=9999999;}   
	$('td[tag=true]').each(function(){
	 	var num = $(this).text()*1.0; 
	 	$('a',this).css('color','black');
	 	if(num>small&&num<big){  
	 		$('a',this).css('color','red');
	 	}
	 });
}

//点击某一个大类别的连接之后，跳转到小类别的详细报表展示页面.
function openBigDetail(year,month,type){   
	 if((''+month).length<2){
	 	month = '0'+month;
	 }
	openNewWin(window.path+'/detailReport!reportDetailByTimeAndType.action?year='+year+"&month="+month
		+"&bigType="+type); 
}

//返回详细的小类别信息和时间的报表.
function openSmallDetail(year,month,type){ 
	if((''+month).length<2){
	 	month = '0'+month;
	 }
	openNewWin(window.path+'/detailReport!reportDetailByTimeAndSmallType.action?year='+year+"&month="+month
		+"&smallType="+type, "dialogWidth=850px;dialogHeight=400px;");  
} 

//按年统计每月里面的收支信息.不管是大类小类，都显示的一样，只按小类统计！
function openYearDetail(year){  
	openNewWin(window.path
							+ "/detailReport!reportInYearPage.action?year="
							+ year, "dialogWidth=850px;dialogHeight=400px;"); 
}

var chart;  
function _loadHightChart(){   
	var s = [];
	//下面是组装列行数据.
	$(sortType).each(function(i){
		var _obj = {}; 
		_obj.name = sortType[i];  
		_obj.type  =  'column';  
		//设置列可以被选择，设置之后可以设置onclick事件。
		_obj.allowPointSelect =  true; 
		var _arr = new Array();
		$(dataSet).each(function(j){  
			 _arr.push(dataSet[j][i+1]);
		}); 
		eval("_obj.data=["+_arr.join(',')+"]"); 
		s.push(_obj);
	});  
	//下面是平均数所在的数据
	var _obj = {};
	_obj.type = 'spline';
	_obj.name = '平均数'; 
	eval("_obj.data=["+avgs.join(',')+"]"); 
	s.push(_obj);
	
	//下面组装合计饼图的内容.
	var _obj2 = {};
	_obj2.type = 'pie';
	_obj2.name = '总计'; 
	_obj2.size = 90;
	_obj2.center=[220,90];
	_obj2.showInLegend = true;
	_obj2.showCheckbox = true;
	_obj2.dataLabels = {enable:true};
	var _s = []; 
	$(typeNames).each(function(_ii){
		_s.push("{name:'"+this+"',y:"+sums[_ii]+"},");
	}); 
	var _str = _s.join('');
	_str = _str.substring(0,_str.length-1);
	eval("_obj2.data=["+_str+"]"); 
	s.push(_obj2);
	 
	chart = new Highcharts.Chart({
		chart: {
			renderTo: 'hightChartDiv',
			zoomType: 'y',
			reflow:true
		},
		title: {
			text: '我的报表'
		},
		credits: {
            text: '百度',
            href: 'http://www.baidu.com'
        },
		xAxis: {
			categories: typeNames   
		}, 
		tooltip: {
            formatter: function() {
            	if (this.point.name) {  
					s =  this.point.name +': '+ this.y +' 元';
				} else {
					s =   this.series.name +'('+ this.x +')<br>'+ this.y;
				} 
				return s;
            }
        }, 
		plotOptions: {
			series: {  
//                allowPointSelect: true,
//                states: {
//	                select: {
//	                    color: "red"
//	                }
//	            }
            },
			bar: { 
				dataLabels: {
					enabled: true
				} 
			},
			column:{
				dataLabels: {
					enabled: true
				},
				events:{
					click:function(aa){ 
						window.setTimeout(sss,10); 
						//this.name:点击柱状图对应categories的描述,this.columnIndex:点击柱状图的相对categories的索引
					}
				}
			}
		},  
		series:s 
	});
}
 
function sss(){ 
 	var points = chart.getSelectedPoints();
 	if(points.length>0){
 		//输出类型.
 		alert(typeIds[points[0].x]);
 	}
 /*
	 var selectedPoints = chart.getSelectedSeries();
       					 alert(selectedPoints[0].x) */
}

//显示表格
function initShowTable(){//dataSet,sortType 
	if(showTable){
		_showTable();
		$('#msgDiv').show();
		$('#chartdiv').hide();
		$('#hightChartDiv').hide();
		$('#showtable').text('隐藏表格');
		showTable = false;
	}else{
		$('#msgDiv').hide();
		$('#hightChartDiv').hide();
		$('#chartdiv').show(); 
		$('#showtable').text('显示表格');
		showTable = true;
	} 
	
	if(!showNestReport){ 
		$('#showNewestReport').text('显示最新报表');
		showNestReport = true;
	}
}

function initShowNewestReport()
{ 
	if(showNestReport){
		$('#msgDiv').hide();
		$('#chartdiv').hide();
		$('#hightChartDiv').show();
		_loadHightChart(); 
		$('#showNewestReport').text('隐藏最新报表');
		showNestReport = false;
	}else{
		$('#msgDiv').hide();
		$('#chartdiv').show();
		$('#hightChartDiv').hide();
		$('#showNewestReport').text('显示最新报表');
		showNestReport = true;
	} 
	if(!showTable){ 
		$('#showtable').text('显示表格');
		showTable = true;
	}
}