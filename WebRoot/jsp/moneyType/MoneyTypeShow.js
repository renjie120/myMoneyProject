//��ʼ������
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
		
		//ɾ���ɵ����ͬʱ�޸���֧��Ϣ�е�����
		function del(){
			var newType = $('#moneySort').val();
			if($('#secondSortSelect').val()!=''){
				newType = $('#secondSortSelect').val();
			}
			dwrBo.deleteTally($('#typeSno').val(),'',function(data){
				$('#msg2').show().text("�ɹ�ɾ��"+data+"����Ϣ��").css('fontSize','11px').fadeOut(1000);
				init();
			});
		}
		
		//ɾ�����֮ǰ��ѯ�ж���С����Ѿ�����֧��Ϣ���á�
		function beforeDel(){
			dwrBo.beforeDeleteTally($('#typeCode').val(),function(data){
				if(data>0){
					$('#del').hide();
					 $('tr[name=updateType]:eq(0)').show();
					 $("#delMsg").css('fontSize','11px').html("��"+data+"����֧��Ϣ���ø����<br>�Ƿ��޸�Ϊ�������ͣ�");
				}else{
					if(confirm('�Ƿ�ȷ��ɾ����')){
						del();
					}
				}
			});
		}
		
		//�Ƿ�ɾ����Ӧ����֧��Ϣ
		function checkDelDetails(){
			if($('input[name=deleteDetail]')[0].checked){
				$('tr[name=updateType]').show();
			}else{
				$('tr[name=updateType]:gt(0)').hide();
			}
		}
		
		//ȷ��ɾ����ť
		function makesureDel(){
			var newType = $('#valueofsort').val();
 			if($('input[type=checkbox][name=deleteDetail]').attr('checked')==true){
				if(newType=='-1'){
					alert('��ѡ���µ����');
				}else{
					dwrBo.deleteTally($('#typeSno').val(),newType,function(data){
						$('#msg2').show().css('fontSize','11px').text("�ɹ�ɾ��"+data+"����Ϣ��").fadeOut(1000);
						init();
					});
				}				
			}else{
				if(confirm('�Ƿ�ȷ��ɾ��(ͬʱҲɾ����Ӧ����֧��Ϣ)��')){
					del();
				}
			}
		}
		
		//�����Ӱ�ť�����ֱ��水ť
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
				msg.show().text('����д������Ϣ��').css('fontSize','11px').fadeOut(1000);
				desc[0].focus();
				return ;
			}
			if(type.val()=='-1'){
				msg.show().text('��ѡ����֧���ͣ�').css('fontSize','11px').fadeOut(1000);
				type[0].focus();
				return ;
			}
			dwrBo.saveTallyType(parent.val(),desc.val(),type.val(),function(data){
				msg.show().css('fontSize','11px').text("�ɹ�ɾ��"+data+"����Ϣ��").fadeOut(1000);
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
		//�޸��������
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
		//�޸��ϼ����
		function updateParent(){
			//�����������ˮ��
			if($('#typeSno').val()!=''){
				dwrBo.updateTallyTypeParentCode($('#typeSno').val(),$('#parentType').val(),function(data){
					$('#msg2').css('fontSize','11px').show().text(data).fadeOut(1000);
					init();	
				});
			}
		}
		//һ������������
		function getSecond(){
		 	var v = $('#moneySort').val(); 
		 	var ms = $('#secondSortSelect');
		 	ms.empty().append('<option value="-1">��ѡ��</option>');
			$('#allmoneySort option[type]').each(function(){
				if(this.getAttribute('parent')==v)
					ms.append("<option value='"+this.value+"'>"+this.text+"</option>");
			});
		 }