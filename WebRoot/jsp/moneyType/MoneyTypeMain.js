function init(){
	$('#mid').width('1px');
}
function downToResize(obj,e,lf,rf,bx){
	obj.style.cursor='col-resize';
	var e = window.event;
	//��¼��ʼ׼���ƶ�����ʼλ��
	obj.mouseDownX=e.clientX; 
	//������߿�ܵĿ��
	obj.parentLeftFW = document.getElementById(lf).parentNode.clientWidth; 
	//�����ұ߿�ܵĿ��
	obj.parentRightFW = document.getElementById(rf).parentNode.clientWidth;
	//obj.parentBox =  document.getElementById(bx);	
	obj.setCapture(); 	
	if(!obj.createBox){
		var div = document.createElement("div");
		div.id = 'box'+lf+rf;
		div.style.display = 'none';
		div.style.position = 'absolute';
		div.style.border = '1px dotted red';
		div.style.zIndex = '5';
		div.style.width = '3px';
		div.style.height = '100%';
		document.body.appendChild(div);
		obj.parentBox  = document.getElementById(div.id);
		obj.createBox = 1;	
	}
}
function moveToResize(obj,e){
	var e = window.event;
	if(!obj.mouseDownX) return false; 	
	obj.removed = 1;
	obj.parentBox.style.display = 'inline';	
	obj.parentBox.style.width = obj.offsetWidth;
	obj.parentBox.style.height = obj.offsetHeight;
	obj.parentBox.style.left = e.clientX;
	obj.parentBox.style.top = getPosTop(obj);
}
function getPosLeft(elm) {
        var left = elm.offsetLeft;
        while((elm = elm.offsetParent) != null)        {
                left += elm.offsetLeft;
        }
        return left;
}
function getPosTop(elm) {
        var top = elm.offsetTop;
        while((elm = elm.offsetParent) != null)        {
                top += elm.offsetTop;
        }
        return top;
}
function upToResize(obj,e,lf,rf){	
	var e = window.event ;
	//������ʵ�ʵ��ƶ��߿�Ĵ�С
	var changeW = e.clientX*1-obj.mouseDownX;
	if(changeW!=0&&obj.removed){	
		var newLeftW=obj.parentLeftFW*1+changeW; 
		var newRightW=obj.parentRightFW*1-changeW; 
		if(newLeftW<=300) {
			var temp = newLeftW;
			newLeftW = 300;
			newRightW =newRightW-(300-temp);
		}
		if(newRightW<=300) {
			var temp = newRightW;
			newRightW = 300;
			newLeftW = newLeftW-(300-temp);
		}
		var leftObj = document.getElementById(lf).parentNode;
		leftObj.width = newLeftW;
		leftObj.firstChild.style.width = newLeftW+'px';
		var rightObj = document.getElementById(rf).parentNode;
		//�����֮����Ҫ����һ��4,��������Աߵ�һ��СС��λ��..
		rightObj.width = newRightW-4;
		rightObj.firstChild.style.width = newRightW-4;
		//rightObj.width = '100%';
		//rightObj.firstChild.style.width = '100%';
	}
	obj.releaseCapture(); 	
	obj.mouseDownX=0; 
	obj.removed = 0;
	obj.style.cursor = 'default';
	obj.parentBox.style.display = 'none';
}