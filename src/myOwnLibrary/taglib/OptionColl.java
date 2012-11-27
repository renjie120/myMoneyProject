package myOwnLibrary.taglib;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

/**
 * �Զ���������˵�����.
 * @author renjie120 419723443@qq.com
 *
 */
public class OptionColl {
	private List list;

	public OptionColl() {
		list = new ArrayList();
	}

	public OptionColl(List otherList) throws Exception{
		if(otherList!=null){
			list = new ArrayList();
			int size = otherList.size();
			for(int i=0;i<size;i++){
				Object o =  otherList.get(i);
				if(o instanceof Object[]){
					Object[] item =	(Object[])o;
					if(item.length>=2)
						list.add(new Option(item[0].toString(),item[1].toString()));
					else{
						throw new Exception("ת������������optionת����ʽ");
					}
				}else if(o instanceof ListOrderedMap){
					ListOrderedMap m = (ListOrderedMap)o;
					list.add(new Option((String)m.getValue(0),(String)m.getValue(1)));
				}
			}
		}
	} 
	
	public void add(Option option) {
		list.add(option);
	}

	public void clear() {
		list.clear();
	}

	public int size() {
		if (list != null)
			return list.size();
		else
			return 0;
	}
	
	public Option get(int index){
		if(list!=null&&list.get(index)!=null){
			return (Option)(list.get(index));
		}else{
			return null;
		}
	}
	
	/**
	 * ����id���Ҷ�Ӧ�ĵ�һ������
	 * @param id
	 * @return
	 */
	public String findName(String id){
		if(list!=null&&list.size()>0)
			for(Object o:list){
				Option option = (Option)o;
				if(id.equals(option.getId())){
					return option.getName();
				}
			}
		return null;
	}
	
	/**
	 * �������Ʋ��Ҷ�Ӧ�ĵ�һ��id
	 * @param name
	 * @return
	 */
	public String findId(String name){
		if(list!=null&&list.size()>0)
			for(Object o:list){
				Option option = (Option)o;
				if(name.equals(option.getName())){
					return option.getId();
				}
			}
		return null;
	}
}
