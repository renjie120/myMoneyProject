package tallyBook.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myOwnLibrary.util.JsonUtil;
import myOwnLibrary.util.Util;

import org.apache.struts2.ServletActionContext;

import tallyBook.bo.ExtTree;
import tallyBook.bo.ExtTreeColl;
import tallyBook.bo.ExtTreeNode;
import tallyBook.dao.CommonSelDao;
import tallyBook.dao.TallyTypeDao;
import tallyBook.pojo.TallyType;

/**
 * 配置类别的action，使用gridTree显示。
 * 
 * @author renjie120 419723443@qq.com
 * 
 */
public class TypeConfigAction {
	private TallyTypeDao dao = null;
	/**
	 * 要修改或者删除的类型id
	 */
	private String typeId;
	/**
	 * 类型名.
	 */
	private String typeName;
	/**
	 * 新的类型id
	 */
	private String newTypeId;

	public String getNewTypeId() {
		return newTypeId;
	}

	public void setNewTypeId(String newTypeId) {
		this.newTypeId = newTypeId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * 得到ext树.
	 * @return
	 */
	public String initExtMoneyTree() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		dao = new TallyTypeDao(); 
		String n = request.getParameter("node");  
		List tempList = getTreeByParent(n);
		List list = new ArrayList();
		Iterator it = tempList.iterator();
		while (it.hasNext()) {
			ExtTree t = new ExtTree();
			ExtTreeNode node = (ExtTreeNode) it.next();
			String id = node.getId();
			if (hasChildType(id)) {
				t.setLeaf(false);
			} else {
				t.setLeaf(true);
			}
			t.setId(node.getId());
			t.setText(node.getText());
			list.add(t);
		}
		String ListStr = JsonUtil.list2json(list);
		// 下面一步很重要，把leaf
		ListStr = ListStr.replaceAll("\"leaf\":\"false\"", "\"leaf\":false");
		ListStr = ListStr.replaceAll("\"leaf\":\"true\"", "\"leaf\":true");
		request.setAttribute("resultList", ListStr);
		response.setContentType("text/html;charset=GBK");
		try {
			response.getWriter().write(ListStr);
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ext树的时候根据父亲节点得到子节点.
	 * @param parentId
	 * @return
	 */
	public ArrayList getTreeByParent(String parentId) {
		ArrayList result = new ArrayList();
		dao = new TallyTypeDao();
		result = dao.doQueryTallyTypesByParentCode(parentId);
		return result;
	}
	
	/**
	 * 判断类型节点是不是有孩子节点
	 * @param nodeId
	 * @return
	 */
	public boolean hasChildType(String nodeId) { 
		dao = new TallyTypeDao();
		return dao.hasChildType(nodeId); 
	}

	private static ExtTreeColl treeData;

	public void getTreeColl() {
		StringBuilder sqlbd = new StringBuilder();
		CommonSelDao dao = new CommonSelDao();
		treeData = dao.getAllTallyTypeTreeColl(); 
	}

	/**
	 * 得到类型的表格树.
	 * 
	 * @return
	 */
	public String getTypeTree() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		dao = new TallyTypeDao();
		List ansList = dao.doGetAllTallyTypeMoney();
		List allType = new ArrayList();
		try {
			if (ansList != null) {
				Iterator it = ansList.iterator();
				while (it.hasNext()) {
					TallyType type = new TallyType();
					Object[] infos = (Object[]) it.next();
					type.setTallyTypeDesc(Util.notBlankStr(infos[0], ""));
					type.setTallyTypeSno(Integer.parseInt(infos[1].toString()));
					type.setTypeCode(infos[2].toString());
					type.setParentCode(Util.notBlankStr(infos[3], ""));
					type.setSumMoney((Util.notBlankStr(infos[4], "").trim()));
					type.setAvgMoney(Util.notBlankStr(infos[5], "").trim());
					type.setMoneyType(infos[6].toString());
					allType.add(type);
				}
			}
			String jsonStr = "{total:" + request.getAttribute("gtcount")
					+ ",page:" + request.getAttribute("gtpage") + ",data:"
					+ JsonUtil.list2json(allType) + "}";
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 形成xyTree的类型结构
	 * 
	 * @return
	 */
	public String getTallyTypeTree() {
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonSelDao seldao = new CommonSelDao();
		dao = new TallyTypeDao();
		List ansList = seldao.getAllTallyTypes();
		try {
			String jsonStr = JsonUtil.list2json(ansList);
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
