package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class QnaDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertQnA(QnaDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO qna(qNum, ctg, nickname, subject, content, qPwd, email, created"
					+ " , saveFilename, originalFilename, filesize)"
					+ " VALUES (?,?,?,?,?,?,?,SYSDATE,?,?,?)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getqNum());
			pstmt.setString(2, dto.getCtg());
			pstmt.setString(3, dto.getNickname());
			pstmt.setString(4, dto.getNickname());
			pstmt.setString(5, dto.getSubject());
			pstmt.setString(6, dto.getqPwd());
			pstmt.setString(7, dto.getEmail());
			
			pstmt.setString(8, dto.getSaveFilename());
			pstmt.setString(9, dto.getOriginalFilename());
			pstmt.setLong(10, dto.getFileSize());
			
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		} 
		
		return result;
	}
	
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM qna";
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);
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
	
	public int dataCount(String condition, String keyword) {	// 검색!!! 여기 오류 난다!!!
		int result=0;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String sql;
        
        try {
        	sql = "SELECT NVL(COUNT(*), 0) FROM qna";
        	
        	/////////////////////////여기부터 손봐야 한다!!!!
        	if(condition.equals("created")) {
        		keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
        		sql+="  WHERE TO_CHAR(created, 'YYYYMMDD') = ?  ";
        	} else if(condition.equals("all")) {
        		sql+="  WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ";
        	} else {
        		sql+="  WHERE INSTR(" + condition + ", ?) >= 1 ";
        	}
        	
        	 pstmt=conn.prepareStatement(sql);
             pstmt.setString(1, keyword);
             if(condition.equals("all")) {
                 pstmt.setString(2, keyword);
             }

             rs=pstmt.executeQuery();

             if(rs.next())
                 result=rs.getInt(1);
        	
        	
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
	
	public List<QnaDTO> listQnA(int offset, int rows) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT qNum, ctg, nickname, subject, content, qPwd, email, "
					+ " TO_CHAR(created, 'YYYY-MM-DD') created, "
					+ " saveFilename "
					+ " FROM qna"
					+ " ORDER BY groupNum DESC, orderNo ASC "
					+ " OFFSET ? ROWS FETCH FIRST? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);

			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setqNum(rs.getInt("qNum"));
				dto.setCtg(rs.getString("ctg"));
				dto.setNickname(rs.getString("nickname"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setqPwd(rs.getString("qPwd"));
				dto.setEmail(rs.getString("email"));
				dto.setCreated(rs.getString("created"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				
				list.add(dto);
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
		
		return list;
	}
	
	public List<QnaDTO> listQnA(int offset, int rows, String condition, String keyword) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT qNum, ctg, nickname, subject, content, qPwd, email, "
					+ " TO_CHAR(created, 'YYYY-MM-DD') created,"
					+ " saveFilename "
					+ " FROM qna";
			//////////////////////////////////////////////////////여기!! 검색하는 데!!!!
			if(condition.equals("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " WHERE TO_CHAR(created, 'YYYYMMDD') = ?  ";
			} else if(condition.equals("all")) {
				sql += " WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ";
			} else {
				sql += " WHERE INSTR(" + condition + ", ?) >= 1  ";
			}
			
			sql += " ORDER BY groupNum DESC, orderNo ASC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt=conn.prepareStatement(sql);
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
            
            rs=pstmt.executeQuery();
			
            while (rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setqNum(rs.getInt("qNum"));
				dto.setCtg(rs.getString("ctg"));
				dto.setNickname(rs.getString("nickname"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setqPwd(rs.getString("qPwd"));
				dto.setEmail(rs.getString("email"));
				
				dto.setCreated(rs.getString("created"));
				
				dto.setSaveFilename(rs.getString("saveFilename"));
				
				list.add(dto);
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
        
        return list;
	}
	
	public QnaDTO readQnA(int qNum) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT qNum, ctg, nickname, subject, content, qPwd, email, created, "
					+ " saveFilename,originalFilename, filesize "
					+ " FROM qna"
					+ " WHERE qNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new QnaDTO();
				dto.setqNum(rs.getInt("qNum"));
				dto.setCtg(rs.getString("ctg"));
				dto.setNickname(rs.getString("nickname"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setqPwd(rs.getString("qNum"));
				dto.setEmail(rs.getString("email"));
				dto.setCreated(rs.getString("created"));
				
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setOriginalFilename(rs.getString("originalFilename"));
				dto.setFileSize(rs.getLong("filesize"));
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
		
		return dto;
	}
	
	public int updateQnA(QnaDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql="UPDATE qna SET ctg=?, subject=?, content=?, email=?, saveFilename=?, originalFilename=?, filesize=? "
				+ " WHERE qNum=? "; 	////// 여기
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCtg());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getSaveFilename());
			pstmt.setString(6, dto.getOriginalFilename());
			pstmt.setLong(7, dto.getFileSize());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	public int deleteQnA(int qNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="DELETE FROM qna WHERE qNum IN (SELECT qNum FROM qna START WITH qNum=? CONNECT BY PRIOR qNum = parent)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qNum);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	
}
