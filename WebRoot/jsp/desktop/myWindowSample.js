/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

// Sample desktop configuration
MyDesktop = new Ext.app.App({
	init : function() {
		Ext.QuickTips.init();
	},

	//�������½ǵĲ˵���ݼ�
	getModules : function() {
		return [new MyDesktop.GridWindow(),
				new MyDesktop.AddMoney(),
				new MyDesktop.ShowExtTree(), 
				new MyDesktop.GridTreeDemo(), 
				//�������һ�������飬���ж���ӵĴ���˵�.
				//new MyDesktop.BogusMenuModule(),
				new MyDesktop.MyStatis()//,
		//new MyDesktop.AccordionWindow(), 
		//new MyDesktop.TabWindow() 
		];
	},

	// config for the start menu
	getStartConfig : function() {
		return {
			title : '���ϵͳ',
			iconCls : 'user',
			toolItems : [{
						text : '����ϵͳ',
						iconCls : 'settings',
						scope : this,
						handler:function(){
						Ext.MessageBox.alert('����', '�ȿ��š����� ', function(btn) {
						    return false;
						});

						}
					}, '-', {
						text : '�˳�ϵͳ',
						iconCls : 'logout',
						handler : function() {
							Ext.MessageBox.confirm('ѡ���', '�Ƿ�ȷ���˳�ϵͳ��',
									function(btn) {
										if(btn=='yes'){
											$.ajax({
														url : appPath
																+ "/login!logout.action",
														type : 'POST',
														dataType : 'json',
														error : function(x,
																textStatus,
																errorThrown) {
															window.location = appPath
																	+ "/jsp/Login.jsp";
														}
													});
										
									}});
						},
						scope : this
					}]
		};
	}
});

/**
 * �ҵı������չʾ
 * @class MyDesktop.GridTreeDemo
 * @extends Ext.app.Module
 */
MyDesktop.GridTreeDemo = Ext.extend(Ext.app.Module, {
	init : function() {
		this.launcher = {
			text : '������ͳ��(GridTreeչʾ)',
			iconCls : 'bogus',
			handler : this.createWindow,
			scope : this,
			windowId : windowIndex
		}
	},

	createWindow : function(src) {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('GridTreeDemo');
		if (!win) {
			win = desktop.createWindow({
				//�����src��Ӧ��windowId�����������launcher��������Ķ���ģ�
				id : 'GridTreeDemo',
				title : '������ͳ��',
				width : 640,
				height : 480,
				html : '<iframe src="'
						+ appPath
						+ '/jsp/moneyType/MoneyTypeList.jsp" style="width:640px;height:480px"></iframe>',
				iconCls : 'bogus',
				shim : false,
				animCollapse : false,
				constrainHeader : true
			});
		}
		win.show();
	}
});
/*
 * Example windows
 */
MyDesktop.GridWindow = Ext.extend(Ext.app.Module, {
	id : 'grid-win',
	init : function() {
		this.launcher = {
			text : '�������',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		//ȡ�ô��壬���ȡ�������ͽ�����ʵ��.
		var win = desktop.getWindow('grid-win');

		if (!win) {
			//��2.0������Ӷ�ѡģʽ
			var sm = new Ext.grid.CheckboxSelectionModel();
			//���ú�̨����ı������Դ
			var ds = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : window.path
											+ '/grid!initExtMoneyDetailGrid.action'
								}),
						reader : new Ext.data.JsonReader({
									totalProperty : 'totalNum',
									root : 'root'
								}, [{
											name : 'moneyTime'
										}, {
											name : 'money'
										}, {
											name : 'moneyType'
										}, {
											name : 'moneyDesc'
										}])
					});
			ds.load({
						params : {
							start : 0,
							limit : 10
						}
					});
			win = desktop.createWindow({
				id : 'grid-win',
				title : '�����Ϣ��ѯ���',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,
				layout : 'fit',
				items : new Ext.grid.GridPanel({
							border : false,
							store : ds,
							bbar : new Ext.PagingToolbar({
										pageSize : 10,
										store : ds,
										displayInfo : true,
										displayMsg : '��ʾ�� {0} ���� {1} ����¼��һ�� {2} ��',
										emptyMsg : "û�м�¼"
									}),
							cm : new Ext.grid.ColumnModel([
									new Ext.grid.RowNumberer(), sm, {
										header : "��֧ʱ��",
										width : 80,
										sortable : true,
										dataIndex : 'moneyTime'
									}, {
										header : "���",
										width : 100,
										sortable : true,
										dataIndex : 'money'
									},
									// �������ø������ݵĲ�ͬ����ʾ��ͬ�Ľ��
									{
										header : "���",
										width : 75,
										sortable : true,
										dataIndex : 'moneyType'
									}, {
										header : "����",
										width : 250,
										sortable : true,
										dataIndex : 'moneyDesc'
									}]),

							viewConfig : {
								forceFit : true
							}
						})
			});
		}
		win.show();
	}
});

