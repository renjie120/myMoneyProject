
/**
 * 
 * ��дwindow.alert/ window.confirm mender:Du DongHui version:1.0 source:network
 * author:dimness
 */

var alternateFrame = null;// ���ɵ�iframe
var alternateWin = null;

//�����ie�ͽ����滻��
if(jQuery.browser.msie){
window.alert = showAlert;
window.confirm = showConfirm;
}

/**
 * �˻��������ڣ������Դ���
 */
function alternateWindow() {
	this.win = null;// ���ɶԻ���Ĵ��ڶ���
	this.pBody = null;// ���ɵ�body��������
	this.pBg = null;
	this.type = "alert";// Ĭ�ϵ�������alert
	this.FocusWhere = "_OK";// �������ĸ���ť��
}
/**
 * ģ�µ�alert����
 */
function showAlert(info) {
	// alert(typeof(info));
	var _info = info;
	if (typeof(info) == 'undefined') {
		// alert(222);
		_info = '';
	}
	if (!info) {
		_info = '';
	}
	alternateWin = new alternateWindow();
	var pBody = alternateWin.init();
	alternateWin.initAlertBody(pBody, _info);
	alternateWin.type = "alert";
}
/**
 * ģ�µ�confirm����
 */
function showConfirm(info, ok_func, notok_func, ok_str, not_okstr) {
	var _info = info;
	if (typeof(info) == 'undefined') {
		// alert(222);
		_info = '';
	}
	if (!info) {
		_info = '';
	}

	alternateWin = new alternateWindow();
	var pBody = alternateWin.init();
	alternateWin.initConfirmBody(pBody, info, ok_func, notok_func, ok_str,
			not_okstr);
	alternateWin.type = "confirm";
}
/**
 * ���ã���ʼ������Ϣ
 */
alternateWindow.prototype.init = function() {
	if (alternateFrame == null) {
		alternateFrame = document
				.createElement("<iframe allowTransparency='true' id='popframe' frameborder=0 marginheight=0 src='about:blank' marginwidth=0 hspace=0 vspace=0 scrolling=no></iframe>")
		alternateFrame.style.position = "absolute";
		document.body.appendChild(alternateFrame);
	} else {
		alternateFrame.style.visibility = "visible";
	}
	alternateFrame.style.width = screen.availWidth;
	alternateFrame.style.height = document.body.scrollHeight;
	// screen.availHeight;

	alternateFrame.style.left = document.body.scrollLeft;
	alternateFrame.style.top = document.body.scrollTop;
	alternateFrame.name = alternateFrame.uniqueID;

	this.win = window.frames[alternateFrame.name];
	this.win.document
			.write("<body leftmargin=0 topmargin=0 oncontextmenu='self.event.returnValue=false'><div id=popbg></div><div id=popbody></div><div></div></body>");
	this.win.document.body.style.backgroundColor = "transparent";
	document.body.style.overflow = "hidden";
	this.pBody = this.win.document.body.children[1];
	this.pBg = this.win.document.body.children[0];
	this.hideAllSelect();
	this.hideAllUL();
	this.initBg();

	return this.pBody;
}

/**
 * ���ã���ʼ��������
 */
alternateWindow.prototype.initBg = function() {
	with (this.pBg.style) {
		position = "absolute";
		left = "0";
		top = "0";
		width = "100%";
		height = "100%";
		visibility = "hidden";
		backgroundColor = "#333333";
		filter = "blendTrans(duration=1) alpha(opacity=30)";
	}
	this.pBg.filters.blendTrans.apply();
	this.pBg.style.visibility = "visible";
	this.pBg.filters.blendTrans.play();
}
/**
 * ���ã���ʼ����ʾ��
 */
alternateWindow.prototype.initAlertBody = function(obj, info) {
	with (obj.style) {
		position = "absolute";
		width = "400";
		height = "110";
		backgroundColor = "#999999";
	}
	obj.style.left = window.document.body.clientWidth / 2 - 200;
	obj.style.top = window.document.body.clientHeight / 3;
	var str;
	if (info == "") {
		info = "�Բ��𣬴˶���δ���壡����";
	}
	str = "<table border=0 cellpadding=0 cellspacing=0 width=100% height=100%><tr height=20>";
	str += "<td align=left style='border-left:1px solid #999;border-right:1px solid #999;border-top:1px solid #999;color:#ffffff;font-size:12px;font-weight:bold;background:#cccbcb url(../_themes/default/images/window/ow_title_bg.jpg) no-repeat;'>��ܰ��ʾ</td></tr>";
	str += "<tr><td align=center bgcolor=#ffffcc style='border-left:1px solid #999;border-right:1px solid #999;font-size:12px;color:#000000;vertical-align: middle;'>";
	str += info
			+ "</td></tr><tr height=30 bgcolor=#ffffcc><td align=center style='border-left:1px solid #999;border-right:1px solid #999;border-bottom:1px solid #999;'>"
			+ "<input style='' type='button' value=' ȷ�� ' id='_OK'"
			+ " onkeydown='parent.alternateWin.onKeyDown(event,this)'"
			+ " onclick='parent.alternateWin.closeWin()' style='border:solid 1px #cccccc;background:#ffffcc'>"
			+ "</td></tr></table>";
	obj.innerHTML = str;
	this.win.document.body.all._OK.focus();
	this.FocusWhere = "_OK";
}

