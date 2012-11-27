package tallyBook.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectCollection {
    private List list = new ArrayList();
    private Map map = new HashMap();

            
    /** 
     * �ڽ���������.
     * ������ArrayList�ڱ���
     */
    public void addElement(Object element){
            list.add(element);
    }

    /** 
     * �ڽ���������.
     * ������ArrayList�ڱ���
     */
    public void addElement(int indx, Object element){
            list.add(indx,element);
    }
    
    /** 
     * �ڽ���������.
     */
    public void addElement(Object key, Object element){
            map.put(key,element);
    }
    
    /** 
     * �ӽ�������Ƴ�
     */
    public Object removeElement(int indexs){
            return list.remove(indexs);
    }
    
    /** 
     * �ӽ�������Ƴ� 
     */
    public Object removeElement(Object key){        
            return map.remove(key);
    }
    
    /**
     * �ӽ���������ȫ�����ݶ���
     */
    public void removeElement(){
            this.list = new ArrayList();
            this.map = new HashMap();
    }
            
    /** 
     * �����кŴӽ�����л�ȡ����.
     */
    public Object getElement(int index){
            if (index>=list.size() || index<0) {
                    return null;
            }
            return list.get(index);
    }

    /** 
     * �����кŴӽ�����л�ȡ����.
     */
    public Object getElement(Object key){
            return map.get(key);
    }
    
    /** 
     * ��ȡ����������ĺ���. 
     */
    public int getRowCount(){
            return list.size();
    }
    
    /**
     * ��ȡ�������hashtable�����size
     */
    public int getHashCount(){
            return map.size();
    }

    /**
     * ����lists���󱣴�����б�
     */
    public List getList() {
            return list;
    }

    /**
     * ֱ������List����
     */
    public void setList(List list) {
            if (list==null) {
                    this.list=new ArrayList();
            } else {
                    this.list = list;
            }
    }
    public Map getMap() {
            return map;
    }
    

    public void setMap(Map map) {
            this.map = map;
    }

}
