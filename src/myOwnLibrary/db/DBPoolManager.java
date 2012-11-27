package myOwnLibrary.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBPoolManager {
	protected Log log = LogFactory.getLog(this.getClass().getName());
	/**
	 * ���ӳع������
	 */
	private static DBPoolManager dbManager = null;
	/**
	 * �����ļ�
	 */
	private Properties pros;
	/**
	 * ���ݿ����ӳ�
	 */
	private DBPool pool;
	/**
	 * ���������ݿ�������ļ��ϣ����Խ���������ݿ����ӳص�������
	 */
	private List drivers = new ArrayList();
	/**
	 * ��ʾ���ڿͻ���ʹ�õ���������
	 */
	private static int clientCount;
	
	public static void main(String[] a){
		DBPoolManager manager = DBPoolManager.getInstance();
		Connection con = manager.getDBConn();
		manager.freeDBConnection(con);
	}
	/**
	 * �������ӳع�������ʵ��
	 * @return
	 */
	public synchronized static DBPoolManager getInstance() {
		if(dbManager==null){
			dbManager = new DBPoolManager();
		}
		//������һ�ξͼ����ۼ�һ�Ρ�
		clientCount++;
		return dbManager;
	}

	public static void setDbManager(DBPoolManager dbManager) {
		DBPoolManager.dbManager = dbManager;
	}

	private DBPoolManager() {
		//���캯���н��г�ʼ������
		init();
	}

	private void init() {
		try {
			pros = new Properties();
			pros.load(this.getClass().getResourceAsStream("/db.properties"));
		} catch (Exception ex) {
			log.error("û���ҵ����ӳ������ļ�", ex);
		}
		loadDriver();
		createPool();
	}

	private void loadDriver() {
		//�õ����ݿ����������ƣ�����ж�����ݿ���������ƣ�������������ݿ�����ӳأ�
		String driverClasses = pros.getProperty("driverName");
		StringTokenizer st = new StringTokenizer(driverClasses);
		while (st.hasMoreElements()) {
			String driverClassName = st.nextToken().trim();
			try {
				Driver driver = (Driver) Class.forName(driverClassName)
						.newInstance();
				DriverManager.registerDriver(driver);
				drivers.add(driver);
			} catch (Exception ex) {
				log.error("�������ݿ����ӳس���", ex);
			}
		}
	}

	/**
	 * �������ݿ����ӳ�.
	 */
	private void createPool() {
		String userName = pros.getProperty("username");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		int maxConn;
		int minConn;
		try {
			maxConn = Integer.valueOf(pros.getProperty("maxConnection", "0"))
					.intValue();
			minConn = Integer.valueOf(pros.getProperty("minConnection", "0"))
				.intValue();
		} catch (NumberFormatException ex) {
			maxConn = 0;
			minConn = 0;
		}
		pool = new DBPool(userName, password, url, maxConn, minConn);
	}

	/**
	 * �õ����������ݿ����ӵ�ʾ����Ŀ.
	 * @return
	 */
	public synchronized int getClientCount() {
		return clientCount;
	}

	/**
	 * ����һ�����õ����ݿ�����.
	 * @return
	 */
	public Connection getDBConn() {
		if (pool != null) {
			return (pool.getDbConn());
		}
		return (null);
	}

	/**
	 * ���ݳ�ʱʱ�䷵��һ�����õ����ݿ�����.
	 * @param timeout
	 * @return
	 */
	public Connection getDbConn(long timeout) {
		if (pool != null) {
			return (pool.getDbConn(timeout));
		}
		return (null);
	}

	/**
	 * �ͷ�һ�����õ����ݿ�����.
	 * @param conn
	 */
	public synchronized void freeDBConnection(Connection conn) {
		if (pool != null) {
			pool.freeDBConnection(conn);
			clientCount--;
		}
	}

	/**
	 * �ͷ����ӳص�ȫ������.
	 */
	public void release() {
		//ֻҪ�ͻ��������ݿ����Ӵ�������״̬���Ͳ���ǿ�ƹر�����
		if (clientCount != 0) {
			return;
		}
		if (pool != null) {
			pool.release();
			Iterator iter = drivers.iterator();
			while (iter.hasNext()) {
				Driver driver = (Driver) iter.next();
				try {
					DriverManager.deregisterDriver(driver);
				} catch (Exception ex) {
					log.error("ע�����ݿ���������.", ex);
				}
			}
		}
	}
}
