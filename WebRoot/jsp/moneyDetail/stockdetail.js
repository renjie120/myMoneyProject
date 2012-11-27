var myTree = new GridTree();
function test() {
	window.path = $('#path').val();
	var content = {
		columnModel : [{
					header : '股票名',
					headerIndex : 'stockNo',
					width : '8%'
				}, {
					header : '价格',
					headerIndex : 'stockPrice',
					width : '8%'
				}, {
					header : '股数',
					headerIndex : 'num',
					width : '8%'
				}, {
					header : '耗资',
					headerIndex : 'cost',
					width : '10%'
				}, {
					header : '涨停价格',
					headerIndex : 'price110',
					width : '10%'
				}, {
					header : '跌停价格',
					headerIndex : 'price90',
					width : '10%'
				}, {
					header : '收益金额',
					headerIndex : 'profitSum',
					width : '10%'
				}, {
					header : '收益率',
					headerIndex : 'profit',
					width : '10%'
				}, {
					header : '交易类型',
					headerIndex : 'type',
					width : '10%'
				}], 
		dataUrl : window.path + "/stockDetailTree!getTypeTree.action",
		lazyLoadUrl: appPath + "/stockDetailTree!getTypeChildTree.action",
		idColumn : 'id',// id所在的列,一般是主键(不一定要显示出来)
		parentColumn : 'PId', // 父亲列id
		closeImg : window.path + '/js/gridTree/images/minus.gif',// 父级节点的关闭图标
		openImg : window.path + '/js/gridTree/images/plus.gif',// 父级节点的打开图标
		lazyLoadImg : window.path + '/js/gridTree/images/plus2.gif',// 懒加载时显示的图标
		blankImg : window.path + '/js/gridTree/images/blank.gif',// 空白节点
		noparentImg : window.path + '/js/gridTree/images/leaf.gif',// 树叶节点的图标
		expandAll : true,
		lazy: true, 
        leafColumn: 'isLeaf',
		exchangeColor : false,
		tableId : 'testTable',// 表格树的id
		el : 'newtableTree'// 要进行渲染的div id
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