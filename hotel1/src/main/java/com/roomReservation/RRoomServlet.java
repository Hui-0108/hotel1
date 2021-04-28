package com.roomReservation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/rr/*")
public class RRoomServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("reserve.do") != -1) {
			reserveForm(req, resp);
		} else if(uri.indexOf("reserve_ok.do") != -1) {
			reserveSubmit(req, resp);
		} else if(uri.indexOf("info.do") != -1) {
			
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String checkIn = req.getParameter("checkIn");
		String checkOut = req.getParameter("checkOut");
		int guestCount = 0;
		try {
			
			if(req.getParameter("guestCount") == null) {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				checkIn = sdf.format(cal.getTime());
				cal.add(Calendar.DATE, 1);
				checkOut = sdf.format(cal.getTime());
				guestCount = 2;
			} else
				guestCount = Integer.parseInt(req.getParameter("guestCount"));
			
			req.setAttribute("checkIn", checkIn);
			req.setAttribute("checkOut", checkOut);
			req.setAttribute("guestCount", guestCount);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/roomReservation/list.jsp");
	}
	
	protected void reserveForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String checkIn = req.getParameter("checkIn");
		String checkOut = req.getParameter("checkOut");
		String guestCount = req.getParameter("guestCount");
		String classNum = req.getParameter("classNum");
		
		forward(req, resp, "/WEB-INF/views/roomReservation/reserve.jsp");
	}
	
	protected void reserveSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	}

}
