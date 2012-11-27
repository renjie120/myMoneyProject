/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.Desktop = function(app){
	this.taskbar = new Ext.ux.TaskBar(app);
	var taskbar = this.taskbar;
	
	var desktopEl = Ext.get('x-desktop'); 
    var taskbarEl = Ext.get('ux-taskbar');
    //得到两个大的桌面图标所在的div层
    var shortcuts = Ext.get('x-shortcuts');

    var windows = new Ext.WindowGroup();
    var activeWindow;
		
    function minimizeWin(win){
        win.minimized = true;
        win.hide();
    }

    function markActive(win){
        if(activeWindow && activeWindow != win){
            markInactive(activeWindow);
        }
        taskbar.setActiveButton(win.taskButton);
        activeWindow = win;
        Ext.fly(win.taskButton.el).addClass('active-win');
        win.minimized = false;
    }

    function markInactive(win){
        if(win == activeWindow){
            activeWindow = null;
            Ext.fly(win.taskButton.el).removeClass('active-win');
        }
    }

    function removeWin(win){
    	taskbar.removeTaskButton(win.taskButton);
        layout();
    }

    /**
     * 设置高宽
     */
    function layout(){
        desktopEl.setHeight(Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight());
    }
    Ext.EventManager.onWindowResize(layout);

    this.layout = layout;

    //定义桌面DeskTop的创建窗体的方法.
    this.createWindow = function(config, cls){
    	var win = new (cls||Ext.Window)(
            Ext.applyIf(config||{}, {
                manager: windows,
                minimizable: true,
                maximizable: true
            })
        );
        win.render(desktopEl);
        win.taskButton = taskbar.addTaskButton(win);

        win.cmenu = new Ext.menu.Menu({
            items: [

            ]
        });

        win.animateTarget = win.taskButton.el;
        
        win.on({
        	'activate': {
        		fn: markActive
        	},
        	'beforeshow': {
        		fn: markActive
        	},
        	'deactivate': {
        		fn: markInactive
        	},
        	'minimize': {
        		fn: minimizeWin
        	},
        	'close': {
        		fn: removeWin
        	}
        });
        
        layout();
        return win;
    };

    this.getManager = function(){
        return windows;
    };

    this.getWindow = function(id){
        return windows.get(id);
    }
    
    this.getWinWidth = function(){
		var width = Ext.lib.Dom.getViewWidth();
		return width < 200 ? 200 : width;
	}
		
	this.getWinHeight = function(){
		var height = (Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight());
		return height < 100 ? 100 : height;
	}
		
	this.getWinX = function(width){
		return (Ext.lib.Dom.getViewWidth() - width) / 2
	}
		
	this.getWinY = function(height){
		return (Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight() - height) / 2;
	}

    layout();

    if(shortcuts){
    	//下面创建的是桌面上面的大图标的点击事件。可以看到是根据这里的id里面的去除-shortcut之后找的窗体对象！
        shortcuts.on('click', function(e, t){
            if(t = e.getTarget('dt', shortcuts)){
                e.stopEvent();
                var module = app.getModule(t.id.replace('-shortcut', ''));
                if(module){
                    module.createWindow();
                }
            }
        });
    }
};
