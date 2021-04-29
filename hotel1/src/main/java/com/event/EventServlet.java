package com.event;

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
@WebServlet("/event/*")
public class EventServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;
	 
	private String pathname;
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		HttpSession session = req.getSession();
		
		String root=session.getServletContext().getRealPath("/");
		pathname=root+"uploads"+File.separator+"event";
		
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
		EventDAO dao = new EventDAO();
		
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
		
		List<EventDTO> list = dao.listEvent(offset, rows);
		
		String listUrl = cp + "/event/list.do";
		String articleUrl = cp + "/event/article.do?page="+current_page;
		String paging=util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		
		forward(req, resp, "/WEB-INF/views/event/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/event/created.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		
		EventDAO dao = new EventDAO();
		
		try {
			EventDTO dto = new EventDTO();
			
			dto.setEventName(req.getParameter("eventName"));
			dto.setContent(req.getParameter("content"));
			dto.setStartDate(req.getParameter("startDate"));
			dto.setEndDate(req.getParameter("endDate"));
			dto.setsummary(req.getParameter("summary"));
			
			String filename = null;
			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if(map != null) {
				filename = map.get("saveFilename");
			}
			
			if(filename!=null) {
				dto.setImageFilename(filename);
				dao.insertEvent(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/event/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		String page=req.getParameter("page");
		EventDAO dao = new EventDAO();
		
		try {
			int eventNum = Integer.parseInt(req.getParameter("eventNum"));
			 
			EventDTO dto = dao.readEvent(eventNum);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/event/list.do?page="+page);
				return;
			}
			
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/event/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/event/list.do?page="+page);
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		EventDAO dao = new EventDAO();
		
		String page = req.getParameter("page");
		
		try {
			int eventNum=Integer.parseInt(req.getParameter("eventNum"));
			EventDTO dto = dao.readEvent(eventNum);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/event/list.do?page="+page);
				return;
			}
			
			if(!info.getUserId().equals("admin")) {
				resp.sendRedirect(cp+"/event/list.do?page="+page);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/event/created.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/event/list.do?page="+page);
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		EventDTO dto = new EventDTO();
		EventDAO dao = new EventDAO();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/event/list.do");
			return;
		}
		
		String page=req.getParameter("page");
		
		try {
			dto.setEventNum(Integer.parseInt(req.getParameter("eventNum")));
			dto.setEventName(req.getParameter("eventName"));
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
			dao.updateEvent(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/event/list.do?page="+page);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String page=req.getParameter("page");
		EventDAO dao = new EventDAO();
		
		try {
			int eventNum = Integer.parseInt(req.getParameter("eventNum"));
			
			EventDTO dto = dao.readEvent(eventNum);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/event/list.do?page="+page);
				return;
			}
			
			if(!info.getUserId().equals("admin")) {
				resp.sendRedirect(cp+"/event/list.do?page="+page);
				return;
			}
			
			FileManager.doFiledelete(pathname, dto.getImageFilename());
			
			dao.deleteEvent(eventNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/event/list.do?page="+page);
	}
}
