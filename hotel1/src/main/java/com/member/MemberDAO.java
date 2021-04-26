package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.client.ClientDTO;
import com.util.DBConn;


public class MemberDAO {
	private Connection conn = DBConn.getConnection();
	
	public int insertMember(MemberDTO mto, ClientDTO cto)throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = " INSERT INTO member1(userId, userPwd, createdDate, modifyDate ) "
					+ " VALUES( ?, ?, SYSDATE, SYSDATE) ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mto.getUserId());
			pstmt.setString(2, mto.getUserPwd());
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			
			sql = " INSERT INTO member2(userId, birth, zip, addr1, addr2) "
					+ " VALUES( ?, TO_DATE(?,'YYYYMMDD') , ?, ?,  ? )  ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mto.getUserId());
			pstmt.setString(2, mto.getBirth());
			pstmt.setString(4, mto.getZip());
			pstmt.setString(5, mto.getAddr1());
			pstmt.setString(6, mto.getAddr2());
			
			result += pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			
			sql= " INSERT INTO client (clientNum, firstName, lastName, email, tel)"
					+ " VALUES(client_seq.NEXTVAL,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cto.getFirstName());
			pstmt.setString(2, cto.getLastName());
			pstmt.setString(3, cto.getEmail());
			pstmt.setString(3, mto.getTel());
			
			result += pstmt.executeUpdate();
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return result;
	}
	
	public MemberDTO readMember(String userId) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT m1.clientNum, m1.userId, userPwd, ");
			sb.append(" firstName, lastName, ");
			sb.append(" createdDate, modifyDate, ");
			sb.append(" TO_CHAR(birth, 'YYYY-MM-DD')birth, ");
			sb.append(" email, tel, ");
			sb.append(" zip, addr1, addr2 ");
			sb.append(" FROM member1 m1 ");
			sb.append(" LEFT OUTER JOIN member2 m2 ON m1.userId = m2.userId ");
			sb.append(" LEFT OUTER JOIN client c ON m1.clientNum = c.clientNum ");
			sb.append(" WHERE m1.userId = ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setClientNum(rs.getInt("clientNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setFirstName(rs.getString("firstName"));
				dto.setLastName(rs.getString("lastName"));
				dto.setCreatedDate(rs.getString("createdDate"));
				dto.setModifyDate(rs.getString("modifyDate"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				if(dto.getEmail()!=null) {
					String[] ss=dto.getEmail().split("@");
					if(ss.length==2) {
						dto.setEmail1(ss[0]);
						dto.setEmail2(ss[1]);
					}
				}
				dto.setTel(rs.getString("tel"));
				if(dto.getTel()!=null) {
					String[] ss = dto.getTel().split("-");
					if(ss.length==3) {
						dto.setTel1(ss[0]);
						dto.setTel2(ss[1]);
						dto.setTel3(ss[2]);						
					}
				}
				dto.setZip(rs.getString("zip"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return dto;
	}
	
	public int updateMember(MemberDTO dto) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE member1 SET userPwd = ?, modifyDate = SYSDATE WHERE userId = ?  ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUserPwd());
			pstmt.setString(2, dto.getUserId());
			
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			
			sql = "UPDATE member2 SET birth=TO_DATE(?,'YYYYMMDD'), zip = ?, "
					+ " addr1 = ?, addr2 = ? WHERE userId = ?  ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBirth());
			pstmt.setString(2, dto.getTel());
			pstmt.setString(3, dto.getZip());
			pstmt.setString(4, dto.getAddr1());
			pstmt.setString(5, dto.getAddr2());
			pstmt.setString(6, dto.getUserId());
			
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			
			sql = "UPDATE client SET firstName = ?, lastName = ?, email = ?, region = ?, tel = ? "
					+ " WHERE clientNum = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getFirstName());
			pstmt.setString(2, dto.getLastName());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(5, dto.getTel());
			pstmt.setInt(6, dto.getClientNum());
			
			result += pstmt.executeUpdate();
						
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
	
	//예약을 한 고객이라도 client 테이블에 예약정보가 담겨 있기 때문에 회원탈퇴를 막지 않는다.
	public int deleteMember(String userId) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		       
		try {
			
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
