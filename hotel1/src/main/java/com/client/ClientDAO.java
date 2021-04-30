package com.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class ClientDAO {
	private Connection conn = DBConn.getConnection();
	
	// 비회원 고객 추가
	public int insertClient(ClientDTO dto)throws SQLException{
		int clientNum = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql="SELECT client_seq.NEXTVAL FROM dual";
			pstmt =conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) clientNum = rs.getInt(1);
			rs.close();
			pstmt.close();
			
			sql = "INSERT INTO client(clientNum, firstName, lastName, email, region, tel, "
					+ " creditCorp, creditNum, creditYear, creditMonth) "
					+ " VALUES(?, ?, ?, ?, ? ,? , ? ,? ,?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clientNum);
			pstmt.setString(2, dto.getFirstName());
			pstmt.setString(3, dto.getLastName());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getRegion());
			pstmt.setString(6, dto.getTel());
			pstmt.setString(7, dto.getCreditCorp());
			pstmt.setString(8, dto.getCreditNum());
			pstmt.setString(9, dto.getCreditYear());
			pstmt.setString(10, dto.getCreditMonth());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
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
		return clientNum;
	}
	
	// 회원 고객의 신용정보 추가
	public int updateExtraMember(ClientDTO dto) throws SQLException{
		int result =0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = " UPDATE client SET region = ?, creditCorp = ?, creditNum = ?, creditYear = ?, creditMonth = ? "
					+ " WHERE clientNum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getRegion());
			pstmt.setString(2, dto.getCreditCorp());
			pstmt.setString(3, dto.getCreditNum());
			pstmt.setString(4, dto.getCreditYear());
			pstmt.setString(5, dto.getCreditMonth());
			pstmt.setInt(6, dto.getClientNum());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	// 예약취소로 인한 클라이언트 정보 삭제
	public int cancelClient(int clientNum) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM client WHERE clientNum = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clientNum);
			
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	// 예약취소로 인한 멤버 신용정보 삭제(null로 업데이트)
	public int cancelMember(int clientNum) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE client SET region = null, creditCorp = null, creditNum = null, creditYear =null, creditMonth = null WHERE clientNum  = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clientNum);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
