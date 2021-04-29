package com.qna;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyUtil;

@WebServlet("/qna/*")
public class QnaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("created.do") != -1) {
			createdForm(req, resp);
		} else if(uri.indexOf("created_ok.do") != -1) {
			createdSubmit(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("reply.do") != -1) {
			replyForm(req, resp);
		} else if(uri.indexOf("reply_ok.do") != -1) {
			replySubmit(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
		
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MyUtil util=new MyUtil();
		QnaDAO dao=new QnaDAO();
		String cp=req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null)
			current_page = Integer.parseInt(page);
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "all";
			keyword = "";
		}
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}
		
		int dataCount;
		if (keyword.length() == 0) {
			dataCount = dao.dataCount();
		} else {
			dataCount = dao.dataCount(condition, keyword);
		}
		
		int rows = 10;
		int total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}
		
		int offset = (current_page - 1) * rows;
		if(offset < 0) offset = 0;
		
		List<QnaDTO> list;
		if (keyword.length() == 0)
			list = dao.listQnA(offset, rows);
		else
			list = dao.listQnA(offset, rows, condition, keyword);
		
		int listNum, n = 0;
		for(QnaDTO dto : list) {
			listNum = dataCount - (offset + n);
			dto.setListNum(listNum);
			n++;
		}
		
		String query = "";
		if (keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}
		
		String listUrl = cp + "/qna/list.do";
		String articleUrl = cp + "/qna/article.do?page=" + current_page;
		if (query.length() != 0) {
			listUrl += "?" + query;
			articleUrl += "&" + query;
		}
		
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		
		forward(req, resp, "/WEB-INF/views/qna/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/qna/created.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/qna/list.do");
			return;
		}
		
		QnaDAO dao = new QnaDAO();
		
		try {
			QnaDTO dto = new QnaDTO();
			dto.setCtg(req.getParameter("ctg"));
			dto.setNickname(req.getParameter("nickname"));
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			dto.setqPwd(req.getParameter("qPwd"));
			dto.setEmail(req.getParameter("email"));
			
			dao.insertQnA(dto, "created");	
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/qna/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String query = "page="+page;
		
		try {
			int qNum = Integer.parseInt(req.getParameter("qNum"));
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition==null) {
				condition="all";
				keyword="";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");
			
			if(keyword.length()!=0) {
				query+="&condition="+condition+
						"&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			QnaDTO dto=dao.readQnA(qNum);
			////////여기 ???
			/*
			 dao.updateHitCount(boardNum);
			BoardDTO dto=dao.readBoard(boardNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/board/list.do?"+query);
				return;
			}
			
			// dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			MyUtil util=new MyUtil();
			dto.setContent(util.htmlSymbols(dto.getContent()));
			
			BoardDTO preReadDto=dao.preReadBoard(dto.getGroupNum(),
					dto.getOrderNo(), condition, keyword);
			BoardDTO nextReadDto=dao.nextReadBoard(dto.getGroupNum(),
					dto.getOrderNo(), condition, keyword);
		
			req.setAttribute("dto", dto);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			 */
			/////////
			
			req.setAttribute("dto", dto);
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/qna/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		String page=req.getParameter("page");
		String query="page="+page;
		
		QnaDAO dao = new QnaDAO();
		
		try {
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition==null) {
				condition="subject";
				keyword="";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");
			if(keyword.length()!=0) {
				query+="&condition="+condition+
					     "&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			
			int qNum=Integer.parseInt(req.getParameter("qNum"));
			QnaDTO dto=dao.readQnA(qNum);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/qna/list.do?"+query);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/qna/created.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/qna/list.do");
			return;
		}
		
		QnaDAO dao = new QnaDAO();
		
		String page=req.getParameter("page");
		String query="page="+page;
		
		try {
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(keyword.length()!=0) {
				query+="&condition="+condition+
					     "&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			
			QnaDTO dto = new QnaDTO();
			
			dto.setqNum(Integer.parseInt(req.getParameter("qNum")));
			dto.setCtg(req.getParameter("ctg"));
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			dto.setEmail(req.getParameter("email"));
			
			dao.updateQnA(dto, dto.getNickname());	/////???
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}
	
	protected void replyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			int qNum = Integer.parseInt(req.getParameter("qNum"));
			
			QnaDTO dto = dao.readQnA(qNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/qna/list.do?page="+page);
				return;
			}
			
			String s="["+dto.getSubject()+"] 에 대한 답변입니다.\n";
			dto.setContent(s);
			
			req.setAttribute("mode", "reply");
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/qna/created.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?page="+page);
	}
	
	protected void replySubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/qna/list.do");
			return;
		}
		
		String page=req.getParameter("page");
		
		QnaDAO dao = new QnaDAO();
		
		try {
			QnaDTO dto = new QnaDTO();
			
			dto.setCtg(req.getParameter("ctg"));
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			dto.setEmail(req.getParameter("email"));
			
			dto.setGroupNum(Integer.parseInt(req.getParameter("groupNum")));
			dto.setOrderNo(Integer.parseInt(req.getParameter("orderNo")));
			dto.setDepth(Integer.parseInt(req.getParameter("depth")));
			dto.setParent(Integer.parseInt(req.getParameter("parent")));
			
			dao.insertQnA(dto, "reply");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?page="+page);	
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		QnaDAO dao = new QnaDAO();
		
		String page=req.getParameter("page");
		String query="page="+page;
		
		try {
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition==null) {
				condition="all";
				keyword="";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");
			if(keyword.length()!=0) {
				query+="&condition="+condition+
					     "&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			
			int qNum = Integer.parseInt(req.getParameter("qNum"));
			QnaDTO dto = dao.readQnA(qNum);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/qna/list.do?"+query);
				return;
			}
			
			dao.deleteQnA(qNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}
	
	
	
	
}
