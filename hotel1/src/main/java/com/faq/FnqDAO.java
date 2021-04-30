package com.faq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class FnqDAO {
	private Connection conn = DBConn.getConnection();
	
	public int InsertFnq(FnqDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO faq_bbs(faqnum, userId, ctg, subject, content, hitCount, created) "
					+ " VALUES(faq_seq.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE)";
			
			pstmt = conn.prepareStatement(sql);	
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getCtg());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e) {
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
			sql = "SELECT NVL(COUNT(*), 0) FROM faq_bbs";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	public int dataCount(String condition, String keyword) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0)  FROM faq_bbs b JOIN member1 m ON b.userId=m.userId ";
			if(condition.equals("all")) {
				sql+="  WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ";
			} else if(condition.equals("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(created, 'YYYYMMDD') = ? ";
			} else {
				sql += "  WHERE INSTR(" + condition+ ", ?) >= 1 ";
			}
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			if(condition.equals("all")) {
				pstmt.setString(2, keyword);
			}
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
			
		} catch (SQLException e) {
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
		
		return result;
	}
	
	public List<FnqDTO> listBoard(int offset, int rows) {
		List<FnqDTO> list=new ArrayList<FnqDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT faqnum, b.userId, ctg, subject, hitCount,  ");
			sb.append("       TO_CHAR(created, 'YYYY-MM-DD') created ");
			sb.append(" FROM faq_bbs b  ");
			sb.append(" JOIN member1 m ON b.userId = m.userId  ");
			sb.append(" ORDER BY faqnum DESC  ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				FnqDTO dto=new FnqDTO();
				
				dto.setFaqnum(rs.getInt("faqnum"));
				dto.setUserId(rs.getString("userId"));
				dto.setCtg(rs.getString("ctg"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		
		return list;
	}
	
	public List<FnqDTO> listBoard(int offset, int rows, String condition, String keyword) {
		List<FnqDTO> list = new ArrayList<FnqDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT faqnum, b.userId, subject, hitCount, ctg,  ");
			sb.append("       TO_CHAR(created, 'YYYY-MM-DD') created ");
			sb.append(" FROM faq_bbs b  ");
			sb.append(" JOIN member1 m ON b.userId = m.userId  ");
			
			if(condition.equals("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(created, 'YYYYMMDD') = ?");
			} else if(condition.equals("all")) {
				sb.append(" WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ");
			} else {
				sb.append(" WHERE INSTR("+condition+", ?) >= 1 ");
			}
			
			sb.append(" ORDER BY faqnum DESC  ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt=conn.prepareStatement(sb.toString());
            if(condition.equals("all")) {
    			pstmt.setString(1, keyword);
    			pstmt.setString(2, keyword);
    			pstmt.setInt(3, offset);
    			pstmt.setInt(4, rows);
            } else {
    			pstmt.setString(1, keyword);
    			pstmt.setInt(2, offset);
    			pstmt.setInt(3, rows);
            }
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				FnqDTO dto=new FnqDTO();
				
				dto.setFaqnum(rs.getInt("faqnum"));
				dto.setUserId(rs.getString("userId"));
				dto.setCtg(rs.getString("ctg"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		
		return list;
	}
	
	public int updateHitCount(int faqnum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE faq_bbs SET hitCount = hitCount + 1 WHERE faqnum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, faqnum);
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
	
	public FnqDTO readBoard(int faqnum) {
		FnqDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT faqnum, ctg, b.userId, subject, content, hitCount, created "
				+ "  FROM faq_bbs b "
				+ "  JOIN member1 m ON b.userId = m.userId "
				+ "  WHERE faqnum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, faqnum);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto=new FnqDTO();
				dto.setFaqnum(rs.getInt("faqnum"));
				dto.setCtg(rs.getString("ctg"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
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
		
		return dto;
	}
	
	public int updateBoard(FnqDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql = "UPDATE faq_bbs SET subject=?, content=?, ctg=? WHERE faqnum=? AND userId=?";
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getCtg());
			pstmt.setInt(4, dto.getFaqnum());
			pstmt.setString(5, dto.getUserId());
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
	
	public int deleteBoard(int faqnum, String userId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM faq_bbs WHERE faqnum = ?";
			if(! userId.equals("admin")) {
				sql += " AND userId = ?";
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, faqnum);
			if(! userId.equals("admin")) {
				pstmt.setString(2, userId);
			}
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
