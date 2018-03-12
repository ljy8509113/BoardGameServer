package com.boardgame.dao;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.boardgame.common.ResCode;
import com.boardgame.util.CustomException;
import com.boardgame.util.DBUtil;
import com.database.model.User;
import com.security.Security;

public class UserDao {

	public void insert(User user) throws CustomException, ClassNotFoundException, SQLException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Connection conn = DBUtil.getInstance().getConnection();
		
		String sql = "select count(*) from user where email = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getEmail());
		
		int count = 0;
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
		
		if(count > 0)
			throw new CustomException(ResCode.ERROR_EMAIL_OVERLAP.getResCode(), ResCode.ERROR_EMAIL_OVERLAP.getMessage());
		
		sql = "select count(*) from user where nickname = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getNickname());
		
		rs = pstmt.executeQuery();
		count = 0;
		if(rs.next()) {
			count = rs.getInt(1);
		}
		
		if(count > 0)
			throw new CustomException(ResCode.ERROR_NICKNAME_OVERLAP.getResCode(), ResCode.ERROR_NICKNAME_OVERLAP.getMessage());
		
		String password = Security.Instance().cryptionDB(user.getPassword());
		sql = "INSERT INTO user (email, password, nickname, birthday, join_date, fail_count) " + 
				"VALUES (?,?,?,?,CURDATE(),0)";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getEmail());
		pstmt.setString(2, password);
		pstmt.setString(3, user.getNickname());
		pstmt.setDate(4, (Date)user.getBirthday());
		
		pstmt.executeUpdate();
		
		DBUtil.getInstance().close(rs, pstmt, conn);
		
	}
	
	public User select(String email, String password) throws CustomException, SQLException, ClassNotFoundException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Connection conn = DBUtil.getInstance().getConnection();
		
		String sql = "select * from user where email=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, email);
		
		ResultSet rs = pstmt.executeQuery();
		User user = null;
		while(rs.next()) {
			user = new User();
			user.setUserNo(rs.getInt("user_no"));
			user.setEmail(rs.getString("email"));
			user.setBirthday(rs.getDate("birthday"));
			user.setJoinDate(rs.getDate("join_date"));
			user.setFailCount(rs.getInt("fail_count"));
			user.setNickname(rs.getString("nickname"));
			user.setPassword(rs.getString("password"));
		}
		
		if(user == null) {
			DBUtil.getInstance().close(rs, pstmt, conn);
			throw new CustomException(ResCode.ERROR_EMAIL_NOT_FOUND.getResCode(), ResCode.ERROR_EMAIL_NOT_FOUND.getMessage());
		}else if(user.getFailCount() >= 10) {
			DBUtil.getInstance().close(rs, pstmt, conn);
			throw new CustomException(ResCode.ERROR_PASSWORD_FAIL_FULL.getResCode(), ResCode.ERROR_PASSWORD_FAIL_FULL.getMessage());
		}else {
			if(password.equals(Security.Instance().deCryptionDB(user.getPassword()))) {
				sql = "update user set fail_count=0 where email=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user.getEmail());
				pstmt.executeUpdate();
				
				DBUtil.getInstance().close(rs, pstmt, conn);
				return user;
			}else {
				sql = "update user set fail_count=? where email=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, user.getFailCount()+1);
				pstmt.setString(2, user.getEmail());
				pstmt.executeUpdate();
				DBUtil.getInstance().close(rs, pstmt, conn);
				
				throw new CustomException(ResCode.ERROR_PASSWORD_MISS.getResCode(), ResCode.ERROR_PASSWORD_MISS.getMessage());
			}
		}
			
	}	
	
}
