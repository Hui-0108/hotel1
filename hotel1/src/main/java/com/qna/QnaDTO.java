package com.qna;

public class QnaDTO {
	private int qNum;				// QnA 글번호
	private int listNum;
	private String ctg;				// QnA 카테고리
	private String nickname;		// 닉네임
	private String subject;			// 제목
	private String content;			// 내용
	private String qPwd;			// 글에 들어가기 위한 비밀번호
	private String email;			// 답변 알림을 받기 위한 이메일
	private String created;			// 글작성일
	
	
	private int groupNum;			// 답변형 게시판 만들기
	private int orderNo;
	private int depth;
	private int parent;
	
	public int getqNum() {
		return qNum;
	}
	public void setqNum(int qNum) {
		this.qNum = qNum;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public String getCtg() {
		return ctg;
	}
	public void setCtg(String ctg) {
		this.ctg = ctg;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getqPwd() {
		return qPwd;
	}
	public void setqPwd(String qPwd) {
		this.qPwd = qPwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public int getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
		
	
}