Function.prototype.binding = function() {
    if (arguments.length < 2 && typeof arguments[0] == "undefined") return this;
    var __method = this, args = jQuery.makeArray(arguments), object = args.shift();
    return function() {
        return __method.apply(object, args.concat(jQuery.makeArray(arguments)));
    }
}

var Class = function(subclass){
	subclass.setOptions = function(options){
		this.options = jQuery.extend({}, this.options,options);
		for(var key in options){
			if(/^on[A-Z][A-Za-z]*$/.test(key)){
				$(this).bind(key,options[key]);
			}
		}
	}
    var fn =  function(){
        if(subclass._init && typeof subclass._init == 'function'){
            this._init.apply(this,arguments);
        }
    }
    if(typeof subclass == 'object'){
        fn.prototype = subclass;
    }
    return fn;
}

var PopupLayer = new Class({
	options:{
		trigger:null,                            //������Ԫ�ػ�id,�������
		popupBlk:null,                           //�������ݲ�Ԫ�ػ�id,�������
		closeBtn:null,                           //�رյ������Ԫ�ػ�id
		popupLayerClass:"popupLayer",            //������������class����
		eventType:"click",                       //�����¼�������
		offsets:{                                //����������λ�õĵ���ֵ
			x:0,
			y:0
		},
		useFx:false,                             //�Ƿ�ʹ����Ч
		useOverlay:false,                        //�Ƿ�ʹ��ȫ������
		usePopupIframe:true,                     //�Ƿ�ʹ����������
		isresize:true,                           //�Ƿ��window�����resize�¼�
		onBeforeStart:function(){}            //�Զ����¼�
	},
	_init:function(options){
		this.setOptions(options);                //��������
		this.isSetPosition = this.isDoPopup = this.isOverlay = true;    //����һЩ����
		this.popupLayer = $(document.createElement("div")).addClass(this.options.popupLayerClass);     //��ʼ�����������
		this.popupIframe = $(document.createElement("iframe")).attr({border:0,frameborder:0});         //��������,��������ie6�µ�select
		this.trigger = $(this.options.trigger);                         //�Ѵ���Ԫ�ط�װ��ʵ����һ�����ԣ�����ʹ�ü����
		this.popupBlk = $(this.options.popupBlk);                       //�ѵ������ݲ�Ԫ�ط�װ��ʵ����һ������
		this.closeBtn = $(this.options.closeBtn);                       //�ѹرհ�ť�ط�װ��ʵ����һ������
		$(this).trigger("onBeforeStart");                               //ִ���Զ����¼���
		this._construct()                                               //ͨ���������ݲ㣬���쵯���㣬��Ϊ���������������ײ�iframe
		this.trigger.bind(this.options.eventType,function(){            //������Ԫ�ذ󶨴����¼�
			if(this.isSetPosition){
				this.setPosition(this.trigger.offset().left + this.options.offsets.x, this.trigger.offset().top + this.trigger.get(0).offsetHeight + this.options.offsets.y);
			}
			this.options.useOverlay?this._loadOverlay():null;               //���ʹ���������������Ԫ��
			(this.isOverlay && this.options.useOverlay)?this.overlay.show():null;
			if(this.isDoPopup && (this.popupLayer.css("display")== "none")){
				this.options.useFx?this.doEffects("open"):this.popupLayer.show();
			}							 
		}.binding(this));
		this.isresize?$(window).bind("resize",this.doresize.binding(this)):null;
		this.options.closeBtn?this.closeBtn.bind("click",this.close.binding(this)):null;   //����йرհ�ť������رհ�ť�󶨹ر��¼�
	},
	_construct:function(){                  //���쵯����
		this.popupBlk.show();
		this.popupLayer.append(this.popupBlk.css({opacity:1})).appendTo($(document.body)).css({position:"absolute",'z-index':2,width:this.popupBlk.get(0).offsetWidth,height:this.popupBlk.get(0).offsetHeight});
		this.options.usePopupIframe?this.popupLayer.append(this.popupIframe):null;
		this.recalculatePopupIframe();
		this.popupLayer.hide();
	},
	_loadOverlay:function(){                //��������
		pageWidth = ($.browser.version=="6.0")?$(document).width()-21:$(document).width();
		this.overlay?this.overlay.remove():null;
		this.overlay = $(document.createElement("div"));
		this.overlay.css({position:"absolute","z-index":1,left:0,top:0,zoom:1,display:"none",width:pageWidth,height:$(document).height()}).appendTo($(document.body)).append("<div style='position:absolute;z-index:2;width:100%;height:100%;left:0;top:0;opacity:0.3;filter:Alpha(opacity=30);background:#000'></div><iframe frameborder='0' border='0' style='width:100%;height:100%;position:absolute;z-index:1;left:0;top:0;filter:Alpha(opacity=0);'></iframe>")
	},
	doresize:function(){
		this.overlay?this.overlay.css({width:($.browser.version=="6.0")?$(document).width()-21:$(document).width(),height:($.browser.version=="6.0")?$(document).height()-4:$(document).height()}):null;
		if(this.isSetPosition){
			this.setPosition(this.trigger.offset().left + this.options.offsets.x, this.trigger.offset().top + this.trigger.get(0).offsetHeight + this.options.offsets.y);
		}
	},
	setPosition:function(left,top){          //ͨ������Ĳ���ֵ�ı䵯�����λ��
		this.popupLayer.css({left:left,top:top});
	},
	doEffects:function(way){                //����Ч
		way == "open"?this.popupLayer.show("slow"):this.popupLayer.hide("slow");
		
	},
	recalculatePopupIframe:function(){     //�ػ�popupIframe,�����֪��ô�Ľ���ֻ������������취��
		this.popupIframe.css({position:"absolute",'z-index':-1,left:0,top:0,opacity:0,width:this.popupBlk.get(0).offsetWidth,height:this.popupBlk.get(0).offsetHeight});
	},
	close:function(){                      //�رշ���
		this.options.useOverlay?this.overlay.hide():null;
		this.options.useFx?this.doEffects("close"):this.popupLayer.hide();
	}
});