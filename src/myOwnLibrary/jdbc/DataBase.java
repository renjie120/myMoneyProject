package myOwnLibrary.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataBase {
	protected Log log = LogFactory.getLog(this.getClass().getName()); 
	/**
	 * 数据库查询结果集
	 */
	private ResultSet rs;

	/**
	 * 数据库操作
	 */
	private Statement stm;

	/**
	 * 数据库操作驱动
	 */
	private final String dbDriver = Content.CLASSNAME;

	/**
	 * 数据库连接
	 */
	private final String url = Content.URL;

	/**
	 * 数据库连接用户名
	 */
	private final String userName = Content.USER;

	/**
	 * 数据库连接密码
	 */
	private final String userPwd = Content.PWD;

	/**
	 * 数据库连接实例
	 */
	private Connection con = null;

	/**
	 * 加载数据库,检查是不是存在jdbc的相关包.
	 */
	public DataBase() {
		try {
			Class.forName(dbDriver).newInstance();
		} catch (Exception ex) {
			System.out.print("数据库加载失败！");
		}
	}

	/**
	 * 连接数据库.
	 * @return
	 */
	public Connection getConnection() {
		try {
			con = DriverManager.getConnection(url, userName, userPwd);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return con;
	}

	/**
	 * 关闭数据库连接.
	 */
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (stm != null) {
				stm.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建一个不敏感的数据库语句对象.
	 * @return
	 */
	public Statement getStatement() {
		try {
			con = getConnection();
			stm = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return stm;
	}

	/**
	 * 创建一个可以滚动的但不敏感的数据库语句对象.
	 * @return
	 */
	public Statement getInsStatement() {
		try {
			con = getConnection();
			stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return stm;
	}
	
	//关闭打开的Statement  
    private void closeStatement(Statement stmt){  
        if(stmt!=null){  
            try {  
                stmt.close();  
                stmt=null;  
            } catch (SQLException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            }
        }  
    }  
    //关闭打开的Connection   
    private void closeConnection(Connection conn){  
        if(conn!=null){  
            try {  
                conn.close();  
                conn=null;  
            } catch (SQLException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            }
        }  
    }  
   
    /**
     * 查询返回集合
     * @param sql
     * @param argList
     * @param handler
     * @return
     */
    public List queryList(String sql,List argList,DataHandler handler) throws SQLException{
    	Connection conn = null;  
    	PreparedStatement cstmt = null ;
        ResultSet rs = null;    
        List ans = new ArrayList();
        try {  
            conn=DBPoolManager.getInstance().getDBConn();  
            cstmt = conn.prepareStatement(sql);
            if(argList!=null&&argList.size()>0){
            	for(int i=0;i<argList.size();i++)
            	{
            		cstmt.setString(i+1, argList.get(i).toString());
            	}
            }
            rs = cstmt.executeQuery();  
            //下面就调用抽象类的进行行处理的方法.
            while(rs.next()){
            	handler.processRow(rs);            	
            }
            ans = handler.getList();           
        } catch (SQLException e) {  
            e.printStackTrace();  
            throw e;
        } catch(Exception e){
        	e.printStackTrace();
        	throw new SQLException();
        }finally{  
            closeStatement(cstmt);  
            DBPoolManager.getInstance().freeDBConnection(conn);
        }    
        return ans;  
    }
    
    public List queryList(String sql,DataHandler handler){
    	List list =new ArrayList();
    	try {
    		list= queryList(sql,null,handler);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
    }
    
    /**
     * 查询一条记录返回结果.
     * @param sql
     * @param argList
     * @return
     */
    public Object queryOneRecord(String sql,List argList) throws SQLException {
    	Connection conn = null;  
    	PreparedStatement cstmt = null ;
        ResultSet rs = null;    
        Object ans = null;
        try {  
            conn=DBPoolManager.getInstance().getDBConn();  
            cstmt = conn.prepareStatement(sql);
            if(argList!=null&&argList.size()>0){
            	for(int i=0;i<argList.size();i++)
            	{
            		cstmt.setString(i+1, argList.get(i).toString());
            	}
            }
            rs = cstmt.executeQuery();  

            if(rs.next()){
                ans = rs.getObject(1);
            }
            	
        } catch (SQLException e) {  
            e.printStackTrace();  
            throw e;
        } catch(Exception e){
        	e.printStackTrace();
        	throw new SQLException();
        }finally{  
            closeStatement(cstmt);  
            DBPoolManager.getInstance().freeDBConnection(conn);
        }    
        return ans;  
    }
    /**
     * 查询一条记录返回结果.
     * @param sql
     * @param argList
     * @return 
     * 多加一个参数 用来返回一条结果集
     */
    public Object queryOneRecord(String sql,List argList,DataHandler handler) throws SQLException{
        Connection conn = null;  
        PreparedStatement cstmt = null ;
        ResultSet rs = null;    
        Object ans = null;
        try {  
            conn=DBPoolManager.getInstance().getDBConn();  
            cstmt = conn.prepareStatement(sql);
            if(argList!=null&&argList.size()>0){
                for(int i=0;i<argList.size();i++)
                {
                    cstmt.setString(i+1, argList.get(i).toString());
                }
            }
            rs = cstmt.executeQuery();  

            if(rs.next()){
                handler.processRow(rs);
                return  handler.getList().get(0);
            }
            
        } catch (SQLException e) {  
            throw e;
        } catch(Exception e){
        	throw new SQLException(e.getMessage());
        }
         finally{  
            closeStatement(cstmt);  
            DBPoolManager.getInstance().freeDBConnection(conn);
        }    
        return ans;  
    }
    
    /**
     * 更新一条语句,适用于插入语句,或者执行删除,更新操作.
     * @param sql sql语句
     * @param argList 参数
     * @return
     */
    public int updateRecords(String sql,List argList) throws SQLException{
    	Connection conn = null;  
    	PreparedStatement cstmt = null ;
        ResultSet rs = null;    
        int ans = 0;
        try {  
            conn=DBPoolManager.getInstance().getDBConn();  
            cstmt = conn.prepareStatement(sql);
            if(argList!=null&&argList.size()>0){
            	for(int i=0;i<argList.size();i++)
            	{
            		cstmt.setString(i+1, argList.get(i).toString());
            	}
            }
            ans = cstmt.executeUpdate();
        } catch (SQLException e) {  
            e.printStackTrace();  
            throw e;
        } catch(Exception e){
        	e.printStackTrace();
        	throw new SQLException();
        }finally{  
            closeStatement(cstmt);  
            DBPoolManager.getInstance().freeDBConnection(conn);
        }    
        return ans;  
    }
    
    /**
     * 更新一条语句,适用于插入语句,或者执行删除,更新操作.
     * @param sql
     * @return
     */
    public int updateRecords(String sql) throws SQLException{
    	Connection conn = null;  
    	PreparedStatement cstmt = null ;
        ResultSet rs = null;    
        int ans = 0;
        try {  
            conn=DBPoolManager.getInstance().getDBConn();  
            cstmt = conn.prepareStatement(sql);            
            ans = cstmt.executeUpdate();
        } catch (SQLException e) {  
            e.printStackTrace();  
            throw e;
        } catch(Exception e){
        	e.printStackTrace();
        	throw new SQLException();
        }finally{  
            closeStatement(cstmt);  
            DBPoolManager.getInstance().freeDBConnection(conn);
        }    
        return ans;  
    }
    /**
     * 加了个参数handler
     * 用于获取一条数据记录，SQL语句不用传参数时用
     */ 
    public Object queryOneRecord(String sql,DataHandler handler) throws SQLException{
        return queryOneRecord(sql,null,handler);
    }
    public int queryForInt(String sql)  throws SQLException{
    	return queryForInt(sql,null);
    }
    
    public int queryForInt(String sql,List argList)  throws SQLException{
    	return Integer.parseInt(queryOneRecord(sql,argList).toString());
    }
    
    public double queryForDouble(String sql)  throws SQLException{
    	return queryForDouble(sql,null);
    }
    
    public double queryForDouble(String sql,List argList)  throws SQLException{
    	return Double.parseDouble(queryOneRecord(sql,argList).toString());
    }
    
    public String queryForString(String sql)  throws SQLException{
    	return queryForString(sql,null);
    }
    
    public String queryForString(String sql,List argList) throws SQLException{
    	return queryOneRecord(sql,argList).toString();
    }
    
    /**
     * 事务回滚.
     * @param conn
     */
    private void transactionRollback(Connection conn){  
        if(conn!=null){  
            try {  
                conn.rollback();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
          
    }  
	/**
	 * 执行sql语句,并返回结果集.
	 * @param sql
	 * @return
	 */
	public ResultSet getResultSet(String sql) {
		if (sql == null) {
			sql = "";
		}
		try {
			stm = getInsStatement();
			rs = stm.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
