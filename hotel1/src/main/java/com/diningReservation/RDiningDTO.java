package com.diningReservation;

public class RDiningDTO {
	private String rodNum;
	private int dinNum;
	private String dinName;
	private int clientNum;
	private int guestCount;
	private String rsvDate; // 예약 날짜
	private String rsvTime; // 예약 시간
	private int seatType; // 테이블 타입 1이면 룸, 2면 테이블

	public String getRodNum() {
		return rodNum;
	}

	public void setRodNum(String rodNum) {
		this.rodNum = rodNum;
	}

	public int getDinNum() {
		return dinNum;
	}

	public void setDinNum(int dinNum) {
		this.dinNum = dinNum;
	}

	public String getDinName() {
		return dinName;
	}

	public void setDinName(String dinName) {
		this.dinName = dinName;
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

	public String getRsvDate() {
		return rsvDate;
	}

	public void setRsvDate(String rsvDate) {
		this.rsvDate = rsvDate;
	}

	public String getRsvTime() {
		return rsvTime;
	}

	public void setRsvTime(String rsvTime) {
		this.rsvTime = rsvTime;
	}

	public int getSeatType() {
		return seatType;
	}

	public void setSeatType(int seatType) {
		this.seatType = seatType;
	}

}
