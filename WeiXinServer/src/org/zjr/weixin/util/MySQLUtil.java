package org.zjr.weixin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;





public class MySQLUtil {
	public Connection getConnection() {
		
		Connection con=null;
		String driver = "com.mysql.jdbc.Driver";
		String username = "n3mj5mzmoz";
		String password = "l04lyyjm0lzmji5y12k3wii302342xyhy351ixix";

		String dbUrl = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_zjr571";
		try {
		    Class.forName(driver);
		    con = DriverManager.getConnection(dbUrl, username, password);
		} catch (Exception e) { }
		return con;
	}
	
	public void releaseResource(Connection conn,PreparedStatement ps) {
		if (ps!=null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (null!=conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void saveTextMessage(String openId,String content) {
		MySQLUtil mysql=new MySQLUtil();
		Connection conn=mysql.getConnection();
		String sql="insert into message_text(open_id,content,create_time)values(?,?,now())";
		
		PreparedStatement ps=null;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, openId);
			ps.setString(2, content);
			ps.execute();
		} catch (Exception e) {
			
			// TODO: handle exception
		}finally{
			mysql.releaseResource(conn, ps);
		}
		
	}
}