// tab����
MyDesktop.TabWindow = Ext.extend(Ext.app.Module, {
	id : 'tab-win',
	init : function() {
		this.launcher = {
			text : 'Tab Window',
			iconCls : 'tabs',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('tab-win');
		if (!win) {
			win = desktop.createWindow({
				id : 'tab-win',
				title : 'Tab Window',
				width : 740,
				height : 480,
				iconCls : 'tabs',
				shim : false,
				animCollapse : false,
				border : false,
				constrainHeader : true,

				layout : 'fit',
				items : new Ext.TabPanel({
					activeTab : 0,

					items : [{
								title : 'Tab Text 1',
								header : false,
								html : '<p>Something useful would be in here.</p>',
								border : false
							}, {
								title : 'Tab Text 2',
								header : false,
								html : '<p>Something useful would be in here.</p>',
								border : false
							}, {
								title : 'Tab Text 3',
								header : false,
								html : '<p>Something useful would be in here.</p>',
								border : false
							}, {
								title : 'Tab Text 4',
								header : false,
								html : '<p>Something useful would be in here.</p>',
								border : false
							}]
				})
			});
		}
		win.show();
	}
});

//������
MyDesktop.AccordionWindow = Ext.extend(Ext.app.Module, {
	id : 'acc-win',
	init : function() {
		this.launcher = {
			text : 'Accordion Window',
			iconCls : 'accordion',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('acc-win');
		if (!win) {
			win = desktop.createWindow({
				id : 'acc-win',
				title : 'Accordion Window',
				width : 250,
				height : 400,
				iconCls : 'accordion',
				shim : false,
				animCollapse : false,
				constrainHeader : true,

				tbar : [{
							tooltip : {
								title : 'Rich Tooltips',
								text : 'Let your users know what they can do!'
							},
							iconCls : 'connect'
						}, '-', {
							tooltip : 'Add a new user',
							iconCls : 'user-add'
						}, ' ', {
							tooltip : 'Remove the selected user',
							iconCls : 'user-delete'
						}],

				layout : 'accordion',
				border : false,
				layoutConfig : {
					animate : false
				},

				items : [new Ext.tree.TreePanel({
					id : 'im-tree',
					title : 'Online Users',
					loader : new Ext.tree.TreeLoader(),
					rootVisible : false,
					lines : false,
					autoScroll : true,
					tools : [{
								id : 'refresh',
								on : {
									click : function() {
										var tree = Ext.getCmp('im-tree');
										tree.body.mask('Loading',
												'x-mask-loading');
										tree.root.reload();
										tree.root.collapse(true, false);
										setTimeout(function() { // mimic a server call
													tree.body.unmask();
													tree.root
															.expand(true, true);
												}, 1000);
									}
								}
							}],
					root : new Ext.tree.AsyncTreeNode({
								text : 'Online',
								children : [{
											text : 'Friends',
											expanded : true,
											children : [{
														text : 'Jack',
														iconCls : 'user',
														leaf : true
													}, {
														text : 'Brian',
														iconCls : 'user',
														leaf : true
													}, {
														text : 'Jon',
														iconCls : 'user',
														leaf : true
													}, {
														text : 'Tim',
														iconCls : 'user',
														leaf : true
													}, {
														text : 'Nige',
														iconCls : 'user',
														leaf : true
													}, {
														text : 'Fred',
														iconCls : 'user',
														leaf : true
													}, {
														text : 'Bob',
														iconCls : 'user',
														leaf : true
													}]
										}, {
											text : 'Family',
											expanded : true,
											children : [{
														text : 'Kelly',
														iconCls : 'user-girl',
														leaf : true
													}, {
														text : 'Sara',
														iconCls : 'user-girl',
														leaf : true
													}, {
														text : 'Zack',
														iconCls : 'user-kid',
														leaf : true
													}, {
														text : 'John',
														iconCls : 'user-kid',
														leaf : true
													}]
										}]
							})
				}), {
					title : 'Settings',
					html : '<p>Something useful would be in here.</p>',
					autoScroll : true
				}, {
					title : 'Even More Stuff',
					html : '<p>Something useful would be in here.</p>'
				}, {
					title : 'My Stuff',
					html : '<p>Something useful would be in here.</p>'
				}]
			});
		}
		win.show();
	}
});

//���洴��һ�����Լ�д�Ĵ���С����
MyDesktop.AddMoney = Ext.extend(Ext.app.Module, {
	id : 'self-win',
	init : function() {
		this.launcher = {
			text : '�����֧��Ϣ',
			iconCls : 'bogus',
			handler : this.createWindow,
			scope : this,
			windowId : windowIndex
		}
	},

	createWindow : function(src) {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('self-win');
		if (!win) {
			win = desktop.createWindow({
				//�����src��Ӧ��windowId�����������launcher��������Ķ���ģ�
				id : 'self-win',
				title : '�����֧��Ϣ',
				width : 640,
				height : 480,
				html : '<iframe src="'
						+ appPath
						+ '/jsp/moneyDetail/AddMoney.jsp" style="width:100%;height:100%"></iframe>',
				iconCls : 'bogus',
				shim : false,
				animCollapse : false,
				constrainHeader : true
			});
		}
		win.show();
	}
});

// for example purposes
var windowIndex = 0;
//���洴��������ͨ�Ĵ���
MyDesktop.BogusModule = Ext.extend(Ext.app.Module, {
	init : function() {
		this.launcher = {
			text : 'ext����չʾ',
			iconCls : 'bogus',
			handler : this.createWindow,
			scope : this,
			windowId : windowIndex
		}
	},

	createWindow : function(src) {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('bogus' + src.windowId);
		if (!win) {
			win = desktop.createWindow({
				//�����src��Ӧ��windowId�����������launcher��������Ķ���ģ�
				id : 'bogus' + src.windowId,
				title : '��֧������չʾ',
				width : 640,
				height : 480,
				html : '<iframe src="'
						+ appPath
						+ '/jsp/extexample/firstTree.jsp" style="width:640px;height:480px"></iframe>',
				iconCls : 'bogus',
				shim : false,
				animCollapse : false,
				constrainHeader : true
			});
		}
		win.show();
	},
	//���������Լ�д��һ������������һ���µĴ��壬������������title����url����������
	createMyWindow : function(src) {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('extTreeDemo');
		if (!win) {
			win = desktop.createWindow({
						//�����src��Ӧ��windowId�����������launcher��������Ķ���ģ�
						id : 'extTreeDemo',
						title : src.title,
						width : 640,
						height : 480,
						html : '<iframe src="'
								+ appPath
								+ src.url
								+ '" style="width:640px;height:480px"></iframe>',
						iconCls : 'bogus',
						shim : true,
						animCollapse : true,
						constrainHeader : true
					});
		}
		win.show();
	}
});

MyDesktop.ShowExtTree = Ext.extend(Ext.app.Module, {
	init : function() {
		this.launcher = {
			text : 'ext����չʾ',
			iconCls : 'bogus',
			handler : this.createWindow,
			scope : this,
			windowId : windowIndex
		}
	},

	createWindow : function(src) {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('extTreeDemo');
		if (!win) {
			win = desktop.createWindow({
				//�����src��Ӧ��windowId�����������launcher��������Ķ���ģ�
				id : 'extTreeDemo',
				title : '��֧������չʾ',
				width : 640,
				height : 480,
				html : '<iframe src="'
						+ appPath
						+ '/jsp/extexample/firstTree.jsp" style="width:640px;height:480px"></iframe>',
				iconCls : 'bogus',
				shim : false,
				animCollapse : false,
				constrainHeader : true
			});
		}
		win.show();
	}
});

//Bogus :�ٵ�   ��MyDesktop.BogusModule�����࣬�����ͼ̳��������createWindow����!
MyDesktop.BogusMenuModule = Ext.extend(MyDesktop.BogusModule, {
			init : function() {
				this.launcher = {
					text : 'Bogus Submenu',
					iconCls : 'bogus',
					handler : function() {
						return false;
					},
					menu : {
						items : [{
									text : 'Bogus Window ' + (++windowIndex),
									iconCls : 'bogus',
									handler : this.createWindow,
									scope : this,
									windowId : windowIndex
								}, {
									text : 'Bogus Window ' + (++windowIndex),
									iconCls : 'bogus',
									handler : this.createWindow,
									scope : this,
									windowId : windowIndex
								}, {
									text : 'Bogus Window ' + (++windowIndex),
									iconCls : 'bogus',
									handler : this.createWindow,
									scope : this,
									windowId : windowIndex
								}, {
									text : 'Bogus Window ' + (++windowIndex),
									iconCls : 'bogus',
									handler : this.createWindow,
									scope : this,
									windowId : windowIndex
								}, {
									text : 'Bogus Window ' + (++windowIndex),
									iconCls : 'bogus',
									handler : this.createWindow,
									scope : this,
									windowId : windowIndex
								}]
					}
				}
			}
		});

MyDesktop.MyStatis = Ext.extend(MyDesktop.BogusModule, {
			init : function() {
				this.launcher = {
					text : 'ͳ�Ʊ�����',
					iconCls : 'bogus',
					handler : function() {
						return false;
					},
					menu : {
						items : [{
									text : '������֧ͼ�����',
									iconCls : 'bogus',
									handler : this.createMyWindow,
									scope : this,
									title : '������֧ͼ�����',
									url : '/jsp/report/baseReport.jsp',
									windowId : 'base-statis'
								}, {
									text : '��֧�Աȷ���',//�˵����������
									iconCls : 'bogus',
									handler : this.createMyWindow,
									title : '��֧�Աȷ���',//��������ı���
									url : '/jsp/report/contrastReport.jsp',
									scope : this,
									windowId : 'contrast-statis'
								}]
					}
				}
			}
		});
// Array data for the grid
Ext.grid.dummyData = [
		['3m Co', 71.72, 0.02, 0.03, '9/1 12:00am'],
		['Alcoa Inc', 29.01, 0.42, 1.47, '9/1 12:00am'],
		['American Express Company', 52.55, 0.01, 0.02, '9/1 12:00am'],
		['American International Group, Inc.', 64.13, 0.31, 0.49, '9/1 12:00am'],
		['AT&T Inc.', 31.61, -0.48, -1.54, '9/1 12:00am'],
		['Caterpillar Inc.', 67.27, 0.92, 1.39, '9/1 12:00am'],
		['Citigroup, Inc.', 49.37, 0.02, 0.04, '9/1 12:00am'],
		['Exxon Mobil Corp', 68.1, -0.43, -0.64, '9/1 12:00am'],
		['General Electric Company', 34.14, -0.08, -0.23, '9/1 12:00am'],
		['General Motors Corporation', 30.27, 1.09, 3.74, '9/1 12:00am'],
		['Hewlett-Packard Co.', 36.53, -0.03, -0.08, '9/1 12:00am'],
		['Honeywell Intl Inc', 38.77, 0.05, 0.13, '9/1 12:00am'],
		['Intel Corporation', 19.88, 0.31, 1.58, '9/1 12:00am'],
		['Johnson & Johnson', 64.72, 0.06, 0.09, '9/1 12:00am'],
		['Merck & Co., Inc.', 40.96, 0.41, 1.01, '9/1 12:00am'],
		['Microsoft Corporation', 25.84, 0.14, 0.54, '9/1 12:00am'],
		['The Coca-Cola Company', 45.07, 0.26, 0.58, '9/1 12:00am'],
		['The Procter & Gamble Company', 61.91, 0.01, 0.02, '9/1 12:00am'],
		['Wal-Mart Stores, Inc.', 45.45, 0.73, 1.63, '9/1 12:00am'],
		['Walt Disney Company (The) (Holding Company)', 29.89, 0.24, 0.81,
				'9/1 12:00am']];