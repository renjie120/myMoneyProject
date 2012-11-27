var myTree = new GridTree();
function test() {
	window.path = $('#path').val();
	var content = {
		columnModel : [{
					header : '��Ʊ��',
					headerIndex : 'stockNo',
					width : '8%'
				}, {
					header : '�۸�',
					headerIndex : 'stockPrice',
					width : '8%'
				}, {
					header : '����',
					headerIndex : 'num',
					width : '8%'
				}, {
					header : '����',
					headerIndex : 'cost',
					width : '10%'
				}, {
					header : '��ͣ�۸�',
					headerIndex : 'price110',
					width : '10%'
				}, {
					header : '��ͣ�۸�',
					headerIndex : 'price90',
					width : '10%'
				}, {
					header : '������',
					headerIndex : 'profitSum',
					width : '10%'
				}, {
					header : '������',
					headerIndex : 'profit',
					width : '10%'
				}, {
					header : '��������',
					headerIndex : 'type',
					width : '10%'
				}], 
		dataUrl : window.path + "/stockDetailTree!getTypeTree.action",
		lazyLoadUrl: appPath + "/stockDetailTree!getTypeChildTree.action",
		idColumn : 'id',// id���ڵ���,һ��������(��һ��Ҫ��ʾ����)
		parentColumn : 'PId', // ������id
		closeImg : window.path + '/js/gridTree/images/minus.gif',// �����ڵ�Ĺر�ͼ��
		openImg : window.path + '/js/gridTree/images/plus.gif',// �����ڵ�Ĵ�ͼ��
		lazyLoadImg : window.path + '/js/gridTree/images/plus2.gif',// ������ʱ��ʾ��ͼ��
		blankImg : window.path + '/js/gridTree/images/blank.gif',// �հ׽ڵ�
		noparentImg : window.path + '/js/gridTree/images/leaf.gif',// ��Ҷ�ڵ��ͼ��
		expandAll : true,
		lazy: true, 
        leafColumn: 'isLeaf',
		exchangeColor : false,
		tableId : 'testTable',// �������id
		el : 'newtableTree'// Ҫ������Ⱦ��div id
	};
	myTree.loadData(content);
	myTree.makeTable();
}
function openAll() {
	myTree.expandAll();
}

function closeAll() {
	myTree.closeAll();
}