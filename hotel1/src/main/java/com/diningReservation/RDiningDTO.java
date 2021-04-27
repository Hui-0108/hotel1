package com.diningReservation;

public class RDiningDTO {
	private int rodNum;
	private int dinNum;
	private int clientNum;
	private int guestCount;
	private String date; // 예약 날짜
	private String time; // 예약 시간
	private int tableType; // 테이블 타입 1이면 룸, 2면 테이블

	public int getRodNum() {
		return rodNum;
	}

	public void setRodNum(int rodNum) {
		this.rodNum = rodNum;
	}

	public int getDinNum() {
		return dinNum;
	}

	public void setDinNum(int dinNum) {
		this.dinNum = dinNum;
	}

	public int getClientNum() {
		return clientNum;
	}

	public void setClientNum(int clientNum) {
		this.clientNum = clientNum;
	}

	public int getGuestCount() {
		return guestCount;
	}

	public void setGuestCount(int guestCount) {
		this.guestCount = guestCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getTableType() {
		return tableType;
	}

	public void setTableType(int tableType) {
		this.tableType = tableType;
	}

}
