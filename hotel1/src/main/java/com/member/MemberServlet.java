package com.member;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyServlet;

@WebServlet("/member/*")
public class MemberServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
	
		String uri = req.getRequestURI();

		// uri에 따른 작업 구분		
		if(uri.indexOf("login.do") != -1) {
			loginForm(req, resp);
		}else if(uri.indexOf("login_ok.do") != -1) {
			loginSubmit(req, resp);
		}else if(uri.indexOf("logout.do") != -1) {
			logout(req, resp);
		}else if(uri.indexOf("member.do") != -1) {
			memberForm(req, resp);
		}else if(uri.indexOf("member_ok.do") != -1) {
			memberSubmit(req, resp);
		}else if(uri.indexOf("pwd.do") != -1) {
			pwdForm(req, resp);
		}else if(uri.indexOf("pwd_ok.do") != -1) {
			pwdSubmit(req, resp);
		}else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		}

	}
	
	//로그인 폼
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
		
	}
	
	//로그인 처리
	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 세션객체. 세션 정보는 서버에 저장(로그인정보, 권한등을저장)
		//그사람의 정보가 db에 존재하는지 없는지 확인하고 있으면 저장하기 //아이디, 고객번호, 이름	(성,이름)	
		HttpSession session = req.getSession();
		
		MemberDAO dao = new MemberDAO();
		
		String cp = req.getContextPath();
		
		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");

		MemberDTO dto = dao.readMember(userId); //여기에 정보 저장 dto가 null이라면 없는 정보
		
		
		if(dto!=null) {
			if(userPwd.equals(dto.getUserPwd())) {
				//로그인 성공 - 로그인 정보를 서버에 저장			

				//1) 세션 유지 시간 20분 설정 (톰켓은 기본 30분으로 설정되어 있음)
				session.setMaxInactiveInterval(20*60);

				//2) 세션에 저장 할 내용
				SessionInfo info = new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setClientNum(dto.getClientNum());
				info.setFirstName(dto.getFirstName());
				info.setLastName(dto.getLastName());
				
				//3) 세션에 member라는 이름으로 info 객체를 저장 
				session.setAttribute("member", info); // 아이디, 고객번호, 이름(성,이름)

				//4) 로그인 성공 후 메인 화면으로 리다이렉트				
				resp.sendRedirect(cp);
				return;
				
			}
		}

		//아이디 또는 패스워드가 잘못 된 경우 다시 로그인 폼으로 
		String msg = "아이디 또는 패스워드가 일치하지 않습니다.";
		req.setAttribute("message", msg);
		
		forward(req, resp, "/WEB-INF/views/member/login.jsp");

	}
	
	//로그아웃
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		//(1)세션에 저장된 로그인 정보를 지운다.		
		session.removeAttribute("member");
		
		//(2)세션에 저장된 모든 정보를 지우고 세션을 초기화 한다.		
		session.invalidate();  //세션 시간 20분 ->30분(세션유지 기본값)으로 돌아옴

		//3) 로그아웃 성공 후 메인 화면으로 리다이렉트			
		resp.sendRedirect(cp);
	}
	
	//회원가입 폼
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");
		
		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}

	//회원가입 처리
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		String message = "";
		
		try {
			MemberDTO dto = new MemberDTO();

			dto.setUserId(req.getParameter("userId"));
			dto.setUserPwd(req.getParameter("userPwd"));
			
			String birth=req.getParameter("birth").replaceAll("(\\.|\\-|\\/)", "");
			dto.setBirth(birth);
			
			dto.setZip(req.getParameter("zip"));
			dto.setAddr1(req.getParameter("addr1"));
			dto.setAddr2(req.getParameter("addr2"));
			
			dto.setFirstName(req.getParameter("firstName"));
			dto.setLastName(req.getParameter("lastName"));
			
			String email1 = req.getParameter("email1");
			String email2 = req.getParameter("email2");			
			dto.setEmail(email1+"@"+email2);
			
			String tel1 = req.getParameter("tel1");
			String tel2 = req.getParameter("tel2");
			String tel3 = req.getParameter("tel3");
			dto.setTel(tel1+"-"+tel2+"-"+tel3);
			
			dao.insertMember(dto);
			String cp = req.getContextPath();
			resp.sendRedirect(cp);
			return;						
		}catch (SQLException e) {
			if(e.getErrorCode()==1)
				message = "아이디 중복으로 회원 가입이 실패 했습니다.";
			else if(e.getErrorCode()==1400)
				message = "필수 사항을 입력하지 않았습니다.";
			else if(e.getErrorCode()==1861)
				message = "날짜 형식이 일치하지 않습니다.";
			else
				message = "회원 가입에 실패 했습니다.";
			
		}catch (Exception e) {
			message = "가입 신청에 실패했습니다.";
			e.printStackTrace();
		}
		
		req.setAttribute("title", "회원가입");
		req.setAttribute("mode", "member");
		req.setAttribute("message", message);
		forward(req, resp, "/WEB-INF/views/member/member.jsp");	
	}
	
	
	//패스워드 확인 폼
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sesion = req.getSession();
		String cp = req.getContextPath();
		
		SessionInfo info = (SessionInfo)sesion.getAttribute("member");
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String mode = req.getParameter("mode");
		if(mode.equals("update")) {
			req.setAttribute("title", "회원정보 수정");
		} else {
			req.setAttribute("title", "회원 탈퇴");
		}
		req.setAttribute("mode", mode);
		
		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
	}
	
	//패스워드 확인
	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		MemberDAO dao = new MemberDAO();
		
		try {
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			if(info==null) {
				resp.sendRedirect(cp+"/member/login.do");
				return;
			}
			
			MemberDTO dto = dao.readMember(info.getUserId());
			if(dto==null) {
				session.invalidate();
				resp.sendRedirect(cp);
				return;
			}
			String userPwd = req.getParameter("userPwd");
			String mode = req.getParameter("mode");
			if(! dto.getUserPwd().equals(userPwd)) {
				if(mode.equals("update")) {
					req.setAttribute("title", "회원정보 수정");
				}else {
					req.setAttribute("title", "회원 탈퇴");
				}
				
				req.setAttribute("mode", mode);
				req.setAttribute("message","<span style='color:red;'>비밀번호가 일치하지 않습니다.</span>" );
				forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
				return;
			}
			
			if(mode.equals("delete")) {
		
					dao.deleteMember(info.getUserId());
					
					session.removeAttribute("member");
					session.invalidate();
					
					resp.sendRedirect(cp);
					return;

			}
			
			req.setAttribute("title", "회원 정보 수정");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp);
	}
	
	//회원정보 수정 완료
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		MemberDAO dao = new MemberDAO();
		
		try {
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			if(info==null) { //로그아웃 된 경우
				resp.sendRedirect(cp+"/member/login.do");
				return;
			}
			
			MemberDTO dto = new MemberDTO();

			dto.setUserId(req.getParameter("userId"));
			dto.setUserPwd(req.getParameter("userPwd"));
			dto.setFirstName(req.getParameter("firstName"));
			dto.setLastName(req.getParameter("lastName"));

			String birth=req.getParameter("birth").replaceAll("(\\.|\\-|\\/)", "");
			dto.setBirth(birth);
			
			String email1 = req.getParameter("email1");
			String email2 = req.getParameter("email2");
			dto.setEmail(email1 + "@" + email2);

			String tel1 = req.getParameter("tel1");
			String tel2 = req.getParameter("tel2");
			String tel3 = req.getParameter("tel3");
			dto.setTel(tel1 + "-" + tel2 + "-" + tel3);
			dto.setZip(req.getParameter("zip"));
			dto.setAddr1(req.getParameter("addr1"));
			dto.setAddr2(req.getParameter("addr2"));

			dao.updateMember(dto);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp);
	}

}
