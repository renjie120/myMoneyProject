package tallyBook.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectCollection {
    private List list = new ArrayList();
    private Map map = new HashMap();

            
    /** 
     * 在结果集中添加.
     * 数据在ArrayList内保存
     */
    public void addElement(Object element){
            list.add(element);
    }

    /** 
     * 在结果集中添加.
     * 数据在ArrayList内保存
     */
    public void addElement(int indx, Object element){
            list.add(indx,element);
    }
    
    /** 
     * 在结果集中添加.
     */
    public void addElement(Object key, Object element){
            map.put(key,element);
    }
    
    /** 
     * 从结果集中移出
     */
    public Object removeElement(int indexs){
            return list.remove(indexs);
    }
    
    /** 
     * 从结果集中移出 
     */
    public Object removeElement(Object key){        
            return map.remove(key);
    }
    
    /**
     * 从结果集中清除全部数据对象
     */
    public void removeElement(){
            this.list = new ArrayList();
            this.map = new HashMap();
    }
            
    /** 
     * 根据行号从结果集中获取数据.
     */
    public Object getElement(int index){
            if (index>=list.size() || index<0) {
                    return null;
            }
            return list.get(index);
    }

    /** 
     * 根据行号从结果集中获取数据.
     */
    public Object getElement(Object key){
            return map.get(key);
    }
    
    /** 
     * 获取结果集行数的函数. 
     */
    public int getRowCount(){
            return list.size();
    }
    
    /**
     * 获取结果集中hashtable对象的size
     */
    public int getHashCount(){
            return map.size();
    }

    /**
     * 内置lists对象保存对象列表
     */
    public List getList() {
            return list;
    }

    /**
     * 直接设置List对象
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
