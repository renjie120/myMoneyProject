package myOwnLibrary.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ������jar����xml���н�����С����.
 * @author renjie120 419723443@qq.com
 *
 */
public class DomUtil {
	protected static Log log = LogFactory.getLog("DomUtil");
	public static void main(String args[]) {
//		Document doc;
//		Element root;
//		String elementname;
//		String filename;
//		try {
//			filename = System.getProperty("user.dir");
//			filename = filename + "/WebRoot/WEB-INF/classes/struts.xml";
//
//			doc = getXmlDocument(filename);
//			// ��ȡxml�ĵ��ĸ��ڵ�
//			// root = getRoot(doc);
//			// System.out.println(root.getElementsByTagName("action").getLength());
//			// elementname = root.getNodeName();//��ø��ڵ�����
//			// System.out.println("������ڵ����ƣ�" + elementname);
//			// ��ӡ���ڵ�����Ժ�ֵ
//			// printAllAttributes(root);
//			// ��ӡ���ĵ�ȫ���ڵ�
//			// System.out.println("��ӡȫ���ڵ�");
//			// printElement(root, 0);
//			NodeList packages = doc.getElementsByTagName("package");
//			if (packages != null && packages.getLength() > 0) {
//				for (int i = 0; i < packages.getLength(); i++) {
//					Node _package = packages.item(i);
//					NodeList actions = _package.getChildNodes();
//					for (int j = 0; j < actions.getLength(); j++) {
//						Node _action = actions.item(j);
//						if (_action.getNodeName().equals("action")) {
//							if (getAttribute(_action,"name").equals("hello")) {
//								NodeList results = _action.getChildNodes();
//								for (int k = 0; k < results.getLength(); k++) {
//									Node _result = results.item(k);
//									if(_result.getNodeName().equals("result")&&getAttribute(_result,"name").equals("success"))
//									System.out.println(_result.getTextContent());
//								}
//							}
//						}
//					}
//				}
//			}
//		} catch (Exception exp) {
//			exp.printStackTrace();
//		}
	}

	/**
	 * �õ��ĵ�����ĸ��ڵ�.
	 * @param doc �ĵ�����
	 * @return
	 */
	public static Element getRoot(Document doc){
		return doc.getDocumentElement();
	}
	
	/**
	 * �õ�ָ���ڵ��ָ������ֵ.
	 * @param node
	 * @param attrName
	 * @return
	 */
	public static String getAttribute(Node node,String attrName){
		if(node.hasAttributes()){
			Node _node = node.getAttributes().getNamedItem(attrName);
			if(_node!=null)
				return _node.getNodeValue();
			else{
				return "";
			}
		}
		else
			return "";
	}
	
	/**
	 * �õ�ָ���ڵ���ı�����.
	 * @param node
	 * @return
	 */
	public static String getText(Node node){
		//return node.getTextContent();
		return null;
	}
	
	/**
	 * ����xml�ļ���ַ�õ�xml����.
	 * @param fileName xml��ַ
	 * @return
	 */
	public static Document getXmlDocument(String fileName){
		Document doc = null;
		DocumentBuilderFactory factory;
		DocumentBuilder docbuilder;

		FileInputStream in;
		try {
			in = new FileInputStream(fileName);
			// ����xml�ļ�,����document����
			factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			docbuilder = factory.newDocumentBuilder();
			doc = docbuilder.parse(in);			
		} catch (Exception e) {
			log.error("DomUtil---getXmlDocument", e);
		}
		return doc;
	}
	
	/**
	 *  ����xml�ļ�����ַ�õ�xml����.
	 * @param in
	 * @return
	 */
	public static Document getXmlDocument(InputStream in){
		Document doc = null;
		DocumentBuilderFactory factory;
		DocumentBuilder docbuilder;
		try {
			// ����xml�ļ�,����document����
			factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			docbuilder = factory.newDocumentBuilder();
			doc = docbuilder.parse(in);			
		} catch (Exception e) {
			log.error("DomUtil---getXmlDocument", e);
		}
		return doc;
	}
	
	/**
	 * ��ӡָ���ڵ��ȫ������.
	 * @param elem �ڵ����
	 */
	public static void printAllAttributes(Element elem) {
		NamedNodeMap attributes;//���ڵ���������
		int i, max;
		String name, value;
		Node curnode;

		attributes = elem.getAttributes();
		max = attributes.getLength();

		for (i = 0; i < max; i++) {
			curnode = attributes.item(i);
			name = curnode.getNodeName();
			value = curnode.getNodeValue();
			System.out.println("����ڵ����ƺ�ֵ��" + name + " = " + value);
		}
	}
	
	/**
	 * �õ�ָ���ڵ����������,���ؽ����һ��map����.
	 * @param elem �ڵ����
	 * @return 
	 */
	public static Map getAllAttributes(Element elem) {
		Map map = new HashMap();
		NamedNodeMap attributes;//���ڵ���������
		int i, max;
		String name, value;
		Node curnode;

		attributes = elem.getAttributes();
		max = attributes.getLength();

		for (i = 0; i < max; i++) {
			curnode = attributes.item(i);
			name = curnode.getNodeName();
			value = curnode.getNodeValue();
			map.put(name, value);
		}
		return map;
	}

	/**
	 * ��ӡ�ڵ�����нڵ�����ƺ�ֵ.
	 * @param elem �ڵ����
	 * @param depth ���
	 */
	public static void printElement(Element elem, int depth) {
		String elementname;
		NodeList children;
		int i, max;
		Node curchild;
		Element curelement;
		String nodename, nodevalue;

		// elementname = elem.getnodename();
		// ��ȡ����ڵ��ȫ���ӽڵ�
		children = elem.getChildNodes();

		// ��һ����ʽ��ӡ����ڵ�
		for (int j = 0; j < depth; j++) {
			//System.out.print(" ");
		}
		printAllAttributes(elem);

		// ���õݹ鷽ʽ��ӡȫ���ӽڵ�
		max = children.getLength();
		System.out.println("����ӽڵ�ĳ��ȣ�" + elem.getNodeName() + ":::" + max);
		//���ȫ���ӽڵ�
		for (int j = 0; j < max; j++) {
			System.out.println("tt:" + children.item(j));
		}

		for (i = 0; i < max; i++) {

			curchild = children.item(i);

			// �ݹ��˳�����
			if (curchild instanceof Element) {
				curelement = (Element) curchild;
				printElement(curelement, depth + 1);
			} else {
				nodename = curchild.getNodeName();
				nodevalue = curchild.getNodeValue();

				for (int j = 0; j < depth; j++) {
					System.out.print(" ");
					System.out.println(nodename + " = " + nodevalue);
				}
			}
		}
	}
}
