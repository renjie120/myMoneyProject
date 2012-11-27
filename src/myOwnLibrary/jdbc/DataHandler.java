package myOwnLibrary.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DataHandler {
	private List list = null;
	public void addRecord(Object obj){
		if(list==null){
			list = new ArrayList();
		}
		list.add(obj);
	}
	public List getList(){
		return list;
	}
	public abstract void processRow(ResultSet rs) throws SQLException;
}
