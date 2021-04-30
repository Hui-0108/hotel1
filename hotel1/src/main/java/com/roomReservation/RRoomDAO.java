package com.roomReservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.util.DBConn;

public class RRoomDAO {
	private Connection conn = DBConn.getConnection();
	
	/**
	 * 룸예약완료시 reservationOfRoom에 RRoomDTO를 삽입
	 * 룸예약번호는 알파벳 r과 시퀀스번호와 시스템날짜로 조합
	 * rorNum = ror_seq.NEXTVAL+'YYYYMMDD'
	 * @param dto		룸예약 DTO
	 * @return rorNum	룸예약번호
	 * @throws SQLException
	 */
	public String insertRoomReservation(RRoomDTO dto) throws SQLException {
		String rorNum = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "SELECT ror_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) rorNum = rs.getString(1);
			rs.close();
			pstmt.close();
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String now = sdf.format(cal.getTime());
			rorNum= "r"+rorNum+now;
			
			sql = "INSERT INTO reservationOfRoom(rorNum, roomNum, clientNum, checkIn, "
					+ " checkOut, guestCount, price) VALUES(?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rorNum);
			pstmt.setInt(2, dto.getRoomNum());
			pstmt.setInt(3, dto.getClientNum());
			pstmt.setString(4, dto.getCheckIn());
			pstmt.setString(5, dto.getCheckOut());
			pstmt.setInt(6, dto.getGuestCount());
			pstmt.setInt(7, dto.getPrice());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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
		return rorNum;
	}
	
	/**
	 * 비회원이 예약정보 확인시 룸예약 테이블에서 룸예약번호로 검색하여 룸예약객체에 담아서 반환
	 * @param rorNum	룸예약번호
	 * @return
	 */
	public RRoomDTO readRoomReservation(String rorNum) {
		RRoomDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "SELECT rorNum, ror.roomNum, r.classNum, clientNum, TO_CHAR(checkIn,'YYYY-MM-DD') checkIn, "
					+ " TO_CHAR(checkOut, 'YYYY-MM-DD') checkOut, guestCount, ror.price, className "
					+ " FROM reservationOfRoom ror JOIN room r ON r.roomNum=ror.roomNum "
					+ " JOIN roomClass c ON r.classNum=c.classNum WHERE rorNum=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rorNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new RRoomDTO();
				dto.setRorNum(rs.getString("rorNum"));
				dto.setRoomNum(rs.getInt("roomNum"));
				dto.setClassNum(rs.getInt("clientNum"));
				dto.setCheckIn(rs.getString("checkIn"));
				dto.setCheckOut(rs.getString("checkOut"));
				dto.setGuestCount(rs.getInt("guestCount"));
				dto.setPrice(rs.getInt("price"));
				dto.setClassName(rs.getString("className"));
			}
		} catch (SQLException e) {
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
		
		return dto;
	}
	
	/**
	 * 예약확인시 clientNum에 해당하는 룸예약객체를 list에 담아 반환
	 * @param clientNum		고객번호
	 * @return
	 */
	public List<RRoomDTO> listRoomReservation(int clientNum) {
		List<RRoomDTO> list = new ArrayList<RRoomDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT rorNum, ror.roomNum, classNum, clientNum, checkIn, checkOut, guestCount, ror.price "
					+ " FROM reservationOfRoom ror JOIN room ON r.roomNum=ror.roomNum WHERE clientNum=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clientNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RRoomDTO dto = new RRoomDTO();
				dto.setRorNum(rs.getString("rorNum"));
				dto.setRoomNum(rs.getInt("roomNum"));
				dto.setClassNum(rs.getInt("clientNum"));
				dto.setCheckIn(rs.getString("checkIn"));
				dto.setCheckOut(rs.getString("checkOut"));
				dto.setGuestCount(rs.getInt("guestCount"));
				dto.setPrice(rs.getInt("price"));
				list.add(dto);
			}
			
		} catch (SQLException e) {
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
				
		return list;
	}
	
	/**
	 * 회원의 룸예약 내역을 리스트로 반환
	 * @param userId	회원아이디
	 * @return
	 */
	public List<RRoomDTO> listByUserId(String userId) {
		List<RRoomDTO> list = new ArrayList<RRoomDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		RRoomDTO dto = null;
		try {
			sql = "SELECT rorNum, ror.roomNum, TO_CHAR(checkIn, 'YYYY-MM-DD') checkIn, "
					+ " TO_CHAR(checkOut, 'YYYY-MM-DD') checkOut, guestCount, ror.price, className "
					+ " FROM client c JOIN member1 m ON m.clientNum=c.clientNum "
					+ " JOIN reservationOfRoom ror ON ror.clientNum=m.clientNum "
					+ " JOIN room r ON ror.roomNum=r.roomNum "
					+ " JOIN roomClass rc ON r.classNum=rc.classNum "
					+ " WHERE userId=? ORDER BY checkIn";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dto = new RRoomDTO();
				dto.setRorNum(rs.getString("rorNum"));
				dto.setRoomNum(rs.getInt("roomNum"));
				dto.setCheckIn(rs.getString("checkIn"));
				dto.setCheckOut(rs.getString("checkOut"));
				dto.setGuestCount(rs.getInt("guestCount"));
				dto.setPrice(rs.getInt("price"));
				dto.setClassName(rs.getString("className"));
				list.add(dto);
			}
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
		return list;
	}
	
	/**
	 * 예약취소 또는 룸이용완료 등의 사유로 룸예약 테이블에서 룸예약번호로 검색하여 해당 데이터 삭제
	 * @param rorNum	룸예약번호
	 * @return
	 * @throws SQLException
	 */
	public int deleteRoomReservation(int rorNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "DELETE FROM reservationOfRoom WHERE rorNum=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rorNum);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
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
