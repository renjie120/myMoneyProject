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
//ͼ�����
var chartObj = null;
//ȫ��������������Ϣ
var dataSet = [];
//ÿ������ƽ����
var avgs = [];
//ÿ�������ܺ�
var sums = [];
//����id�ļ���
var typeIds = [];
//������Ƶļ���
var typeNames = [];
//�������ͣ�year�ǰ���ݣ�month�ǰ��·�.
var reportType = 'year';
//�������ͣ�big�ǰ����࣬small�ǰ�С��.
var reportType2 = 'big';
//ȫ���ķ�����Ϣ
var sortType = [];

//�ı����֮��������ʾС����ѡ���
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
		$('#setup').text('��ʾ����');
	}else{
		$("tr:gt(0):not(:last)").fadeIn(10);
		showCheckMsgDiv = true;
		$('#setup').text('��������');
	}
}

//�������µ�������ʾͼ��
function show(){
	if (chartLoaded){
       chartObj.setDataXML(generateXML($('input[name=AnimateChart]').attr('checked'),$('input[name=showValue]').attr('checked')));
    }
}
//ȫѡ��ť
function choose(){
	$('#tallyTypes input[name=types]').each(function(i){
		$(this).attr('checked',true);
	});	
	clickChkbox();
}

//ȫ��ѡ��ť
function dischoose(){
	$('#tallyTypes input[name=types]').each(function(i){
		$(this).attr('checked',false);
	});	
	clickChkbox();
}

//�Ƿ�������ʾ
function changeNowShow(){
	if($('input[name=showChangeNow]').attr('checked')==false){
		$('#btnshow').attr('disabled',false);	
	}else{
		$('#btnshow').attr('disabled',true);
	}
}

//���С����ѡ����õ�js��������Ҫ����ѡ���С����Զ����γ�xml���ݡ�
function clickChkbox(){
	if($('input[name=showChangeNow]').attr('checked')==false){
		return ;
	}
    if (chartLoaded){
       chartObj.setDataXML(generateXML($('input[name=AnimateChart]').attr('checked'),$('input[name=showValue]').attr('checked')));
    }
}

//�����ѡ����ݻ���ѡ���·�֮���ѯ��ʾͼ��
function clickChkbox2(){
	if($('input[name=showChangeNow]').attr('checked')==false){
		return ;
	}
    if (chartLoaded){
       chartObj.setDataXML(generateXML($('input[name=AnimateChart]').attr('checked'),$('input[name=showValue]').attr('checked')));
    }
}

//�õ����֮��ĶԱ�����
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
		 	//�õ�����������Ϣ
		 	eval("dataSet="+strs[1]);
		 	//���ݷ��ص�������Ϣ������tallyTypes div��ȥ��
		 	addCheckBox()
		 	//�õ��������Ϣ��
		 	eval("sortType="+strs[2]);
		 	eval("avgs="+strs[3]);
		 	eval("sums="+strs[4]);
		 	eval("typeIds="+strs[5]);
		 	addCategories()
		 	$('#showCategories').html('ѡ�����');
	}});
}


//�õ���ѡ��ݵĶԱ�����
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
		 	//�õ�����������Ϣ
		 	eval("dataSet="+strs[1]);		 	
		 	addCheckBox();
		 	//�õ��������Ϣ��
		 	eval("sortType="+strs[2]);
		 	eval("avgs="+strs[3]);
		 	eval("sums="+strs[4]);
		 	eval("typeIds="+strs[5]);
		 	addCategories();
		 	$('#showCategories').html('ѡ���·�');
	}});
}

//���ݷ��ص�������Ϣ������tallyTypes div��ȥ��
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

//���ݷ��ص��������ÿ��Զ�̬�ı������checkbox
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

//��ͼ����е�һ�μ��ص�ʱ���Զ�ִ������ĺ�����
function FC_Rendered(domId) {
	if (domId == "fusionchart") {
		$('#tallyTypes').show();
		$('#months').show();
		chartLoaded = true;
		chartObj = getChartFromId(domId);
	}
	return true;
}

