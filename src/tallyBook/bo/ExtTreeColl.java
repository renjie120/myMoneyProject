package tallyBook.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import myOwnLibrary.util.JsonUtil;

public class ExtTreeColl extends ObjectCollection {
	private Map parentMap = new HashMap();

	/**
	 * <p>添加一个Ext树的节点</p>
	 * <p>Remark: </p>
	 * @param vo
	 */
	public void addExtTreeVO(ExtTreeNode vo) {
		addElement(vo);
		addElement(vo.getId(), vo);
		addParentViewElement(vo.getParent(), vo);
	}

	/**
	 * <p>返回一个父节点与孩子们的map映射对象</p>
	 * <p>Remark: </p>
	 * @return
	 */
	public Map getParentMap() {
		return parentMap;
	}

	/** 
	 * 添加一个关于父亲节点视角看的map
	 * key:字符串，表示父亲字符串
	 * value:arrayList类型，存放key对应的儿子节点组成的一个list
	 */
	private void addParentViewElement(Object key, Object element) {
		List list = new ArrayList();
		if (!parentMap.containsKey(key)) {
			list.add(element);
			parentMap.put(key, list);
		} else {
			list = (ArrayList) parentMap.get(key);
			list.add(element);
			parentMap.put(key, list);
		}
	}

	/**
	 * <p>和addExtTreeVO一样的实现函数，为了不改变之前的设计</p>
	 * <p>Remark: </p>
	 * @param vo
	 */
	public void addMenu(ExtTreeNode vo) {
		addElement(vo);
		addElement(vo.getId(), vo);
	}

	/**
	 * 根据行号获取Ext树中的指定节点
	 * @param index
	 */
	public ExtTreeNode getExtTreeVO(int index) {
		return (ExtTreeNode) getElement(index);
	}

	/**
	 * <p>下面根据树节点的id得到节点，id是字符串类型！</p>
	 * <p>Remark: </p>
	 * @param menuId
	 * @return
	 */
	public ExtTreeNode getMenu(String menuId) {
		//return (MenuVO)getElement(menuId);
		for (int i = 0; i < getRowCount(); i++) {
			//下面得到某一行的节点
			ExtTreeNode vo = getExtTreeVO(i);
			String menuIdTmp = vo.getId();
			if (menuIdTmp.equals(menuId))
				return vo;
		}
		return null;
	}

	/**
	 * <p>通过ExtTree树节点的parent属性设置cls样式以及Leaf属性</p>
	 * <p>Remark: </p>
	 * @return
	 */
	public boolean setLeaves() {
		List list = this.getList();
		//将所有的父节点的id得到放在一个set中，这样去除重复的id值
		Set parentIdSet = new HashSet();
		Map map = this.getMap();
		Iterator it = list.iterator();
		try {
			//下面循环查询每一个节点，将该节点的所有父节点得到，并且放到一个set中
			parentIdSet = getParentSet();
			it = parentIdSet.iterator();
			//下面设置父节点的leaf属性和cls属性。
			while (it.hasNext()) {
				ExtTreeNode vo = new ExtTreeNode();
				String parentId = it.next().toString();
				vo = getMenu(parentId);
				if (vo != null) {
					vo.setLeaf(false);
					vo.setCls("folder");
				}
			}
			it = list.iterator();
			//下面设置树节点的cls和leaf属性。以及设置未打印的初始化标记。
			while (it.hasNext()) {
				ExtTreeNode vo = (ExtTreeNode) it.next();
				vo.setF(0);
				if (vo.getCls() == null) {
					vo.setLeaf(true);
					vo.setCls("file");
				}
			}
			return true;
		} catch (Exception e) {
			System.out.print("ExtTreeColl-setLeaves-error：" + e.getMessage());
			return false;
		}
	}

