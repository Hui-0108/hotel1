package com.qna;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/sbbs/*")
public class QnaServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		// 세션 정보
		HttpSession session = req.getSession();

		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "qna";

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
		} else if(uri.indexOf("deleteFile.do")!=-1) {
			deleteFile(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		} else if(uri.indexOf("download.do")!=-1) {
			download(req, resp);
		}
	}

	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MyUtil util = new MyUtil();
		QnaDAO dao = new QnaDAO();
		String cp = req.getContextPath();

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
		if (offset < 0)
			offset = 0;

		List<QnaDTO> list;
		if (keyword.length() == 0)
			list = dao.listQnA(offset, rows);
		else
			list = dao.listQnA(offset, rows, condition, keyword);

		int listNum, n = 0;
		for (QnaDTO dto : list) {
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

	private void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/qna/created.jsp");
	}

	private void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/qna/list.do");
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

			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if (map != null) {
				String saveFilename = map.get("saveFilename");
				String originalFilename = map.get("originalFilename");
				long size = p.getSize();
				dto.setSaveFilename(saveFilename);
				dto.setOriginalFilename(originalFilename);
				dto.setFileSize(size);
			}

			dao.insertQnA(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/sbbs/list.do");
	}

	private void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		QnaDAO dao = new QnaDAO();
		MyUtil util = new MyUtil();
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			int qNum = Integer.parseInt(req.getParameter("qNum"));

			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			QnaDTO dto = dao.readQnA(qNum);
			if (dto == null) { // 게시물이 없으면 다시 리스트로
				resp.sendRedirect(cp + "/qna/list.do?" + query);
				return;
			}
			dto.setContent(util.htmlSymbols(dto.getContent()));

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);

			forward(req, resp, "/WEB-INF/views/qna/article.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/qna/list.do?" + query);
	}

	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();

		String page = req.getParameter("page");

		QnaDAO dao = new QnaDAO();

		try {
			int qNum = Integer.parseInt(req.getParameter("qNum"));
			QnaDTO dto = dao.readQnA(qNum);

			if (dto == null) {
				resp.sendRedirect(cp + "/qna/list.do?page=" + page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/qna/created.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/qna/list.do?page=" + page);
	}

	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		String page = req.getParameter("page");

		QnaDAO dao = new QnaDAO();

		try {
			if (req.getMethod().equalsIgnoreCase("GET")) {
				resp.sendRedirect(cp + "/qna/list.do");
				return;
			}
			QnaDTO dto = new QnaDTO();

			dto.setqNum(Integer.parseInt(req.getParameter("qNum")));
			dto.setCtg(req.getParameter("ctg"));
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			dto.setEmail(req.getParameter("email"));
			dto.setSaveFilename(req.getParameter("saveFilename"));
			dto.setOriginalFilename(req.getParameter("originalFilename"));
			dto.setFileSize(Long.parseLong(req.getParameter("fileSize")));

			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if(map != null) {
				if(req.getParameter("saveFilename").length()!=0) {
					// 기존파일 삭제
					FileManager.doFiledelete(pathname, req.getParameter("saveFilename"));
				}
				
				String saveFilename = map.get("saveFilename");
				String originalFilename = map.get("originalFilename");
				long size = p.getSize();
				dto.setSaveFilename(saveFilename);
		    	dto.setOriginalFilename(originalFilename);
		    	dto.setFileSize(size);
			}
			
			dao.updateQnA(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/sbbs/list.do?page="+page);
	}

	private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
			
			FileManager.doFiledelete(pathname, dto.getSaveFilename());
			
			dto.setOriginalFilename("");
			dto.setSaveFilename("");
			dto.setFileSize(0);
			dao.updateQnA(dto);
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			
			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/qna/created.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/qna/list.do?page="+page);
		
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		QnaDAO dao = new QnaDAO();
		
		String page=req.getParameter("page");
		String query="page="+page;
		
		try {
			int qNum = Integer.parseInt(req.getParameter("qNum"));
			String condition=req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			if(condition==null) {
				condition="all";
				keyword="";
			}
			keyword=URLDecoder.decode(keyword, "utf-8");
			
			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
			}
			
			QnaDTO dto = dao.readQnA(qNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/qna/list.do?"+query);
				return;
			}
			if(dto.getSaveFilename()!=null && dto.getSaveFilename().length()!=0) {
				FileManager.doFiledelete(pathname, dto.getSaveFilename());
			}
			
			dao.deleteQnA(qNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}

	private void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		boolean b = false;

		try {
			int qNum = Integer.parseInt(req.getParameter("qNum"));

			QnaDTO dto = dao.readQnA(qNum);
			if (dto != null) {
				b = FileManager.doFiledownload(dto.getSaveFilename(), dto.getOriginalFilename(), pathname, resp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!b) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>alert('파일다운로드가 실패 했습니다.');history.back();</script>");
		}

	}

}
