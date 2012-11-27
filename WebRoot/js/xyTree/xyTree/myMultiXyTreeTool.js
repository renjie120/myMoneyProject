//分析数组并将结果组装成树所需要的js对象添加到contentRoot中。	
	function colseArray(arrayData,contentRoot){
		var parents = [];
		var parentToChildMap = new HashMap();
		var nodeMap = new HashMap();
		var firstLevelNodes = [];
		var firstLevelParentIds = [];
		forEach(arrayData,function(i,n){
			var _id = n["typeCode"];
			var _parent = n["parentCode"];
			if (findInArray(_parent,parents) == -1)
				parents.push( _parent);
			if (parentToChildMap.containsKey( _parent)) {
				var arr = parentToChildMap.get( _parent);
				arr.push(_id);
				parentToChildMap.put( _parent, arr);
			} else {
				var arr = [];
				arr.push(_id);
				parentToChildMap.put(_parent, arr);
			}
			nodeMap.put(_id,n);
		});
		
		forEach(parents,function(ii,n){
			if(!nodeMap.get(n)){
				firstLevelParentIds.push(n);
			}
		});
		parents = removeArrayFromOtherArray(parents, firstLevelParentIds);
		
		forEach(firstLevelParentIds,function(ii,n){
			firstLevelNodes = firstLevelNodes.concat(parentToChildMap.get(n));
		});
		
		//从第一层节点逐个添加!
		forEach(firstLevelNodes,function(ii,n){
			var vNode = nodeMap.get(n);
			var node1 = new xyTree.Node(vNode['tallyTypeDesc']);
			$.extend(node1,vNode);
			contentRoot.add(node1);
			addChild(node1);
		});
		
		//递归添加子节点.
		function addChild(nd){
			if(parentToChildMap.get(nd['typeCode'])!=null){
				forEach(parentToChildMap.get(nd['typeCode']),function(iii,childId){
					var ccnd = nodeMap.get(childId);
					var cld = new xyTree.Node(ccnd['tallyTypeDesc']);
					cld = $.extend(cld,ccnd);
					nd.add(cld);
					
					if(parentToChildMap.get(ccnd['typeCode'])!=null){
						addChild(cld);
					}								
				});
			}
		}
	}	
	
	//对数组的每个元素执行方法fn
	function forEach(arr,fn){
		if(arr){
			for(var i=0,j=arr.length;i<j;i++)
				fn.call(arr[i],i,arr[i]);
		}
	}
	
	function findInArray(obj,arr) {
		var ans = -1;
		var len = arr.length;
		for (var i = 0; i < len; i++) {		
			if(arr[i]==obj){
				ans = i;
				return ans;
			}
		}
		return -1;
	}
	
	function removeArrayFromOtherArray(arr1, arr2) {
		var tempArr = [];
		var bingo = [];
		forEach(arr2,function(ii){
			bingo.push(findInArray(arr2[ii],arr1));
		})
		forEach(arr1,function(ii){
			if (findInArray(ii,bingo) == -1) {
				tempArr.push(arr1[ii]);
			}
		})
		return tempArr;
	}