	/**
	 * <p>得到所有的父亲字符串</p>
	 * <p>Remark: </p>
	 * @return
	 */
	public Set getParentSet() {
		Set parentIdSet = new HashSet();
		List list = this.getList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			ExtTreeNode vo = (ExtTreeNode) it.next();
			parentIdSet.add(vo.getParent());
		}
		return parentIdSet;
	}

	/**
	 * <p>根据父节点号查找这个父节点下面的全部的子节点</p>
	 * <p>Remark: </p>
	 * @param parentId
	 * @return
	 */
	public ArrayList getExtTreeVOByParentId(String parentId) {
		return (ArrayList) parentMap.get(parentId);
	}

	/**
	 * <p>根据一个虚根设置这个树的第一层，为了防止树的节点找不到根的情况，例如虚根为om，本来下面应该设置总部的，但是如果实际节点集合最高为省份的时候，就应该主动设置这些节点的
	 *                 父亲为om（本来他们的父亲是总部，但是虚根不是总部，所以以前的设计就会出现问题！）
	 *           使用这个方法就不用再在Bo里面根据不同地区级别设置不同虚根了！比较方便，更加完善的现在的这个类。</p>
	 * <p>Remark: </p>
	 * @param nodes
	 * @param root
	 */
	public void setFirstFloor(ExtTreeNode root) {
		Set parentIdSet = this.getParentSet();
		String parentId = root.getId();
		ArrayList list = new ArrayList();
		//下面查找所有的父节点字符串，如果不存在表明应该准备把这些父亲的孩子转存到虚根的下面，作为虚根的孩子
		Object[] parents = parentIdSet.toArray();
		for (int temp = 0; temp < parents.length; temp++) {
			//这里假设只有一个父亲节点不存在，这样找到了他之后就马上可以退出循环了，而不用继续循环。。。
			if (this.getMenu(parents[temp].toString()) == null) {
				list.add(parents[temp].toString());
				break;
			}
		}
		//下面根据父亲孩子map找到所有的没有找到父亲节点的孩子们，将它们放到虚根的下面。。下面的是当有多个父亲节点不存在的时候进行更改父亲到虚根下面的操作。
		/*Iterator it = list.iterator();
		while(it.hasNext())
		{
		        ArrayList list2 = getExtTreeVOByParentId(it.next().toString());
		        for(int temp=0;temp<list2.size();temp++)
		        {
		                ExtTreeNode tempNode = (ExtTreeNode)list2.get(temp);
		                tempNode.setParent(parentId);
		                addParentViewElement(parentId,tempNode);
		        }                                
		}*/
		ArrayList list2 = getExtTreeVOByParentId(list.get(0).toString());
		for (int temp = 0; temp < list2.size(); temp++) {
			ExtTreeNode tempNode = (ExtTreeNode) list2.get(temp);
			tempNode.setParent(parentId);
			addParentViewElement(parentId, tempNode);
		}
	}

	/**
	 * <p>根据树的节点的集合以及树的跟节点开始拼装成json字符串 </p>
	 * <p>Remark: </p>
	 * @param nodes
	 * @param root
	 * @return
	 */
	public String getExtTreeStr(ExtTreeColl nodes, ExtTreeNode root) {
		StringBuilder ansStr = new StringBuilder();
		Iterator it = null;
		//下面设置节点的cls属性，leaf属性，以及设置为未打印的标记。
		nodes.setLeaves();
		Set parentIdSet = nodes.getParentSet();
		it = nodes.getList().iterator();
		StackL stack = new StackL();

		stack.push(root);
		//下面设置第一层的数据，主要是将找不到父亲节点的树节点都放在这个虚根下面，作为第一层数据。
		//nodes.setFirstFloor(root);
		//得到一个包含了虚根的父亲，孩子节点的map映射。。
		Map map = nodes.getParentMap();

		String result = "";
		ansStr.append("[");
		String debug = "";
		while (!stack.isEmpty()) {
			ExtTreeNode e = (ExtTreeNode) stack.top();
			ArrayList childs = (ArrayList) map.get(e.getId());
			Iterator cIt = childs.iterator();
			boolean stackChanged = false;
			while (cIt.hasNext() && (!stackChanged)) {
				ExtTreeNode aChild = (ExtTreeNode) cIt.next();
				if (aChild.getF() == 0) {
					// 如果是树枝节点并且打印标记位为0 ，就直接转换成为json字符串
					if ((!parentIdSet.contains(aChild.getId()))) {
						ansStr.append(JsonUtil.bean2json(aChild) + ",");
						aChild.setF(1);
						debug = ansStr.toString();
					}
					// 如果是非树枝节点就打印一部分字符串，同时入堆栈进行下次的循环
					else if (parentIdSet.contains(aChild.getId())) {
						ansStr.append(JsonUtil.bean2json(aChild));
						ansStr.deleteCharAt(ansStr.lastIndexOf("}"));
						ansStr.append(",\"children\":[");
						aChild.setF(1);
						stack.push(aChild);
						stackChanged = true;
						debug = ansStr.toString();
						break;
					}
				}
			}
			//下面是进行的json数组封闭组串。
			//打印完全部的孩子们的json串之后删除最后一个逗号。再加上一个"]".同时扔掉已经查询完了的父亲节点。
			//注意查询条件最后一个是因为没有打印root这个json串，所以没有必要在后面进行数组的封闭操作！
			if ((!cIt.hasNext()) && stackChanged == false) {
				if (e.getF() == 1) {
					ansStr.deleteCharAt(ansStr.lastIndexOf(","));
					ansStr.append("]},");
				}
				stack.pop();
				debug = ansStr.toString();
			}
		}
		ansStr.deleteCharAt(ansStr.lastIndexOf(","));
		ansStr.append("]");
		//ansStr.delete(ansStr.lastIndexOf("}]}]"),ansStr.lastIndexOf("}]}]")+4);
		result = ansStr.toString();
		result = result.replaceAll("\"leaf\":\"false\"", "\"leaf\":false");
		result = result.replaceAll("\"leaf\":\"true\"", "\"leaf\":true");
		return result;
	}
}