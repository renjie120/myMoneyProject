//初始化界面
		$(function(){
			if($('#typeSno').val()!=''){
				$('#msg').hide();
				$('#ad').show();
				$('#sv').hide();
				$('#del').show();
			}
			var w = $('#tallyTypeDesc').width();
			$('#parentType').width(w);
			$('#moneyType').width(w);
			$("#parentType option[value="+$('#parentCodValue').val()+"]").attr('selected',true);
			$("#moneyType option[value="+$('#moneyTypeValue').val()+"]").attr('selected',true);
		});
		
		//删除旧的类别同时修改收支信息中的类别号
		function del(){
			var newType = $('#moneySort').val();
			if($('#secondSortSelect').val()!=''){
				newType = $('#secondSortSelect').val();
			}
			dwrBo.deleteTally($('#typeSno').val(),'',function(data){
				$('#msg2').show().text("成功删除"+data+"条信息！").css('fontSize','11px').fadeOut(1000);
				init();
			});
		}
		
		//删除类别之前查询有多少小类别已经被收支信息所用。
		function beforeDel(){
			dwrBo.beforeDeleteTally($('#typeCode').val(),function(data){
				if(data>0){
					$('#del').hide();
					 $('tr[name=updateType]:eq(0)').show();
					 $("#delMsg").css('fontSize','11px').html("有"+data+"条收支信息采用该类别<br>是否修改为其他类型？");
				}else{
					if(confirm('是否确定删除？')){
						del();
					}
				}
			});
		}
		
		//是否删除对应的收支信息
		function checkDelDetails(){
			if($('input[name=deleteDetail]')[0].checked){
				$('tr[name=updateType]').show();
			}else{
				$('tr[name=updateType]:gt(0)').hide();
			}
		}
		
		//确定删除按钮
		function makesureDel(){
			var newType = $('#valueofsort').val();
 			if($('input[type=checkbox][name=deleteDetail]').attr('checked')==true){
				if(newType=='-1'){
					alert('请选择新的类别！');
				}else{
					dwrBo.deleteTally($('#typeSno').val(),newType,function(data){
						$('#msg2').show().css('fontSize','11px').text("成功删除"+data+"条信息！").fadeOut(1000);
						init();
					});
				}				
			}else{
				if(confirm('是否确定删除(同时也删除对应的收支信息)？')){
					del();
				}
			}
		}
		
		//点击添加按钮，出现保存按钮
		function add(){
			init();
			$('input').val('');
			$('select').val('-1');
			$('#ad').hide();
			$('#del').hide();
			$('#sv').show();
		}
		function save(){
			var desc = $('#tallyTypeDesc');
			var msg = $('#msg2');
			var type = $('#moneyType');
			var parent = $('#parentType');
			if(desc.val()==''){
				msg.show().text('请填写描述信息！').css('fontSize','11px').fadeOut(1000);
				desc[0].focus();
				return ;
			}
			if(type.val()=='-1'){
				msg.show().text('请选择收支类型！').css('fontSize','11px').fadeOut(1000);
				type[0].focus();
				return ;
			}
			dwrBo.saveTallyType(parent.val(),desc.val(),type.val(),function(data){
				msg.show().css('fontSize','11px').text("成功删除"+data+"条信息！").fadeOut(1000);
				init();
			});
		}
		function init(){
			$('#msg').hide();
			$('#ad').show();
			$('#sv').hide();
			$('#del').hide();
			$('input').val('');
			$('select').val('-1');
			$('tr[name=updateType]').hide();
		}
		//修改类别描述
		function updateDesc(){
			var newType = $('#tallyTypeDesc').val();
			var oldType = $('#oldDesc').val();
			if(newType&&oldType&&oldType!=newType){
				dwrBo.updateTallyTypeName($('#typeSno').val(),$('#tallyTypeDesc').val(),function(data){
					$('#msg2').show().css('fontSize','11px').text(data).fadeOut(1000);	
					init();				
				});
			}
		}
		 function refresh(){
			 parent.frames[0].location.reload();
			 init();
		 }
		//修改上级类别
		function updateParent(){
			//如果有类型流水号
			if($('#typeSno').val()!=''){
				dwrBo.updateTallyTypeParentCode($('#typeSno').val(),$('#parentType').val(),function(data){
					$('#msg2').css('fontSize','11px').show().text(data).fadeOut(1000);
					init();	
				});
			}
		}
		//一二级类别的联动
		function getSecond(){
		 	var v = $('#moneySort').val(); 
		 	var ms = $('#secondSortSelect');
		 	ms.empty().append('<option value="-1">请选择</option>');
			$('#allmoneySort option[type]').each(function(){
				if(this.getAttribute('parent')==v)
					ms.append("<option value='"+this.value+"'>"+this.text+"</option>");
			});
		 }