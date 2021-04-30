package com.roomReservation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.room.RoomDAO;
import com.room.RoomDTO;
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
		} else if(uri.indexOf("detail.do") != -1) {
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
	
	protected void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String classNum = req.getParameter("classNum");
		String checkIn = req.getParameter("checkIn");
		String checkOut = req.getParameter("checkOut");
		String guestCount = req.getParameter("guestCount");
		String nights = req.getParameter("nights");
		req.setAttribute("classNum", classNum);
		req.setAttribute("checkIn", checkIn);
		req.setAttribute("checkOut", checkOut);
		req.setAttribute("guestCount", guestCount);
		req.setAttribute("nights", nights);
		String num = req.getParameter("num");
		forward(req, resp, "/WEB-INF/views/roomReserve/de"+num+".jsp");
	
	}
	
	protected void reserveForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String checkIn = req.getParameter("checkIn");
		String checkOut = req.getParameter("checkOut");
		String guestCount = req.getParameter("guestCount");
		String classNum = req.getParameter("classNum");
		String nights = req.getParameter("nights");
		
		req.setAttribute("checkIn", checkIn);
		req.setAttribute("checkOut", checkOut);
		req.setAttribute("guestCount", guestCount);
		req.setAttribute("classNum", classNum);
		req.setAttribute("nights", nights);
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		if(info != null) {
			String usetId = info.getUserId();
			MemberDAO mdao = new MemberDAO();
			MemberDTO mdto = mdao.readMember(usetId);
			req.setAttribute("mdto", mdto);
		}
		
		forward(req, resp, "/WEB-INF/views/roomReservation/reserve.jsp");
	}
	
	protected void reserveSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String rorNum = "";
		String cp = req.getContextPath();
		try {
			String checkIn = req.getParameter("checkIn");
			String checkOut = req.getParameter("checkOut");
			int guestCount = Integer.parseInt(req.getParameter("guestCount"));
			int classNum = Integer.parseInt(req.getParameter("classNum"));
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
			int nights = Integer.parseInt(req.getParameter("nights"));
			
			// 방번호 가져오기
			RoomDAO rdao = new RoomDAO();
			int roomNum = rdao.getEmptyRoomNum(classNum, checkIn, checkOut);
			if(roomNum==0) {
				resp.sendRedirect(cp+"/rr/result.do?rorNum=0");
				return;
			}
			
			// 회원/비회원 client 테이블에 데이터 입력
			ClientDAO cdao = new ClientDAO();
			ClientDTO cdto = new ClientDTO();
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
			
			// 가격 = (하루 당 가격) * (숙박일)
			RoomDTO rdto = rdao.readRoom(roomNum);
			int price = rdto.getPrice() * nights;
			
			// 예약 테이블에 저장
			RRoomDAO rrdao = new RRoomDAO();
			RRoomDTO rrdto= new RRoomDTO();
			rrdto.setCheckIn(checkIn);
			rrdto.setCheckOut(checkOut);
			rrdto.setClassNum(classNum);
			rrdto.setGuestCount(guestCount);
			rrdto.setPrice(price);
			rrdto.setRoomNum(roomNum);
			rrdto.setClientNum(clientNum);
			
			rorNum = rrdao.insertRoomReservation(rrdto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/rr/result.do?rorNum="+rorNum);
	}
	
	protected void result(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RRoomDAO dao = new RRoomDAO();
		RRoomDTO dto = new RRoomDTO();
		try {
			String rorNum = req.getParameter("rorNum");
			dto = dao.readRoomReservation(rorNum);
			req.setAttribute("dto", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/roomReservation/result.jsp");
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
		String rorNum="";
		RRoomDAO dao = new RRoomDAO();
		rorNum = req.getParameter("num");

		if(dao.readRoomReservation(rorNum)==null) {
			req.setAttribute("message", "잘못된 룸 예약번호입니다.");
			forward(req, resp, "/WEB-INF/views/reservationCheck/reserveNumCheck.jsp");
			return;
		}
		
		resp.sendRedirect(cp+"/rr/info.do?rorNum="+rorNum);
	}
	
	protected void showInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RRoomDAO dao = new RRoomDAO();
		String rorNum = req.getParameter("rorNum");
		RRoomDTO dto = dao.readRoomReservation(rorNum);
		req.setAttribute("dto", dto);
		forward(req, resp, "/WEB-INF/views/roomReservation/reserveCheck.jsp");
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RRoomDAO dao = new RRoomDAO();
		ClientDAO cdao = new ClientDAO();
		String cp = req.getContextPath();
		int clientNum = 0;
		String rorNum = req.getParameter("rorNum");
		
		try {
			dao.deleteRoomReservation(rorNum);
			
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			clientNum = dao.findClientNumByRor(rorNum);
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