//���������������xml�ַ�����ƴ��
function generateXML(animate,showValue) {
	var checkCategories = [];
	$('#months input[name=categoryId]').each(function(i){ 
		  checkCategories.push(this.checked); 
	}); 
	var ans = [];
	ans.push("<chart caption='�Աȷ���ͼ' formatNumberScale='0' numberPrefix='$' animation='" + ((animate == true) ? "1" : "0") + "' ");
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
					//����Ӧ��category�ǲ����Ѿ�ѡ��
					ans2.push(dataSet[i][st+1]);
					ans2.push("'/>");
				}
			});	
			ans2.push("</dataset>");
		}
	}	
	ans.push(ans2.join('')); 
	//�������ƽ��ֵ
	ans.push("<dataSet seriesName='ƽ��ֵ' showValues='0' parentYAxis='s'>");
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
	//������ӵ�������
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
	//���а������/�¶ȵ��ۼƼ���.
	var stats = new HashMap();
	str.push('<table width="800px" class="mytable"><tr>');
	str.push("<th>����</th>");
	$(sortType).each(function(i){
		str.push("<th>"+sortType[i]+"</th>");
		stats.put(sortType[i],0);
	}); 
	str.push("<th>ƽ����</th>");
	str.push("<th>�ܺ�</th>");
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
				//�����������ۼƺ�.
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
		//�����������ۼƺ�.
		var _i = stats.get('all')*1;
		_i=_i+1*sums[i];
		stats.put('all',_i);
		str.push("</tr>");
	}); 
	
	str.push("<tr><td>�ۼ�</td>");
	$(sortType).each(function(i){
		str.push("<td >"+stats.get(sortType[i]).toFixed(2)+"</td>");
	}); 
	str.push("<td>-</td>");
	str.push("<td >"+stats.get('all').toFixed(2)+"</td>");
	str.push("</tr>");
	
	str.push("</table>");
	$('#mytable').html(str.join(''));
}
 
//������ɫ��ʶ
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

//���ĳһ������������֮����ת��С������ϸ����չʾҳ��.
function openBigDetail(year,month,type){   
	 if((''+month).length<2){
	 	month = '0'+month;
	 }
	openNewWin(window.path+'/detailReport!reportDetailByTimeAndType.action?year='+year+"&month="+month
		+"&bigType="+type); 
}

//������ϸ��С�����Ϣ��ʱ��ı���.
function openSmallDetail(year,month,type){ 
	if((''+month).length<2){
	 	month = '0'+month;
	 }
	openNewWin(window.path+'/detailReport!reportDetailByTimeAndSmallType.action?year='+year+"&month="+month
		+"&smallType="+type, "dialogWidth=850px;dialogHeight=400px;");  
} 

//����ͳ��ÿ���������֧��Ϣ.�����Ǵ���С�࣬����ʾ��һ����ֻ��С��ͳ�ƣ�
function openYearDetail(year){  
	openNewWin(window.path
							+ "/detailReport!reportInYearPage.action?year="
							+ year, "dialogWidth=850px;dialogHeight=400px;"); 
}

var chart;  
function _loadHightChart(){   
	var s = [];
	//��������װ��������.
	$(sortType).each(function(i){
		var _obj = {}; 
		_obj.name = sortType[i];  
		_obj.type  =  'column';  
		//�����п��Ա�ѡ������֮���������onclick�¼���
		_obj.allowPointSelect =  true; 
		var _arr = new Array();
		$(dataSet).each(function(j){  
			 _arr.push(dataSet[j][i+1]);
		}); 
		eval("_obj.data=["+_arr.join(',')+"]"); 
		s.push(_obj);
	});  
	//������ƽ�������ڵ�����
	var _obj = {};
	_obj.type = 'spline';
	_obj.name = 'ƽ����'; 
	eval("_obj.data=["+avgs.join(',')+"]"); 
	s.push(_obj);
	
	//������װ�ϼƱ�ͼ������.
	var _obj2 = {};
	_obj2.type = 'pie';
	_obj2.name = '�ܼ�'; 
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
			text: '�ҵı���'
		},
		credits: {
            text: '�ٶ�',
            href: 'http://www.baidu.com'
        },
		xAxis: {
			categories: typeNames   
		}, 
		tooltip: {
            formatter: function() {
            	if (this.point.name) {  
					s =  this.point.name +': '+ this.y +' Ԫ';
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
						//this.name:�����״ͼ��Ӧcategories������,this.columnIndex:�����״ͼ�����categories������
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
 		//�������.
 		alert(typeIds[points[0].x]);
 	}
 /*
	 var selectedPoints = chart.getSelectedSeries();
       					 alert(selectedPoints[0].x) */
}

//��ʾ���
function initShowTable(){//dataSet,sortType 
	if(showTable){
		_showTable();
		$('#msgDiv').show();
		$('#chartdiv').hide();
		$('#hightChartDiv').hide();
		$('#showtable').text('���ر��');
		showTable = false;
	}else{
		$('#msgDiv').hide();
		$('#hightChartDiv').hide();
		$('#chartdiv').show(); 
		$('#showtable').text('��ʾ���');
		showTable = true;
	} 
	
	if(!showNestReport){ 
		$('#showNewestReport').text('��ʾ���±���');
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
		$('#showNewestReport').text('�������±���');
		showNestReport = false;
	}else{
		$('#msgDiv').hide();
		$('#chartdiv').show();
		$('#hightChartDiv').hide();
		$('#showNewestReport').text('��ʾ���±���');
		showNestReport = true;
	} 
	if(!showTable){ 
		$('#showtable').text('��ʾ���');
		showTable = true;
	}
}