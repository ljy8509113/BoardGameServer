package com.boardgame.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.boardgame.dao.UserDao;
import com.boardgame.util.CustomException;
import com.database.model.User;

public class UserService {
	private UserDao dao;
	
	public UserService() {
		dao = new UserDao();
	}
	
	public void join(User user) throws ClassNotFoundException, CustomException, SQLException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		dao.insert(user);
	}
	
	public void login(String email, String password) throws InvalidKeyException, ClassNotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, CustomException, SQLException {
		dao.select(email, password);
	}
}
