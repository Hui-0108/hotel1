package com.pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class PackDAO {
	private Connection conn = DBConn.getConnection();
	
	public int insertPack(PackDTO dto) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql ="INSERT ALL INTO pack (pkgNum, pkgName, content, startDate, endDate, summary, imageFilename) "
					+ "VALUES (pack_seq.NEXTVAL, ?, ?, ?, ?, ?, ?) "
					+ "INTO packPrice (pkgNum, deluxe, bDeluxe, gcDeluxe, ebDeluxe, egDeluxe, sSuite) "
					+ "VALUES (pack_seq,CURRVAL, ?, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getPkgName());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getStartDate());
			pstmt.setString(4, dto.getEndDate());
			pstmt.setString(5, dto.getsummary());
			pstmt.setString(6, dto.getImageFilename());
			pstmt.setInt(7, dto.getDeluxe());
			pstmt.setInt(8, dto.getbDeluxe());
			pstmt.setInt(9, dto.getGcDeluxe());
			pstmt.setInt(10, dto.getEbDeluxe());
			pstmt.setInt(11, dto.getEgDeluxe());
			pstmt.setInt(12, dto.getsSuite());
			
			result = pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (Exception e) {
			try {
				conn.commit();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
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
			sql = "SELECT NVL(COUNT(*), 0) FROM pack";
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
	
	public List<PackDTO> listPack(int offset, int rows){
		List<PackDTO> list = new ArrayList<PackDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT pkgNum, pkgName, content, startDate, endDate, summary, imageFilename "
					+ "FROM pack "
					+ "ORDER BY pkgNum DESC "
					+ "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				PackDTO dto = new PackDTO();
				dto.setPkgNum(rs.getInt("pkgNum"));
				dto.setPkgName(rs.getString("pkgName"));
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
	
	public PackDTO readPack(int pkgNum) {
		PackDTO dto = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT pkgNum, pkgName, content, startDate, endDate, summary, imageFilename, "
					+ "pp.deluxe, pp.bDeluxe, pp.gcDeluxe, pp.ebDeluxe, pp.egDeluxe, pp.sSuite "
					+ "FROM pack p "
					+ "JOIN packPrice pp ON p.pkgNum = pp.pkgNum "
					+ "WHERE pkgNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, pkgNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new PackDTO();
				dto.setPkgNum(rs.getInt("pkgNum"));
				dto.setPkgName(rs.getString("pkgName"));
				dto.setContent(rs.getString("content"));
				dto.setStartDate(rs.getString("startDate"));
				dto.setEndDate(rs.getString("endDate"));
				dto.setsummary(rs.getString("summary"));
				dto.setImageFilename(rs.getString("imageFilename"));
				dto.setDeluxe(rs.getInt("deluxe"));
				dto.setbDeluxe(rs.getInt("bDeluxe"));
				dto.setGcDeluxe(rs.getInt("gcDeluxe"));
				dto.setEbDeluxe(rs.getInt("ebDeluxe"));
				dto.setEgDeluxe(rs.getInt("egDeluxe"));
				dto.setsSuite(rs.getInt("sSuite"));
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
	
	public int updatePack(PackDTO dto) throws SQLException{
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="UPDATE pack SET pkgName=?, content=?, startDate=?, endDate=?, summary=?, imageFilename=? "
					+ "WHERE pkgNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPkgName());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getStartDate());
			pstmt.setString(4, dto.getEndDate());
			pstmt.setString(5, dto.getsummary());
			pstmt.setString(6, dto.getImageFilename());
			pstmt.setInt(7, dto.getPkgNum());
			
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
	
	public int updatePackPrice(PackDTO dto) throws SQLException{
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="UPDATE packPrice SET deluxe=?, bDeluxe=?, gcDeluxe=?, ebDeluxe=?, egDeluxe=?, sSuite=? "
					+ "WHERE pkgNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getDeluxe());
			pstmt.setInt(2, dto.getbDeluxe());
			pstmt.setInt(3, dto.getGcDeluxe());
			pstmt.setInt(4, dto.getEbDeluxe());
			pstmt.setInt(5, dto.getEgDeluxe());
			pstmt.setInt(6, dto.getsSuite());
			
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
	
	public int deletePack(int pkgNum) throws SQLException{
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql ="DELETE FROM pack WHERE pkgNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, pkgNum);
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
