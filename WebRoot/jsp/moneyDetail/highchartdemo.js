$(function() {
			window.path = $('#path').val();
		});
var showTable = true;
var showNestReport = true;
var data = new Array();
var chartLoaded = false;
var showCheckMsgDiv = false;
// ͼ�����
var chartObj = null;
// ȫ��������������Ϣ
var dataSet = [];
// ÿ������ƽ����
var avgs = [];
// ÿ�������ܺ�
var sums = [];
// ����id�ļ���
var typeIds = [];
// ������Ƶļ���
var typeNames = [];
// �������ͣ�year�ǰ���ݣ�month�ǰ��·�.
var reportType = 'year';
// �������ͣ�big�ǰ����࣬small�ǰ�С��.
var reportType2 = 'big';
// ȫ���ķ�����Ϣ
var sortType = [];

function changeYears() {
}

// �õ����֮��ĶԱ�����
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
					// �õ�����������Ϣ
					eval("dataSet=" + strs[1]);

					addTypeNames()

					// �õ��������Ϣ��
					eval("sortType=" + strs[2]);
					eval("avgs=" + strs[3]);
					eval("sums=" + strs[4]);
					eval("typeIds=" + strs[5]);

					initShowNewestReport();
				}
			});
}

// ���ݷ��ص�������Ϣ������tallyTypes div��ȥ��
function addTypeNames() {
	typeNames = [];
	for (var i = 0, j = dataSet.length; i < j; i++) {
		var dataStr = dataSet[i].join('$$').split(/\$\$/);
		typeNames.push(dataStr[0]);
	}
}

// ���ĳһ������������֮����ת��С������ϸ����չʾҳ��.
function openBigDetail(year, month, type) {
	if (('' + month).length < 2) {
		month = '0' + month;
	}
	openNewWin(window.path
			+ '/detailReport!reportDetailByTimeAndType.action?year=' + year
			+ "&month=" + month + "&bigType=" + type);
}

function changeTypeSort(){
	//����
	if($('#bigTypeSort').val()=='1'){
	 	  origChartWidth = 900; 
		  chartWidth = origChartWidth; 
	}
	//С��
	else{
	  origChartWidth = 2000; 
	  chartWidth = origChartWidth; 
	}
}
// ������ϸ��С�����Ϣ��ʱ��ı���.
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

// ����ͳ��ÿ���������֧��Ϣ.�����Ǵ���С�࣬����ʾ��һ����ֻ��С��ͳ�ƣ�
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
	// ��������װ��������.
	$(sortType).each(function(i) {
				var _obj = {};
				_obj.name = sortType[i];
				_obj.id = sortType[i];
				_obj.type = 'column';
				// �����п��Ա�ѡ������֮���������onclick�¼���
				//_obj.allowPointSelect = true;
				//_obj.showCheckbox = true;
				var _arr = new Array();
				$(dataSet).each(function(j) {
							_arr.push(dataSet[j][i + 1]);
						});
				eval("_obj.data=[" + _arr.join(',') + "]"); 
				s.push(_obj);
			});
	// ������ƽ�������ڵ�����
	var _obj = {};
	_obj.type = 'spline';
	_obj.name = 'ƽ����';
	eval("_obj.data=[" + avgs.join(',') + "]");
	s.push(_obj);

	// ������װ�ϼƱ�ͼ������.
	var _obj2 = {};
	_obj2.type = 'pie';
	_obj2.name = '�ܼ�'; 
	_obj2.center = [120, 90];
	//_obj2.showInLegend = true;  //�Ƿ���ʾ������Ĳ˵���
	//_obj2.showCheckbox = true;  //�Ƿ���ֶ�ѡ�� 
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
					text : '�ҵı���'
				},
				credits : {
					text : '�ٶ�',
					href : 'http://www.baidu.com'
				},
				xAxis : {
					categories : typeNames
				},
				tooltip : {
					formatter : function() {
						if (this.point.name) {
							s = this.point.name + ': ' + this.y + ' Ԫ';
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
								// this.name:�����״ͼ��Ӧcategories������,this.columnIndex:�����״ͼ�����categories������
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
		// �������.
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
//�޸��ƶ���ϵ�е�����
function setData() {  
	var selectedSeries = chart.getSelectedSeries(); 
	if (!selectedSeries.length) alert('��ѡ��Ҫ�޸ĵ�����ϵ��'); 
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
//�Ƴ�ָ��������ϵ��.
function removeSeries() {
	var selectedSeries = chart.getSelectedSeries(); 
	if (!selectedSeries.length) alert('��ѡ��Ҫɾ��������ϵ��'); 
	$.each(selectedSeries, function(i, series) {
		series.remove(false);
		series.selected = false;
	}); 
	$('input[type=checkbox]').attr('checked',false);
	chart.redraw();
}
//�������ϵ��
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
