package tallyBook.dao;

import java.util.List;

import myOwnLibrary.util.Cdd2;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import tallyBook.base.HibernateUtil;

/**
 * 登陆验证dao
 * @author renjie120 419723443@qq.com
 *
 */
public class LoginDao {
	/**
	 * 验证密码
	 * @param pass
	 * @return
	 */
	public boolean doCheckPass(String pass) {
		Cdd2 cdd = new Cdd2();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		SQLQuery q = session
				.createSQLQuery("SELECT 1 FROM CONFIG WHERE PASSWORD =:pas");
		q.setString("pas", cdd.strToBase64Str(pass));
		List ansList = q.list();
		session.getTransaction().commit();
		if(ansList.size()>0)
			return true;
		return false;
	}
	
	public static void main(String[] a){
		Cdd2 cdd = new Cdd2();
		System.out.println(cdd.strToBase64Str("1"));
	}
	/**
	 * 添加密码.
	 * @param pass
	 */
	public void doAddPass(String pass){
		Cdd2 cdd = new Cdd2();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		SQLQuery q = session.createSQLQuery("INSERT INTO CONFIG (PASSWORD) VALUES(:pas)");
		q.setString("pas", cdd.strToBase64Str(pass));
		q.executeUpdate();
		session.getTransaction().commit();
	}
	
	/**
	 * 更新密码
	 * @param pass
	 */
	public void doUpdatePass(String pass){
		Cdd2 cdd = new Cdd2();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		SQLQuery q = session.createSQLQuery("UPDATE CONFIG SET PASSWORD=:pas");
		q.setString("pas", cdd.strToBase64Str(pass));
		q.executeUpdate();
		session.getTransaction().commit();
	}
}
