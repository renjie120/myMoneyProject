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
 * ����action�Ĺ�����,������action�����ʵ����,action�����ĵ����Լ������������Ҫ����.
 * @author wblishq
 *
 */
public class ActionFactory {
	protected Log log = LogFactory.getLog(this.getClass().getName()); 
	/**
	 * struts.xmlĬ����·��
	 */
	public static final String STRUTS_XML_FILE = "/struts.xml";

	/**
	 * struts.xml��·��
	 */
	private String filePath;

	public ActionFactory() {
		this.filePath = STRUTS_XML_FILE;
	}

	public ActionFactory(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * ���ڵ�Ԫ����
	 */
	public String test() {
		filePath = "/leo.xml";
		return "success";
	}

	/**
	 * ����StrutsFilter��������actionName,�õ���Ӧ��Action��
	 * @param actionName	    StrutsFilter��������actionName
	 * @param fileName        �����ļ���ַ
	 * @param foundFiles �Ѿ��ҹ��������ļ����ļ���,��ֹ������ѭ��
	 * @return ��Ӧ��Action����ַ���
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
			// ����ڵ�ǰxml�ļ����Ҳ�����Ӧ��action�����ļ�,�͵�include���õ��������ļ���ȥ��.
			if (config == null) {
				NodeList includes = doc.getElementsByTagName(MVCTags.INCLUDE);
				if (includes != null && includes.getLength() > 0) {
					for (int i = 0; i < includes.getLength(); i++) {
						Node _include = includes.item(i);
						String subFileName = Util.getDirName(fileName) + "\\"
								+ DomUtil.getAttribute(_include, MVCTags.FILE);
						// ���û���ҹ���������ļ�,�͵�����Ѱ��.
						if (!foundFiles.contains(subFileName)) {
							config = getActionConfig(actionName, subFileName,foundFiles);
							// ���������ļ����ҵ���ֹͣ����
							if (config != null) {
								return config;
							} else {
								// û���ҵ��Ͱѵ�ǰ��������ļ������浽�Ѿ����Ҷ�����.
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
	 * ��xml�ĵ��в��Ҷ�Ӧ��action�����ļ�
	 * @param actionName
	 * @param doc
	 * @return
	 */
	private ActionConfig getConfig(String actionName,Document doc){
		ActionConfig config = null;
		// �õ����е�package���
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
	 * ���ݲ�����ֵ������ָ����action����.
	 * @param config action����
	 * @param params ������ֵ��
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
			//�������˲�����actionʵ������󱣴浽��Ա������.
			config.setAction(obj);
		}catch(Exception e){
			log.error("���ݲ�������"+config.getClassName()+"�������,���struts.xml!", e);
		}
	}
	
	/**
	 * ִ��action��������޲η���,����������struts�����ļ��е�method����.
	 * @param clas
	 * @throws MVCException 
	 */
	public void invoke(ActionConfig clas) throws Exception,MVCException {
		try {
			//�õ������˲�����action����.
			Object obj = clas.getAction();
			//��ִ̬��ָ����method����ķ���,�����ܷ��ص��ַ���
			String resultName = (String)Util.invoke(obj, clas.getMethod());
			//�����ajax����,������ajax�����Ľ��׼������ǰ̨ȥ.
			if(clas.isAjax()){
				clas.setAjaxAns(resultName);
				return ;
			}
			//�������ļ��е�results���ҵ���Ӧ��ʵ��url��ַ,����������.
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
