package com.diningReservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class RDiningDAO {
	private Connection conn = DBConn.getConnection();
	
	/**
	 * 예약완료시 다이닝예약테이블에 다이닝예약객체 삽입
	 * 다이닝예약번호는 시퀀스번호와 시스템날짜 조합
	 * rodNum = rod_seq.NEXTVAL + 'YYYYMMDD'
	 * @param dto		다이닝예약객체
	 * @return
	 * @throws SQLException
	 */
	public int insertDiningReservation(RDiningDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO reservationOfDin(rodNum, dinNum, clientNum, guestCount, date, tableType) "
					+ " VALUES(CONCAT(rod_seq.NEXTVAL,TO_CHAR(SYSDATE, 'YYYYMMDD'), ?, ?, ?, ?, ?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getDinNum());
			pstmt.setInt(2, dto.getClientNum());
			pstmt.setInt(3, dto.getGuestCount());
			pstmt.setString(4, dto.getDate());
			pstmt.setInt(5, dto.getTableType());
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
	
	/**
	 * 비회원이 예약정보 확인시 다이닝예약 테이블에서 다이닝예약번호로 검색하여 다이닝예약객체에 담아서 반환
	 * @param rodNum	다이닝예약번호
	 * @return
	 */
	public RDiningDTO readDiningReservation(int rodNum) {
		RDiningDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "SELECT rodNum, dinNum, clientNum, guestCount, TO_CHAR(date, 'YYYY-MM-DD') date, "
					+ " TO_CHAR(date, 'HH24:MI') time, tableType FROM reservationOfDin WHERE rodNum=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rodNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new RDiningDTO();
				dto.setRodNum(rs.getInt("rodNum"));
				dto.setDinNum(rs.getInt("dinNum"));
				dto.setClientNum(rs.getInt("clientNum"));
				dto.setGuestCount(rs.getInt("guestCount"));
				dto.setDate(rs.getString("date"));
				dto.setTime(rs.getString("time"));
				dto.setTableType(rs.getInt("tableType"));
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
	public List<RDiningDTO> listDiningReservation(int clientNum) {
		List<RDiningDTO> list = new ArrayList<RDiningDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "SELECT rodNum, dinNum, clientNum, guestCount, TO_CHAR(date, 'YYYY-MM-DD') date, "
					+ " TO_CHAR(date, 'HH24:MI') time, tableType FROM reservationOfDin WHERE clientNum=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clientNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RDiningDTO dto = new RDiningDTO();
				dto.setRodNum(rs.getInt("rodNum"));
				dto.setDinNum(rs.getInt("dinNum"));
				dto.setClientNum(rs.getInt("clientNum"));
				dto.setGuestCount(rs.getInt("clientNum"));
				dto.setDate(rs.getString("date"));
				dto.setTime(rs.getString("time"));
				dto.setTableType(rs.getInt("tableType"));
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
	public int deleteDiningReservation(int rodNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "DELETE FROM reservationOfDin WHERE rodNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rodNum);
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
