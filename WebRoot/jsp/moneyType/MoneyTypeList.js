var myTree= new GridTree();		
function test(){
	window.path = $('#path').val();
	var content = {columnModel:[
						      {header:'��������',headerIndex:'tallyTypeDesc',width:'8%'},
                              {header:'����',headerIndex:'sumMoney',width:'8%'},   
                              {header:'ƽ��ֵ',headerIndex:'avgMoney',width:'8%'},                                 
                              {header:'��֧����',headerIndex:'moneyType',width:'10%'}
                             ],  
                      hidddenProperties:["tallyTypeSno","tallyTypeDesc","parentCode","typeCode","moneyType"],      
                      dataUrl:window.path+"/typeConfig!getTypeTree.action",
                      idColumn:'typeCode',//id���ڵ���,һ��������(��һ��Ҫ��ʾ����)
                      parentColumn:'parentCode', //������id
                      closeImg : window.path+'/js/gridTree/images/minus.gif',//�����ڵ�Ĺر�ͼ��
					  openImg : window.path+'/js/gridTree/images/plus.gif',//�����ڵ�Ĵ�ͼ��
					  lazyLoadImg : window.path+'/js/gridTree/images/plus2.gif',//������ʱ��ʾ��ͼ��
					  blankImg : window.path+'/js/gridTree/images/blank.gif',//�հ׽ڵ�
					  noparentImg :window.path+'/js/gridTree/images/leaf.gif',//��Ҷ�ڵ��ͼ��
                      expandAll:true,
                      onSuccess:function(){
                      	$('td[_td_pro=moneyType]').each(function(){
                      		if($(this).text()=='2')	$(this).text("֧��");
                      		else
                      				$(this).text("����");
                      	});
                      },
                      handler:[{"onclick":function(row){
                      	parent.frames[1].location.assign(window.path+"/jsp/moneyType/MoneyTypeShow.jsp?typeSno="+row.getAttribute('tallyTypeSno')
                      		+"&parentCode="+row.getAttribute('parentCode')+"&typeCode="+row.getAttribute('typeCode')
                      		+"&tallyTypeDesc="+row.getAttribute('tallyTypeDesc')
                      		+"&moneyType="+row.getAttribute('moneyType'));
                      }}],
                      exchangeColor:false,
                    	tableId:'testTable',//�������id
                      el:'newtableTree'//Ҫ������Ⱦ��div id
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