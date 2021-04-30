package com.diningReservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.util.DBConn;

public class RDiningDAO {
	private Connection conn = DBConn.getConnection();
	
	/**
	 * 예약완료시 다이닝예약테이블에 다이닝예약객체 삽입 후 예약번호 반환
	 * 다이닝예약번호는 알파벳 d와 시퀀스번호와 시스템날짜 조합
	 * rodNum = rod_seq.NEXTVAL + 'YYYYMMDD'
	 * @param dto		다이닝예약객체
	 * @return
	 * @throws SQLException
	 */
	public String insertDiningReservation(RDiningDTO dto) throws SQLException {
		String rodNum = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT rod_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) rodNum=rs.getString(1);
			rs.close();
			pstmt.close();
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String now = sdf.format(cal.getTime());
			rodNum= "d"+rodNum+now;
			
			sql = "INSERT INTO reservationOfDin(rodNum, dinNum, clientNum, guestCount, rsvDate, rsvTime, seatType) "
					+ " VALUES(?, ?, ?, ?, ?, ?, ?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rodNum);
			pstmt.setInt(2, dto.getDinNum());
			pstmt.setInt(3, dto.getClientNum());
			pstmt.setInt(4, dto.getGuestCount());
			pstmt.setString(5, dto.getRsvDate());
			pstmt.setString(6, dto.getRsvTime());
			pstmt.setInt(7, dto.getSeatType());
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
		
		return rodNum;
	}
	
	/**
	 * 비회원이 예약정보 확인시 다이닝예약 테이블에서 다이닝예약번호로 검색하여 다이닝예약객체에 담아서 반환
	 * @param rodNum	다이닝예약번호
	 * @return
	 */
	public RDiningDTO readDiningReservation(String rodNum) {
		RDiningDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "SELECT rodNum, ror.dinNum, clientNum, guestCount, TO_CHAR(rsvDate, 'YYYY-MM-DD') rsvDate, "
					+ " rsvTime, seatType, dinName FROM reservationOfDin ror "
					+ " JOIN dining d ON ror.dinNum=d.dinNum WHERE rodNum=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rodNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new RDiningDTO();
				dto.setRodNum(rs.getString("rodNum"));
				dto.setDinNum(rs.getInt("dinNum"));
				dto.setClientNum(rs.getInt("clientNum"));
				dto.setGuestCount(rs.getInt("guestCount"));
				dto.setRsvDate(rs.getString("rsvDate"));
				dto.setRsvTime(rs.getString("rsvTime"));
				dto.setSeatType(rs.getInt("seatType"));
				dto.setDinName(rs.getString("dinName"));
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
	 * 예약확인시 clientNum에 해당하는 다이닝예약객체를 list에 담아 반환
	 * @param clientNum		고객번호
	 * @return
	 */
	public List<RDiningDTO> listByUserId(String userId) {
		List<RDiningDTO> list = new ArrayList<RDiningDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "SELECT rodNum, dinName, guestCount, TO_CHAR(rsvDate, 'YYYY-MM-DD') rsvDate, "
					+ " rsvTime, seatType FROM client c "
					+ " JOIN member1 m ON m.clientNum=c.clientNum "
					+ " JOIN reservationOfDin rod ON rod.clientNum=m.clientNum "
					+ " JOIN dining d ON rod.dinNum=d.dinNum "
					+ " WHERE userId=? ORDER BY rsvDate";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RDiningDTO dto = new RDiningDTO();
				dto.setRodNum(rs.getString("rodNum"));
				dto.setDinName(rs.getString("dinName"));
				dto.setGuestCount(rs.getInt("guestCount"));
				dto.setRsvDate(rs.getString("rsvDate"));
				dto.setRsvTime(rs.getString("rsvTime"));
				dto.setSeatType(rs.getInt("seatType"));
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
	 * 예약취소 또는 다이닝이용완료 등의 사유로 다이닝예약 테이블에서 다이닝예약번호로 검색하여 해당 데이터 삭제
	 * @param rodNum	다이닝예약번호
	 * @return
	 * @throws SQLException
	 */
	public int deleteDiningReservation(String rodNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "DELETE FROM reservationOfDin WHERE rodNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rodNum);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
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
