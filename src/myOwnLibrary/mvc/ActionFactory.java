package myOwnLibrary.mvc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import myOwnLibrary.util.DomUtil;
import myOwnLibrary.util.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 处理action的工厂类,包含了action的类的实例化,action方法的调用以及处理请求等主要内容.
 * @author wblishq
 *
 */
public class ActionFactory {
	protected Log log = LogFactory.getLog(this.getClass().getName()); 
	/**
	 * struts.xml默认类路径
	 */
	public static final String STRUTS_XML_FILE = "/struts.xml";

	/**
	 * struts.xml类路径
	 */
	private String filePath;

	public ActionFactory() {
		this.filePath = STRUTS_XML_FILE;
	}

	public ActionFactory(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 用于单元测试
	 */
	public String test() {
		filePath = "/leo.xml";
		return "success";
	}

	/**
	 * 根据StrutsFilter传过来的actionName,得到相应的Action类
	 * @param actionName	    StrutsFilter传过来的actionName
	 * @param fileName        配置文件地址
	 * @param foundFiles 已经找过的配置文件名的集合,防止出现死循环
	 * @return 相应的Action类的字符串
	 */
	public ActionConfig getActionConfig(String actionName,String fileName,HashSet foundFiles) {
		Document doc = DomUtil.getXmlDocument(fileName);
		ActionConfig config = null;
		try {
			config = getConfig(actionName, doc);
			if (foundFiles==null) {
				foundFiles = new HashSet();
				foundFiles.add(fileName);
			} else {
				foundFiles.add(fileName);
			}
			// 如果在当前xml文件中找不到对应的action配置文件,就到include配置的子配置文件中去找.
			if (config == null) {
				NodeList includes = doc.getElementsByTagName(MVCTags.INCLUDE);
				if (includes != null && includes.getLength() > 0) {
					for (int i = 0; i < includes.getLength(); i++) {
						Node _include = includes.item(i);
						String subFileName = Util.getDirName(fileName) + "\\"
								+ DomUtil.getAttribute(_include, MVCTags.FILE);
						// 如果没有找过这个配置文件,就到其中寻找.
						if (!foundFiles.contains(subFileName)) {
							config = getActionConfig(actionName, subFileName,foundFiles);
							// 在子配置文件中找到就停止查找
							if (config != null) {
								return config;
							} else {
								// 没有找到就把当前这个配置文件名保存到已经查找对象中.
								foundFiles.add(subFileName);
							}
						}
					}
				}else
					return null;
			} else {
				return config;
			}

		} catch (Exception e) {
			log.error("ActionFactory--getAction", e);
		}
		throw null;
	}
	
	/**
	 * 在xml文档中查找对应的action配置文件
	 * @param actionName
	 * @param doc
	 * @return
	 */
	private ActionConfig getConfig(String actionName,Document doc){
		ActionConfig config = null;
		// 得到所有的package结点
		NodeList packages = doc.getElementsByTagName(MVCTags.PACKAGE);		
		try {
			if (packages != null && packages.getLength() > 0) {
				for (int i = 0; i < packages.getLength(); i++) {
					Node _package = packages.item(i);
					NodeList actions = _package.getChildNodes();
					for (int j = 0; j < actions.getLength(); j++) {
						Node _action = actions.item(j);
						if (_action.getNodeName().equals(MVCTags.ACTION)) {
							String _actionName = DomUtil.getAttribute(_action, MVCTags.NAME);
							if (actionName.endsWith(_actionName)) {
								config = new ActionConfig();
								config.setClassName(DomUtil.getAttribute(_action, MVCTags.CLAS));
								if("true".equals(DomUtil.getAttribute(_action, MVCTags.AJAX)))
									config.setAjax(true);
								else
									config.setAjax(false);
								config.setMethod(DomUtil.getAttribute(_action, MVCTags.METHOD));
								config.setResults(_action.getChildNodes());
								config.setName(actionName);
								return config;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("ActionFactory--getAction", e);
		}
		return config;
	}
	
	/**
	 * 根据参数键值对设置指定的action对象.
	 * @param config action对象
	 * @param params 参数键值对
	 * @return
	 */
	public void setActionObject(ActionConfig config,Map<String,String[]> params){
		try{
			Class _class = Class.forName(config.getClassName());
			Object obj = _class.newInstance();
			
			if(params!=null&&params.size()>0){
				Set entrySet = params.entrySet(); 
				Iterator iterator = entrySet.iterator(); 
				while(iterator.hasNext()) 
				{ 
				        Map.Entry<String,String[]> entry = (Map.Entry) iterator.next(); 
				        String key = entry.getKey().toString(); 
				        String[] value = entry.getValue(); 
				        Util.setPro(obj, key, Util.StringArrayToString(value));
				}
			}
			//将设置了参数的action实体类对象保存到成员变量中.
			config.setAction(obj);
		}catch(Exception e){
			log.error("根据参数配置"+config.getClassName()+"对象出错,检查struts.xml!", e);
		}
	}
	
	/**
	 * 执行action类里面的无参方法,方法名来至struts配置文件中的method属性.
	 * @param clas
	 * @throws MVCException 
	 */
	public void invoke(ActionConfig clas) throws Exception,MVCException {
		try {
			//得到设置了参数的action对象.
			Object obj = clas.getAction();
			//动态执行指定的method对象的方法,并接受返回的字符串
			String resultName = (String)Util.invoke(obj, clas.getMethod());
			//如果是ajax方法,就设置ajax方法的结果准备传到前台去.
			if(clas.isAjax()){
				clas.setAjaxAns(resultName);
				return ;
			}
			//到配置文件中的results中找到对应的实际url地址,并进行设置.
			NodeList results = clas.getResults();
			for(int i=0;i<results.getLength();i++){
				Node node = (Node)results.item(i);
				if(DomUtil.getAttribute(node, MVCTags.NAME).equals(resultName)){
					clas.setResultUrl(DomUtil.getText(node));
				}
			}
		} catch (MVCException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
}
