package com.boardgame.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	// 싱글턴 패턴 만들기
	
	// 1. 객체를 저장할 필드 선언
	private static DBUtil instance;
	
	// 2. 외부에서 객체를 생성하지 못하도록 private 기본 생성자 만들기
	private DBUtil() {}
	
	// 3. 외부에서 DBUtil 객체를 사용하기 위한 메소드 (캡슐화)
	public static DBUtil getInstance() {
		if (instance == null) {
			/*
			 *  최초로 DBUtil을 사용할 경우 객체가 만들어지지 않았기 때문에
			 *  새로운 객체 생성
			 *  이후에는 필드에 저장된 DBUtil 객체를 계속사용
			 *  이 프로그램에서 DBUtil은 오로지 하나의 객체만 존재
			 */
			instance = new DBUtil();
		}
		return instance;
	}
	// End of 싱글턴 패턴 만들기
	
	// 데이터베이스 커넥션 객체 가져오는 메소드
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		
		try{
			
			String path = DBUtil.class.getResource("").getPath(); 
			
            // 프로퍼티 파일 위치
			String propFile = path+"jdbc.properties";
			System.out.println(propFile);
			
            // 프로퍼티 객체 생성
            Properties props = new Properties();
            // 프로퍼티 파일 스트림에 담기
            FileInputStream fis = new FileInputStream(propFile);
            // 프로퍼티 파일 로딩
            props.load(new java.io.BufferedInputStream(fis));
             
            // 항목 읽기
            String url = props.getProperty("url") ;
            String id = props.getProperty("username");
            String password = props.getProperty("password");
            String dbName = props.getProperty("dbname");
            
    		// 1. 드라이버 로드
    		Class.forName("com.mysql.jdbc.Driver");
    		
    		// 2. 데이터베이스 연결
    		Connection conn = DriverManager.getConnection(url+"/"+dbName, id, password);
    		
    		return conn;
             
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
		
	}
	
	// Connection 객체 연결 해제
	public void close(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
	
	// Statement(PreparedStatement 포함) 객체 연결 해제
	public void close(Statement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}
	
	// ResultSet 객체 연결 해제
	public void close(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}
	
	public void close(ResultSet rs, Statement stmt, Connection con) throws SQLException{
		close(rs);
		close(stmt);
		close(con);
	}
	
	// Rollback 메소드
	public void rollback(Connection conn) throws SQLException {
		if (conn != null) {
			conn.rollback();
		}
	}
	
	
}





