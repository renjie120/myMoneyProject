package myOwnLibrary.taglib;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import tallyBook.dao.DaoUtil;

import common.base.SpringContextUtil;

public class ListOrderedMapTest {
	public static void main(String[] a) {
		   ApplicationContext ctx = new FileSystemXmlApplicationContext("F:\\newMoney\\NewHibernateMoney\\WebRoot\\WEB-INF\\applicationContext.xml");
		DaoUtil u = (DaoUtil)ctx.getBean("daoUtil");
		List l = u.doGetSqlQueryList("select * from money_detail_year");
		Iterator it = l.iterator();
		while(it.hasNext()){
			ListOrderedMap m = (ListOrderedMap)it.next();
			List ll = m.asList();
			for(int i=0,j=m.size();i<j;i++){
				System.out.println(m.get(i)+":"+m.getValue(i));
			}
			System.out.println("-------------------");
		}
	}
}
