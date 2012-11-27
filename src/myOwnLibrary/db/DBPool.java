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
	 * 数据库用户名
	 */
	private String dbUserName;
	/**
	 * 数据库密码
	 */
	private String dbPassWord;
	/**
	 * 数据库连接
	 */
	private String dbConnUrl;
	/**
	 * 最大连接数
	 */
	private int maxConn;
	
	/**
	 * 初始化数据库连接池的默认最小连接数.
	 */
	private int minConn;
	/**
	 * 当前连接数，即正在使用的连接数
	 */
	private int checkedOut;
	/**
	 * 空闲的连接对象的集合（可直接在其中取出有效的连接）
	 */
	private ArrayList<Connection> freeConn;

	/*
	 * 初始化连接池
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
		//创建默认最小数目的链接到空闲连接中备用.
		for(int i=0;i<minConn;i++){
			freeConn.add(newDbConn());
		}
	}
	
	/**
	 * 得到一个连接.
	 * @return
	 */
	public synchronized Connection getDbConn() {
		Connection conn = null;
		//log.info("得到数据库连接之前:活动连接数"+checkedOut+";空闲连接池中的连接数:"+freeConn.size()+";最大连接数:"+maxConn);		
		//如果在空闲的链接，就把在空闲连接链中的最前面的取出来。
		if (freeConn.size() > 0) {
			conn = freeConn.get(0);
			freeConn.remove(0);
			try {
				//如果取出来的链接已经关闭，继续取连接，进行递归调用。
				if (conn.isClosed()) {
					conn = getDbConn();
				}
			} catch (SQLException e) {
				log.error("取空闲的数据库连接出错.", e);
			}
		} 
		//如果没有设置最大连接数（即没有设置缓存区中的连接数）或者当前使用的连接数目低于最大的连接数，就创建新的连接。
		else if (maxConn == 0 || checkedOut < maxConn) {
			conn = newDbConn();
		}
		//创建成功了数据库连接，就设置正在使用的连接数目加1		
		if (conn != null) {
			checkedOut++;
		}
		//log.info("得到数据库连接之后:活动连接数"+checkedOut+";空闲连接池中的连接数:"+freeConn.size()+";最大连接数:"+maxConn);	
		return conn;
	}

	/**
	 * 得到一个可用的数据库连接.
	 * @param timeout 用户等待时间.
	 * @return
	 */
	public synchronized Connection getDbConn(long timeout) {
		long starttime = System.currentTimeMillis();
		Connection conn;
		while ((conn = getDbConn()) == null) {
			try {
				//等待指定时间之后,再获取连接,看是否有可用连接.
				wait(timeout);
			} catch (InterruptedException e) {
				log.error("得到数据库连接池出错.", e);
			}
			//超出等待的时间没有获得连接,就直接返回空.
			if (System.currentTimeMillis() - starttime >= timeout) {
				return null;
			}
		}
		return conn;
	}

	/**
	 * 得到一个新的数据库连接.
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
			log.error("创建数据库连接池出错.", ex);
		}
		return conn;
	}

	/**
	 * 将不再使用的连接返回连接池.
	 * @param conn
	 */
	public synchronized void freeDBConnection(Connection conn) {
		//将不用的连接不直接close，而是放到空闲连接集合中去
		freeConn.add(conn);
		// 将正在使用的连接数减1
		checkedOut--;
		/*
		 * 注意下面的处理： notifyAll()让等待某个对象K的所有线程离开阻塞状态，
		 * notify()随机地选取等待某个对象K的线程，让它离开阻塞状态。 重新启动wait（）所在的函数
		 */
		notifyAll(); 
	}

	/**
	 * 关闭所有的数据库连接. 
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