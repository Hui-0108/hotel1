package com.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class RoomDAO {
	private Connection conn = DBConn.getConnection();
	
	public RoomDTO readRoom(int roomNum) {
		RoomDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT c.classNum, className, roomSize, roomNum, price "
					+ " FROM roomClass c JOIN room r ON c.classNum=r.classNum WHERE roomNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new RoomDTO();
				dto.setClassNum(rs.getInt("classNum"));
				dto.setClassName(rs.getString("className"));
				dto.setRoomSize(rs.getInt("roomSize"));
				dto.setRoomNum(rs.getInt("roomNum"));
				dto.setPrice(rs.getInt("price"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return dto;
	}
	
	public int getEmptyRoomNum(int classNum, String checkIn, String checkOut) {
		int roomNum = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<Integer> list = new ArrayList<>();

		try {
			sql = "SELECT roomNum FROM room WHERE classNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt("roomNum"));
			}
			pstmt.close();
			rs.close();
			
			sql = "SELECT r.roomNum FROM reservationOfRoom ror JOIN room r ON r.roomNum=ror.roomNum "
					+ " WHERE classNum = ? AND (( TO_DATE(checkIn) >= TO_DATE(?) AND TO_DATE(checkOut) < TO_DATE(?) ) "
					+ "   OR ( TO_DATE(checkIn) <= TO_DATE(?) AND TO_DATE(checkOut) >= TO_DATE(?) ) "
					+ "   OR ( TO_DATE(checkOut) > TO_DATE(?) AND TO_DATE(checkOut) < TO_DATE(?) ) "
					+ "   OR ( TO_DATE(checkIn) >= TO_DATE(?) AND TO_DATE(checkIn) < TO_DATE(?) )) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classNum);
			pstmt.setString(2, checkIn);
			pstmt.setString(3, checkOut);
			pstmt.setString(4, checkIn);
			pstmt.setString(5, checkOut);
			pstmt.setString(6, checkIn);
			pstmt.setString(7, checkOut);
			pstmt.setString(8, checkIn);
			pstmt.setString(9, checkOut);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.remove((Integer)rs.getInt("roomNum"));
			}
			
			if(list.size()>0) {
				roomNum = list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return roomNum;
	}
	
}
