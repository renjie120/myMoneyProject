$(function() {
			window.path = $('#path').val();
		});
var showTable = true;
var showNestReport = true;
var data = new Array();
var chartLoaded = false;
var showCheckMsgDiv = false;
// 图表对象
var chartObj = null;
// 全部的数据内容信息
var dataSet = [];
// 每个类别的平均数
var avgs = [];
// 每个类别的总和
var sums = [];
// 类别的id的集合
var typeIds = [];
// 类别名称的集合
var typeNames = [];
// 报表类型，year是按年份，month是按月份.
var reportType = 'year';
// 报表类型，big是按大类，small是按小类.
var reportType2 = 'big';
// 全部的分类信息
var sortType = [];

function changeYears() {
}

// 得到年份之间的对比数据
function reportByYears() {
	var t = $('#tp').val();
	var tt = $('#bigTypeSort').val();
	var method = 'reportMoneyWithBigTypeAndYear';
	reportType2 = 'big';
	if (tt == '2') {
		reportType2 = 'small';
		method = 'reportMoneyWithTypeAndYear';
	}
	$.ajax({
				url : window.path + "/constractReport!" + method
						+ ".action?type=" + t,
				type : "POST",
				async : false,
				dataType : "json",
				error : function(x, textStatus, errorThrown) {
					$('#showtable').show();
					$('#showNewestReport').show();
					reportType = 'year';
					var strs = x.responseText.split(/\$\$/g);
					// 得到数据内容信息
					eval("dataSet=" + strs[1]);

					addTypeNames()

					// 得到分类的信息。
					eval("sortType=" + strs[2]);
					eval("avgs=" + strs[3]);
					eval("sums=" + strs[4]);
					eval("typeIds=" + strs[5]);

					initShowNewestReport();
				}
			});
}

// 根据返回的数据信息添加类别到tallyTypes div中去！
function addTypeNames() {
	typeNames = [];
	for (var i = 0, j = dataSet.length; i < j; i++) {
		var dataStr = dataSet[i].join('$$').split(/\$\$/);
		typeNames.push(dataStr[0]);
	}
}

// 点击某一个大类别的连接之后，跳转到小类别的详细报表展示页面.
function openBigDetail(year, month, type) {
	if (('' + month).length < 2) {
		month = '0' + month;
	}
	openNewWin(window.path
			+ '/detailReport!reportDetailByTimeAndType.action?year=' + year
			+ "&month=" + month + "&bigType=" + type);
}

function changeTypeSort(){
	//大类
	if($('#bigTypeSort').val()=='1'){
	 	  origChartWidth = 900; 
		  chartWidth = origChartWidth; 
	}
	//小类
	else{
	  origChartWidth = 2000; 
	  chartWidth = origChartWidth; 
	}
}
// 返回详细的小类别信息和时间的报表.
function openSmallDetail(year, month, type) {
	if (('' + month).length < 2) {
		month = '0' + month;
	}
	openNewWin(
			window.path
					+ '/detailReport!reportDetailByTimeAndSmallType.action?year='
					+ year + "&month=" + month + "&smallType=" + type,
			"dialogWidth=850px;dialogHeight=400px;");
}

// 按年统计每月里面的收支信息.不管是大类小类，都显示的一样，只按小类统计！
function openYearDetail(year) {
	openNewWin(window.path + "/detailReport!reportInYearPage.action?year="
					+ year, "dialogWidth=850px;dialogHeight=400px;");
}

var chart;
var origChartWidth = 900;
var origChartHeight = 430;
var chartWidth = origChartWidth;
var chartHeight = origChartHeight;
function _loadHightChart() { 
    
	var s = [];
	// 下面是组装列行数据.
	$(sortType).each(function(i) {
				var _obj = {};
				_obj.name = sortType[i];
				_obj.id = sortType[i];
				_obj.type = 'column';
				// 设置列可以被选择，设置之后可以设置onclick事件。
				//_obj.allowPointSelect = true;
				//_obj.showCheckbox = true;
				var _arr = new Array();
				$(dataSet).each(function(j) {
							_arr.push(dataSet[j][i + 1]);
						});
				eval("_obj.data=[" + _arr.join(',') + "]"); 
				s.push(_obj);
			});
	// 下面是平均数所在的数据
	var _obj = {};
	_obj.type = 'spline';
	_obj.name = '平均数';
	eval("_obj.data=[" + avgs.join(',') + "]");
	s.push(_obj);

	// 下面组装合计饼图的内容.
	var _obj2 = {};
	_obj2.type = 'pie';
	_obj2.name = '总计'; 
	_obj2.center = [120, 90];
	//_obj2.showInLegend = true;  //是否显示在下面的菜单栏
	//_obj2.showCheckbox = true;  //是否出现多选框 
	var _s = [];
	$(typeNames).each(function(_ii) {
		_s.push("{name:'" + this + "',y:" + sums[_ii] + "},");
	});
	var _str = _s.join('');
	_str = _str.substring(0, _str.length - 1);
	eval("_obj2.data=[" + _str + "]");
	s.push(_obj2);

	chart = new Highcharts.Chart({
				chart : { 
					renderTo : 'hightChartDiv',
					//zoomType : 'y',
					width: chartWidth,
            		height: chartHeight,
					reflow : true
				},
				title : {
					text : '我的报表'
				},
				credits : {
					text : '百度',
					href : 'http://www.baidu.com'
				},
				xAxis : {
					categories : typeNames
				},
				tooltip : {
					formatter : function() {
						if (this.point.name) {
							s = this.point.name + ': ' + this.y + ' 元';
						} else {
							s = this.series.name + '(' + this.x + ')<br>'
									+ this.y;
						}
						return s;
					}
				},
				plotOptions : {
					series : { 
					},
					bar : {
						dataLabels : {
							enabled : true
						}
					},
					pie : {
						size:90, 
						dataLabels : {
							enabled : true
						}
					},
					column : {
						dataLabels : {
							enabled : true
						},
						events : {
							click : function(aa) {
								//window.setTimeout(sss, 10);
								// this.name:点击柱状图对应categories的描述,this.columnIndex:点击柱状图的相对categories的索引
							}
						}
					}
				},
				 exporting: {
		            buttons: {
		                exportButton: {
		                    menuItems: null,
		                    onclick: function() {
		                        this.exportChart(); 
		                    }
		                }
		            }
		        },
				series : s
			});
}

