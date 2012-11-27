package myOwnLibrary.jdbc;


public class Content
{
    /**
     *  数据库连接字符串
     */
    public static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:lsq";
    /**
     * 数据库连接的驱动
     */
    public static final String CLASSNAME = "oracle.jdbc.driver.OracleDriver";

    /**
     * 数据库用户名
     */
    public static final String USER = "lsqtest";

    /**
     * 数据库登录密码
     */
    public static final String PWD = "lsqtest";

    /**
     * 末页显示的行数
     */
    public static int NUM_PER = 1;
}