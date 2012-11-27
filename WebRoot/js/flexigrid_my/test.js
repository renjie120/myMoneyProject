$(function() {
	    $("#flex").flexigrid({
		        url : 'all.action',
		        dataType : 'json',
		        colModel : [{
			            display : 'ID',
			            name : 'id',
			            width : 50,// 得加上 要不IE报错
			            sortable : true,
			            align : 'center'
		            }, {
			            display : '商品名称',
			            name : 'name',
			            width : 100,
			            sortable : true,
			            align : 'center'
		            }, {
			            display : '标准',
			            name : 'stand',
			            width : 100,
			            sortable : true,
			            align : 'center'
		            }, {
			            display : '单价',
			            name : 'money',
			            width : 100,
			            sortable : true,
			            align : 'center'
		            }, {
			            display : '库存',
			            name : 'leavings',
			            width : 100,
			            sortable : true,
			            align : 'center'
		            }, {
			            display : '已经订购',
			            name : 'orders',
			            width : 100,
			            sortable : true,
			            align : 'center'
		            }],
		        buttons : [{
			            name : '添加',
			            bclass : 'add',
			            onpress : action
		            }, {
			            // 设置分割线
			            separator : true
		            }, {
			            name : '删除',
			            bclass : 'delete',
			            onpress : action
		            }, {
			            separator : true
		            }, {
			            name : '修改',
			            bclass : 'edit',
			            onpress : action
		            }, {
			            separator : true
		            }],
//		        searchitems : [{
//			            display : 'ID',
//			            name : 'id',
//			            isdefault : true
//		            }, {
//			            display : '库存',
//			            name : 'leavings'
//		            }],
		        sortname : "id",
		        sortorder : "asc",
		        usepager : true,
		        title : '商品信息',
		        useRp : true,
		        checkbox : true,// 是否要多选框
		        rowId : 'id',// 多选框绑定行的id
		        rp : 10,
		        showTableToggleBtn : true,
		        width : 700,
		        height : 263
	        });
	    var actions="";
	    function action(com, grid) {
		    switch (com) {
			    case '添加' :
				    $("#savegoods input[type='text']").each(function() {
					        $(this).val("");
				        });
				     $('#savegoods input[name="id"]').removeAttr("readonly");
				     $('#savegoods').attr("action","add.action");
				    actions="add.action";
				    $("#goods").jqmShow();
				    break;
			    case '修改' :
				    selected_count = $('.trSelected', grid).length;
				    if (selected_count == 0) {
					    alert('请选择一条记录!');
					    return;
				    }
				    if (selected_count > 1) {
					    alert('抱歉只能同时修改一条记录!');
					    return;
				    }
				    data = new Array();
				    $('.trSelected td', grid).each(function(i) {
					        data[i] = $(this).children('div').text();
				        });
				    $('#savegoods input[name="id"]').val(data[0]).attr("readonly","readonly");
				    $('#savegoods input[name="name"]').val(data[1]);
				    $('#savegoods input[name="stand"]').val(data[2]);
				    $('#savegoods input[name="money"]').val(data[3]);
				    $('#savegoods input[name="leavings"]').val(data[4]);
				    $('#savegoods input[name="orders"]').val(data[5]);
					
				   actions="modify.action";

				    $("#goods").jqmShow();
				    break;
			    case '删除' :
				    selected_count = $('.trSelected', grid).length;
				    if (selected_count == 0) {
					    alert('请选择一条记录!');
					    return;
				    }
				    names = '';
				    $('.trSelected td:nth-child(3) div', grid).each(function(i) {
					        if (i)
						        names += ',';
					        names += $(this).text();
				        });
				    ids = '';
				    $('.trSelected td:nth-child(2) div', grid).each(function(i) {
					        if (i)
						        ids += ',';
					        ids += $(this).text();
				        })
				    if (confirm("确定删除商品[" + names + "]?")) {
					    delUser(ids);
				    }
				    break;
		    }
	    }
	    $("#goods").jqm({
		    // trigger : 'a.showDialog',// 触发
		    // ajax: '@href',//ajax读取方式
		    // ajaxText:'',//提示语言
		    modal : true,// 限制输入（鼠标点击，按键）的对话
		    overlay : 60 // 遮罩程度%
		      // target : t,// 提示
		      // onHide : function(h) {
		      // // // t.html('Please Wait...'); // Clear Content HTML on Hide.
		      // h.o.remove(); // remove overlay
		      // // h.w.fadeOut(888); // hide window
		      // }
	      }).jqmAddClose('.close')// 添加触发关闭的selector
	      .jqDrag('.drag');// 添加拖拽的selector
		
	    function delUser(ids) {
		    $.ajax({
			        url : 'delete.action',
			        data : {
				        ids : ids
			        },
			        type : 'POST',
			        dataType : 'json',
			        success : function() {
				        $('#flex').flexReload();//表格重载
			        }
		        });
	    }
	    $("#submit").click(function(){
	    	 $.ajax({
			        url : actions,
			        data : $("#savegoods").serialize(),
			        type : 'POST',
			        dataType : 'json',
			        success : function(data) {
				    	$("#goods").jqmHide();
				        $('#flex').flexReload();
			        }
		        });
	    })
    });