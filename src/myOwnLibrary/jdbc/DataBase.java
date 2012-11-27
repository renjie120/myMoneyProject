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
	 * ���ݿ��ѯ�����
	 */
	private ResultSet rs;

	/**
	 * ���ݿ����
	 */
	private Statement stm;

	/**
	 * ���ݿ��������
	 */
	private final String dbDriver = Content.CLASSNAME;

	/**
	 * ���ݿ�����
	 */
	private final String url = Content.URL;

	/**
	 * ���ݿ������û���
	 */
	private final String userName = Content.USER;

	/**
	 * ���ݿ���������
	 */
	private final String userPwd = Content.PWD;

	/**
	 * ���ݿ�����ʵ��
	 */
	private Connection con = null;

	/**
	 * �������ݿ�,����ǲ��Ǵ���jdbc����ذ�.
	 */
	public DataBase() {
		try {
			Class.forName(dbDriver).newInstance();
		} catch (Exception ex) {
			System.out.print("���ݿ����ʧ�ܣ�");
		}
	}

	/**
	 * �������ݿ�.
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
	 * �ر����ݿ�����.
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
	 * ����һ�������е����ݿ�������.
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
	 * ����һ�����Թ����ĵ������е����ݿ�������.
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
	
	//�رմ򿪵�Statement  
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
    //�رմ򿪵�Connection   
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
     * ��ѯ���ؼ���
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
            //����͵��ó�����Ľ����д���ķ���.
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
     * ��ѯһ����¼���ؽ��.
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
     * ��ѯһ����¼���ؽ��.
     * @param sql
     * @param argList
     * @return 
     * ���һ������ ��������һ�������
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
     * ����һ�����,�����ڲ������,����ִ��ɾ��,���²���.
     * @param sql sql���
     * @param argList ����
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
     * ����һ�����,�����ڲ������,����ִ��ɾ��,���²���.
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
     * ���˸�����handler
     * ���ڻ�ȡһ�����ݼ�¼��SQL��䲻�ô�����ʱ��
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
     * ����ع�.
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
	 * ִ��sql���,�����ؽ����.
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
