package com.boardgame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.boardgame.model.GameRoom;
import com.boardgame.util.DBUtil;

public class GameRoomDao {
	
	public List<GameRoom> selectAll(Integer gameNo) throws ClassNotFoundException, SQLException{
		Connection conn = DBUtil.getInstance().getConnection();

		// 2. SQL문 작성 (글 번호 내림차순 정렬, 최신글 우선)
		//		String sql = "SELECT * FROM board ORDER BY no DESC";
		String sql="select * from game_room where game_no = ?";
		
		// 3. PreparedStatement 객체 생성
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, gameNo);
		
		// 4. SQL문 실행
		ResultSet rs = pstmt.executeQuery();

		// 5. ResultSet 객체를 이용하여 게시물 값들을 가져온 뒤 Board 객체에 저장
		List<GameRoom> list = new ArrayList<GameRoom>();
		
		while (rs.next()) {
			GameRoom room = new GameRoom();
			
			room.setGameNo(rs.getInt("game_no"));
			room.setNo(rs.getInt("no"));
			room.setTitle(rs.getString("title"));
			room.setFullUser(rs.getInt("full_user"));
			room.setMasterUuid(rs.getString("master_uuid"));
			room.setState(rs.getString("state"));
			room.setCurrent(0);
			list.add(room);
		}

		// 6. 객체 해제
		DBUtil.getInstance().close(rs, pstmt, conn);
		
		return list;
	}
	
	
	Integer no;
	String title;
	Integer gameNo;
	Integer fullUser;
	String state;
	String masterUuid;
	Integer current;
	
	
	public void insertRoom(GameRoom room) throws SQLException, ClassNotFoundException {
		Connection conn = DBUtil.getInstance().getConnection();

		// 2. SQL문 작성 (글 번호 내림차순 정렬, 최신글 우선)
		//		String sql = "SELECT * FROM board ORDER BY no DESC";
		String sql="insert into game_room(title, game_no, full_user, state, master_uuid, current)"
				+ " values(?,?,?,?,?,?)";
		
		// 3. PreparedStatement 객체 생성
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, room.getTitle());
		pstmt.setInt(2, room.getGameNo());
		pstmt.setInt(3, room.getFullUser());
		pstmt.setString(4, room.getState());
		pstmt.setString(5, room.getMasterUuid());
		
		pstmt.executeUpdate();
		
		// 6. 객체 해제
		DBUtil.getInstance().close(null, pstmt, conn);
	}
}