alternateWindow.prototype.onKeyDown = function(event, obj) {
	// alert(event.keyCode);
	switch (event.keyCode) {
		case 9 :
			event.keyCode = -1;
			if (this.type == "confirm") {
				if (this.FocusWhere == "_OK") {
					this.win.document.body.all.NO.focus();
					this.FocusWhere = "NO";
				} else {
					this.win.document.body.all._OK.focus();
					this.FocusWhere = "_OK";
				}
			}
			break;
		case 13 :
			obj.click();
			break;
		case 27 :
			this.closeWin();
			break;
	}

}
/**
 * ���ã���ʼ����ʾ�� conFirm��ʾ��
 */
alternateWindow.prototype.initConfirmBody = function(obj, info, ok_func,
		notok_func, ok_str, notok_str) {
	with (obj.style) {
		position = "absolute";
		width = "400";
		height = "150";
		backgroundColor = "#ffffff";
	}
	if (ok_str == null) {
		ok_str = " ȷ�� ";
	}
	if (notok_str == null) {
		notok_str = " ȡ�� "
	}
	// default ok_func
	if (!ok_func) {
		ok_func = "_ok";
	}
	if (!notok_func) {
		notok_func = "_notok";
	}
	// default notok_func
	obj.style.left = window.document.body.clientWidth / 2 - 200;
	obj.style.top = window.document.body.clientHeight / 3;
	var str;
	str = "<table border=0 cellpadding=0 cellspacing=0 width=100% height=100%><tr height=20>";
	str += "<td align=left style='border-left:1px solid #999;border-right:1px solid #999;border-top:1px solid #999;color:#ffffff;font-size:12px;font-weight:bold;background:#cccbcb url(../_themes/default/images/window/ow_title_bg.jpg) no-repeat;'>��ܰ��ʾ</td></tr>";
	str += "<tr><td align=center bgcolor=#ffffcc style='border-left:1px solid #999;border-right:1px solid #999;font-size:12px;color:#000000;vertical-align: middle;'>";
	str += info
			+ "</td></tr><tr height=30 bgcolor=#ffffcc ><td align=center style='border-left:1px solid #999;border-right:1px solid #999;border-bottom:1px solid #999;'>"
			+ "<input type='button' id='_OK'"
			+ " onkeydown='parent.alternateWin.onKeyDown(event,this)'"
			+ " onclick='parent.alternateWin.closeWin();parent."
			+ ok_func
			+ "();' "
			+ " value='"
			+ ok_str
			+ "' style='border:solid 1px #cccccc;background:#ffffcc'>"
			+ "&nbsp;&nbsp;&nbsp;<input type='button' value='"
			+ notok_str
			+ "' id='NO'"
			+ " onkeydown='parent.alternateWin.onKeyDown(event,this)'"
			+ " onclick='parent.alternateWin.closeWin();"
			+ " parent."
			+ notok_func
			+ "();' style='border:solid 1px #cccccc;background:#ffffcc'></td></tr></table>";
	obj.innerHTML = str;
	this.win.document.body.all._OK.focus();
}

function _ok() {
	alert('ok!');
}

function _notok() {
	alert('not ok!');
}

/**
 * ���ã��ر�һ��
 */
alternateWindow.prototype.closeWin = function() {
	alternateFrame.style.visibility = "hidden";
	this.showAllSelect();
	this.showAllUL();
	document.body.style.overflow = "auto";

}

/**
 * ����:�������е�select
 */
alternateWindow.prototype.hideAllSelect = function() {
	var obj;
	obj = document.getElementsByTagName("SELECT");
	var i;
	for (i = 0; i < obj.length; i++)
		obj[i].style.visibility = "hidden";
}
/**
 * ��ʾ���е�select
 */
alternateWindow.prototype.showAllSelect = function() {
	var obj;
	obj = document.getElementsByTagName("SELECT");
	var i;
	for (i = 0; i < obj.length; i++)
		obj[i].style.visibility = "visible";
}

/**
 * ����:�������е�UL
 */
alternateWindow.prototype.hideAllUL = function() {
	var obj;
	obj = document.getElementsByTagName("UL");
	var i;
	for (i = 0; i < obj.length; i++)
		obj[i].style.visibility = "hidden";
}
/**
 * ��ʾ���е�UL
 */
alternateWindow.prototype.showAllUL = function() {
	var obj;
	obj = document.getElementsByTagName("UL");
	var i;
	for (i = 0; i < obj.length; i++)
		obj[i].style.visibility = "visible";
}