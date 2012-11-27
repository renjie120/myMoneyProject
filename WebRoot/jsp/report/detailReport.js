var AAA = '';
$(function(){
	AAA = $('#path').val(); 
	$('#showStatisBtn').toggle(function(){
	 	$('tr[commonData=1]').hide();
	 	$('#showStatisBtn').html("显示全部");
	 },function(){
	 	$('tr[commonData=1]').show();
	 	$('#showStatisBtn').html("仅显示统计");
	 }) ;
});  

function showAllSpan()
{
	 $('span[time]').toggle() ; 
}
 
//进行颜色标识
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
//返回详细的报表展示信息.
function openType(type){
	location.href= AAA+'/detailReport!reportDetail.action?bigType='+type;
}

//点击某一个大类别的连接之后，跳转到小类别的详细报表展示页面.
function openBigDetail(year,month,type){  
	location.href= AAA+'/detailReport!reportDetailByTimeAndType.action?year='+year+"&month="+month
		+"&bigType="+type; 
}

//返回详细的小类别信息和时间的报表.
function openSmallDetail(year,month,type){  
	openNewWin(AAA+'/detailReport!reportDetailByTimeAndSmallType.action?year='+year+"&month="+month
		+"&smallType="+type, "dialogWidth=850px;dialogHeight=400px;");  
} 

//按年统计每月里面的收支信息.得到每月里面的最多的几项开支信息.
function openYearDetail(year){ 
	openNewWin(AAA
							+ "/detailReport!reportInYearPage.action?year="
							+ year, "dialogWidth=850px;dialogHeight=400px;"); 
}

//按月统计每月里面的收支信息.得到每月里面的最多的几项开支信息.
function openMonthDetail1(year,month){ 
	openNewWin(AAA
							+ "/detailReport!reportInMonthPage.action?year="
							+ year+"&month="+month, "dialogWidth=850px;dialogHeight=400px;"); 
}

//得到一年里面的某个类别的统计排行信息.
function getEachMonthByType(year,type,typeName){
 	openNewWin(AAA
							+ "/detailReport!getEachMonthByType.action?year="
							+ year+"&smallType="+type+"&bigType="+typeName, "dialogWidth=850px;dialogHeight=400px;"); 
}

//按月统计每月里面的收支信息.得到每月里面的最多的几项开支信息.
function openMonthDetail2(year,month,bigType){ 
	openNewWin(AAA
							+ "/detailReport!reportInMonthPage.action?year="
							+ year+"&month="+month+"&bigType="+bigType, "dialogWidth=850px;dialogHeight=400px;"); 
}

//点击更新按钮，弹出更新信息的页面.s
function update(id){
	var ans =openNewWin(AAA
							+ "/grid!beforeUpdateMoneyDetail.action?moneySno="
							+ id, "dialogWidth=400px;dialogHeight=300px;");
	if (ans != 'start')
				location.reload();
}

/**
  * willcheck:要进行处理的表格对象(或者行的集合即可)
  * colnum:要进行合并单元格的列
  */
function coltogather(willcheck,colnum){ 
	var alltext = [],togotherNum = [],oldnum = [],id=1,lasttext=null,rmflag=1; 
	//逐列的数据进行扫描,得到里面的不重复的数据,以及各个数据的数目,然后将依据此结果进行设置rowspan属性
	willcheck.each(function(){
	      var _rmnum = this.getAttribute('rmnum');
		  if(!_rmnum)_rmnum=0;
	      var trdom= $('td:eq('+(colnum-_rmnum)+')',this)
		  var text = $(trdom).text();  
		  //如果上一行记录文本为空,说明是第一行
		  if(lasttext==null) {
			   lasttext = text;
		  }else {
			  //如果当前行和上一行记录一样,说明要进行合并,合并的单元格数目就加1
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
	 //复制allnum数组中的数据到oldnum数组
	 $.each(togotherNum, function(i, n){
         oldnum[i] =n;
     }); 
     var index = 0,len = togotherNum.length;
	 //逐行进行处理,设置指定列的rowspan属性,以及将要合并的单元格remove!
	 willcheck.each(function() {
				// 得到一个属性表示该行已经被移除了几个td
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
					// 移除了td之后,要在tr里面添加一个属性标示已经移除的td的数目
					if (_rmnum == 0) {
						$(this).attr('rmnum', 1);
					} else {
						$(this).attr('rmnum', 1 + _rmnum * 1);
					}
				}
			});   
	 //清空数组
	 alltext = null;
	 togotherNum = null;
	 oldnum = null; 
}

function replaceSameYear(){
	coltogather($('#reportTable tr'),0); 
	showColor();
} 

//点击删除按钮，进行删除记录的操作.
function del(id){
	if (confirm("确定删除记录[" + id + "]?")) {
		$.ajax({
				url : AAA + "/grid!deleteMoney.action",
				data : "moneySno=" + id,
				type : 'POST',
				dataType : 'json',
				error : function(x, textStatus, errorThrown) {
					if (x.responseText.indexOf('成功') != -1) {
						alert(x.responseText);
						location.reload();
					}
				}
			});
	}
}