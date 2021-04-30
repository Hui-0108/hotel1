package com.diningReserve;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.client.ClientDAO;
import com.client.ClientDTO;
import com.diningReservation.RDiningDAO;
import com.diningReservation.RDiningDTO;
import com.member.MemberDAO;
import com.member.MemberDTO;
import com.member.SessionInfo;
import com.roomReservation.RRoomDAO;
import com.roomReservation.RRoomDTO;
import com.util.MyServlet;

@WebServlet("/diningReserve/*")
public class DiningServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if(uri.indexOf("reservation.do") != -1) {
			reservation(req, resp);
		} else if (uri.indexOf("detail.do") != -1) {
			detail(req, resp);
		} else if(uri.indexOf("reserve.do") != -1) {
			reserveForm(req, resp);
		} else if(uri.indexOf("reserve_ok.do") != -1) {
			reserveSubmit(req, resp);
		} else if(uri.indexOf("result.do") != -1) {
			result(req,resp);
		} else if(uri.indexOf("login.do") != -1) {
			login(req,resp);
		} else if(uri.indexOf("login_ok.do") != -1) {
			loginSubmit(req, resp);
		} else if(uri.indexOf("info.do") != -1) {
			showInfo(req,resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
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
	
	protected void reserveForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String dinNum = req.getParameter("dinNum");
		req.setAttribute("dinNum", dinNum);
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		if(info != null) {
			String usetId = info.getUserId();
			MemberDAO mdao = new MemberDAO();
			MemberDTO mdto = mdao.readMember(usetId);
			req.setAttribute("mdto", mdto);
		}
		forward(req, resp, "/WEB-INF/views/diningReservation/reserve.jsp");
	}
	
	protected void reserveSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		String rodNum = "";
		ClientDAO cdao = new ClientDAO();
		ClientDTO cdto = new ClientDTO();
		RDiningDAO ddao = new RDiningDAO();
		RDiningDTO ddto = new RDiningDTO();
		try {
			int guestCount = Integer.parseInt(req.getParameter("guestCount"));
			String rsvDate = req.getParameter("rsvDate");
			String rsvTime = req.getParameter("time");
			int seatType = Integer.parseInt(req.getParameter("seatType"));
			int dinNum = Integer.parseInt(req.getParameter("dinNum"));
			
			String mode = req.getParameter("mode");
			String region = req.getParameter("region");
			String creditCorp = req.getParameter("creditCorp");
			String creditNum = req.getParameter("c1")+"-"+req.getParameter("c2")+"-"+req.getParameter("c3")+"-"+req.getParameter("c4");
			String creditMonth = req.getParameter("creditMonth");
			String creditYear = req.getParameter("creditYear");
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			String email = req.getParameter("email");
			String tel = req.getParameter("tel");
			
			int clientNum = 0;
			if(mode.equals("memNone")) {
				cdto.setFirstName(firstName);
				cdto.setLastName(lastName);
				cdto.setTel(tel);
				cdto.setEmail(email);
				cdto.setRegion(region);
				cdto.setCreditCorp(creditCorp);
				cdto.setCreditNum(creditNum);
				cdto.setCreditMonth(creditMonth);
				cdto.setCreditYear(creditYear);
				
				clientNum = cdao.insertClient(cdto);
			} else {
				HttpSession session = req.getSession();
				SessionInfo info = (SessionInfo)session.getAttribute("member");
				clientNum = info.getClientNum();
				cdto.setClientNum(clientNum);
				cdto.setRegion(region);
				cdto.setCreditCorp(creditCorp);
				cdto.setCreditNum(creditNum);
				cdto.setCreditMonth(creditMonth);
				cdto.setCreditYear(creditYear);
				
				cdao.updateExtraMember(cdto);
			}
			
			ddto.setClientNum(clientNum);
			ddto.setDinNum(dinNum);
			ddto.setGuestCount(guestCount);
			ddto.setRsvDate(rsvDate);
			ddto.setRsvTime(rsvTime);
			ddto.setSeatType(seatType);
			
			rodNum = ddao.insertDiningReservation(ddto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/diningReserve/result.do?rodNum="+rodNum);
	}
	
	protected void result(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RDiningDAO dao = new RDiningDAO();
		RDiningDTO dto = null;
		try {
			String rodNum = req.getParameter("rodNum");
			dto = dao.readDiningReservation(rodNum);
			req.setAttribute("dto", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/diningReservation/result.jsp");
	}
	
	protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		if(info!=null) {
			String userId = info.getUserId();
			
			RRoomDAO rdao = new RRoomDAO();
			List<RRoomDTO> rorList = rdao.listByUserId(userId);
			RDiningDAO ddao = new RDiningDAO();
			List<RDiningDTO> rodList = ddao.listByUserId(userId);
			
			req.setAttribute("rorList", rorList);
			req.setAttribute("rodList", rodList);
			
			forward(req, resp, "/WEB-INF/views/reservationCheck/reserveCheck.jsp");
			return;
		}
		forward(req, resp, "/WEB-INF/views/reservationCheck/reserveNumCheck.jsp");
	}
	
	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		String rodNum="";
		RDiningDAO dao = new RDiningDAO();
		rodNum = req.getParameter("num");
		if(dao.readDiningReservation(rodNum)==null) {
			req.setAttribute("message", "잘못된 다이닝 예약번호입니다.");
			forward(req, resp, "/WEB-INF/views/reservationCheck/reserveNumCheck.jsp");
			return;
		}
		
		resp.sendRedirect(cp+"/diningReserve/info.do?rodNum="+rodNum);
	}
	
	protected void showInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RDiningDAO dao = new RDiningDAO();
		String rodNum = req.getParameter("rodNum");
		RDiningDTO dto = dao.readDiningReservation(rodNum);
		req.setAttribute("dto", dto);
		forward(req, resp, "/WEB-INF/views/diningReservation/reserveCheck.jsp");
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RDiningDAO dao = new RDiningDAO();
		ClientDAO cdao = new ClientDAO();
		String cp = req.getContextPath();
		int clientNum = 0;
		String rodNum = req.getParameter("rodNum");
		
		try {
			dao.deleteDiningReservation(rodNum);
			
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			clientNum = dao.findClientNumByRod(rodNum);
			if(info==null) {
				cdao.cancelClient(clientNum);
			} else {
				cdao.cancelMember(clientNum);
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		resp.sendRedirect(cp);
	}
}
