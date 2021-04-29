package com.diningReserve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/diningReserve/*")
public class DiningServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		
		if(uri.indexOf("reservation") != -1) {
			// 예약화면
			reservation(req, resp);
		} else if(uri.indexOf("detail") != -1) {
			// 상세화면
			detail(req, resp);
		}
		
	}
	
	protected void reservation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String reserveNum =  req.getParameter("reserveNum");
		if(reserveNum == null) {
			reserveNum = "1";
		}
		
		req.setAttribute("reserveNum", reserveNum);
		forward(req, resp, "/WEB-INF/views/diningReserve/reservation.jsp");
	}

	protected void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String reserveNum = req.getParameter("reserveNum");
		String path = "/WEB-INF/views/diningReserve/detail"+reserveNum+".jsp";
		
		forward(req, resp, path);
	}

}
