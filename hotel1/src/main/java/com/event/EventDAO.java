package com.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class EventDAO {
 
	private Connection conn = DBConn.getConnection();
	
	public int insertEvent(EventDTO dto) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="INSERT INTO event (eventNum, eventName, content, startDate, endDate, summary, imageFilename) "
					+ "VALUES (event_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getEventName());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getStartDate());
			pstmt.setString(4, dto.getEndDate());
			pstmt.setString(5, dto.getsummary());
			pstmt.setString(6, dto.getImageFilename());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM event";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	public List<EventDTO> listEvent(int offset, int rows){
		List<EventDTO> list = new ArrayList<EventDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT eventNum, eventName, content, TO_CHAR(startDate, 'YYYY-MM-DD') startDate, TO_CHAR(endDate, 'YYYY-MM-DD') endDate, summary, imageFilename "
					+ "FROM event "
					+ "ORDER BY eventNum DESC "
					+ "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				EventDTO dto = new EventDTO();
				dto.setEventNum(rs.getInt("eventNum"));
				dto.setEventName(rs.getString("eventName"));
				dto.setContent(rs.getString("content"));
				dto.setStartDate(rs.getString("startDate"));
				dto.setEndDate(rs.getString("endDate"));
				dto.setsummary(rs.getString("summary"));
				dto.setImageFilename(rs.getString("imageFilename"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return list;
	}
	
	public EventDTO readEvent(int eventNum) {
		EventDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT eventNum, eventName, content, TO_CHAR(startDate, 'YYYY-MM-DD') startDate, TO_CHAR(endDate, 'YYYY-MM-DD') endDate, summary, imageFilename "
					+ "FROM event "
					+ "WHERE eventNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, eventNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new EventDTO();
				dto.setEventNum(rs.getInt("eventNum"));
				dto.setEventName(rs.getString("eventName"));
				dto.setContent(rs.getString("content"));
				dto.setStartDate(rs.getString("startDate"));
				dto.setEndDate(rs.getString("endDate"));
				dto.setsummary(rs.getString("summary"));
				dto.setImageFilename(rs.getString("imageFilename"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return dto;
	}
	
	public int updateEvent(EventDTO dto) throws SQLException{
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="UPDATE event SET eventName=?, content=?, startDate=?, endDate=?, summary=?, imageFilename=? "
					+ "WHERE eventNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getEventName());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getStartDate());
			pstmt.setString(4, dto.getEndDate());
			pstmt.setString(5, dto.getsummary());
			pstmt.setString(6, dto.getImageFilename());
			pstmt.setInt(7, dto.getEventNum());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	public int deleteEvent(int eventNum) throws SQLException{
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql ="DELETE FROM event WHERE eventNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, eventNum);
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
}
