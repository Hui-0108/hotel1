package com.pack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/pack/*")
public class PackServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;
	
	private String pathname;
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		HttpSession session = req.getSession();
		
		
		String root=session.getServletContext().getRealPath("/");
		pathname=root+"uploads"+File.separator+"pack";
		
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if(uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		} else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		} else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		MyUtil util = new MyUtil();
		PackDAO dao = new PackDAO();
		
		String page = req.getParameter("page");
		int current_page = 1;
		if(page != null) {
			current_page=Integer.parseInt(page);
		}
		
		int dataCount = dao.dataCount();
		int rows = 10;
		int total_page = util.pageCount(rows, dataCount);
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		int offset = (current_page-1) * rows;
		if(offset < 0) offset = 0;
		
		List<PackDTO> list = dao.listPack(offset, rows);
		
		String listUrl = cp + "/pack/list.do";
		String articleUrl = cp + "/pack/article.do?page="+current_page;
		String paging=util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		
		forward(req, resp, "/WEB-INF/views/pack/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/pack/created.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		PackDAO dao = new PackDAO();
		
		try {
			PackDTO dto = new PackDTO();
					
			dto.setPkgName(req.getParameter("pkgName"));
			dto.setContent(req.getParameter("content"));
			dto.setStartDate(req.getParameter("startDate"));
			dto.setEndDate(req.getParameter("endDate"));
			dto.setsummary(req.getParameter("summary"));
			if(req.getParameter("deluxe")!=null && req.getParameter("deluxe").length()!=0) {
			dto.setDeluxe(Integer.parseInt(req.getParameter("deluxe")));
			}
			if(req.getParameter("bDeluxe")!=null && req.getParameter("bDeluxe").length()!=0) {
			dto.setbDeluxe(Integer.parseInt(req.getParameter("bDeluxe")));
			}
			if(req.getParameter("gcDeluxe")!=null && req.getParameter("gcDeluxe").length()!=0) {
			dto.setGcDeluxe(Integer.parseInt(req.getParameter("gcDeluxe")));
			}
			if(req.getParameter("ebDeluxe")!=null && req.getParameter("ebDeluxe").length()!=0) {
			dto.setEbDeluxe(Integer.parseInt(req.getParameter("ebDeluxe")));
			}
			if(req.getParameter("egDeluxe")!=null && req.getParameter("egDeluxe").length()!=0) {
			dto.setEgDeluxe(Integer.parseInt(req.getParameter("egDeluxe")));
			}
			if(req.getParameter("sSuite")!=null && req.getParameter("sSuite").length()!=0) {
			dto.setsSuite(Integer.parseInt(req.getParameter("sSuite")));
			}
			
			String filename = null;
			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if(map != null) {
				filename = map.get("saveFilename");
			}
			
			if(filename!=null) {
				dto.setImageFilename(filename);
			}
			
			String thumbnail = null;
			p = req.getPart("thumbnail");
			map = doFileUpload(p, pathname);
			if(map != null) {
				thumbnail = map.get("saveFilename");
			}
			
			if(thumbnail!=null) {
				dto.setThumbnail(thumbnail);
				dao.insertPack(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/pack/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		String page=req.getParameter("page");
		PackDAO dao = new PackDAO();
		
		try {
			int pkgNum = Integer.parseInt(req.getParameter("pkgNum"));
			
			PackDTO dto = dao.readPack(pkgNum);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/pack/list.do?page="+page);
				return;
			}
			
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/pack/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/pack/list.do?page="+page);
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String page=req.getParameter("page");
		PackDAO dao = new PackDAO();
		
		try {
			int pkgNum = Integer.parseInt(req.getParameter("pkgNum"));
			PackDTO dto = dao.readPack(pkgNum);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/pack/list.do?page="+page);
				return;
			}
			
			if(!info.getUserId().equals("admin")) {
				resp.sendRedirect(cp+"/pack/list.do?page="+page);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/pack/created.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/pack/list.do?page="+page);
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		PackDAO dao = new PackDAO();
		PackDTO dto = new PackDTO();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/pack/list.do");
			return;
		}
		
		String page=req.getParameter("page");
		
		try {
			dto.setPkgNum(Integer.parseInt(req.getParameter("pkgNum")));
			dto.setPkgName(req.getParameter("pkgName"));
			dto.setContent(req.getParameter("content"));
			dto.setStartDate(req.getParameter("startDate"));
			dto.setEndDate(req.getParameter("endDate"));
			dto.setsummary(req.getParameter("summary"));
			
			String imageFilename=req.getParameter("imageFilename");
			dto.setImageFilename(imageFilename);
			
			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if(map!=null) {
				String filename = map.get("saveFilename");
				FileManager.doFiledelete(pathname, imageFilename);
				dto.setImageFilename(filename);
			}
			
			String thumbnail=req.getParameter("thumbnail");
			dto.setThumbnail(thumbnail);
			
			p = req.getPart("thumbnail");
			map = doFileUpload(p, pathname);
			if(map!=null) {
				String filename = map.get("saveFilename");
				FileManager.doFiledelete(pathname, thumbnail);
				dto.setThumbnail(filename);
			}
			
			if(req.getParameter("deluxe")!=null && req.getParameter("deluxe").length()!=0) {
				dto.setDeluxe(Integer.parseInt(req.getParameter("deluxe")));
				}
				if(req.getParameter("bDeluxe")!=null && req.getParameter("bDeluxe").length()!=0) {
				dto.setbDeluxe(Integer.parseInt(req.getParameter("bDeluxe")));
				}
				if(req.getParameter("gcDeluxe")!=null && req.getParameter("gcDeluxe").length()!=0) {
				dto.setGcDeluxe(Integer.parseInt(req.getParameter("gcDeluxe")));
				}
				if(req.getParameter("ebDeluxe")!=null && req.getParameter("ebDeluxe").length()!=0) {
				dto.setEbDeluxe(Integer.parseInt(req.getParameter("ebDeluxe")));
				}
				if(req.getParameter("egDeluxe")!=null && req.getParameter("egDeluxe").length()!=0) {
				dto.setEgDeluxe(Integer.parseInt(req.getParameter("egDeluxe")));
				}
				if(req.getParameter("sSuite")!=null && req.getParameter("sSuite").length()!=0) {
				dto.setsSuite(Integer.parseInt(req.getParameter("sSuite")));
				}
			
			dao.updatePack(dto);
			dao.updatePackPrice(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/pack/list.do?page="+page);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String page=req.getParameter("page");
		PackDAO dao = new PackDAO();
		
		try {
			int pkgNum = Integer.parseInt(req.getParameter("pkgNum"));
			PackDTO dto = dao.readPack(pkgNum);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/pack/list.do?page="+page);
				return;
			}
			
			if(!info.getUserId().equals("admin")) {
				resp.sendRedirect(cp+"/pack/list.do?page="+page);
				return;
			}
			
			FileManager.doFiledelete(pathname, dto.getImageFilename());
			FileManager.doFiledelete(pathname, dto.getThumbnail());
			dao.deletePack(pkgNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/pack/list.do?page="+page);
	}
	
}
