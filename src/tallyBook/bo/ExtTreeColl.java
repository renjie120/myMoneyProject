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
	 * <p>���һ��Ext���Ľڵ�</p>
	 * <p>Remark: </p>
	 * @param vo
	 */
	public void addExtTreeVO(ExtTreeNode vo) {
		addElement(vo);
		addElement(vo.getId(), vo);
		addParentViewElement(vo.getParent(), vo);
	}

	/**
	 * <p>����һ�����ڵ��뺢���ǵ�mapӳ�����</p>
	 * <p>Remark: </p>
	 * @return
	 */
	public Map getParentMap() {
		return parentMap;
	}

	/** 
	 * ���һ�����ڸ��׽ڵ��ӽǿ���map
	 * key:�ַ�������ʾ�����ַ���
	 * value:arrayList���ͣ����key��Ӧ�Ķ��ӽڵ���ɵ�һ��list
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
	 * <p>��addExtTreeVOһ����ʵ�ֺ�����Ϊ�˲��ı�֮ǰ�����</p>
	 * <p>Remark: </p>
	 * @param vo
	 */
	public void addMenu(ExtTreeNode vo) {
		addElement(vo);
		addElement(vo.getId(), vo);
	}

	/**
	 * �����кŻ�ȡExt���е�ָ���ڵ�
	 * @param index
	 */
	public ExtTreeNode getExtTreeVO(int index) {
		return (ExtTreeNode) getElement(index);
	}

	/**
	 * <p>����������ڵ��id�õ��ڵ㣬id���ַ������ͣ�</p>
	 * <p>Remark: </p>
	 * @param menuId
	 * @return
	 */
	public ExtTreeNode getMenu(String menuId) {
		//return (MenuVO)getElement(menuId);
		for (int i = 0; i < getRowCount(); i++) {
			//����õ�ĳһ�еĽڵ�
			ExtTreeNode vo = getExtTreeVO(i);
			String menuIdTmp = vo.getId();
			if (menuIdTmp.equals(menuId))
				return vo;
		}
		return null;
	}

	/**
	 * <p>ͨ��ExtTree���ڵ��parent��������cls��ʽ�Լ�Leaf����</p>
	 * <p>Remark: </p>
	 * @return
	 */
	public boolean setLeaves() {
		List list = this.getList();
		//�����еĸ��ڵ��id�õ�����һ��set�У�����ȥ���ظ���idֵ
		Set parentIdSet = new HashSet();
		Map map = this.getMap();
		Iterator it = list.iterator();
		try {
			//����ѭ����ѯÿһ���ڵ㣬���ýڵ�����и��ڵ�õ������ҷŵ�һ��set��
			parentIdSet = getParentSet();
			it = parentIdSet.iterator();
			//�������ø��ڵ��leaf���Ժ�cls���ԡ�
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
			//�����������ڵ��cls��leaf���ԡ��Լ�����δ��ӡ�ĳ�ʼ����ǡ�
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
			System.out.print("ExtTreeColl-setLeaves-error��" + e.getMessage());
			return false;
		}
	}

	/**
	 * <p>�õ����еĸ����ַ���</p>
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
	 * <p>���ݸ��ڵ�Ų���������ڵ������ȫ�����ӽڵ�</p>
	 * <p>Remark: </p>
	 * @param parentId
	 * @return
	 */
	public ArrayList getExtTreeVOByParentId(String parentId) {
		return (ArrayList) parentMap.get(parentId);
	}

	/**
	 * <p>����һ���������������ĵ�һ�㣬Ϊ�˷�ֹ���Ľڵ��Ҳ�������������������Ϊom����������Ӧ�������ܲ��ģ��������ʵ�ʽڵ㼯�����Ϊʡ�ݵ�ʱ�򣬾�Ӧ������������Щ�ڵ��
	 *                 ����Ϊom���������ǵĸ������ܲ���������������ܲ���������ǰ����ƾͻ�������⣡��
	 *           ʹ����������Ͳ�������Bo������ݲ�ͬ�����������ò�ͬ����ˣ��ȽϷ��㣬�������Ƶ����ڵ�����ࡣ</p>
	 * <p>Remark: </p>
	 * @param nodes
	 * @param root
	 */
	public void setFirstFloor(ExtTreeNode root) {
		Set parentIdSet = this.getParentSet();
		String parentId = root.getId();
		ArrayList list = new ArrayList();
		//����������еĸ��ڵ��ַ�������������ڱ���Ӧ��׼������Щ���׵ĺ���ת�浽��������棬��Ϊ����ĺ���
		Object[] parents = parentIdSet.toArray();
		for (int temp = 0; temp < parents.length; temp++) {
			//�������ֻ��һ�����׽ڵ㲻���ڣ������ҵ�����֮������Ͽ����˳�ѭ���ˣ������ü���ѭ��������
			if (this.getMenu(parents[temp].toString()) == null) {
				list.add(parents[temp].toString());
				break;
			}
		}
		//������ݸ��׺���map�ҵ����е�û���ҵ����׽ڵ�ĺ����ǣ������Ƿŵ���������档��������ǵ��ж�����׽ڵ㲻���ڵ�ʱ����и��ĸ��׵��������Ĳ�����
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
	 * <p>�������Ľڵ�ļ����Լ����ĸ��ڵ㿪ʼƴװ��json�ַ��� </p>
	 * <p>Remark: </p>
	 * @param nodes
	 * @param root
	 * @return
	 */
	public String getExtTreeStr(ExtTreeColl nodes, ExtTreeNode root) {
		StringBuilder ansStr = new StringBuilder();
		Iterator it = null;
		//�������ýڵ��cls���ԣ�leaf���ԣ��Լ�����Ϊδ��ӡ�ı�ǡ�
		nodes.setLeaves();
		Set parentIdSet = nodes.getParentSet();
		it = nodes.getList().iterator();
		StackL stack = new StackL();

		stack.push(root);
		//�������õ�һ������ݣ���Ҫ�ǽ��Ҳ������׽ڵ�����ڵ㶼�������������棬��Ϊ��һ�����ݡ�
		//nodes.setFirstFloor(root);
		//�õ�һ������������ĸ��ף����ӽڵ��mapӳ�䡣��
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
					// �������֦�ڵ㲢�Ҵ�ӡ���λΪ0 ����ֱ��ת����Ϊjson�ַ���
					if ((!parentIdSet.contains(aChild.getId()))) {
						ansStr.append(JsonUtil.bean2json(aChild) + ",");
						aChild.setF(1);
						debug = ansStr.toString();
					}
					// ����Ƿ���֦�ڵ�ʹ�ӡһ�����ַ�����ͬʱ���ջ�����´ε�ѭ��
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
			//�����ǽ��е�json�������鴮��
			//��ӡ��ȫ���ĺ����ǵ�json��֮��ɾ�����һ�����š��ټ���һ��"]".ͬʱ�ӵ��Ѿ���ѯ���˵ĸ��׽ڵ㡣
			//ע���ѯ�������һ������Ϊû�д�ӡroot���json��������û�б�Ҫ�ں����������ķ�ղ�����
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