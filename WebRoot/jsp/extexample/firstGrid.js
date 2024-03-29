 /*
  *下面是最简单的数组表格
  */ /*
Ext.onReady(function(){

    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    var myData = [
        ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
        ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
        ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
        ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
        ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
        ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am'],
        ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
        ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am'],
        ['Citigroup, Inc.',49.37,0.02,0.04,'9/1 12:00am'],
        ['E.I. du Pont de Nemours and Company',40.48,0.51,1.28,'9/1 12:00am'],
        ['Exxon Mobil Corp',68.1,-0.43,-0.64,'9/1 12:00am'],
        ['General Electric Company',34.14,-0.08,-0.23,'9/1 12:00am'],
        ['General Motors Corporation',30.27,1.09,3.74,'9/1 12:00am'],
        ['Hewlett-Packard Co.',36.53,-0.03,-0.08,'9/1 12:00am'],
        ['Honeywell Intl Inc',38.77,0.05,0.13,'9/1 12:00am'],
        ['Intel Corporation',19.88,0.31,1.58,'9/1 12:00am'],
        ['International Business Machines',81.41,0.44,0.54,'9/1 12:00am'],
        ['Johnson & Johnson',64.72,0.06,0.09,'9/1 12:00am'],
        ['JP Morgan & Chase & Co',45.73,0.07,0.15,'9/1 12:00am'],
        ['McDonald\'s Corporation',36.76,0.86,2.40,'9/1 12:00am'],
        ['Merck & Co., Inc.',40.96,0.41,1.01,'9/1 12:00am'],
        ['Microsoft Corporation',25.84,0.14,0.54,'9/1 12:00am'],
        ['Pfizer Inc',27.96,0.4,1.45,'9/1 12:00am'],
        ['The Coca-Cola Company',45.07,0.26,0.58,'9/1 12:00am'],
        ['The Home Depot, Inc.',34.64,0.35,1.02,'9/1 12:00am'],
        ['The Procter & Gamble Company',61.91,0.01,0.02,'9/1 12:00am'],
        ['United Technologies Corporation',63.26,0.55,0.88,'9/1 12:00am'],
        ['Verizon Communications',35.57,0.39,1.11,'9/1 12:00am'],
        ['Wal-Mart Stores, Inc.',45.45,0.73,1.63,'9/1 12:00am']
    ];

    // 下面是演示根据不同的数据内容显示不同的格式
    function change(val){
        if(val > 0){
            return '<span style="color:green;">' + val + '</span>';
        }else if(val < 0){
            return '<span style="color:red;">' + val + '</span>';
        }
        return val;
    }

    // example of custom renderer function
    function pctChange(val){
        if(val > 0){
            return '<span style="color:green;">' + val + '%</span>';
        }else if(val < 0){
            return '<span style="color:red;">' + val + '%</span>';
        }
        return val;
    }

    // create the data store
    var store = new Ext.data.SimpleStore({
        fields: [
           {name: 'company'},
           {name: 'price', type: 'float'},
           {name: 'change', type: 'float'},
           {name: 'pctChange', type: 'float'},
           {name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia'}
        ]
    });
    store.loadData(myData); 

    //在2.0下面添加多选模式
    var sm = new Ext.grid.CheckboxSelectionModel();

    
    // 创建表格
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
	        new Ext.grid.RowNumberer(), 
	        sm,
            {header: "Company", width: 160, sortable: true, dataIndex: 'company'},
            {header: "Price", width: 75, sortable: true, renderer: 'usMoney', dataIndex: 'price'},
            //下面设置根据数据的不同而显示不同的结果
            {header: "Change", width: 75, sortable: true, renderer: change, dataIndex: 'change'},
            {header: "% Change", width: 75, sortable: true, renderer: pctChange, dataIndex: 'pctChange'},
            {header: "Last Updated", width: 85, sortable: true, renderer: Ext.util.Format.dateRenderer('m/d/Y'), dataIndex: 'lastChange'}
        ],
        stripeRows: true,
        sm:sm,
       // autoExpandColumn: 'company',
        height:350,
        width:600,
        title:'收支列表',
        el:'grid',
        bbar: new Ext.PagingToolbar({
	        pageSize: 10,
	        store: store,
	        displayInfo: true,
	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	        emptyMsg: "没有记录"
	    }) 
    }); 
	
    grid.render();
	//默认选择第一行
    grid.getSelectionModel().selectFirstRow();
});*/
Ext.onReady(function(){ 
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
    //调用后台代码的表格数据源
    var ds = new Ext.data.Store({
	    proxy: new Ext.data.HttpProxy({url:window.path+'/grid!initExtMoneyDetailGrid.action'}), 
	    reader: new Ext.data.JsonReader({
		    totalProperty: 'totalNum',
		    root: 'root'
		}, [
		    {name: 'moneyTime'},
		    {name: 'money'},
		    {name: 'moneyType'},
		    {name: 'moneyDesc'}
		]) 
	});
	ds.load({params:{start:0,limit:10}});

    // 下面是演示根据不同的数据内容显示不同的格式
    function change(val){
        if(val*1.0 > 100){
            return '<span style="color:red;">' + val + '</span>';
        } 
        return val;
    } 
    //在2.0下面添加多选模式
    var sm = new Ext.grid.CheckboxSelectionModel();

    // 下面为创建可以编辑的表格列进行准备
    var fm = Ext.form;
    
    // 创建表格
    var grid = new Ext.grid.GridPanel({
        store: ds, 
        columns : [new Ext.grid.RowNumberer(), sm, {
									header : "收支时间",
									width : 80,
									sortable : true,
									dataIndex : 'moneyTime'
								}, {
									header : "金额",
									width : 100,
									sortable : true,
									render : change,
									dataIndex : 'money',
									editor : new fm.TextField({
												allowBlank : false
											})
								},
								// 下面设置根据数据的不同而显示不同的结果
								{
									header : "类别",
									width : 75,
									sortable : true,
									dataIndex : 'moneyType'
								}, {
									header : "描述",
									width : 250,
									sortable : true,
									dataIndex : 'moneyDesc'
								}],
        stripeRows: true,
        sm:sm,
       // autoExpandColumn: 'company',
        height:350,
        width:600,
        title:'收支列表',
        el:'grid', 
        bbar: new Ext.PagingToolbar({
	        pageSize: 10,
	        store: ds,
	        displayInfo: true,
	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	        emptyMsg: "没有记录"
	    }) 
    });  

    grid.render();
	//默认选择第一行
    grid.getSelectionModel().selectFirstRow();
});