function sss() {
	var points = chart.getSelectedPoints();
	if (points.length > 0) {
		// 输出类型.
		alert(typeIds[points[0].x]);
	}
	/*
	 * var selectedPoints = chart.getSelectedSeries();
	 * alert(selectedPoints[0].x)
	 */
}

function initShowNewestReport() {
	$('#hightChartDiv').show();
	_loadHightChart();

} 
 
function setWH(w){
	if(w>0){
	   chart.setSize(chartWidth *= 1.1, chartHeight*= 1.1);
	}else{
	   chart.setSize(chartWidth *= 0.9, chartHeight *= 0.9);
	}
} 

function setReset(){
	  chartWidth = origChartWidth;
      chartHeight = origChartHeight;
      chart.setSize(chartWidth , chartHeight);
}

function addLine(){
	var i = chart.series.length; 
	var options = {
		type : 'line', 
		dataLabels : {
			 enabled : true
		},
		data : [19999,19999,19999,19999,19999]
	}; 
	chart.addSeries(options, false); 
	chart.redraw(); 
	$('#addLine').attr('disabled',true); 
}
function addBar(){
	var i = chart.series.length; 
	var options = {
		type : 'bar' ,
		data : [1111,9222,6333,5444,3555]
	}; 
	chart.addSeries(options, false); 
	chart.redraw(); 
	$('#addBar').attr('disabled',true); 
}
function addColumn(){
	var i = chart.series.length; 
	var options = {
		type : 'column' ,
		data : [1111,9222,6333,5444,3555]
	}; 
	chart.addSeries(options, false); 
	chart.redraw(); 
	$('#addColumn').attr('disabled',true); 
}
//修改制定的系列的数据
function setData() {  
	var selectedSeries = chart.getSelectedSeries(); 
	if (!selectedSeries.length) alert('请选择要修改的数据系列'); 
	$.each(selectedSeries, function(i, series) {
		series.setData([9999,19999,29999,19999,39999], false);
		series.selected = false;
	});  
	$('input[type=checkbox]').attr('checked',false);
	chart.redraw(); 
}
function addPie() { 
	var i = chart.series.length; 
	var options = {
		type : 'pie',
		center:[320, 90],  
		data : [111,222,333,444,555]
	}; 
	chart.addSeries(options, false); 
	chart.redraw(); 
	$('#addPie').attr('disabled',true); 
}
//移除指定的数据系列.
function removeSeries() {
	var selectedSeries = chart.getSelectedSeries(); 
	if (!selectedSeries.length) alert('请选择要删除的数据系列'); 
	$.each(selectedSeries, function(i, series) {
		series.remove(false);
		series.selected = false;
	}); 
	$('input[type=checkbox]').attr('checked',false);
	chart.redraw();
}
//添加数据系列
function addSeries(type, stacking, line) {
			var i = chart.series.length;
			var options = {
				type: type,
				stacking: stacking,
				yAxis: /(bar|column)/.test(type) ? 1 : 0,
				dataLabels: {
					enabled: type == 'pie'
				},
				data: [
					9999,9999,9999,9999,9999
				]
			};
			
			if (type == 'scatter' && line) {
				options.lineWidth = 1;
			}
			
			chart.addSeries(options, false);
			
			chart.redraw();
		}

function hidePic(){
	var nm = $('#sname').val(); 
	if(nm!=''){
		var series = chart.get(nm);
        series.hide() ; 
	}
}

function showPic(){
	var nm = $('#sname').val();
	if(nm!=''){
		var series = chart.get(nm); 
        series.show();
	}
}
