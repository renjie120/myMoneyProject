var myTree= new GridTree();		
function test(){
	window.path = $('#path').val();
	var content = {columnModel:[
						      {header:'类型名称',headerIndex:'tallyTypeDesc',width:'8%'},
                              {header:'总数',headerIndex:'sumMoney',width:'8%'},   
                              {header:'平均值',headerIndex:'avgMoney',width:'8%'},                                 
                              {header:'收支类型',headerIndex:'moneyType',width:'10%'}
                             ],  
                      hidddenProperties:["tallyTypeSno","tallyTypeDesc","parentCode","typeCode","moneyType"],      
                      dataUrl:window.path+"/typeConfig!getTypeTree.action",
                      idColumn:'typeCode',//id所在的列,一般是主键(不一定要显示出来)
                      parentColumn:'parentCode', //父亲列id
                      closeImg : window.path+'/js/gridTree/images/minus.gif',//父级节点的关闭图标
					  openImg : window.path+'/js/gridTree/images/plus.gif',//父级节点的打开图标
					  lazyLoadImg : window.path+'/js/gridTree/images/plus2.gif',//懒加载时显示的图标
					  blankImg : window.path+'/js/gridTree/images/blank.gif',//空白节点
					  noparentImg :window.path+'/js/gridTree/images/leaf.gif',//树叶节点的图标
                      expandAll:true,
                      onSuccess:function(){
                      	$('td[_td_pro=moneyType]').each(function(){
                      		if($(this).text()=='2')	$(this).text("支出");
                      		else
                      				$(this).text("收入");
                      	});
                      },
                      handler:[{"onclick":function(row){
                      	parent.frames[1].location.assign(window.path+"/jsp/moneyType/MoneyTypeShow.jsp?typeSno="+row.getAttribute('tallyTypeSno')
                      		+"&parentCode="+row.getAttribute('parentCode')+"&typeCode="+row.getAttribute('typeCode')
                      		+"&tallyTypeDesc="+row.getAttribute('tallyTypeDesc')
                      		+"&moneyType="+row.getAttribute('moneyType'));
                      }}],
                      exchangeColor:false,
                    	tableId:'testTable',//表格树的id
                      el:'newtableTree'//要进行渲染的div id
         };
	myTree.loadData(content);
	myTree.makeTable();
}	
function openAll(){
	myTree.expandAll();
}

function closeAll()
{
	myTree.closeAll();
}