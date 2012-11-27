package myOwnLibrary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBPool {
	protected Log log = LogFactory.getLog(this.getClass().getName()); 
	/**
	 * ���ݿ��û���
	 */
	private String dbUserName;
	/**
	 * ���ݿ�����
	 */
	private String dbPassWord;
	/**
	 * ���ݿ�����
	 */
	private String dbConnUrl;
	/**
	 * ���������
	 */
	private int maxConn;
	
	/**
	 * ��ʼ�����ݿ����ӳص�Ĭ����С������.
	 */
	private int minConn;
	/**
	 * ��ǰ��������������ʹ�õ�������
	 */
	private int checkedOut;
	/**
	 * ���е����Ӷ���ļ��ϣ���ֱ��������ȡ����Ч�����ӣ�
	 */
	private ArrayList<Connection> freeConn;

	/*
	 * ��ʼ�����ӳ�
	 */
	public DBPool(String dbUserName, String dbPassWord, String dbConnUrl,
			int maxConn,int minConn) {
		super();
		this.dbUserName = dbUserName;
		this.dbPassWord = dbPassWord;
		this.dbConnUrl = dbConnUrl;
		this.maxConn = maxConn;
		this.minConn = minConn;
		this.freeConn = new ArrayList();
		//����Ĭ����С��Ŀ�����ӵ����������б���.
		for(int i=0;i<minConn;i++){
			freeConn.add(newDbConn());
		}
	}
	
	/**
	 * �õ�һ������.
	 * @return
	 */
	public synchronized Connection getDbConn() {
		Connection conn = null;
		//log.info("�õ����ݿ�����֮ǰ:�������"+checkedOut+";�������ӳ��е�������:"+freeConn.size()+";���������:"+maxConn);		
		//����ڿ��е����ӣ��Ͱ��ڿ����������е���ǰ���ȡ������
		if (freeConn.size() > 0) {
			conn = freeConn.get(0);
			freeConn.remove(0);
			try {
				//���ȡ�����������Ѿ��رգ�����ȡ���ӣ����еݹ���á�
				if (conn.isClosed()) {
					conn = getDbConn();
				}
			} catch (SQLException e) {
				log.error("ȡ���е����ݿ����ӳ���.", e);
			}
		} 
		//���û�������������������û�����û������е������������ߵ�ǰʹ�õ�������Ŀ�����������������ʹ����µ����ӡ�
		else if (maxConn == 0 || checkedOut < maxConn) {
			conn = newDbConn();
		}
		//�����ɹ������ݿ����ӣ�����������ʹ�õ�������Ŀ��1		
		if (conn != null) {
			checkedOut++;
		}
		//log.info("�õ����ݿ�����֮��:�������"+checkedOut+";�������ӳ��е�������:"+freeConn.size()+";���������:"+maxConn);	
		return conn;
	}

	/**
	 * �õ�һ�����õ����ݿ�����.
	 * @param timeout �û��ȴ�ʱ��.
	 * @return
	 */
	public synchronized Connection getDbConn(long timeout) {
		long starttime = System.currentTimeMillis();
		Connection conn;
		while ((conn = getDbConn()) == null) {
			try {
				//�ȴ�ָ��ʱ��֮��,�ٻ�ȡ����,���Ƿ��п�������.
				wait(timeout);
			} catch (InterruptedException e) {
				log.error("�õ����ݿ����ӳس���.", e);
			}
			//�����ȴ���ʱ��û�л������,��ֱ�ӷ��ؿ�.
			if (System.currentTimeMillis() - starttime >= timeout) {
				return null;
			}
		}
		return conn;
	}

	/**
	 * �õ�һ���µ����ݿ�����.
	 * @return
	 */
	public synchronized Connection newDbConn() {
		Connection conn = null;
		try {
			if (this.dbUserName == null) {
				conn = DriverManager.getConnection(this.dbConnUrl);
			} else {
				conn = DriverManager.getConnection(this.dbConnUrl,
						this.dbUserName, this.dbPassWord);
			}
		} catch (SQLException ex) {
			log.error("�������ݿ����ӳس���.", ex);
		}
		return conn;
	}

	/**
	 * ������ʹ�õ����ӷ������ӳ�.
	 * @param conn
	 */
	public synchronized void freeDBConnection(Connection conn) {
		//�����õ����Ӳ�ֱ��close�����Ƿŵ��������Ӽ�����ȥ
		freeConn.add(conn);
		// ������ʹ�õ���������1
		checkedOut--;
		/*
		 * ע������Ĵ��� notifyAll()�õȴ�ĳ������K�������߳��뿪����״̬��
		 * notify()�����ѡȡ�ȴ�ĳ������K���̣߳������뿪����״̬�� ��������wait�������ڵĺ���
		 */
		notifyAll(); 
	}

	/**
	 * �ر����е����ݿ�����. 
	 */
	public synchronized void release() {
		Iterator<Connection> iter = freeConn.iterator();
		Connection conn;
		while (iter.hasNext()) {
			try {
				conn = iter.next();
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		freeConn.clear();
	}
}